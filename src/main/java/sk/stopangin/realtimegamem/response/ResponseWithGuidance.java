package sk.stopangin.realtimegamem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseWithGuidance {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Hint hint;
}
