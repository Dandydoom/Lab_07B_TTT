/**
 * TTTGame - the game logic / rules.
 * Tracks whose turn it is and how many moves have been made, applies moves
 * to the TTTBoard, and decides when the game is won or tied.
 * Has no Swing code, so it can be tested with JUnit.
 */
public class TTTGame
{
    public enum MoveResult { INVALID, CONTINUE, WIN, TIE }

    private static final int MOVES_FOR_WIN = 5;   // earliest possible win
    private static final int MOVES_FOR_TIE = 7;   // earliest possible tie

    private final TTTBoard board = new TTTBoard();
    private String currentPlayer = "X";
    private int moveCount = 0;

    /**
     * Attempts a move for the current player.
     * On WIN or TIE the current player is left as the player who just moved.
     * On CONTINUE the turn passes to the other player.
     */
    public MoveResult makeMove(int row, int col)
    {
        if (!board.setMove(row, col, currentPlayer))
            return MoveResult.INVALID;

        moveCount++;

        if (moveCount >= MOVES_FOR_WIN && board.isWin(currentPlayer))
            return MoveResult.WIN;

        if (moveCount >= MOVES_FOR_TIE && (board.isTie() || board.isFull()))
            return MoveResult.TIE;

        switchPlayer();
        return MoveResult.CONTINUE;
    }

    private void switchPlayer()
    {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    /** Clears the board and starts a fresh game with X going first. */
    public void reset()
    {
        board.clear();
        currentPlayer = "X";
        moveCount = 0;
    }

    public String getCurrentPlayer() { return currentPlayer; }
    public int getMoveCount()        { return moveCount; }
    public TTTBoard getBoard()       { return board; }
}
