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
	
	public PathFinder(byte source[][]) {
		this.source = source;
	}
	
	public LinkedList<Point> showFootPrint() {
		//ADICIONAR EXCESSÃO
		//if (footPrint == null) throws exception;
		return footPrint;
	}
	
	/*DFS util*/
	private void dfsRun(int x, int y) {
		Point now = new Point(x, y);
		
		//Marca a posição atual como visitada e a adiciona ao caminho
		used[x][y] = true;
		auxPath.add(now);
		
		//Pinta a posição atual de vermelho
		footPrint.add(now);
		
		//Caso saída encontrada
		if (source[x][y] == 3) {
			//Armazena uma cópia do caminho até a saída
			paths.add(new LinkedList<Point>(auxPath));
			
			//Exibe o caminho encontrado
			footPrint.add(null);
		}
		
		//Visita os nós vizinhos
		if (x>0 && source[x-1][y] != 1 && !used[x-1][y]) dfsRun(x-1, y);	//Esquerda
		if (y+1 < source[x].length && source[x][y+1] != 1 && !used[x][y+1]) dfsRun(x, y+1);	//Baixo
		if (x+1 < source.length && source[x+1][y] != 1 && !used[x+1][y])	dfsRun(x+1, y);	//Direita
		if (y>0 && source[x][y-1] != 1 && !used[x][y-1]) dfsRun(x, y-1);	//Cima
		
		//Remove a posição atual da lista e pinta a posição atual de cinza
		auxPath.remove();
		footPrint.add(now);
	}
	
	/*DFS*/
	public ArrayList<LinkedList<Point>> dfs(Point entrance, int nExits) {
		//Inicialização
		auxPath = new LinkedList<Point>();
		footPrint = new LinkedList<Point>();
		paths = new ArrayList<LinkedList<Point>>(nExits);
		
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
	private void extractFromParent(LinkedList<Point> path, Point[][] parent, Point now) {
		if (parent[now.x][now.y] == null) return;
		extractFromParent(path, parent, parent[now.x][now.y]);
		path.add(now);
	}
	
	/*BFS util*/
	private void bfsMark(LinkedList<Point> queue, Point now, Point next) {
		//Adiciona "next" à fila, marca-o como visitados, pinta-o de vermelho e define seu antecessor
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
		
		//Queue
		LinkedList<Point> queue = new LinkedList<Point>();
		
		//Adiciona a entrada
		used[entrance.x][entrance.y] = true;
		queue.add(entrance);
		
		//Pinta a entrada de vermelho
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
				extractFromParent(paths.get(paths.size() - 1), parent, now);
				
				//Exibe o caminho encontrado
				footPrint.add(null);
			}
			
			//Para cada posição adjacente...
			//Esquerda
			if (now.x > 0 && source[now.x - 1][now.y] != 1 && !used[now.x - 1][now.y]) {
				next = new Point(now.x - 1, now.y);
				bfsMark(queue, now, next);
			}
			
			//Baixo
			if (now.y < source.length && source[now.x][now.y + 1] != 1 && !used[now.x][now.y + 1]) {
				next = new Point(now.x - 1, now.y);
				bfsMark(queue, now, next);
			}
			
			//Direita
			if (now.x < source.length && source[now.x + 1][now.y] != 1 && !used[now.x + 1][now.y]) {
				next = new Point(now.x + 1, now.y);
				bfsMark(queue, now, next);
			}
			
			//Cima
			if (now.y > 0 && source[now.x][now.y - 1] != 1 && !used[now.x][now.y - 1]) {
				next = new Point(now.x, now.y - 1);
				bfsMark(queue, now, next);
			}
		}
		
		//Retorna o(s) caminho(s) encontrado(s)
		//ADICIONAR EXCEÇÃO CAMINHO NÃO ENCONTRADO
		//if (paths.size == 0) throws exception
		return paths;
	}
	
	/* A* util*/
	private void AStarMark(TreeSet<StarCell> set, StarCell now, StarCell next) {
		//Adiciona "next" ao set, pinta-o de vermelho e define seu antecessor
		parent[next.x][next.y] = now.point();
		set.add(next);
		footPrint.add(next.point());
	}
	
	/* A* */
	public ArrayList<LinkedList<Point>> AStar(byte[][] source, Point entrance, Point exit) {
		//Inicialização
		footPrint = new LinkedList<Point>();
		auxPath = new LinkedList<Point>();
		
		//Inicialização
		used = new boolean[source.length][];
		for (int i=0; i<source.length; i++) used[i] = new boolean[source[i].length];
		
		//Inicialização
		parent = new Point[source.length][];
		for (int i=0; i<source.length; i++) parent[i] = new Point[source[i].length];
		
		//Set Ordenado implementado com Rubro-Negra para a lista de nós e matriz de adjacência
		TreeSet<StarCell> set = new TreeSet<StarCell>();
		
		//Adiciona a entrada ao set
		set.add(new StarCell(entrance, 0, exit));
		
		//Pinta a entrada de Vermelho
		footPrint.add(entrance);
		
		while (!set.isEmpty()) {
			//Salva o nó atual e o tira do set
			StarCell next = null, now = set.pollFirst();
			
			//Pinta o nó de cinza
			footPrint.add(now.point());
			
			//Para cada posição adjacente...
			//Esquerda
			if (now.x > 0 && source[now.x - 1][now.y] != 1 && !used[now.x - 1][now.y]) {
				next = new StarCell(now.x - 1, now.y, now.d + 1, exit);
				AStarMark(set, now, next);
			}
			
			//Baixo
			if (now.y < source.length && source[now.x][now.y + 1] != 1 && !used[now.x][now.y + 1]) {
				next = new StarCell(now.x - 1, now.y, now.d+1, exit);
				AStarMark(set, now, next);
			}
			
			//Direita
			if (now.x < source.length && source[now.x + 1][now.y] != 1 && !used[now.x + 1][now.y]) {
				next = new StarCell(now.x + 1, now.y, now.d+1, exit);
				AStarMark(set, now, next);
			}
			
			//Cima
			if (now.y > 0 && source[now.x][now.y - 1] != 1 && !used[now.x][now.y - 1]) {
				next = new StarCell(now.x, now.y - 1, now.d+1, exit);
				AStarMark(set, now, next);
			}
		}
		
		return null;
	}
}
