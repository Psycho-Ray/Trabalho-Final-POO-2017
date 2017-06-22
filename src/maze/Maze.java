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
				//Atualiza a entrada (sï¿½ pode haver uma)
				if (source[i][j] == 2) {
					if (entrance == null) entrance = new Point(i, j);
					//else ADCIONAR EXCESSï¿½O, DUAS ENTRADAS
				}
				
<<<<<<< HEAD
				//Adiciona a saï¿½da ï¿½ lista de saï¿½das conhecidas
=======
				//Adiciona a saída à lista de saídas conhecidas
>>>>>>> ee3ee1c1cb5dc8a9d77ed184521926949a0dacbd
				else if (source[i][j] == 3) exits.add(new Point(i, j));
			}
		}
		
		//Caso entrada ou saï¿½da nï¿½o encontrada
		//if (entrance == null) ADICIONAR EXCESSï¿½O LABIRINTO SEM ENTRADA
		//if (exits.size() == 0) ADICIONAR EXCESSï¿½O LABIRINTO SEM SAï¿½DA
	}
	
	public ArrayList<LinkedList<Point>> dfs() {
		return solver.dfs(entrance, exits.size());
	}
	
	public ArrayList<LinkedList<Point>> bfs() {
		return solver.bfs(entrance, exits.size());
	}
	
	public ArrayList<LinkedList<Point>> AStar() {
<<<<<<< HEAD
		//Vetor dos caminhos atï¿½ cada saï¿½da
		ArrayList<LinkedList<Point>> paths = new ArrayList<LinkedList<Point>>(exits.size());
		
		//Encontra cada saï¿½da...
=======
		//Vetor dos caminhos até cada saída
		ArrayList<LinkedList<Point>> paths = new ArrayList<LinkedList<Point>>(exits.size());
		
		//Encontra cada saída...
>>>>>>> ee3ee1c1cb5dc8a9d77ed184521926949a0dacbd
		for (Point exit : exits)
			paths.add(solver.AStar(source, entrance, exit));
		
		return paths;
	}
	
	public LinkedList<Point> showFootPrint() {
		return solver.showFootPrint();
	}
}
