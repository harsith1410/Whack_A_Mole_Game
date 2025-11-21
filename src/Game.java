import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;
import Class.*;

public class Game extends JFrame {
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel highScoreLabel;
    private JPanel gridPanel;
    private JButton[] holeButtons;

    private GameEngine engine;
    private Thread gameThread;
    private int currentHighScore = 0;

    public Game() {
        intialize();
    }

    private void intialize() {
        setTitle("WHACK A MOLE ");
        setSize(600 , 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        infoPanel.setBackground(Color.DARK_GRAY);

        scoreLabel = createStyledLabel("Score: 0");
        highScoreLabel = createStyledLabel("High Score: " + currentHighScore);
        timeLabel = createStyledLabel("Time: 30s");

        infoPanel.add(scoreLabel);
        infoPanel.add(highScoreLabel);
        infoPanel.add(timeLabel);
        add(infoPanel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        gridPanel.setBackground(Color.PINK);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        holeButtons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.setBackground(Color.DARK_GRAY);
            final int index = i;
            button.addActionListener(e -> handleClick(index));
            holeButtons[i] = button;
            gridPanel.add(button);
        }
        add(gridPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.DARK_GRAY);

        JButton StartBtn = new JButton("Start Game");
        styleButton(StartBtn, Color.GREEN);
        StartBtn.addActionListener(e -> startGame());

        JButton ExitBtn = new JButton("Exit");
        styleButton(ExitBtn, Color.RED);
        ExitBtn.addActionListener(e -> System.exit(0));

        controlPanel.add(StartBtn);
        controlPanel.add(ExitBtn);
        add(controlPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (gameThread != null && gameThread.isAlive()) {
                    gameThread.interrupt();
                }
            }
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
    }

    private void startGame() {
        if (gameThread != null && gameThread.isAlive()) {
            return; // Already running
        }

        // Reset UI
        scoreLabel.setText("Score: 0");

        engine = new GameEngine(this);
        gameThread = new Thread(engine);
        gameThread.start();
    }

    private void handleClick(int index) {
        if (engine != null) {
            engine.handleWhack(index);
        }
    }


    public void updateTimer(int seconds) {
        timeLabel.setText("Time: " + seconds + "s");
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void refreshGrid(HoleOccupant[] gridState) {
        for (int i = 0; i < 9; i++) {
            HoleOccupant occ = gridState[i];
            JButton button = holeButtons[i];

            if (occ == null) {
                button.setBackground(Color.DARK_GRAY); // Empty Hole
                button.setText("");
                button.setIcon(null);
            } else {
                button.setText(occ.getType());
                button.setIcon(occ.getImage());
            }
        }
    }

    public void gameOver(int finalScore) {
        String name = JOptionPane.showInputDialog(this, "GAME OVER!\nScore: " + finalScore + "\nEnter Name:");

        if (name != null && !name.trim().isEmpty()) {

            if (finalScore > currentHighScore) {
                highScoreLabel.setText("High Score: " + finalScore);
                currentHighScore = finalScore;
            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}