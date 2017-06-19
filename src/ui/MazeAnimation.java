package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MazeAnimation extends JFrame implements Runnable, KeyListener {
	private JPanel canvas;
	private AnimationSquare[][] maze;
	private long msInterval;
	private boolean exitRequested;
	
	public MazeAnimation(byte[][] byteMaze) {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(new FlowLayout());
		setUndecorated(true);
		setBackground(MainFrame.DEFAULT_BACKGROUND_COLOR);
		
		canvas = new JPanel(new GridLayout(64, 64, 1, 1));
		
		exitRequested = false;
		msInterval = 500l;
		
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
		
		setFocusable(true);
		setVisible(true);
	}

	@Override
	public void run() {
		LinkedList<Point> points = Maze.bfs();
		LinkedList<Point> footprint = Maze.footprint();
		
		Point p = null;
		long lastTimeMillis = System.currentTimeMillis();
		while(footprint.size() > 0 && !exitRequested) {
			if((System.currentTimeMillis() - lastTimeMillis) >= msInterval) {
				 p = footprint.poll();
				 if(p != null) {
					 maze[p.x][p.y].setColor(Color.BLUE);
					 maze[p.x][p.y].repaint();
					 lastTimeMillis = System.currentTimeMillis();
				 }
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			exitRequested = true;
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
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, 10, 10);
	}
}
