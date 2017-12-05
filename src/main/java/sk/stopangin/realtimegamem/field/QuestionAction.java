package sk.stopangin.realtimegamem.field;

import sk.stopangin.realtimegamem.game.Game;
import sk.stopangin.realtimegamem.player.Player;


public class QuestionAction extends Action {
    private int score = 1;

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
        if (getActionData().getData().equals(data)) {
            roundScore = score;
        } else {
            roundScore = -score;
        }

        Player player = game.getPlayerById(playerId);
        player.setScore(player.getSwordsCount() + roundScore);
        return roundScore;
    }
}
