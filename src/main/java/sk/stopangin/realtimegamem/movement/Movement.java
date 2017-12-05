package sk.stopangin.realtimegamem.movement;

import lombok.Data;

import java.io.Serializable;

@Data
public class Movement<T extends Serializable> {
    private Long pieceId;
    private Coordinates<T> newPosition;
}
