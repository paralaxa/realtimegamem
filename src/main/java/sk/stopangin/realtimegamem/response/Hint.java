package sk.stopangin.realtimegamem.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Hint {

    USE_MOVE_ENDPOINT("Use /game/bewegen endpoint and POST your new coordinates " +
            "in the following format {\"x\": 1, \"y\": 1}"),
    USE_ACTION_ENDPOINTS("Please GET action from the following endpoint /game/akcija. To answer please POST your " +
                                 "answer as string using the same endpoint.");

    private String hint;

    Hint(String hint) {
        this.hint = hint;
    }

    @JsonValue
    public String getHint() {
        return hint;
    }
}
