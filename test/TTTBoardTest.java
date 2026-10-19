import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TTTBoard.
 *
 * @author Kirby Fortney
 */
class TTTBoardTest
{
    private TTTBoard board;

    @BeforeEach
    void setUp() { board = new TTTBoard(); }

    @Test
    void newBoardIsEmpty()
    {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                assertEquals(TTTBoard.EMPTY, board.getCell(r, c));
    }

    @Test
    void validAndInvalidMoves()
    {
        assertTrue(board.isValidMove(1, 1));
        assertTrue(board.setMove(1, 1, "X"));
        assertFalse(board.isValidMove(1, 1));
        assertFalse(board.setMove(1, 1, "O"));
        assertEquals("X", board.getCell(1, 1));
        assertFalse(board.isValidMove(3, 0));
        assertFalse(board.isValidMove(-1, 2));
    }

    @Test
    void rowWin()
    {
        board.setMove(0, 0, "X"); board.setMove(0, 1, "X"); board.setMove(0, 2, "X");
        assertTrue(board.isWin("X"));
        assertFalse(board.isWin("O"));
    }

    @Test
    void columnWin()
    {
        board.setMove(0, 2, "O"); board.setMove(1, 2, "O"); board.setMove(2, 2, "O");
        assertTrue(board.isWin("O"));
    }

    @Test
    void diagonalWins()
    {
        board.setMove(0, 0, "X"); board.setMove(1, 1, "X"); board.setMove(2, 2, "X");
        assertTrue(board.isWin("X"));
        board.clear();
        board.setMove(0, 2, "O"); board.setMove(1, 1, "O"); board.setMove(2, 0, "O");
        assertTrue(board.isWin("O"));
    }

    @Test
    void tieWhenFullWithNoWinner()
    {
        // X O X
        // X O O
        // O X X
        String[] marks = {"X","O","X","X","O","O","O","X","X"};
        for (int i = 0; i < 9; i++) board.setMove(i / 3, i % 3, marks[i]);
        assertTrue(board.isFull());
        assertFalse(board.isWin("X"));
        assertFalse(board.isWin("O"));
        assertTrue(board.isTie());
    }

    @Test
    void notATieOnEmptyBoard()
    {
        assertFalse(board.isTie());
    }

    @Test
    void clearResetsBoard()
    {
        board.setMove(0, 0, "X");
        board.clear();
        assertTrue(board.isValidMove(0, 0));
    }
}
