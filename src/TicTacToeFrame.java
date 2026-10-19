import javax.swing.*;
import java.awt.*;

/**
 * TicTacToeFrame - the GUI (view/controller).
 * Builds the 3x3 grid of TTTTileButtons and the Quit button, passes clicks
 * to the TTTGame, and shows the results to the players.
 * All game rules live in TTTGame / TTTBoard, not in the listener.
 */
public class TicTacToeFrame extends JFrame
{
    private final TTTGame game = new TTTGame();
    private final TTTTileButton[][] tiles = new TTTTileButton[TTTBoard.ROWS][TTTBoard.COLS];
    private final JLabel statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);

    public TicTacToeFrame()
    {
        super("Tic Tac Toe");
        setLayout(new BorderLayout());

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createBoardPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createTitlePanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(title, BorderLayout.NORTH);
        panel.add(statusLabel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createBoardPanel()
    {
        JPanel panel = new JPanel(new GridLayout(TTTBoard.ROWS, TTTBoard.COLS));
        for (int row = 0; row < TTTBoard.ROWS; row++)
        {
            for (int col = 0; col < TTTBoard.COLS; col++)
            {
                TTTTileButton tile = new TTTTileButton(row, col);
                tile.addActionListener(e -> handleTileClick(tile));
                tiles[row][col] = tile;
                panel.add(tile);
            }
        }
        return panel;
    }

    private JPanel createControlPanel()
    {
        JPanel panel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        quitButton.addActionListener(e -> System.exit(0));
        panel.add(quitButton);
        return panel;
    }

    /** The listener only translates the click into a game move and updates the view. */
    private void handleTileClick(TTTTileButton tile)
    {
        String player = game.getCurrentPlayer();
        TTTGame.MoveResult result = game.makeMove(tile.getRow(), tile.getCol());

        switch (result)
        {
            case INVALID:
                JOptionPane.showMessageDialog(this, "Invalid move! That square is taken.");
                return;
            case WIN:
                tile.setText(player);
                endGame("Player " + player + " wins!");
                return;
            case TIE:
                tile.setText(player);
                endGame("It's a tie!");
                return;
            case CONTINUE:
                tile.setText(player);
                statusLabel.setText("Player " + game.getCurrentPlayer() + "'s turn");
                break;
        }
    }

    private void endGame(String message)
    {
        statusLabel.setText(message);
        int choice = JOptionPane.showConfirmDialog(this, message + "\nPlay again?",
                "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
            resetGame();
        else
            System.exit(0);
    }

    private void resetGame()
    {
        game.reset();
        for (TTTTileButton[] row : tiles)
            for (TTTTileButton tile : row)
                tile.setText(" ");
        statusLabel.setText("Player X's turn");
    }
}
