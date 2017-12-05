package sk.stopangin.realtimegamem.piece;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;
import sk.stopangin.realtimegamem.movement.Coordinates;
import sk.stopangin.realtimegamem.movement.MovementType;


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Piece<T extends Serializable> extends BaseIdentifiableEntity {
    private Long playerId;
    private String name;
    @JsonIgnore
    private Set<MovementType<T>> movementTypes = new HashSet<>();

    public Piece(Long id, String name, Long playerId, MovementType<T>... varMovementTypes) {
        setId(id);
        this.playerId=playerId;
        this.name = name;
        movementTypes.addAll(Arrays.asList(varMovementTypes));
    }

    public boolean isValidMove(Coordinates<T> actualPosition, Coordinates<T> newCoordinates) {
        for (MovementType movementType : movementTypes) {
            if (movementType.isMatch(actualPosition, newCoordinates)) {
                return true;
            }
        }
        return false;
    }
}
