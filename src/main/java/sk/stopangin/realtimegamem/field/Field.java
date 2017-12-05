package sk.stopangin.realtimegamem.field;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;
import sk.stopangin.realtimegamem.movement.Coordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
import sk.stopangin.realtimegamem.piece.Piece;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Field<T extends Serializable> extends BaseIdentifiableEntity {
    private Coordinates<T> position;
    private Set<Piece<T>> pieces = new HashSet<>();

    public boolean hasPieceWithId(Long pieceId) {
        return getPieceWithPieceId(pieceId) == null ? false : true;
    }

    public void removePieceWithId(Long pieceId) {
        for (Piece<T> piece : pieces) {
            if (piece.getId().equals(pieceId)) {
                pieces.remove(piece);
                break;
            }
        }
    }

    public Piece<T> getPieceWithPieceId(Long pieceId) {
        for (Piece<T> piece : pieces) {
            if (piece.getId().equals(pieceId)) {
                return piece;
            }
        }
        return null;
    }

}
