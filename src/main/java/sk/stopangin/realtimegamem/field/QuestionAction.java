package sk.stopangin.realtimegamem.field;

import sk.stopangin.realtimegamem.game.Game;
import sk.stopangin.realtimegamem.player.Player;


public class QuestionAction extends Action {
    private int score = 1;

    public QuestionAction(ActionData actionData) {
        super(actionData);
    }

    @Override
    public Integer perform(Game game) {
        throw new UnsupportedOperationException("Use perform with game and data param.");
    }

    @Override
    public Integer perform(Game game, Long playerId, String data) {
        int roundScore;
        if (getActionData().isUsed()) {
            return 0;
        }
        if (getActionData().getData().equalsIgnoreCase(data)) {
            roundScore = score;
        } else {
            roundScore = -score;
        }

        Player player = game.getPlayerById(playerId);
        int newScore = player.getSwordsCount() + roundScore;
        newScore = newScore < 0 ? 0 : newScore;
        player.setSwordsCount(newScore);
        return roundScore;
    }
}
