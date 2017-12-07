package sk.stopangin.realtimegamem.service;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sk.stopangin.realtimegamem.game.GameException;
import sk.stopangin.realtimegamem.player.PlayerException;

@ControllerAdvice
public class ExceptionMapper {


    @ResponseBody
    @ExceptionHandler({PlayerException.class, GameException.class})
    public ExceptionValueHolder handleBusinessException(Exception e) {
        return new ExceptionValueHolder(e.getMessage());
    }

    private static class ExceptionValueHolder {
        private String message;

        public ExceptionValueHolder(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
