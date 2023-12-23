import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class TreasureHuntGame extends JFrame implements Runnable {
    private static final int ROWS = 9;
    private static final int COLS = 9;
    private static final int NUM_TREASURES = 5;
    private static final int GAME_DURATION_SECONDS = 30;

    private ImageIcon[][] islandIcons;      // 보물섬 이미지 아이콘
    private ImageIcon treasureIcon;         // 보물 이미지 아이콘

    private JButton[][] gridButtons;
    private List<Point> treasureLocations;
    private int treasuresFound;
    private int totalTreasures;
    private boolean gameRunning;
    private Timer gameTimer;
    private long startTime;

    private JButton startButton;
    private JButton stopButton;
    private JLabel infoLabel;
    private Thread timeThread;

    public TreasureHuntGame() {
        setTitle("Treasure Hunt Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 이미지 아이콘 초기화
        initializeIcons();

        setupUI();
        setupGame();
    }

    private void initializeIcons() {
        try {
            BufferedImage islandImage = ImageIO.read(new File("보물섬지도.jpg"));
            int width = islandImage.getWidth() / COLS;
            int height = islandImage.getHeight() / ROWS;

            islandIcons = new ImageIcon[ROWS][COLS];
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    BufferedImage subimage = islandImage.getSubimage(j * width, i * height, width, height);
                    islandIcons[i][j] = new ImageIcon(subimage);
                }
            }

            treasureIcon = new ImageIcon("treasure.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        JPanel panel = new JPanel(new GridLayout(ROWS, COLS));
        gridButtons = new JButton[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gridButtons[i][j] = new JButton(islandIcons[i][j]);
                gridButtons[i][j].setPreferredSize(new Dimension(50, 50));
                gridButtons[i][j].setOpaque(true);
                gridButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final int row = i;
                final int col = j;
                gridButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (gameRunning) {
                            revealTreasure(row, col);
                        }
                    }
                });

                panel.add(gridButtons[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        infoLabel = new JLabel("Treasures Found: 0  Time Remaining: 30s");

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(infoLabel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void setupGame() {
        treasuresFound = 0;
        gameRunning = false;
        totalTreasures = NUM_TREASURES;
        treasureLocations = new ArrayList<>();
        gameTimer = new Timer(GAME_DURATION_SECONDS * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
                showGameResult(treasuresFound, (int) ((System.currentTimeMillis() - startTime) / 1000.0));
            }
        });
    }

    private void startGame() {
        if (!gameRunning) {
            resetGrid();
            placeTreasures();
            treasuresFound = 0;
            gameRunning = true;
            startTime = System.currentTimeMillis();
            gameTimer.start();

            timeThread = new Thread(this);
            timeThread.start();
        }
    }

    private void stopGame() {
        if (gameRunning) {
            gameRunning = false;
            gameTimer.stop();
            resetGrid();
            timeThread.interrupt();

            SwingUtilities.invokeLater(() -> {
                showGameResult(treasuresFound, (int) ((System.currentTimeMillis() - startTime) / 1000.0));
                updateInfoLabel(0, 0);
            });
        }
    }

    private void resetGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gridButtons[i][j].setIcon(islandIcons[i][j]);
            }
        }
    }

    private void placeTreasures() {
        Random rand = new Random();
        for (int i = 0; i < NUM_TREASURES; i++) {
            int row = rand.nextInt(ROWS);
            int col = rand.nextInt(COLS);
            Point treasureLocation = new Point(row, col);

            if (!treasureLocations.contains(treasureLocation)) {
                treasureLocations.add(treasureLocation);
            } else {
                i--;
            }
        }
    }

    private void revealTreasure(int row, int col) {
        Point clickedPoint = new Point(row, col);
        if (treasureLocations.contains(clickedPoint) && gridButtons[row][col].getIcon() == islandIcons[row][col]) {
            treasuresFound++;
            gridButtons[row][col].setIcon(treasureIcon);

            if (treasuresFound == totalTreasures) {
                stopGame();  // 모든 보물을 찾았을 때 게임 종료
            }
        }
        updateInfoLabel(treasuresFound, getRemainingTime());
    }

    private int getRemainingTime() {
        if (timeThread != null && timeThread.isAlive()) {
            return (int) Math.ceil((GAME_DURATION_SECONDS - (System.currentTimeMillis() - startTime) / 1000.0));
        }
        return 0;
    }

    private void updateInfoLabel(int found, int remainingTime) {
        SwingUtilities.invokeLater(() -> infoLabel.setText("Treasures Found: " + found + "  Time Remaining: " + remainingTime + "s"));
    }

    private void showGameResult(int found, int elapsedSeconds) {
        String message = "Game Over!\n";
        if (found == totalTreasures) {
            message += "Congratulations! You found all treasures.\n";
        } else {
            message += "Found treasures: " + found + " treasures\n";
        }
        message += "Time taken: " + elapsedSeconds + " seconds";

        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void run() {
        while (getRemainingTime() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            updateInfoLabel(treasuresFound, getRemainingTime());
        }
        stopGame();
        showGameResult(treasuresFound, (int) ((System.currentTimeMillis() - startTime) / 1000.0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TreasureHuntGame::new);
    }
}


