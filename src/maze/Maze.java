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
				//Atualiza a entrada (s� pode haver uma)
				if (source[i][j] == 2) {
					if (entrance == null) entrance = new Point(i, j);
					//else ADCIONAR EXCESS�O, DUAS ENTRADAS
				}
				
				//Adiciona a sa�da � lista de sa�das conhecidas
				else if (source[i][j] == 3) exits.add(new Point(i, j));
			}
		}
		
		//Caso entrada ou sa�da n�o encontrada
		//if (entrance == null) ADICIONAR EXCESS�O LABIRINTO SEM ENTRADA
	}
	
	public ArrayList<LinkedList<Point>> dfs() {
		return solver.dfs(entrance, exits.size());
	}
	
	public ArrayList<LinkedList<Point>> bfs() {
		return solver.bfs(entrance, exits.size());
	}
	
	public ArrayList<LinkedList<Point>> AStar() {
		//Vetor dos caminhos at� cada sa�da
		ArrayList<LinkedList<Point>> paths = new ArrayList<LinkedList<Point>>(exits.size());
		
		//Inicializa o vetor footPrint
		LinkedList<Point> footPrint = new LinkedList<Point>();
		
		//Encontra cada sa�da...
		for (Point exit : exits) {
			//Procura um caminho
			LinkedList<Point> auxPath = solver.AStar(source, footPrint, entrance, exit);
			
			//Se exitir, adiciona aos caminhos conhecidos
			//if (auxPath == null) throws exception;
			/*else */if (auxPath != null) paths.add(auxPath);
		}
		
		return paths;
	}
	
	public LinkedList<Point> showFootPrint() {
		return solver.showFootPrint();
	}
}