import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoard extends KeyAdapter{
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
			case KeyEvent.VK_UP: 
				if (GameFullVersion.canMerge(GameFullVersion.board).contains(new Integer(1))) {
					GameFullVersion.board = GameFullVersion.swipeUp(GameFullVersion.board);
					//TODO: animate objects up
					
					if (GameFullVersion.board.getFreeList().size() != 0) {GameFullVersion.board.addRandom();}
					break;
				}
				break;
			case KeyEvent.VK_DOWN: 
				if (GameFullVersion.canMerge(GameFullVersion.board).contains(new Integer(2))) {
					GameFullVersion.board = GameFullVersion.swipeDown(GameFullVersion.board);
					//TODO: animate objects down
					
					if (GameFullVersion.board.getFreeList().size() != 0) {GameFullVersion.board.addRandom();}
					break;
				}
				break;
			case KeyEvent.VK_LEFT: 
				if (GameFullVersion.canMerge(GameFullVersion.board).contains(new Integer(3))) {
					GameFullVersion.board = GameFullVersion.swipeLeft(GameFullVersion.board);
					//TODO: animate objects left
					
					if (GameFullVersion.board.getFreeList().size() != 0) {GameFullVersion.board.addRandom();}
					break;
				}
				break;
			case KeyEvent.VK_RIGHT: 
				if (GameFullVersion.canMerge(GameFullVersion.board).contains(new Integer(4))) {
					GameFullVersion.board = GameFullVersion.swipeRight(GameFullVersion.board);
					//TODO: animate objects right
					
					if (GameFullVersion.board.getFreeList().size() != 0) {GameFullVersion.board.addRandom();}
					break;
				}
				break;
			default: break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	}
}

