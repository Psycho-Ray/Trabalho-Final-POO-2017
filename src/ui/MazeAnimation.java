package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MazeAnimation extends JFrame implements Runnable {
	private JPanel canvas;
	private BoardSquare[][] maze;
	private long msInterval;
	
	public MazeAnimation(byte[][] byteMaze) {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setUndecorated(true);
		setBackground(MainFrame.DEFAULT_BACKGROUND_COLOR);
		
		canvas = new JPanel(new GridLayout(64, 64, 1, 1));
		
		msInterval = 500l;
		
		maze = new BoardSquare[64][64];
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				maze[i][j] = new BoardSquare();
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
		add(canvas);
		
		setVisible(true);
	}

	@Override
	public void run() {
		LinkedList<Point> points = Maze.bfs();
		LinkedList<Point> footprint = Maze.footprint();
		
		long currentTimeMillis = System.currentTimeMillis();
		while(footprint.size() > 0) {
			
		}
	}
}
