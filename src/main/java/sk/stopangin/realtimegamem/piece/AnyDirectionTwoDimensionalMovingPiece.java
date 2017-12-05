package sk.stopangin.realtimegamem.piece;


import sk.stopangin.realtimegamem.movement.DiagonalMovementType;
import sk.stopangin.realtimegamem.movement.DirectMovementType;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;

public class AnyDirectionTwoDimensionalMovingPiece extends Piece<TwoDimensionalCoordinatesData> {

    public AnyDirectionTwoDimensionalMovingPiece(Long id, String name, Long playerId) {
        super(id, name, playerId, new DirectMovementType(), new DiagonalMovementType());
    }
}
