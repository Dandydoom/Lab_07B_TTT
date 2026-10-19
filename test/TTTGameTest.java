import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for TTTGame.
 *
 * @author Kirby Fortney
 */
class TTTGameTest
{
    private TTTGame game;

    @BeforeEach
    void setUp() { game = new TTTGame(); }

    @Test
    void xGoesFirstAndTurnsAlternate()
    {
        assertEquals("X", game.getCurrentPlayer());
        assertEquals(TTTGame.MoveResult.CONTINUE, game.makeMove(0, 0));
        assertEquals("O", game.getCurrentPlayer());
        assertEquals(TTTGame.MoveResult.CONTINUE, game.makeMove(1, 1));
        assertEquals("X", game.getCurrentPlayer());
    }

    @Test
    void invalidMoveDoesNotChangeTurn()
    {
        game.makeMove(0, 0);
        assertEquals(TTTGame.MoveResult.INVALID, game.makeMove(0, 0));
        assertEquals("O", game.getCurrentPlayer());
        assertEquals(1, game.getMoveCount());
    }

    @Test
    void xWinsTopRow()
    {
        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        assertEquals(TTTGame.MoveResult.WIN, game.makeMove(0, 2)); // X
        assertEquals("X", game.getCurrentPlayer());
    }

    @Test
    void tieGame()
    {
        // After 8 moves: X O X / X O O / O X _
        // Every row, column, and diagonal is blocked, so the tie is detected early.
        int[][] moves = {{0,0},{0,1},{0,2},{1,1},{1,0},{1,2},{2,1}};
        for (int[] m : moves)
            assertEquals(TTTGame.MoveResult.CONTINUE, game.makeMove(m[0], m[1]));
        assertEquals(TTTGame.MoveResult.TIE, game.makeMove(2, 0));
    }

    @Test
    void resetStartsNewGame()
    {
        game.makeMove(0, 0);
        game.reset();
        assertEquals("X", game.getCurrentPlayer());
        assertEquals(0, game.getMoveCount());
        assertTrue(game.getBoard().isValidMove(0, 0));
    }
}
