package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JPanel;
import java.util.Iterator;

/** Representa o quadro onde o usuário desenha o labirinto.
 * @author Igor Trevelin
 *
 */
@SuppressWarnings("serial")
public class Board extends JPanel {
	private BoardSquare[][] board;
	
	/**Construtor da classe Board
	 * 
	 */
	public Board() {
		setLayout(new GridLayout(64, 64, 1, 1));
		setBackground(Color.BLACK);
		
		board = new BoardSquare[64][64];
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				board[i][j] = new BoardSquare();
				add(board[i][j]);
			}
		}
		
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setCursor(MainFrame.crosshairCursor);
			}
			
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor());
			}
		});
	}
	
	/**Carrega um labirinto de um arquivo e atualiza o quadro.
	 * @param file Arquivo de entrada contendo o labirinto.
	 * @return true caso a operação ocorra com sucesso, false caso contrário.
	 */
	public boolean loadBoardFromFile(File file) {
		if(file.isDirectory()) return false;
		if(file.length() != (64 * 64))
			return false;
		
		try {
			FileInputStream istream = null;
			Vector<Byte> vector = new Vector<Byte>();
			istream = new FileInputStream(file);
			byte data;
			for(int i = 0; i < 64; i++) {
				for(int j = 0; j < 64; j++) {
					if((data = (byte) istream.read()) != -1)
						vector.add(new Byte(data));
				}
			}
			istream.close();
			
			if(vector.size() != (64 * 64)) {
				vector.clear();
				vector = null;
				return false;
			}
			
			Iterator<Byte> iterator = vector.iterator();
			for(int i = 0; i < 64; i++) {
				for(int j = 0; j < 64; j++) {
					Byte b = iterator.next();
					switch(b.byteValue()) {
					case 1:
						board[i][j].setColor(Color.BLACK);
						break;
					case 2:
						board[i][j].setColor(Color.RED);
						break;
					case 3:
						board[i][j].setColor(Color.GREEN);
						break;
					}
				}
			}
			repaint();
			
			vector.clear();
			vector = null;
			return true;
		}
		catch(FileNotFoundException fnfe) {
			return false;
		}
		catch(IOException ioe) {
			return false;
		}
	}
	
	/**Salva o labirinto desenhado no quadro em um arquivo.
	 * @param filePath O caminho do arquivo.
	 * @return true caso a operação ocorra com sucesso, false caso contrário.
	 */
	public boolean saveBoardToFile(String filePath) {
		try {
			FileOutputStream ostream = new FileOutputStream(filePath);
			for(int i = 0; i < 64; i++) {
				for(int j = 0; j < 64; j++) {
					if(board[i][j].getColor() == Color.WHITE)
						ostream.write(0);
					else if(board[i][j].getColor() == Color.BLACK)
						ostream.write(1);
					else if(board[i][j].getColor() == Color.RED)
						ostream.write(2);
					else if(board[i][j].getColor() == Color.GREEN)
						ostream.write(3);
				}
			}
			ostream.close();
			return true;
		}
		catch(FileNotFoundException fnfe) {
			return false;
		}
		catch(SecurityException se) {
			return false;
		}
		catch(IOException ioe) {
			return false;
		}
	}
	
	/**Limpa o quadro.
	 * 
	 */
	public void clear() {
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				board[i][j].setColor(Color.WHITE);
			}
		}
		repaint();
	}
	
	/**Retorna uma matriz bidimensional de bytes de tamanho 64x64 contendo
	 * uma sumarização do labirinto desenhado.
	 * @return Matriz bidimensional do tipo byte[][]
	 */
	public byte[][] toBytes() {
		byte[][] bytes = new byte[64][64];
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				if(board[i][j].getColor() == Color.WHITE)
					bytes[i][j] = 0;
				else if(board[i][j].getColor() == Color.BLACK)
					bytes[i][j] = 1;
				else if(board[i][j].getColor() == Color.RED)
					bytes[i][j] = 2;
				else if(board[i][j].getColor() == Color.GREEN)
					bytes[i][j] = 3;
			}
		}
		return bytes;
	}
	
	public int isValidMaze() {
		int entrances = 0, exits = 0;
		
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				if(board[i][j].getColor() == Color.RED)
					entrances++;
				else if(board[i][j].getColor() == Color.GREEN)
					exits++;
			}
		}
		
		if(entrances != 1)
			return 2;
		
		if(exits < 1)
			return 3;
		
		return 1;
	}
}
