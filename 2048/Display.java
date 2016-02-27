import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends Canvas{

	private static final long serialVersionUID = -1725514168658036790L;
	private JFrame frame;
	private final int WIDTH = 510;
	private final int HEIGHT = 540;
	
	public Display(String title, GameFullVersion game) {
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}
