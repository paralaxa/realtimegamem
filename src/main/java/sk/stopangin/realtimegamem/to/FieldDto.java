package sk.stopangin.realtimegamem.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;
import sk.stopangin.realtimegamem.movement.Coordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
import sk.stopangin.realtimegamem.piece.AnyDirectionTwoDimensionalMovingPiece;
import sk.stopangin.realtimegamem.piece.Piece;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class FieldDto extends BaseIdentifiableEntity {
    private Coordinates<TwoDimensionalCoordinatesData> position;
    private Set<AnyDirectionTwoDimensionalMovingPiece> pieces;

}
