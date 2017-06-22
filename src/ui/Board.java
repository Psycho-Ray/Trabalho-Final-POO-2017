package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import maze.Maze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/** Representa o quadro onde o usuário desenha o labirinto.
 * @author Igor Trevelin
 * @author Alex Sander
 * @author Rodrigo Anes
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements Runnable {
	private BoardSquare[][] board;
	private Maze mazeObj;
	private long msInterval;
	private byte[][] mazeByte;
	
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
	
	public void reset() {
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				if(mazeByte[i][j] == 0)
					board[i][j].setColor(Color.WHITE);
				else if(mazeByte[i][j] == 1)
					board[i][j].setColor(Color.BLACK);
				else if(mazeByte[i][j] == 2)
					board[i][j].setColor(Color.RED);
				else if(mazeByte[i][j] == 3)
					board[i][j].setColor(Color.GREEN);
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
	
	/**Verifica se o labirinto desenhado no quadro é válido.
	 * @return Número inteiro onde:
	 * 1: labirinto válido
	 * 2: número de entradas inválido
	 * 3: número de saídas inválido
	 */
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
	
	@Override
	public void run() {
		ArrayList<LinkedList<Point>> solutions;
		LinkedList<Point> footprint;
		String selectedOption;
		
		mazeByte = toBytes();
		mazeObj = new Maze(mazeByte);
		
		if(mazeObj != null) {
			String[] options = {
				"Busca em largura",
				"Busca em profundidade",
				"A*"
			};
			
			selectedOption = (String) JOptionPane.showInputDialog(this,
				"Selecione o algoritmo a ser utilizado:",
				"Algoritmo",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
				);
			
			if(selectedOption == "Busca em largura")
				solutions = mazeObj.bfs();
			else if(selectedOption == "Busca em profundidade")
				solutions = mazeObj.dfs();
			else if(selectedOption == "A*")
				solutions = mazeObj.AStar();
			else solutions = mazeObj.bfs();
			footprint = mazeObj.showFootPrint();
		}
		else {
			JOptionPane.showMessageDialog(this, "Não foi possível executar a animação.",
				"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
			msInterval = (15000l / footprint.size());
			if(msInterval > 250l)
				msInterval = 250l;
		
		for(Point p : footprint) {
			if(p != null) {
				if(board[p.x][p.y].getColor() == Color.BLUE)
					board[p.x][p.y].setColor(Color.GRAY);
				else
					board[p.x][p.y].setColor(Color.BLUE);
				board[p.x][p.y].repaint();
				Toolkit.getDefaultToolkit().sync();
				try {
					Thread.sleep(msInterval);
				} catch(InterruptedException ie) {}
			}
			else {
				if(selectedOption == "A*") {
					reset();
				}
			}
		}
		
		reset();
		
		if(solutions.size() > 0) {
			Vector<String> options = new Vector<String>();
			int selectedSolutionIndex = -1;
			for(int i = 0; i < solutions.size(); i++) {
				options.add(Integer.toString(i + 1));
			}
			Object[] optionsStrings = options.toArray();
			String result = (String) JOptionPane.showInputDialog(this,
				"Select the solution you want to run:",
				"Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				optionsStrings,
				optionsStrings[0]
				);
			try {
				selectedSolutionIndex = Integer.parseInt(result) - 1;
			} catch(NumberFormatException nfe) {
				return;
			}
			
			LinkedList<Point> sol = solutions.get(selectedSolutionIndex);
			
			if(sol.size() > 0) {
				msInterval = (5000l / sol.size());
				if(msInterval > 250l)
					msInterval = 250l;
				
				for(Point p : sol) {
					board[p.x][p.y].setColor(Color.BLUE);
					board[p.x][p.y].repaint();
					Toolkit.getDefaultToolkit().sync();
					try {
						Thread.sleep(msInterval);
					} catch(InterruptedException ie) {}
				}
				
				JOptionPane.showMessageDialog(this, "A execução foi concluída.", "Fim", JOptionPane.INFORMATION_MESSAGE);
				reset();
			}
			else {
				JOptionPane.showMessageDialog(this, "Não foi possível executar a solução.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		else {
			JOptionPane.showMessageDialog(this, "Não foi encontrada nenhuma solução para o labirinto.", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
