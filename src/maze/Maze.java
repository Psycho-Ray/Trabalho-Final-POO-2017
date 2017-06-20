package maze;

import java.awt.Point;
import java.util.*;

public class Maze {
	private LinkedList<Point> exits;
	
	public Point entrance;
	public byte[][] source;
	private PathFinder solver;
	
	public Maze(byte source[][]) {
		this.source = source;
		this.solver = new PathFinder(source);
		this.exits = new LinkedList<Point>();
		firstRun();
	}
	
	private void firstRun() {
		//Percorre o labirinto
		for (int i=0; i < source.length; i++) {
			for (int j=0; j < source[i].length; j++) {
				//Atualiza a entrada (só pode haver uma)
				if (source[i][j] == 2) {
					if (entrance == null) entrance = new Point(i, j);
					//else ADCIONAR EXCESSÃO, DUAS ENTRADAS
				}
				
				//Adiciona a saída à lista de saídas conhecidas
				else if (source[i][i] == 3) exits.add(new Point(i, j));
			}
		}
		
		//Caso entrada ou saída não encontrada
		//if (entrance == null) ADICIONAR EXCESSÃO LABIRINTO SEM ENTRADA
		//if (exits.size() == 0) ADICIONAR EXCESSÃO LABIRINTO SEM SAÍDA
	}
	
	public ArrayList<LinkedList<Point>> dfs() {
		return solver.dfs(entrance, exits.size());
	}
	
	public ArrayList<LinkedList<Point>> bfs() {
		return solver.bfs(entrance, exits.size());
	}
	
	public LinkedList<Point> showFootPrint() {
		return solver.showFootPrint();
	}
}
