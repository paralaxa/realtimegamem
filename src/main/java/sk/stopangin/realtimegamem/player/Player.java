package sk.stopangin.realtimegamem.player;

        import com.fasterxml.jackson.annotation.JsonGetter;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import sk.stopangin.realtimegamem.board.Board;
        import sk.stopangin.realtimegamem.entity.BaseIdentifiableEntity;
        import sk.stopangin.realtimegamem.movement.Movement;
        import sk.stopangin.realtimegamem.movement.MovementStatus;
        import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
        import sk.stopangin.realtimegamem.piece.Piece;

        import java.util.Set;


public class Player extends BaseIdentifiableEntity {
    private String name;
    @JsonIgnore
    private Set<Piece<TwoDimensionalCoordinatesData>> pieces;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int score;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int swordsCount;

    public MovementStatus doMove(Board board, Movement<TwoDimensionalCoordinatesData> movement) {
        updateMovementWithMyPiece(movement);
        return doMovement(board, movement);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Piece<TwoDimensionalCoordinatesData>> getPieces() {
        return pieces;
    }

    public void setPieces(Set<Piece<TwoDimensionalCoordinatesData>> pieces) {
        this.pieces = pieces;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getSwordsCount() {
        return swordsCount;
    }

    public void setSwordsCount(int swordsCount) {
        this.swordsCount = swordsCount;
    }

    /**
     * If id is null and there is only one piece for player, this method return the only pieces id,
     * otherwise finds the piece for given id and return it
     *
     * @param pieceId piece id to be found, or null, if there is only one piece for player
     * @return valid piece id for this player
     */
    public Long findOrFillPieceIdIfNull(Long pieceId) {
        if (pieceNotSpecifiedForMovement(pieceId) && hasOnlyOnePiece()) {
            return getPlayersOnlyPiece().getId();
        }
        if (hasPieceWithId(pieceId)) {
            return pieceId;
        } else {
            throw new PlayerException("No piece with id:" + pieceId + " for player:" + name);
        }
    }

    private void updateMovementWithMyPiece(Movement<TwoDimensionalCoordinatesData> movement) {
        Long currentMovementPieceId = movement.getPieceId();
        movement.setPieceId(findOrFillPieceIdIfNull(currentMovementPieceId));
    }

    public Piece<TwoDimensionalCoordinatesData> getPlayersOnlyPiece() {
        return pieces.iterator().next();
    }

    private boolean pieceNotSpecifiedForMovement(Long currentMovementPieceId) {
        return currentMovementPieceId == null;
    }

    private boolean hasOnlyOnePiece() {
        return pieces.size() == 1;
    }


    private boolean hasPieceWithId(Long pieceId) {
        for (Piece<TwoDimensionalCoordinatesData> myPiece : pieces) {
            if (myPiece.getId().equals(pieceId)) {
                return true;
            }
        }
        return false;
    }

    public MovementStatus doMovement(Board board, Movement<TwoDimensionalCoordinatesData> movement) {
        return board.updateBasedOnMovement(movement);
    }

    public void incrementScore() {
        score++;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", pieces=" + pieces +
                ", score=" + score +
                ", swordsCount=" + swordsCount +
                '}';
    }
}
