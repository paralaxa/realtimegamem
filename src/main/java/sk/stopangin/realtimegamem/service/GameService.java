package sk.stopangin.realtimegamem.service;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
import sk.stopangin.realtimegamem.player.Player;
import sk.stopangin.realtimegamem.repository.InMemoryQuestionsRepositoryImpl;
import sk.stopangin.realtimegamem.repository.QuestionsRepository;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//todo movement left right upper left, lower right...
//todo join player
//todo return movement for move method
//todo webscoket
//todo questions repository
//todo swords generator (2 in advance)
//STOPSHIP - how will be player notified, that he was kicked out of the field?
@RestController
@RequestMapping("game")
@Api(value = "game", description = "Operations for controlling the game flow.")
public class GameService {
    @Autowired
    private QuestionsRepository questionsRepository;

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
        return getBoardFields();
    }


    @PostMapping("move/{playerId}")
    public void move(@PathVariable("playerId") Long playerId, @RequestBody TwoDimensionalCoordinatesData newPosition) {
        validateGameStat();
        writeLock.lock();
        game.move(playerId, newPosition);
        writeLock.unlock();
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
        return game.getActionData(playerId);
    }

    @PostMapping("action/{playerId}")
    public Integer commitAction(@PathVariable("playerId") Long playerId, @RequestBody String actionData) {
        return game.commitAction(playerId, actionData);
    }

    private void validateGameStat() {
        if (game == null) {
            throw new GameException("Game not initialized, create game first.");
        }
    }
}
