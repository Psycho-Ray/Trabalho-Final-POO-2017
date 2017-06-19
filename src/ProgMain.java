import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.MainFrame;

public class ProgMain {
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame("Trabalho Final POO 2017");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		
		if(JOptionPane.showConfirmDialog(null, "Utilize o quadro em branco para desenhar um novo labirinto e o menu à esquerda para carregar/salvar labirintos ou limpar o quadro.\n"
			+ "Você pode selecionar as cores de pintura na paleta de cores.\n\nLegenda:\n"
			+ "Branco: espaço em branco\n"
			+ "Preto: parede\n"
			+ "Vermelho: ponto inicial\n"
			+ "Verde: ponto final\n\n"
			+ "Trabalho realizado por:\n"
			+ "Igor Trevelin Xavier da Silva\n"
			+ "Rodrigo Anes Sena de Araujo\n"
			+ "Alex Sander Ribeiro da Silva",
			"Info",
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION)
			mainFrame.setVisible(true);
		else
			return;
	}
}
