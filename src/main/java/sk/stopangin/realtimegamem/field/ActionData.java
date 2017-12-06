package sk.stopangin.realtimegamem.field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;

@Data
@Builder
public class ActionData extends BaseIdentifiableEntity {
    @JsonIgnore
    private boolean blocking; //signals, that round cannot be finished without performing action
    @JsonIgnore
    private boolean used;//this action was used and won't be fired anymore
    private String info;
    @JsonIgnore
    private String data;
}
