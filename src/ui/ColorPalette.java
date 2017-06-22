package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**Representa a paleta de cores.
 * @author Igor Trevelin
 * @author Alex Sander
 * @author Rodrigo Anes
 */
@SuppressWarnings("serial")
public class ColorPalette extends JPanel {
	private ColorSelector[] colorSelectors;
	
	private JLabel selectColorLabel;
	private JLabel selectedColorLabel;
	
	/**Construtor da classe ColorPalette.
	 * 
	 */
	public ColorPalette() {
		setLayout(new GridLayout(7, 1, 5, 5));
		setBackground(MainFrame.DEFAULT_BACKGROUND_COLOR);
		
		colorSelectors = new ColorSelector[5];
		colorSelectors[0] = new ColorSelector(Color.WHITE);
		colorSelectors[1] = new ColorSelector(Color.BLACK);
		colorSelectors[2] = new ColorSelector(Color.RED);
		colorSelectors[3] = new ColorSelector(Color.GREEN);
		
		colorSelectors[4] = new ColorSelector(Color.BLACK);
		
		selectColorLabel = new JLabel("Paleta de Cores");
		selectColorLabel.setBackground(MainFrame.DEFAULT_BACKGROUND_COLOR);
		add(selectColorLabel);
		
		for(int i = 0; i < 4; i++) {
			add(colorSelectors[i]);
		}
		
		selectedColorLabel = new JLabel("Cor Selecionada");
		selectedColorLabel.setBackground(MainFrame.DEFAULT_BACKGROUND_COLOR);
		add(selectedColorLabel);
		
		add(colorSelectors[4]);
	}
	
	/**Altera a cor de pintura.
	 * @param color Objeto do tipo Color.
	 */
	public void setSelectedColor(Color color) {
		colorSelectors[4].setColor(color);
		colorSelectors[4].repaint();
	}
	
	/**Retorna a cor de pintura selecionada.
	 * @return Objeto do tipo Color.
	 */
	public Color getSelectedColor() {
		return colorSelectors[4].getColor();
	}
}

/**Representa um seletor para uma cor que pode ser selecionada na paleta de cores.
 * @author Igor Trevelin
 * @author Alex Sander
 * @author Rodrigo Anes
 */
@SuppressWarnings("serial")
class ColorSelector extends JComponent implements MouseListener {
	private Color color;
	
	/**Construtor da classe ColorSelector.
	 * @param color Objeto do tipo Color.
	 */
	ColorSelector(Color color) {
		this.color = color;
		setPreferredSize(new Dimension(20, 20));
		addMouseListener(this);
	}
	
	/**Retorna a cor do seletor.
	 * @return Objeto do tipo Color.
	 */
	public Color getColor() {
		return color;
	}
	
	/**Altera a cor do seletor.
	 * @param color Objeto do tipo Color.
	 */
	public void setColor(Color color) {
		this.color = color;
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
		MainFrame.colorPalette.setSelectedColor(color);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(MainFrame.handCursor);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Cursor.getDefaultCursor());
	}
}