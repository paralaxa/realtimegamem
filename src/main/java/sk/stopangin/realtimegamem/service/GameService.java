package sk.stopangin.realtimegamem.service;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//STOPSHIP - how will be player notified, that he was kicked out of the field?
@RestController
@RequestMapping("game")
@Api(value = "game", description = "Operations for controlling the game flow.")
public class GameService {
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    private Game game;
    private static final FieldsComparator fc = new FieldsComparator();
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    @PostMapping("create")
    public List<Field<TwoDimensionalCoordinatesData>> createGame(@RequestBody Set<Player> players) {
        game = new Game();
        SimpleGameFieldsGenerator sgf = new SimpleGameFieldsGenerator(20);
        Set<Field<TwoDimensionalCoordinatesData>> fields = sgf.generateFields();
        Board board = new RectangularBoard(fields);
        ActionFieldInserter afi = new ActionFieldInserter(board, questionsRepository);
        game.startGame(board, players, afi);
        messagingTemplate.convertAndSend("/topic/board", getBoardFields());
        return getBoardFields();
    }

    @PostMapping("/player/join")
    public void joinPlayer(@RequestBody Player player) {
        validateGameStat();
        writeLock.lock();
        game.joinPlayer(player);
        messagingTemplate.convertAndSend("/topic/board", getBoardFields());
        writeLock.unlock();
    }

    @PostMapping("move/{playerId}")
    public MovementStatus move(@PathVariable("playerId") Long playerId, @RequestBody TwoDimensionalCoordinatesData newPosition) {
        validateGameStat();
        writeLock.lock();
        MovementStatus movementStatus = game.move(playerId, newPosition);
        messagingTemplate.convertAndSend("/topic/board", getBoardFields());
        writeLock.unlock();
        return movementStatus;
    }

    @GetMapping("board")
    public List<Field<TwoDimensionalCoordinatesData>> getBoardFields() {
        validateGameStat();
        readLock.lock();
        Set<Field<TwoDimensionalCoordinatesData>> fields = game.getBoard().getFields();
        List<Field<TwoDimensionalCoordinatesData>> fieldArrayList = new ArrayList<>(fields);
        Collections.sort(fieldArrayList, fc);
        readLock.unlock();
        return fieldArrayList;
    }

    @GetMapping("action/{playerId}")
    public ActionData getActionData(@PathVariable("playerId") Long playerId) {
        validateGameStat();
        readLock.lock();
        ActionData actionData = game.getActionData(playerId);
        readLock.unlock();
        return actionData;
    }

    @PostMapping("action/{playerId}")
    public Integer commitAction(@PathVariable("playerId") Long playerId, @RequestBody String actionData) {
        validateGameStat();
        writeLock.lock();
        Integer actionScore = game.commitAction(playerId, actionData);
        messagingTemplate.convertAndSend("/topic/board", getBoardFields());
        writeLock.unlock();
        return actionScore;
    }

    private void validateGameStat() {
        if (game == null) {
            throw new GameException("Game not initialized, create game first.");
        }
    }
}
