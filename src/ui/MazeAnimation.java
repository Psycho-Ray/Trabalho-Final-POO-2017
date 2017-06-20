package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import maze.Maze;

@SuppressWarnings("serial")
public class MazeAnimation extends JFrame implements Runnable, KeyListener {
	private JPanel canvas;
	private AnimationSquare[][] maze;
	private Maze mazeObj;
	private long msInterval;
	private Thread runner;
	
	public MazeAnimation(byte[][] byteMaze) {
		super("Animation Frame");
		setAlwaysOnTop(true);
		setLayout(new FlowLayout());
		setBackground(Color.BLACK);
		
		canvas = new JPanel(new GridLayout(64, 64, 1, 1));
		
		msInterval = 500l;
		mazeObj = new Maze(byteMaze);
		
		maze = new AnimationSquare[64][64];
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				maze[i][j] = new AnimationSquare();
				switch(byteMaze[i][j]) {
				case 1:
					maze[i][j].setColor(Color.BLACK);
					break;
				case 2:
					maze[i][j].setColor(Color.RED);
					break;
				case 3:
					maze[i][j].setColor(Color.GREEN);
					break;
				}
				canvas.add(maze[i][j]);
			}
		}
		canvas.setAlignmentX(CENTER_ALIGNMENT);
		canvas.setAlignmentY(CENTER_ALIGNMENT);
		canvas.setBackground(MainFrame.DEFAULT_BACKGROUND_COLOR);
		add(canvas);
		setSize(canvas.getPreferredSize());
		setVisible(true);
		
		runner = new Thread(this);
		runner.start();
	}
	
	@Override
	public void run() {
		ArrayList<LinkedList<Point>> solutions;
		
		if(mazeObj != null) {
			String[] options = {
				"Busca em largura",
				"Busca em profundidade"
			};
			
			String selectedOption = (String) JOptionPane.showInputDialog(this,
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
			else
				solutions = mazeObj.dfs();
		}
		else {
			JOptionPane.showMessageDialog(this, "Não foi possível executar a animação.",
				"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
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
			selectedSolutionIndex = Integer.parseInt(result) - 1;
			
			LinkedList<Point> sol = solutions.get(selectedSolutionIndex);
			
			if(sol.size() > 0) {
				for(Point p : sol) {
					maze[p.x][p.y].setColor(Color.BLUE);
					maze[p.x][p.y].repaint();
					try {
						Thread.sleep(msInterval);
					} catch(InterruptedException ie) {}
				}
				
				JOptionPane.showMessageDialog(this, "A execução foi concluída.", "Fim", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Não foi possível executar a solução.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			dispose();
			return;
		}
		else {
			JOptionPane.showMessageDialog(this, "Não foi encontrada nenhuma solução para o labirinto.", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}

@SuppressWarnings("serial")
class AnimationSquare extends JComponent {
	private Color color;
	
	public AnimationSquare() {
		this.color = Color.WHITE;
		setPreferredSize(new Dimension(10, 10));
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, 10, 10);
	}
}
