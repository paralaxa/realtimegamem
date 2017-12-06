package sk.stopangin.realtimegamem.board;

import lombok.AllArgsConstructor;
import sk.stopangin.realtimegamem.field.*;
import sk.stopangin.realtimegamem.movement.Coordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
public class SimpleGameFieldsGenerator {
    private int max;

    public Set<Field<TwoDimensionalCoordinatesData>> generateFields() {
        Set<Field<TwoDimensionalCoordinatesData>> fields = new HashSet<>();
        for (int i = 1; i < max; i++) {
            for (int j = 1; j < max; j++) {
                TwoDimensionalCoordinatesData currentFieldCoordinatesData = new TwoDimensionalCoordinatesData(i, j);
                Field<TwoDimensionalCoordinatesData> field = new RegularField();
                Coordinates<TwoDimensionalCoordinatesData> twoDimensionalCoordinatesDataCoordinates =
                        new TwoDimensionalCoordinates(currentFieldCoordinatesData);
                field.setPosition(twoDimensionalCoordinatesDataCoordinates);
                fields.add(field);
            }
        }
        return fields;
    }
}