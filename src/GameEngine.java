import javax.swing.SwingUtilities;
import java.util.Random;
import Class.*;

public class GameEngine implements Runnable {
    private Game gui;
    private boolean isRunning;
    private int score;
    private int timeRemaining;
    private final int GRID_SIZE = 9;
    private HoleOccupant[] gridState;
    private Random random;

    public GameEngine(Game gui) {
        this.gui = gui;
        this.gridState = new HoleOccupant[GRID_SIZE];
        this.random = new Random();
        this.score = 0;
        this.timeRemaining = 15;
        this.isRunning = true;
    }

    @Override
    public void run() {
        try {
            while (isRunning && timeRemaining > 0) {

                Thread.sleep(1000);
                timeRemaining--;

                int removeMole = random.nextInt(GRID_SIZE);
                if (gridState[removeMole] != null) {
                    despawn(removeMole);
                }

                spawnRandom();

                SwingUtilities.invokeLater(() -> {
                    gui.updateTime(timeRemaining);
                    gui.refreshGrid(gridState);
                });
            }

            isRunning = false;
            SwingUtilities.invokeLater(() -> gui.gameOver(score));

        } catch (InterruptedException e) {
            System.out.println("Game Engine Interrupted. Shutting down.");
            isRunning = false;
        }
    }

    private void spawnRandom() {
        int index = random.nextInt(GRID_SIZE);
        int typeRoll = random.nextInt(10);
        HoleOccupant mole;

        if (typeRoll < 6) {
            mole = new Mole();
        } else if (typeRoll < 9) {
            mole = new Bomb();
        } else {
            mole = new BonusMole();
        }
        gridState[index] = mole;

    }

    private void despawn(int index) {
        gridState[index] = null;
    }

    public synchronized int handleWhack(int index) {
        if (!isRunning) return 0;

        if (index < 0 || index >= GRID_SIZE) {
            throw new InvalidGameStateException("Whack attempt at invalid index: " + index);
        }

        HoleOccupant target = gridState[index];
        int points = 0;

        if (target != null) {
            points = target.whack();
            score += points;
            gridState[index] = null;

            // UI Update
            final int finalScore = score;
            SwingUtilities.invokeLater(() -> {
                gui.updateScore(finalScore);
                gui.refreshGrid(gridState);
            });
        }
        return points;
    }

    public void stopGame() {
        isRunning = false;
    }
}