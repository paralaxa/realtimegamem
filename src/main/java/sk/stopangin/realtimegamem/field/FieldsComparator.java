package sk.stopangin.realtimegamem.field;

import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;

import java.util.Comparator;

public class FieldsComparator  implements Comparator<Field<TwoDimensionalCoordinatesData>> {
    @Override
    public int compare(Field<TwoDimensionalCoordinatesData> o1, Field<TwoDimensionalCoordinatesData> o2) {
        if (o1.getPosition().getData().getX() == o2.getPosition().getData().getX()) {
            return Integer.valueOf(o1.getPosition().getData().getY()).compareTo(o2.getPosition().getData().getY());
        } else {
            return Integer.valueOf(o1.getPosition().getData().getX()).compareTo(o2.getPosition().getData().getX());
        }
    }
}