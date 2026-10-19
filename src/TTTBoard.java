/**
 * TTTBoard - the 3x3 Tic Tac Toe board.
 * Holds the board state and owns all of the board-checking logic
 * (the isWin / isTie helper methods from the original GUI TTT now live here).
 *
 * @author Kirby Fortney
 */
public class TTTBoard
{
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String EMPTY = " ";

    private final String[][] board = new String[ROWS][COLS];

    public TTTBoard()
    {
        clear();
    }

    /** Sets every cell back to EMPTY. */
    public void clear()
    {
        for (int row = 0; row < ROWS; row++)
            for (int col = 0; col < COLS; col++)
                board[row][col] = EMPTY;
    }

    /** A move is valid if it is on the board and the cell is empty. */
    public boolean isValidMove(int row, int col)
    {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS
                && board[row][col].equals(EMPTY);
    }

    /** Places the player's mark. Returns false (and changes nothing) if the move is invalid. */
    public boolean setMove(int row, int col, String player)
    {
        if (!isValidMove(row, col))
            return false;
        board[row][col] = player;
        return true;
    }

    public String getCell(int row, int col)
    {
        return board[row][col];
    }

    public boolean isWin(String player)
    {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private boolean isRowWin(String player)
    {
        for (int row = 0; row < ROWS; row++)
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player))
                return true;
        return false;
    }

    private boolean isColWin(String player)
    {
        for (int col = 0; col < COLS; col++)
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player))
                return true;
        return false;
    }

    private boolean isDiagonalWin(String player)
    {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player))
            || (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    /** True when every cell has a mark. */
    public boolean isFull()
    {
        for (int row = 0; row < ROWS; row++)
            for (int col = 0; col < COLS; col++)
                if (board[row][col].equals(EMPTY))
                    return false;
        return true;
    }

    /**
     * A tie happens when every row, column, and diagonal contains both an X and an O,
     * so nobody can win anymore (this can be detected before the board is full).
     */
    public boolean isTie()
    {
        for (int row = 0; row < ROWS; row++)
            if (!isBlocked(board[row][0], board[row][1], board[row][2]))
                return false;
        for (int col = 0; col < COLS; col++)
            if (!isBlocked(board[0][col], board[1][col], board[2][col]))
                return false;
        if (!isBlocked(board[0][0], board[1][1], board[2][2]))
            return false;
        if (!isBlocked(board[0][2], board[1][1], board[2][0]))
            return false;
        return true;
    }

    private boolean isBlocked(String a, String b, String c)
    {
        boolean hasX = a.equals("X") || b.equals("X") || c.equals("X");
        boolean hasO = a.equals("O") || b.equals("O") || c.equals("O");
        return hasX && hasO;
    }
}
