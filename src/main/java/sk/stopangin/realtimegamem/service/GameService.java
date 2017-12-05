package sk.stopangin.realtimegamem.service;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import sk.stopangin.realtimegamem.board.Board;
import sk.stopangin.realtimegamem.board.RectangularBoard;
import sk.stopangin.realtimegamem.board.SimpleGameFieldsGenerator;
import sk.stopangin.realtimegamem.field.Field;
import sk.stopangin.realtimegamem.field.FieldsComparator;
import sk.stopangin.realtimegamem.game.Game;
import sk.stopangin.realtimegamem.game.GameException;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
import sk.stopangin.realtimegamem.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
//todo movement left right upper left, lower right...
//todo questions repository
//todo swords generator (2 in advance)
@RestController
@RequestMapping("game")
@Api(value = "game", description = "Operations for controlling the game flow.")
public class GameService {
    private Game game;
    private static final FieldsComparator fc = new FieldsComparator();

    @PostMapping("create")
    public List<Field<TwoDimensionalCoordinatesData>> createGame(@RequestBody Set<Player> players) {
        game = new Game();
        SimpleGameFieldsGenerator sgf = new SimpleGameFieldsGenerator(20, null);
        Set<Field<TwoDimensionalCoordinatesData>> fields = sgf.generateFields();
        Board board = new RectangularBoard(fields);
        game.startGame(board, players);
        return getBoardFields();
    }



    @PostMapping("move")
    public void move(@RequestBody Long playerId, @RequestBody TwoDimensionalCoordinatesData newPosition) {
        validateGameStat();
        game.move(playerId, newPosition);
    }

    @GetMapping("board")
    public List<Field<TwoDimensionalCoordinatesData>> getBoardFields() {
        validateGameStat();
        Set<Field<TwoDimensionalCoordinatesData>> fields = game.getBoard().getFields();
        List<Field<TwoDimensionalCoordinatesData>> fieldArrayList = new ArrayList<>(fields);
        Collections.sort(fieldArrayList, fc);
        return fieldArrayList;
    }

    private void validateGameStat() {
        if (game == null) {
            throw new GameException("Game not initialized, create game first.");
        }
    }
}
