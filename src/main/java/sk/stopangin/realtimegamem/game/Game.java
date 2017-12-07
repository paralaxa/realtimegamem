package sk.stopangin.realtimegamem.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.stopangin.realtimegamem.board.ActionFieldInserter;
import sk.stopangin.realtimegamem.board.Board;
import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;
import sk.stopangin.realtimegamem.field.ActionData;
import sk.stopangin.realtimegamem.field.ActionField;
import sk.stopangin.realtimegamem.field.Field;
import sk.stopangin.realtimegamem.field.RegularField;
import sk.stopangin.realtimegamem.movement.*;
import sk.stopangin.realtimegamem.piece.AnyDirectionTwoDimensionalMovingPiece;
import sk.stopangin.realtimegamem.piece.Piece;
import sk.stopangin.realtimegamem.player.Player;

import java.util.HashSet;
import java.util.Set;

// TODO GENERIFY!
public class Game extends BaseIdentifiableEntity {
    private static final Logger log = LoggerFactory.getLogger(Game.class);

    private Board board;
    private Set<Player> players = new HashSet<>();
    private ActionFieldInserter actionFieldInserter;

    public Board startGame(Board board, Set<Player> players, ActionFieldInserter actionFieldInserter) {
        this.board = board;
        this.players = players;
        this.actionFieldInserter = actionFieldInserter;
        enrichPlayersWithPieces(players);
        actionFieldInserter.init();
        actionFieldInserter.insertActionFields(20);
        return board;
    }

    public void joinPlayer(Player player) {
        if (getPlayerById(player.getId()) == null) {
            players.add(player);
            enrichPlayerWithPiece(player);
        } else {
            throw new GameException("Player with id:" + player.getId() + " already exist, choose another id.");
        }
    }

    public Board getBoard() {
        return board;
    }

    public MovementStatus move(Long playerId, TwoDimensionalCoordinatesData newPosition) {
        Player player = getPlayerById(playerId);
        Movement<TwoDimensionalCoordinatesData> movement = new Movement<>();
        TwoDimensionalCoordinates newCoordinates = new TwoDimensionalCoordinates(newPosition);
        movement.setNewPosition(newCoordinates);
        MovementStatus movementStatus = player.doMove(board, movement);
        if (MovementStatus.COLLISION.equals(movementStatus)) {
            return handleCollision(movement, player);
        }
        return movementStatus;
    }

    public ActionData getActionData(Long playerId) {
        Field<TwoDimensionalCoordinatesData> fieldForCoordinates = getFieldPlayerIsStayingOn(playerId);
        if (fieldForCoordinates instanceof ActionField) {
            return ((ActionField) fieldForCoordinates).getAction().getActionData();
        }
        return null;
    }

    public Integer commitAction(Long playerId, String actionData) {
        Field<TwoDimensionalCoordinatesData> fieldForCoordinates = getFieldPlayerIsStayingOn(playerId);
        if (fieldForCoordinates instanceof ActionField) {
            log.info("Player with id:{} is performing action with data:{}", playerId, actionData);
            Integer actionScore = ((ActionField) fieldForCoordinates).getAction().perform(this, playerId, actionData);
            removeActionFieldAndCreateRegularFieldOnItsPosition(fieldForCoordinates);
            actionFieldInserter.insertActionFields(1);
            return actionScore;
        }
        return null;
    }

    private void removeActionFieldAndCreateRegularFieldOnItsPosition(Field<TwoDimensionalCoordinatesData> fieldForCoordinates) {
        getBoard().getFields().remove(fieldForCoordinates);
        Field<TwoDimensionalCoordinatesData> field = new RegularField();
        field.setPosition(fieldForCoordinates.getPosition());
        field.setPieces(fieldForCoordinates.getPieces());
        getBoard().getFields().add(field);
        log.info("Removing action field and creating regular field on position:{}", fieldForCoordinates.getPosition());
    }

    private Field<TwoDimensionalCoordinatesData> getFieldPlayerIsStayingOn(Long playerId) {
        Player player = getPlayerById(playerId);
        Piece<TwoDimensionalCoordinatesData> playersPiece = player.getPlayersOnlyPiece();
        Coordinates<TwoDimensionalCoordinatesData> coordinatesForPieceId = board.getCoordinatesForPieceId(playersPiece.getId());
        return board.getFieldForCoordinates(coordinatesForPieceId);
    }

    private MovementStatus handleCollision(Movement<TwoDimensionalCoordinatesData> movement, Player currentMovementPlayer) {
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
        log.info("Collision winner:{}", winner);
        log.info("Collision looser:{}", looser);
        return getMovementStatus(currentMovementPlayer, winner);
    }

    private MovementStatus getMovementStatus(Player currentMovementPlayer, Player winner) {
        if (winner.getId().equals(currentMovementPlayer.getId())) {
            return MovementStatus.DONE;
        } else {
            return MovementStatus.RESET;
        }
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


}
