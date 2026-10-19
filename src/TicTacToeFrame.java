import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for the 2-player Tic Tac Toe game (the view / controller).
 * Builds the same interface as the original GUI TTT: a status label on top,
 * a 3 x 3 grid of TicTacToeTile buttons, and a Quit button on the bottom.
 * All of the game rules now live in TTTGame and TTTBoard, so the listener
 * only passes the move to the game and shows the result.
 *
 * @author Kirby Fortney
 */
public class TicTacToeFrame extends JFrame
{
    private final TTTGame game = new TTTGame();

    // GUI parts
    private final TicTacToeTile[][] tiles = new TicTacToeTile[TTTBoard.ROWS][TTTBoard.COLS];
    private JLabel statusLabel;
    private JButton quitButton;

    public TicTacToeFrame()
    {
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());

        // status label at the top
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        // board in the center
        add(createBoardPanel(), BorderLayout.CENTER);

        // quit button at the bottom
        JPanel buttonPanel = new JPanel();
        quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent ae) -> quitGame());
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createBoardPanel()
    {
        JPanel boardPanel = new JPanel(new GridLayout(TTTBoard.ROWS, TTTBoard.COLS));
        TileListener tileListener = new TileListener(); // one listener for all tiles

        for (int row = 0; row < TTTBoard.ROWS; row++)
        {
            for (int col = 0; col < TTTBoard.COLS; col++)
            {
                tiles[row][col] = new TicTacToeTile(row, col);
                tiles[row][col].setText(" ");
                tiles[row][col].setFont(new Font("SansSerif", Font.BOLD, 48));
                tiles[row][col].addActionListener(tileListener);
                boardPanel.add(tiles[row][col]);
            }
        }
        return boardPanel;
    }

    /**
     * Single listener used by every tile on the board.
     * It no longer contains game logic - it asks TTTGame to make the move.
     */
    private class TileListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            TicTacToeTile clickedTile = (TicTacToeTile) ae.getSource();
            String player = game.getCurrentPlayer();
            TTTGame.MoveResult result = game.makeMove(clickedTile.getRow(), clickedTile.getCol());

            if (result == TTTGame.MoveResult.INVALID)
            {
                JOptionPane.showMessageDialog(TicTacToeFrame.this,
                        "That square is taken. Please pick an empty square.",
                        "Illegal Move", JOptionPane.WARNING_MESSAGE);
                return; // wait for a legal move
            }

            clickedTile.setText(player);

            if (result == TTTGame.MoveResult.WIN)
            {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Player " + player + " wins!");
                askPlayAgain();
            }
            else if (result == TTTGame.MoveResult.TIE)
            {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "It's a Tie!");
                askPlayAgain();
            }
            else
            {
                statusLabel.setText("Player " + game.getCurrentPlayer() + "'s turn");
            }
        }
    }

    private void askPlayAgain()
    {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to play again?",
                "Play Again?", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION)
        {
            resetGame();
        }
        else
        {
            System.exit(0);
        }
    }

    private void quitGame()
    {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?",
                "Quit", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

    /** Clears the game and the tiles for a new game. */
    private void resetGame()
    {
        game.reset();
        for (int row = 0; row < TTTBoard.ROWS; row++)
        {
            for (int col = 0; col < TTTBoard.COLS; col++)
            {
                tiles[row][col].setText(" ");
            }
        }
        statusLabel.setText("Player X's turn");
    }
}
