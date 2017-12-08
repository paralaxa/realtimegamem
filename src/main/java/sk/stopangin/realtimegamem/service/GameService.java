package sk.stopangin.realtimegamem.service;

import io.swagger.annotations.Api;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sk.stopangin.realtimegamem.board.ActionFieldInserter;
import sk.stopangin.realtimegamem.board.Board;
import sk.stopangin.realtimegamem.board.RectangularBoard;
import sk.stopangin.realtimegamem.board.SimpleGameFieldsGenerator;
import sk.stopangin.realtimegamem.field.ActionData;
import sk.stopangin.realtimegamem.field.Field;
import sk.stopangin.realtimegamem.field.FieldsComparator;
import sk.stopangin.realtimegamem.game.Game;
import sk.stopangin.realtimegamem.game.GameException;
import sk.stopangin.realtimegamem.movement.MovementStatus;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
import sk.stopangin.realtimegamem.player.Player;
import sk.stopangin.realtimegamem.repository.QuestionsRepository;
import sk.stopangin.realtimegamem.response.Hint;
import sk.stopangin.realtimegamem.response.ResponseWithGuidance;
import sk.stopangin.realtimegamem.to.ActionFieldDto;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//STOPSHIP - how will be player notified, that he was kicked out of the field?
@RestController
@RequestMapping("game")
@Api(value = "game", description = "Operations for controlling the game flow.")
public class GameService {

    public static final String TOPIC_BOARD = "/topic/board";
    public static final String TOPIC_STATUS = "/topic/status";
    public static final String USER_1 = "IVAN.HROZNY_1";

    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private MapperFacade mapperFacade;
    private Game game;
    private static final FieldsComparator fc = new FieldsComparator();
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    @PostMapping("create")
    @Secured({"ROLE_ADMIN"})
    public List<ActionFieldDto> createGame() {
        game = new Game();
        Set<Player> players = createFirstPlayerForGame();
        SimpleGameFieldsGenerator sgf = new SimpleGameFieldsGenerator(20);
        Set<Field<TwoDimensionalCoordinatesData>> fields = sgf.generateFields();
        Board board = new RectangularBoard(fields);
        ActionFieldInserter afi = new ActionFieldInserter(board, questionsRepository);
        game.startGame(board, players, afi);
        game.setMessagingTemplate(messagingTemplate);
        messagingTemplate.convertAndSend(TOPIC_BOARD, getBoardFields());
        messagingTemplate.convertAndSend(TOPIC_STATUS, getPlayers());
        return getBoardFields();
    }

    private Set<Player> createFirstPlayerForGame() {
        Set<Player> players = new HashSet<>();
        Player p1 = new Player();
        p1.setName(USER_1);
        p1.setId(getUserIdFromUsername(USER_1));
        p1.setSwordsCount(100);
        players.add(p1);
        return players;
    }

    @PostMapping("/players/join")
    public ResponseWithGuidance joinPlayer() {
        validateGameStat();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Player player = new Player();
        player.setId(getUserIdFromUsername(username));
        player.setName(username);
        try {
            writeLock.lock();
            game.joinPlayer(player);
            messagingTemplate.convertAndSend(TOPIC_BOARD, getBoardFields());
            messagingTemplate.convertAndSend(TOPIC_STATUS, getPlayers());
        } finally {
            writeLock.unlock();
        }
        return ResponseWithGuidance.builder().hint(Hint.USE_MOVE_ENDPOINT).build();
    }

    @GetMapping("/players")
    public Set<Player> getPlayers() {
        validateGameStat();
        try {
            readLock.lock();
           return game.getPlayers();
        } finally {
            readLock.unlock();
        }
    }

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable("playerId") Long playerId) {
        validateGameStat();
        try {
            readLock.lock();
            return game.getPlayerById(playerId);
        } finally {
            readLock.unlock();
        }
    }

    @PostMapping("bewegen")
    public ResponseWithGuidance move(@RequestBody TwoDimensionalCoordinatesData newPosition) {
        validateGameStat();
        try {
            writeLock.lock();
            MovementStatus movementStatus = game.move(getUserId(), newPosition);
            messagingTemplate.convertAndSend(TOPIC_BOARD, getBoardFields());
            if (movementStatus == MovementStatus.ACTION_REQUIRED || movementStatus == MovementStatus.ACTION_POSSIBLE) {
                return ResponseWithGuidance.builder().message(movementStatus).hint(Hint.USE_ACTION_ENDPOINTS)
                        .build();
            }
            return ResponseWithGuidance.builder().message(movementStatus)
                    .build();
        } finally {
            writeLock.unlock();
        }
    }

    @GetMapping("board")
    public List<ActionFieldDto> getBoardFields() {
        validateGameStat();
        try {
            readLock.lock();
            Set<Field<TwoDimensionalCoordinatesData>> fields = game.getBoard().getFields();
            List<Field<TwoDimensionalCoordinatesData>> fieldArrayList = new ArrayList<>(fields);
            Collections.sort(fieldArrayList, fc);
            return mapperFacade.mapAsList(fieldArrayList, ActionFieldDto.class);
        } finally {
            readLock.unlock();
        }
    }

    @GetMapping("akcija")
    public ActionData getActionData() {
        validateGameStat();
        try {
            readLock.lock();
            return game.getActionData(getUserId());
        } finally {
            readLock.unlock();
        }
    }

    @PostMapping("akcija")
    public Integer commitAction(@RequestBody String actionData) {
        validateGameStat();
        try {
            writeLock.lock();
            Integer actionScore = game.commitAction(getUserId(), actionData);
            messagingTemplate.convertAndSend(TOPIC_BOARD, getBoardFields());
            messagingTemplate.convertAndSend(TOPIC_STATUS, getPlayers());
            return actionScore;
        } finally {
            writeLock.unlock();
        }
    }

    private void validateGameStat() {
        if (game == null) {
            throw new GameException("Game not initialized, create game first.");
        }
    }

    private Long getUserId() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return getUserIdFromUsername(username);
    }

    private Long getUserIdFromUsername(String username) {
        if (StringUtils.isEmpty(username) || !username.contains("_")) {
            throw new GameException("Access denied. Invalid user, please contact support.");
        }
        return Long.valueOf(username.split("_")[1]);
    }
}
