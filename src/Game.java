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
    private JButton[] holes;

    private GameEngine engine;
    private Thread thread;
    private int currentHighScore = 0;



    public Game() {
        setTitle("WHACK A MOLE ");
        setSize(600 , 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        JPanel ScorePanel = new JPanel(new GridLayout(1, 3));
        ScorePanel.setBackground(Color.DARK_GRAY);

        scoreLabel = styleLabel("Score: 0");
        highScoreLabel = styleLabel("High Score: " + currentHighScore);
        timeLabel = styleLabel("Time: 15s");

        ScorePanel.add(scoreLabel);
        ScorePanel.add(highScoreLabel);
        ScorePanel.add(timeLabel);
        add(ScorePanel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        gridPanel.setBackground(Color.PINK);

        holes = new JButton[9];
        for (int i = 0; i < 9; i++) {
            JButton btn = new JButton();
            btn.setBackground(Color.DARK_GRAY);
            final int index = i;
            btn.addActionListener(e -> handleClick(index));
            holes[i] = btn;
            gridPanel.add(btn);
        }
        add(gridPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (thread != null && thread.isAlive()) {
                    thread.interrupt();
                }
            }
        });
    }

    private JLabel styleLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.DARK_GRAY);
    }

    private void handleClick(int index) {
        if (engine != null) {
            engine.handleWhack(index);
        }
    }

    public void updateTime(int seconds) {
        timeLabel.setText("Time: " + seconds + "s");
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void refreshGrid(HoleOccupant[] gridState) {
        for (int i = 0; i < 9; i++) {
            HoleOccupant occ = gridState[i];
            JButton btn = holes[i];

            if (occ == null) {
                btn.setBackground(Color.DARK_GRAY);
                btn.setText("");
                btn.setIcon(null);
            } else {
                btn.setText(occ.getType());
                btn.setIcon(occ.getImage());
            }
        }
    }

    private void startGame() {
        if (thread != null && thread.isAlive()) {
            return;
        }
        scoreLabel.setText("Score: 0");
        engine = new GameEngine(this);
        thread = new Thread(engine);
        thread.start();
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

        Game game = new Game();
        game.setVisible(true);

    }
}