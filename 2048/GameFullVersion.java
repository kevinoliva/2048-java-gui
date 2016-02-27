import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

/*
 * Recreation of the game 2048.
 * Author: Kevin Oliva
 */

public class GameFullVersion extends Canvas implements Runnable{
	
	public GameFullVersion() {
		this.addKeyListener(new KeyBoard());
		new Display("2048 by Kevin Oliva", this);
		
	}
//////////////////////    Global Variables    /////////////////////////////////
	private static final long serialVersionUID = 6595253647575224934L;
	public static GameBoard board = new GameBoard();
	private Thread thread;
	private boolean isRunning = false;
	
	 
	
//////////////////////    Additional Methods    ///////////////////////////////	
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	 }
	
	public synchronized void stop() {
		try {
			thread.join();
			isRunning = false;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	public void run() {
		// Auto-generated method stub
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				delta--;
			}
			if(isRunning){
				render();
			}
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 510, 540);
		//create grid
		int x = 30;
		int y = 50;
		for(int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				g.setColor(Color.GRAY);
				x = 20 + (120*i);
				y = 20 + (120*j);
				g.fillRect(x, y, 100, 100);
				g.setColor(Color.BLACK);
				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
				
				String num = board.getBoardList().get(j*4+i).toString();
				int n = Integer.valueOf(board.getBoardList().get(j*4+i).toString()).intValue();
				if (n != 0) {
					switch (n) {
						case 2: g.setColor(new Color(32, 128, 232)); break;
						case 4: g.setColor(new Color(13, 236, 236)); break;
						case 8: g.setColor(new Color(19, 230, 130)); break;
						case 16: g.setColor(new Color(191, 249, 157)); break;
						case 32: g.setColor(new Color(241, 248, 10)); break;
						case 64: g.setColor(new Color(240, 163, 17)); break;
						case 128: g.setColor(new Color(242, 106, 15)); break;
						case 256: g.setColor(new Color(238, 24, 19)); break;
						case 512: g.setColor(new Color(242, 15, 168)); break;
						case 1024: g.setColor(new Color(210, 19, 238)); break;
						case 2048: g.setColor(new Color(227, 227, 227)); break;
					}
					g.fillRect(x, y, 100, 100);
					g.setColor(Color.BLACK);
					g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
					if (num.length() == 4) g.drawString(num, x+5, y+60);
					if (num.length() == 3) g.drawString(num, x+15, y+60);
					if (num.length() == 2) g.drawString(num, x+25, y+60);
					if (num.length() == 1) g.drawString(num, x+35, y+60);
				}
			}
		}
		g.dispose();
		bs.show();
	}
	
	//adds spaces for printing the value on the game board
	public static String whitespace(int count, String spaces) {
		for (int i = 0; i < count; i++) {spaces += " ";}
		return spaces;
	}
	
	//function used to combine rows and columns in the swipe functions
	public static List merge(List col) {
		//fill in gaps
		for (int i = 0; i < 4; i++) {
			if (!col.get(i).equals(new Integer(0))) { //find an existing element
				for (int j = 0; j < i; j++) { //move it to the first available slot
					if (col.get(j).equals(new Integer(0))) {
						col.set(j, col.get(i));
						col.set(i, new Integer(0));
						break;
					}
				}
				
			}
		}
		//merge identical values
		for (int k = 0; k < 3; k++) {
			if (!col.get(k).equals(new Integer(0)) && col.get(k).equals(col.get(k+1))) {
				Integer convert = (Integer) col.get(k);
				int dbl = convert.intValue() * 2;
				col.set(k, new Integer(dbl));
				col.set(k+1, new Integer(0));
				for (int l = k+2; l < 4; l++) {col.set(l-1, col.get(l));}
				col.set(3, new Integer(0));
			}
		}
		return col;
	}
	
	public static GameBoard swipeUp(GameBoard gb) {
		for (int i = 1; i < 5; i++) {
			List temp = merge(gb.getColumn(i));
			gb.setColumn(i, temp);
		}
		return gb;
	}
	
	public static GameBoard swipeDown(GameBoard gb) {
		for (int i = 1; i < 5; i++) {
			//first reverse column you receive 
			List temp = new ArrayList();
			List temp2 = new ArrayList();
			for (int j = 0; j < 4; j++) {temp.add(gb.getColumn(i).get(3-j));}
			temp = merge(temp);
			//undo reverse
			for (int k = 0; k < 4; k++) {temp2.add(temp.get(3-k));}
			gb.setColumn(i, temp2);
		}
		return gb;
	}
	
	public static GameBoard swipeLeft(GameBoard gb) {
		for (int i = 1; i < 5; i++) {
			List temp = merge(gb.getRow(i));
			gb.setRow(i, temp);
		}
		return gb;
	}
	
	public static GameBoard swipeRight(GameBoard gb) {
		for (int i = 1; i < 5; i++) {
			//first reverse row you receive 
			List temp = new ArrayList();
			List temp2 = new ArrayList();
			for (int j = 0; j < 4; j++) {temp.add(gb.getRow(i).get(3-j));}
			temp = merge(temp);
			//undo reverse
			for (int k = 0; k < 4; k++) {temp2.add(temp.get(3-k));}
			gb.setRow(i, temp2);
		}
		return gb;
	}
	
	//helps check for game termination and board updating
	public static List canMerge(GameBoard gb) {
		List fullCol = new ArrayList();
		List fullRow = new ArrayList();
		List direction = new ArrayList();
		for (int i = 1; i < 5; i++) {
			fullCol.add(gb.getColumn(i));
			fullRow.add(gb.getRow(i));
		} //return list of directions that can merge
		//check up
		List temp = new ArrayList();
		for (int i = 1; i < 5; i++) {
			temp.add(merge(gb.getColumn(i)));
		}
		if (!temp.equals(fullCol)) {direction.add(new Integer(1));}
		//check down
		temp = new ArrayList();
		for (int i = 1; i < 5; i++) {
			//first reverse column you receive 
			List temp1 = new ArrayList();
			List temp2 = new ArrayList();
			for (int j = 0; j < 4; j++) {temp1.add(gb.getColumn(i).get(3-j));}
			temp1 = merge(temp1);
			//undo reverse
			for (int k = 0; k < 4; k++) {temp2.add(temp1.get(3-k));}
			temp.add(temp2);
		}
		if (!temp.equals(fullCol)) {direction.add(new Integer(2));}
		//check left
		temp = new ArrayList();
		for (int i = 1; i < 5; i++) {
			temp.add(merge(gb.getRow(i)));
		}
		if (!temp.equals(fullRow)) {direction.add(new Integer(3));}
		//check right
		temp = new ArrayList();
		for (int i = 1; i < 5; i++) {
			//first reverse row you receive 
			List temp1 = new ArrayList();
			List temp2 = new ArrayList();
			for (int j = 0; j < 4; j++) {temp1.add(gb.getRow(i).get(3-j));}
			temp1 = merge(temp1);
			//undo reverse
			for (int k = 0; k < 4; k++) {temp2.add(temp1.get(3-k));}
			temp.add(temp2);
		}
		if (!temp.equals(fullRow)) {direction.add(new Integer(4));}
		return direction;
	}
	
	public static boolean winCheck(GameBoard gb) {
		for (int i = 0; i < 16; i++) {
			if (gb.getBoardList().get(i).equals(new Integer(2048))) {
				int answer = JOptionPane.showConfirmDialog(null, "YOU WIN!!! "
						+ "\nWould you like to restart?", "Congratulations!", 
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					board = new GameBoard();
					board.addRandom();
					board.addRandom();
				}
				else return true;
			}
		}
		return false;
	}

/////////////////////    Main Program    //////////////////////////////////////	

	public static void main(String[] args) {
		new GameFullVersion();
		
		//initialize game board
		board.addRandom();
		board.addRandom();
		
		//show instructions
		JOptionPane.showMessageDialog(null, "Use the arrow keys to move the "
				+ "tiles.", "Directions:", JOptionPane.INFORMATION_MESSAGE);
		
		//game loop
		while (!winCheck(board)) {
			if (board.getFreeList().isEmpty() && canMerge(board).isEmpty()) {
				int answer = JOptionPane.showConfirmDialog(null, "GAME OVER!!! "
						+ "\nWould you like to restart?", "Sorry.", 
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					board = new GameBoard();
					board.addRandom();
					board.addRandom();
				}
				else break;
			}
		}
		System.exit(0);
	}

}