import java.io.Serializable;
import Class.*;

public class PlayerScore implements Serializable {

    private String playerName;
    private int score;

    public PlayerScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "PlayerScore{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                '}';
    }
}