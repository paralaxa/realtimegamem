package sk.stopangin.realtimegamem.board;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.stopangin.realtimegamem.field.ActionData;
import sk.stopangin.realtimegamem.field.ActionField;
import sk.stopangin.realtimegamem.field.Field;
import sk.stopangin.realtimegamem.movement.*;
import sk.stopangin.realtimegamem.piece.Piece;

import java.util.Set;


@Data
public abstract class Board {
    private static final Logger log = LoggerFactory.getLogger(Board.class);

    private Set<Field<TwoDimensionalCoordinatesData>> fields;

    public Board(Set<Field<TwoDimensionalCoordinatesData>> fields) {
        this.fields = fields;
    }

    public MovementStatus updateBasedOnMovement(Movement<TwoDimensionalCoordinatesData> movement) {
        Long currentMovementPieceId = movement.getPieceId();
        Piece<TwoDimensionalCoordinatesData> currentMovementPiece = null;
        Field<TwoDimensionalCoordinatesData> currentMovementField = null;
        Field<TwoDimensionalCoordinatesData> newMovementField = null;
        for (Field<TwoDimensionalCoordinatesData> field : fields) {
            if (isCurrentMovementsPieceOnField(currentMovementPieceId, field)) {
                currentMovementPiece = field.getPieceWithPieceId(currentMovementPieceId);
                currentMovementField = field;
            }
            if (isFieldNewMovementsField(movement, field)) {
                newMovementField = field;
            }
            if (isEverythingSetup(currentMovementPiece, currentMovementField, newMovementField)) {
                break;
            }
        }
        return movementStatusForCurrentMovement(movement, currentMovementPiece, currentMovementField, newMovementField);
    }

    private boolean isFieldNewMovementsField(Movement<TwoDimensionalCoordinatesData> movement, Field<TwoDimensionalCoordinatesData> field) {
        return field.getPosition().equals(movement.getNewPosition());
    }

    private MovementStatus movementStatusForCurrentMovement(Movement<TwoDimensionalCoordinatesData> movement, Piece<TwoDimensionalCoordinatesData> currentMovementPiece, Field<TwoDimensionalCoordinatesData> currentMovementField, Field<TwoDimensionalCoordinatesData> newMovementField) {
        if (isEverythingSetup(currentMovementPiece, currentMovementField, newMovementField)) {
            if (isNotMoving(currentMovementField, newMovementField)) {
                log.info("piece: {} is staying on its position {}.", currentMovementPiece, movement);
                return MovementStatus.DONE;
            }
            if (isInitialPosition(movement.getNewPosition().getData())) {
                log.info("returning to initial position {}.", movement);
                proceedWithMovement(currentMovementPiece, currentMovementField, newMovementField);
                return MovementStatus.DONE;
            }

            if (currentMovementPiece.isValidMove(currentMovementField.getPosition(), movement.getNewPosition())) {
                if (isMovementCollision(movement)) {
                    log.info("collision found for movement {}.", movement);
                    return MovementStatus.COLLISION;
                }
                MovementStatus movementStatus = movementStatusBasedOnNewMovementFieldType(currentMovementField, newMovementField);
                if (movementStatus.isAllowMovement()) {
                    proceedWithMovement(currentMovementPiece, currentMovementField, newMovementField);
                    log.info("proceeding with movement {}.", movement);
                }
                return movementStatus;
            } else {
                log.info("invalid position for movement {}.", movement);
                return MovementStatus.INVALID_POSITION;
            }
        }
        log.info("non existing field for movement {}.", movement);
        return MovementStatus.NON_EXISTING_FIELD;
    }

    private boolean isNotMoving(Field<TwoDimensionalCoordinatesData> currentMovementField, Field<TwoDimensionalCoordinatesData> newMovementField) {
        TwoDimensionalCoordinatesData newCoordinates = newMovementField.getPosition().getData();
        TwoDimensionalCoordinatesData currentCoordinates = currentMovementField.getPosition().getData();
        return currentCoordinates.getX() == newCoordinates.getX() && currentCoordinates.getY() == newCoordinates.getY();
    }

    private void proceedWithMovement(Piece<TwoDimensionalCoordinatesData> currentMovementPiece, Field<TwoDimensionalCoordinatesData> currentMovementField, Field<TwoDimensionalCoordinatesData> newMovementField) {
        currentMovementField.removePieceWithId(currentMovementPiece.getId());
        putPieceOnNewField(newMovementField, currentMovementPiece);
    }

    private MovementStatus movementStatusBasedOnNewMovementFieldType(Field<TwoDimensionalCoordinatesData> currentMovementField, Field<TwoDimensionalCoordinatesData> newMovementField) {
        if (currentMovementField instanceof ActionField) {
            ActionData actionData = extractActionDataFromField((ActionField) currentMovementField);
            if (!actionData.isUsed()) {
                return MovementStatus.ACTION_REQUIRED;
            }
        }

        if (newMovementField instanceof ActionField) {
            ActionData actionData = extractActionDataFromField((ActionField) newMovementField);
            if (!actionData.isUsed()) {
                return MovementStatus.ACTION_POSSIBLE;
            }
        }
        return MovementStatus.DONE;
    }

    private ActionData extractActionDataFromField(ActionField newMovementField) {
        return newMovementField.getAction().getActionData();
    }

    private boolean isEverythingSetup(Piece<TwoDimensionalCoordinatesData> currentMovementPiece, Field<TwoDimensionalCoordinatesData> currentMovementField, Field<TwoDimensionalCoordinatesData> newMovementField) {
        return currentMovementField != null && newMovementField != null && currentMovementPiece != null;
    }

    public Field<TwoDimensionalCoordinatesData> getFieldForCoordinates(Coordinates<TwoDimensionalCoordinatesData> coordinates) {
        return fields.stream()
                .filter(tField -> tField.getPosition().equals(coordinates))
                .findFirst()
                .orElse(null);
    }

    public Set<Piece<TwoDimensionalCoordinatesData>> getPieceForCoordinates(Coordinates<TwoDimensionalCoordinatesData> coordinates) {
        return getFieldForCoordinates(coordinates).getPieces();
    }


    private boolean isCurrentMovementsPieceOnField(Long currentMovementPieceId, Field<TwoDimensionalCoordinatesData> field) {
        return field.hasPieceWithId(currentMovementPieceId);
    }

    private void putPieceOnNewField(Field<TwoDimensionalCoordinatesData> field, Piece<TwoDimensionalCoordinatesData> currentMovementPiece) {
        field.getPieces().add(currentMovementPiece);
    }

    protected boolean isMovementCollision(Movement<TwoDimensionalCoordinatesData> movement) {
        TwoDimensionalCoordinatesData coordinatesData = movement.getNewPosition().getData();
        boolean isPieceOnPosition = !getPieceForCoordinates(movement.getNewPosition()).isEmpty();
        return isPieceOnPosition && !isInitialPosition(coordinatesData);
    }

    public abstract boolean isInitialPosition(TwoDimensionalCoordinatesData coordinatesData);

    public abstract Coordinates<TwoDimensionalCoordinatesData> getCoordinatesForPieceId(Long pieceId);

    public abstract TwoDimensionalCoordinates getCoordinatesForInitialPosition();


}
