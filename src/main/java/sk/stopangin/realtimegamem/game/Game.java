package sk.stopangin.realtimegamem.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.stopangin.realtimegamem.board.Board;
import sk.stopangin.realtimegamem.board.RectangularBoard;
import sk.stopangin.realtimegamem.board.SimpleGameFieldsGenerator;
import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;
import sk.stopangin.realtimegamem.field.Field;
import sk.stopangin.realtimegamem.field.FieldsComparator;
import sk.stopangin.realtimegamem.movement.Movement;
import sk.stopangin.realtimegamem.movement.MovementStatus;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
import sk.stopangin.realtimegamem.piece.AnyDirectionTwoDimensionalMovingPiece;
import sk.stopangin.realtimegamem.piece.Piece;
import sk.stopangin.realtimegamem.player.Player;

import java.util.*;

// TODO GENERIFY!
public class Game extends BaseIdentifiableEntity {
    private static final Logger log = LoggerFactory.getLogger(Game.class);

    private Board board;
    private Set<Player> players = new HashSet<>();

    public Board startGame(Board board, Set<Player> players) {
        this.board = board;
        this.players = players;
        enrichPlayersWithPieces(players);
        return board;
    }

    public void joinPlayer(Player player) {
        players.add(player);
        enrichPlayerWithPiece(player);
    }

    public Board getBoard() {
        return board;
    }

    //todo co tu mam vraciat? MovementStatus, void?
    public void move(Long playerId, TwoDimensionalCoordinatesData newPosition) {
        Player player = getPlayerById(playerId);
        Movement<TwoDimensionalCoordinatesData> movement = new Movement<>();
        TwoDimensionalCoordinates newCoordinates = new TwoDimensionalCoordinates(newPosition);
        movement.setNewPosition(newCoordinates);
        MovementStatus movementStatus = player.doMove(board, movement);
        if (MovementStatus.COLLISION.equals(movementStatus)) {
            handleCollision(movement, player);
        }
    }

    private void handleCollision(Movement<TwoDimensionalCoordinatesData> movement, Player currentMovementPlayer) {
        Piece<TwoDimensionalCoordinatesData> pieceForCoordinates = board.getPieceForCoordinates(movement.getNewPosition()).iterator().next();
        Player playerOnNewCoordinates = getPlayerById(pieceForCoordinates.getPlayerId());
        CollisionStatus collisionStatus = identifyCollisionWinner(playerOnNewCoordinates, currentMovementPlayer);
        Player winner = collisionStatus.getWinner();
        Player looser = collisionStatus.getLooser();
        updateSwordsCount(winner, looser);
        looser.doMove(board, generateMovementForInitialField(looser.getId()));
        movement.setPieceId(winner.getId());
        winner.doMove(board, movement);
        winner.incrementScore();
    }


    /**
     * If player on new coordinates >= swords count than player making the move, he wins (= cause of strategic advantage)
     *
     * @param playerOnNewCoordinates
     * @param currentMovementPlayer
     * @return {@link CollisionStatus}
     */
    private CollisionStatus identifyCollisionWinner(Player playerOnNewCoordinates, Player currentMovementPlayer) {
        if (playerOnNewCoordinates.getSwordsCount() >= currentMovementPlayer.getSwordsCount()) {
            return CollisionStatus.builder().winner(playerOnNewCoordinates).looser(currentMovementPlayer).build();
        } else {
            return CollisionStatus.builder().winner(currentMovementPlayer).looser(playerOnNewCoordinates).build();
        }
    }

    private void updateSwordsCount(Player winner, Player looser) {
        int loosersSwordsCount = looser.getSwordsCount();
        int winnersSwordsCount = winner.getSwordsCount();
        winner.setSwordsCount(winnersSwordsCount - loosersSwordsCount);
        looser.setSwordsCount(0);
    }

    private void enrichPlayersWithPieces(Set<Player> players) {
        for (Player player : players) {
            enrichPlayerWithPiece(player);
        }
    }

    private Movement<TwoDimensionalCoordinatesData> generateMovementForInitialField(Long pieceId) {
        Movement<TwoDimensionalCoordinatesData> movement = new Movement<>();
        movement.setPieceId(pieceId);
        movement.setNewPosition(board.getCoordinatesForInitialPosition());
        return movement;
    }

    private void enrichPlayerWithPiece(Player player) {
        Piece<TwoDimensionalCoordinatesData> piece = new AnyDirectionTwoDimensionalMovingPiece(player.getId(), player.getName() + "_piece", player.getId());
        Set<Piece<TwoDimensionalCoordinatesData>> pieces1 = new HashSet<>();
        pieces1.add(piece);
        player.setPieces(pieces1);
        Field<TwoDimensionalCoordinatesData> beginningField = getBoard().getFieldForCoordinates(board.getCoordinatesForInitialPosition());
        beginningField.getPieces().add(piece);
    }

    public Player getPlayerById(Long playerId) {
        for (Player player : players) {
            if (player.getId().equals(playerId)) {
                return player;
            }
        }
        throw new GameException("Non existing player for id:" + playerId);
    }

    public static void main(String[] args) throws Exception {
        Game g = new Game();
        SimpleGameFieldsGenerator sgf = new SimpleGameFieldsGenerator(20, null);
        Set<Field<TwoDimensionalCoordinatesData>> fields = sgf.generateFields();
        Board b = new RectangularBoard(fields);

        Set<Player> players = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            players.add(generatePlayer(i));
        }
        g.startGame(b, players);

        Player player1 = players.iterator().next();

        g.move(player1.getId(), new TwoDimensionalCoordinatesData(1, 2));
        g.move(player1.getId(), new TwoDimensionalCoordinatesData(1, 3));
        g.move(player1.getId(), new TwoDimensionalCoordinatesData(1, 4));
        g.move(player1.getId(), new TwoDimensionalCoordinatesData(1, 5));

        g.move(2l, new TwoDimensionalCoordinatesData(1, 2));
        g.move(2l, new TwoDimensionalCoordinatesData(1, 3));
        g.move(2l, new TwoDimensionalCoordinatesData(1, 4));
        g.move(2l, new TwoDimensionalCoordinatesData(1, 5));

        g.move(3l, new TwoDimensionalCoordinatesData(1, 2));
        g.move(3l, new TwoDimensionalCoordinatesData(1, 3));
        g.move(3l, new TwoDimensionalCoordinatesData(1, 4));
        g.move(3l, new TwoDimensionalCoordinatesData(1, 5));

        ArrayList<Field<TwoDimensionalCoordinatesData>> list = new ArrayList<>(g.board.getFields());
        Collections.sort(list, new FieldsComparator());
        ObjectMapper om = new ObjectMapper();
        om.writeValue(System.out, list);
    }

    private static Player generatePlayer(int index) {
        Player p = new Player();
        p.setId(Long.valueOf(index));
        p.setName("player_" + index);
        return p;
    }


}
