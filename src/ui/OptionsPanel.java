package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


/**Representa o menu com as seguinte opções: Carregar labirinto de arquivo,
 * salvar labirinto em arquivo, limpar o quadro, 
 * @author Igor Trevelin
 *
 */
@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	private JButton loadBoardBtn;
	private JButton saveBoardBtn;
	private JButton clearBoardBtn;
	private JButton runBtn;
	private JFileChooser fileChooser;
	
	public OptionsPanel() {
		setLayout(new GridLayout(4, 1, 5, 5));
		setBackground(MainFrame.DEFAULT_BACKGROUND_COLOR);
		
		loadBoardBtn = new JButton("Abrir");
		saveBoardBtn = new JButton("Salvar");
		clearBoardBtn = new JButton("Limpar");
		runBtn = new JButton("Executar");
		
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("LAB files", "lab"));
		
		
		loadBoardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setDialogTitle("Load labyrinth...");
				fileChooser.setDialogType(JFileChooser. OPEN_DIALOG);
				
				int result = fileChooser.showOpenDialog(MainFrame.optionsPanel);	
				if(result == JFileChooser.APPROVE_OPTION) {
					if(!MainFrame.board.loadBoardFromFile(fileChooser.getSelectedFile())) {
						JOptionPane.showMessageDialog(null, "N�o foi poss�vel carregar o arquivo!",
								"Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		saveBoardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setDialogTitle("Save labyrinth...");
				fileChooser.setDialogType(JFileChooser. SAVE_DIALOG);
				
				int result = fileChooser.showSaveDialog(MainFrame.optionsPanel);
				if(result == JFileChooser.APPROVE_OPTION) {
					String str = fileChooser.getSelectedFile().toString();
					if(!str.endsWith(".lab"))
						str = str.concat(".lab");
					if(!MainFrame.board.saveBoardToFile(str)) {
						JOptionPane.showMessageDialog(null, "N�o foi poss�vel salvar o labirinto!",
								"Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		clearBoardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.board.clear();
			}
		});
		
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MainFrame.board.isValidMaze()) {
					Thread thread = new Thread(new MazeAnimation(MainFrame.board.toBytes()));
					thread.run();
				}
				else {
					JOptionPane.showMessageDialog(null, "Verifique se o labirinto possui pelo menos 1 entra e 1 saída!",
						"Labirinto Inválido",
						JOptionPane.ERROR_MESSAGE
					);
				}
			}
		});
		
		add(loadBoardBtn);
		add(saveBoardBtn);
		add(clearBoardBtn);
		add(runBtn);
	}
}
