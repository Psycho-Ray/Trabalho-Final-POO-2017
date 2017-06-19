package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**Representa a janela principal da aplicação.
 * @author Igor Trevelin
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	public static ColorPalette colorPalette;
	public static OptionsPanel optionsPanel;
	public static Board board;
	public static Cursor crosshairCursor;
	public static Cursor handCursor;
	public static Color DEFAULT_BACKGROUND_COLOR;
	
	private JMenuBar menuBar;
	
	
	/**Construtor da classe MainFrame.
	 * @param frameCaption Título da janela principal.
	 */
	public MainFrame(String frameCaption) {
		super(frameCaption);
		
		try{
			setIconImage(ImageIO.read(new File("./img/favicon.png")));
		}
		catch(IllegalArgumentException iae) {}
		catch(IOException ioe) {}
		
		DEFAULT_BACKGROUND_COLOR = new Color(51, 42, 42, 200);
		
		colorPalette = new ColorPalette();
		optionsPanel = new OptionsPanel();
		board = new Board();
		
		crosshairCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		handCursor = new Cursor(Cursor.HAND_CURSOR);
		
		initGUI();
	}
	
	private void initGUI() {
		setJMenuBar(menuBar);
		Container contentPane = getContentPane();
		setLayout(new FlowLayout());
		contentPane.setBackground(DEFAULT_BACKGROUND_COLOR);
		
		JPanel leftSizePanel = new JPanel(new GridLayout(2, 1));
		leftSizePanel.add(optionsPanel);
		leftSizePanel.add(colorPalette);
		contentPane.add(leftSizePanel);
		contentPane.add(board);
		pack();
	}
}
