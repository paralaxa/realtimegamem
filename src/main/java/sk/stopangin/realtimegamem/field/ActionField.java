package sk.stopangin.realtimegamem.field;

import lombok.Data;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;

import java.io.Serializable;

@Data
public class ActionField extends Field<TwoDimensionalCoordinatesData> {
    private Action action;
}
