package sk.stopangin.realtimegamem.to;

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
public class BoardDto {

    private Set<ActionFieldDto> fields;


}
