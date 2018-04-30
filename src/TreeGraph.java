import java.util.HashMap;
import java.util.HashSet;

public class TreeGraph {
	public Tree[] trees;
	
	public TreeGraph(Tree[] trees) {
		this.trees = trees;
//		this.G =new Graph(trees.length);
//		
//		for (int i = 0; i < trees.length; i++) {
//			for (int j = i; j < trees.length; j++) {
//				//System.out.println(trees[i]);
//				//System.out.println(trees[j]);
//				//Edge e = new Edge(i, j, trees[i].distanceTo(trees[j]));
//				//G.addEdge(e);
//			}
//		}
	}
	
	/*
	 * Draws given graph with green vertices and black edges.
	 */
	public void draw(Graph g) {
		double minLat = Double.MAX_VALUE;
		double minLong = Double.MAX_VALUE;
		double maxLat = -Double.MAX_VALUE;
		double maxLong = -Double.MAX_VALUE;
		for (Tree t : trees) {
			minLat = Double.min(minLat, t.getLat());
			minLong = Double.min(minLong, t.getLong());
			maxLat = Double.max(maxLat, t.getLat());
			maxLong = Double.max(maxLong, t.getLong());
		}
		
		double latRange = (maxLat - minLat);
		double longRange = (maxLong - minLong);
		
		PennDraw.setPenColor(PennDraw.GREEN);
		PennDraw.setPenRadius(0.003);
		int count = 0;
		for (Tree t: trees) {
			count++;
			PennDraw.point( (t.getLong() - minLong) / longRange, (t.getLat() - minLat) / latRange);
			//if (count % 1000 == 0) System.out.println("Trees Plotted: " + count);
		}
		
		if (g != null) {
			PennDraw.setPenColor(PennDraw.BLACK);
			PennDraw.setPenRadius(0.0005);
			for (Edge e : g.edges()) {
				int v = e.either();
				int u = e.other(v);
				PennDraw.line((trees[u].getLong() - minLong) / longRange, (trees[u].getLat() - minLat) / latRange, 
						(trees[v].getLong() - minLong) / longRange, (trees[v].getLat() - minLat) / latRange);
			}
		}
	}
	
	//Finds closest tree to the specified location
	public Tree findClosestTree(double lat, double longitude, Species s) {
		Tree position = new Tree(1, lat, longitude, Species.GINKGO);
		double minDist = Double.MAX_VALUE;
		Tree closestTree = null;
		for (Tree t : trees) {
			if (s != null && t.getSpecies() == s) {
				closestTree = (position.distanceTo(t) < minDist) ? t : closestTree;
				minDist = (position.distanceTo(t) < minDist) ? position.distanceTo(t) : minDist;
			} else if (s == null) {
				closestTree = (position.distanceTo(t) < minDist) ? t : closestTree;
				minDist = (position.distanceTo(t) < minDist) ? position.distanceTo(t) : minDist;
			}
		}
		return closestTree;
	}
	
	/*returns a hashmap of the species, int pairs pertaining to the amount of trees of each species that are
	 * within 100 meters of the specified location. 
	 */
	public HashMap<Species, Integer> getTreesAroundMe(double lat, double longitude) {
		Tree position = new Tree(-1, lat, longitude, null);
		HashMap<Species, Integer> treeCounts = new HashMap<Species, Integer>();
		for (Tree t: trees) {
			if (t.distanceTo(position) < 100) {
				if (!treeCounts.containsKey(t.getSpecies())) {
					treeCounts.put(t.getSpecies(), 1);
				} else {
					treeCounts.put(t.getSpecies(), treeCounts.get(t.getSpecies()) + 1);
				}
			}
		}
		return treeCounts;
	}
	
	public int indexOf(Tree t) {
		for (int i = 0; i < trees.length; i++) {
			if (trees[i] == t) return i;
		}
		return -1;
	}
	
	//Uses simple closest neighbor heuristic to calculate the hamiltonian path through all the trees
	public Graph hamiltonianPath(Tree s) {
		HashSet<Tree> visited = new HashSet<Tree>();
		Tree curr = s;
		Graph path = new Graph(trees.length);
		while(visited.size() < trees.length - 1) {
			Tree next = null;
			double dist = Double.MAX_VALUE;
			for (Tree t : trees) {
				if (!visited.contains(t) && t.distanceTo(curr) < dist && t != curr) {
					next = t;
					dist = t.distanceTo(curr);
				}
			}
			if (next == null) System.out.println(indexOf(curr));
			path.addEdge(new Edge(indexOf(curr), indexOf(next), 1));
			visited.add(next);
			curr = next;
		}
		return path;
	}
	
	public static void main(String[] args) {
		
		TreeGraph treeG = new TreeGraph(GeoJsonParser.parseTreeData());
		
		treeG.draw(treeG.hamiltonianPath(treeG.trees[0]));
	}
}
