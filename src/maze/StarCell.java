package maze;

import java.awt.Point;

public class StarCell implements Comparable<StarCell>{
	public int x,y,d,t;
	private Point p;
	
	public StarCell(int x, int y, Point goal) {
		this.x = x;
		this.y = y;
		this.d = 0;
		this.t = update(goal);
	}
	
	public StarCell(int x, int y, int d, Point goal) {
		this.x = x;
		this.y = y;
		this.d = d;
		this.t = update(goal);
	}
	
	public StarCell(Point p, int d, Point goal) {
		this.x = p.x;
		this.y = p.y;
		this.d = d;
		this.t = update(goal);
	}
	
	public StarCell(StarCell aux, Point goal) {
		this.x = aux.x;
		this.y = aux.y;
		this.d = aux.d;
		this.t = update(goal);
	}
	
	public Point point() {
		return (p == null) ? new Point(this.x, this.y) : p;
	}
	
	//Retorna a distância até o ponto destino
	private int ManhattanDistance(Point goal) {
		return Math.abs(goal.x - this.x) + Math.abs(goal.y - this.x);
	}
	
	//Retorna a distância total utilizada para o algoritmo A*
	public int update(Point goal) {
		return d + ManhattanDistance(goal);
	}
	
	@Override
	public int compareTo(StarCell aux) {
		//Maior = Maior t, ou maior x, ou maior y, nessa ordem.
		if (this.t != aux.t) return this.t - aux.t;
		if (this.x != aux.x) return this.x - aux.x;
		return this.y - aux.y;
	}
}
