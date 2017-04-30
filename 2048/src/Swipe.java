import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

/**
 * Created by ellecheung on 4/29/17.
 */
public class Swipe implements KeyListener{
    BoardState board;
    Swipe () {
        board = new BoardState();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_UP) {
            board.up();
            board.printBoard();
        } else if (e.getKeyChar() == KeyEvent.VK_DOWN) {
            board.down();
            board.printBoard();
        } else if (e.getKeyChar() == KeyEvent.VK_LEFT) {
            board.left();
            board.printBoard();
        } else if (e.getKeyChar() == KeyEvent.VK_RIGHT) {
            board.right();
            board.printBoard();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        return;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    public static void main(String[] args) {
        Swipe x = new Swipe();
    }
}
