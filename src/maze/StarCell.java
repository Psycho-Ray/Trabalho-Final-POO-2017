package maze;

import java.awt.Point;

public class StarCell implements Comparable<StarCell>{
	public int x,y,d,t;
	private Point p;
	
	public StarCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.d = 0;
		this.t = Integer.MAX_VALUE;
	}
	
	public StarCell(int x, int y, Point goal) {
		this.x = x;
		this.y = y;
		this.d = 0;
		this.t = d + ManhattanDistance(goal);
	}
	
	public StarCell(int x, int y, int d, Point goal) {
		this.x = x;
		this.y = y;
		this.d = d;
		this.t = d + ManhattanDistance(goal);
	}
	
	public StarCell(Point p, int d, Point goal) {
		this.x = p.x;
		this.y = p.y;
		this.d = d;
		this.d = d + ManhattanDistance(goal);
	}
	
	public StarCell(StarCell aux, Point goal) {
		this.x = aux.x;
		this.y = aux.y;
		this.d = aux.d;
		this.d = d + ManhattanDistance(goal);
	}
	
	public Point point() {
		if (p == null) p = new Point(this.x, this.y);
		return p;
	}
	
	//Retorna a distância até o ponto destino
	private int ManhattanDistance(Point goal) {
		return Math.abs(goal.x - this.x) + Math.abs(goal.y - this.y);
	}
	
	public int nextT(int dIncrement, Point goal) {
		return d + dIncrement + ManhattanDistance(goal);
	}
	
	public void update(int dIncrement, Point goal) {
		this.d += dIncrement;
		this.t = this.d + ManhattanDistance(goal);
	}
	
	@Override
	public int compareTo(StarCell aux) {
		//Maior = Maior t, ou menor y, ou menor x, nessa ordem.
		if (this.t != aux.t) return this.t - aux.t;
		if (this.y != aux.y) return - (this.y - aux.y);
		return - (this.x - aux.x);
	}
}
