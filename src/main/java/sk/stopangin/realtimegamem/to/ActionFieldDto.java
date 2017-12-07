package sk.stopangin.realtimegamem.to;

import lombok.Data;
import sk.stopangin.realtimegamem.field.Action;
import sk.stopangin.realtimegamem.field.Field;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;

@Data
public class ActionFieldDto extends FieldDto {
    private ActionDto action;
}
