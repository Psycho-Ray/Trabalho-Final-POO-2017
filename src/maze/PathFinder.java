package maze;

import java.awt.Point;
import java.util.*;

public class PathFinder {
	private LinkedList<Point> auxPath;
	private LinkedList<Point> footPrint;
	
	private ArrayList<LinkedList<Point>> paths;
	
	private boolean[][] used;
	private byte[][] source;
	private Point[][] parent;
	private StarCell[][] starMap;
	
	public PathFinder(byte source[][]) {
		this.source = source;
	}
	
	public LinkedList<Point> showFootPrint() {
		//ADICIONAR EXCESSÃO
		//if (footPrint == null) throws exception;
		return footPrint;
	}
	
	/*Util*/
	private void extractFromParent(LinkedList<Point> path, Point[][] parent, Point now) {
		if (parent[now.x][now.y] == null) return;
		extractFromParent(path, parent, parent[now.x][now.y]);
		path.add(now);
	}
	
	/*DFS util*/
	private void dfsRun(int x, int y) {
		Point now = new Point(x, y);
		
		//Marca a posição atual como visitada e a adiciona ao caminho
		used[x][y] = true;
		
		//Pinta a posição atual de azul
		footPrint.add(now);
		
		//Caso saída encontrada
		if (source[x][y] == 3) {
			//Armazena uma cópia do caminho até a saída
			paths.add(new LinkedList<Point>());
			extractFromParent(paths.get(paths.size() - 1), parent, parent[now.x][now.y]);
		}
		
		//Visita os nós vizinhos	
		//Direita
		if (y+1 < source[x].length && source[x][y+1] != 1 && !used[x][y+1]) {
			parent[x][y+1] = now;
			dfsRun(x, y+1);
		}
		
		//Baixo
		if (x+1 < source.length && source[x+1][y] != 1 && !used[x+1][y]) {
			parent[x+1][y] = now;
			dfsRun(x+1, y);
		}
		
		//Esquerda
		if (y>0 && source[x][y-1] != 1 && !used[x][y-1]) {
			parent[x][y-1] = now;
			dfsRun(x, y-1);
		}
		//Cima
		if (x>0 && source[x-1][y] != 1 && !used[x-1][y]) {
			parent[x-1][y] = now;
			dfsRun(x-1, y);
		}
		
		//Pinta a posição atual de cinza
		footPrint.add(now);
	}
	
	/*DFS*/
	public ArrayList<LinkedList<Point>> dfs(Point entrance, int nExits) {
		//Inicialização
		footPrint = new LinkedList<Point>();
		paths = new ArrayList<LinkedList<Point>>(nExits);
		
		//Inicialização
		parent = new Point[source.length][];
		for (int i=0; i<source.length; i++) parent[i] = new Point[source[i].length];
		parent[entrance.x][entrance.y] = null;
		
		//Inicialização
		used = new boolean[source.length][];
		for (int i=0; i<source.length; i++) used[i] = new boolean[source[i].length];
		
		//Realiza a busca em profundidade
		dfsRun(entrance.x, entrance.y);
		
		//Retorna os caminhos encontrados
		//ADICIONAR EXCEÇÃO CAMINHO NÃO ENCONTRADO
		//if (paths.size() == 0) throws exception
		return paths;
	}
	
	/*BFS util*/
	private void bfsMark(LinkedList<Point> queue, Point now, Point next) {
		//Adiciona "next" à fila, marca-o como visitados, pinta-o de azul e define seu antecessor
		used[next.x][next.y] = true;
		parent[next.x][next.y] = now;
		queue.add(next);
		footPrint.add(next);
	}
	
	/*BFS*/
	public ArrayList<LinkedList<Point>> bfs(Point entrance, int nExits) {
		//Inicialização
		footPrint = new LinkedList<Point>();
		paths = new ArrayList<LinkedList<Point>>(nExits);
		
		//Inicialização
		used = new boolean[source.length][];
		for (int i=0; i<source.length; i++) used[i] = new boolean[source[i].length];
		
		//Inicialização
		parent = new Point[source.length][];
		for (int i=0; i<source.length; i++) parent[i] = new Point[source[i].length];
		parent[entrance.x][entrance.y] = null;
		
		//Queue
		LinkedList<Point> queue = new LinkedList<Point>();
		
		//Adiciona a entrada
		used[entrance.x][entrance.y] = true;
		queue.add(entrance);
		
		//Pinta a entrada de azul
		footPrint.add(entrance);
		
		//Enquanto existirem vértices a serem visitados...
		while (!queue.isEmpty()) {
			//Salva o nó atual e o tira da fila
			Point next = null, now = queue.pop();
			
			//Pinta-o de cinza
			footPrint.add(now);
			
			//Caso saída encontrada
			if (source[now.x][now.y] == 3) {
				//Armazena o caminho até a saída
				paths.add(new LinkedList<Point>());
				extractFromParent(paths.get(paths.size() - 1), parent, parent[now.x][now.y]);
			}
			
			//Para cada posição adjacente...
			//Direita
			if (now.y + 1 < source[now.x].length && source[now.x][now.y + 1] != 1 && !used[now.x][now.y + 1]) {
				next = new Point(now.x, now.y + 1);
				bfsMark(queue, now, next);
			}
			
			//Baixo
			if (now.x + 1 < source.length && source[now.x + 1][now.y] != 1 && !used[now.x + 1][now.y]) {
				next = new Point(now.x + 1, now.y);
				bfsMark(queue, now, next);
			}
			
			//Esquerda
			if (now.y > 0 && source[now.x][now.y - 1] != 1 && !used[now.x][now.y - 1]) {
				next = new Point(now.x, now.y - 1);
				bfsMark(queue, now, next);
			}
			
			//Cima
			if (now.x > 0 && source[now.x - 1][now.y] != 1 && !used[now.x - 1][now.y]) {
				next = new Point(now.x - 1, now.y);
				bfsMark(queue, now, next);
			}
		}
		
		//Retorna o(s) caminho(s) encontrado(s)
		//ADICIONAR EXCEÇÃO CAMINHO NÃO ENCONTRADO
		//if (paths.size == 0) throws exception
		return paths;
	}
	
	/* A* util*/
	private void AStarMark(TreeSet<StarCell> set, StarCell now, StarCell next, Point exit) {
		//Se o campo ainda não estiver pintando, pinta-o
		if (next.t == Integer.MAX_VALUE) footPrint.add(next.point());
		
		//Atualiza "next", adiciona-o set, define seu antecessor
		next.update(1, exit);
		set.add(next);
		parent[next.x][next.y] = now.point();
	}
	
	/* A* */
	public LinkedList<Point> AStar(byte[][] source, LinkedList<Point>footPrint, Point entrance, Point exit) {
		//Inicialização
		this.footPrint = footPrint;
		auxPath = new LinkedList<Point>();
		
		//Inicialização
		starMap = new StarCell[source.length][];
		for (int i=0; i<source.length; i++) {
			starMap[i] = new StarCell[source[i].length];
			for (int j=0; j<source[i].length; j++) starMap[i][j] = new StarCell(i,j);
		}
		
		//Inicialização
		used = new boolean[source.length][];
		for (int i=0; i<source.length; i++) used[i] = new boolean[source[i].length];
		
		//Inicialização
		parent = new Point[source.length][];
		for (int i=0; i<source.length; i++) parent[i] = new Point[source[i].length];
		parent[entrance.x][entrance.y] = null;
		
		//Set Ordenado implementado com Rubro-Negra para a lista de nós e matriz de adjacência
		TreeSet<StarCell> set = new TreeSet<StarCell>();
		
		//Adiciona a entrada ao set
		//starMap[entrance.x][entrance.y].t = 0;
		set.add(starMap[entrance.x][entrance.y]);
		
		//Pinta-a de azul
		footPrint.add(entrance);
		
		while (!set.isEmpty()) {
			//Salva o nó atual e o tira do set
			StarCell next = null, now = set.pollFirst();
			
			//Marca-o como visitado
			used[now.x][now.y] = true;
			
			//Pinta-o de cinza
			footPrint.add(now.point());
			
			///Caso saída encontrada
			if (now.point().equals(exit)) {
				//Armazena o caminho até a saída
				extractFromParent(auxPath, parent, parent[now.x][now.y]);
				
				//Exibe o caminho encontrado
				footPrint.add(null);
				
				//Fim do algoritmo
				return auxPath;
			}
			
			//Para cada posição adjacente...
			//Direita
			if (now.y + 1 < source.length && source[now.x][now.y + 1] != 1 && !used[now.x][now.y + 1]) {
				next = starMap[now.x][now.y + 1];
				if (next.nextT(1, exit) < next.t) AStarMark(set, now, next, exit);
			}
			
			//Baixo
			if (now.x + 1 < source[now.y].length && source[now.x + 1][now.y] != 1 && !used[now.x + 1][now.y]) {
				next = starMap[now.x + 1][now.y];
				if (next.nextT(1, exit) < next.t) AStarMark(set, now, next, exit);
			}
			
			//Esquerda
			if (now.y > 0 && source[now.x][now.y - 1] != 1 && !used[now.x][now.y - 1]) {
				next = starMap[now.x][now.y - 1];
				if (next.nextT(1, exit) < next.t) AStarMark(set, now, next, exit);
			}
			
			//Cima
			if (now.x > 0 && source[now.x - 1][now.y] != 1 && !used[now.x - 1][now.y]) {
				next = starMap[now.x - 1][now.y];
				if (next.nextT(1, exit) < next.t) AStarMark(set, now, next, exit);
			}
		}
		
		return null;
	}
}
