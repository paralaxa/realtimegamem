package sk.stopangin.realtimegamem.field;

        import lombok.Data;
        import sk.stopangin.realtimegamem.game.Game;

@Data
public abstract class Action {
    private ActionData actionData;

    public abstract Integer perform(Game game);

    public abstract Integer perform(Game game, Long playerId, String data);
}
