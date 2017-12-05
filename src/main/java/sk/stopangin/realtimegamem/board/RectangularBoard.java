package sk.stopangin.realtimegamem.board;


import sk.stopangin.realtimegamem.field.Field;
import sk.stopangin.realtimegamem.movement.Coordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;

import java.util.Set;

public class RectangularBoard extends Board {

    public RectangularBoard(Set<Field<TwoDimensionalCoordinatesData>> fields) {
        super(fields);
    }

    @Override
    public Coordinates<TwoDimensionalCoordinatesData> getCoordinatesForPieceId(Long pieceId) {
        for (Field<TwoDimensionalCoordinatesData> field : getFields()) {
            if (field.hasPieceWithId(pieceId)) {
                return field.getPosition();
            }
        }
        return getCoordinatesForInitialPosition();
    }

    @Override
    public TwoDimensionalCoordinates getCoordinatesForInitialPosition() {
        return new TwoDimensionalCoordinates(new TwoDimensionalCoordinatesData(1, 1));
    }

    @Override
    public boolean isInitialPosition(TwoDimensionalCoordinatesData coordinatesData) {
        return coordinatesData.getY() == 1 && coordinatesData.getX() == 1;
    }
}
