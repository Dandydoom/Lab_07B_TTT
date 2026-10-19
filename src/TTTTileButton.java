import javax.swing.JButton;
import java.awt.Font;

/**
 * TTTTileButton - a JButton that remembers its row and column on the board.
 */
public class TTTTileButton extends JButton
{
    private final int row;
    private final int col;

    public TTTTileButton(int row, int col)
    {
        super(" ");
        this.row = row;
        this.col = col;
        setFont(new Font("SansSerif", Font.BOLD, 48));
        setFocusPainted(false);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
