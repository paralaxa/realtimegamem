package sk.stopangin.realtimegamem.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import sk.stopangin.realtimegamem.field.ActionData;
import sk.stopangin.realtimegamem.game.Game;

@AllArgsConstructor
@Data
public class ActionDto {
    private ActionDataDto actionData;

}
