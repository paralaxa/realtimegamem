package sk.stopangin.realtimegamem.game;

import lombok.Builder;
import lombok.Data;
import sk.stopangin.realtimegamem.player.Player;

@Data
@Builder
public class CollisionStatus {
    private Player winner;
    private Player looser;
}
