package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

/**Representa um quadrado 10x10 px que será contido pelo quadro de desenho.
 * @author Igor Trevelin
 * @author Alex Sander
 * @author Rodrigo Anes
 */
@SuppressWarnings("serial")
public class BoardSquare extends JComponent implements MouseListener {
	private Color color;
	public static final int width = 10;
	public static final int height = 10;
	public static boolean mousePressed = false;
	
	/**Construtor da classe BoardSquare.
	 * 
	 */
	public BoardSquare() {
		color = Color.WHITE;
		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
	}
	
	/**Altera a cor do quadrado.
	 * @param color Nova cor.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**Retorna a cor atual do quadrado.
	 * @return Cor do quadrado.
	 */
	public Color getColor() {
		return color;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mousePressed = true;
			
			BoardSquare square = (BoardSquare) e.getSource();
			square.setColor(MainFrame.colorPalette.getSelectedColor());
			repaint();
		}	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mousePressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(MainFrame.crosshairCursor);
		if(mousePressed) {
			BoardSquare square = (BoardSquare) e.getSource();
			square.setColor(MainFrame.colorPalette.getSelectedColor());
			repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {}
}
