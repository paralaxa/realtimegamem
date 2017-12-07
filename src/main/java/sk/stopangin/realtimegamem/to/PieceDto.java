package sk.stopangin.realtimegamem.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;

import java.io.Serializable;

@Data
public class PieceDto extends BaseIdentifiableEntity {
    private Long playerId;
    private String name;
   }
