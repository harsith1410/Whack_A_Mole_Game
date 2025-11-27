import java.io.Serializable;

public class Player implements Serializable {

    private String playerName;
    private int score;

    public Player(String playerName, int score) {
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
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                '}';
    }
}