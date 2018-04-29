import java.util.HashMap;
import java.util.HashSet;

public class TreeGraph {
	private Graph G;
	private Tree[] trees;
	
	public TreeGraph(Tree[] trees) {
		this.trees = trees;
		this.G =new Graph(trees.length);
		for (int i = 0; i < trees.length; i++) {
			for (int j = i; j < trees.length; j++) {
				Edge e = new Edge(i, j, trees[i].distanceTo(trees[j]));
				G.addEdge(e);
			}
		}
	}
	
	public void draw(Graph g) {
		double minLat = Double.MAX_VALUE;
		double minLong = Double.MAX_VALUE;
		double maxLat = Double.MIN_VALUE;
		double maxLong = Double.MIN_VALUE;
		for (Tree t : trees) {
			minLat = Double.min(minLat, t.getLat());
			minLong = Double.min(minLong, t.getLong());
			maxLat = Double.max(maxLat, t.getLat());
			maxLong = Double.max(maxLong, t.getLong());
		}
		double latRange = (maxLat - minLat);
		double longRange = (maxLong - minLong);
		
		if (g != null) {
			PennDraw.setPenColor(PennDraw.BLACK);
			PennDraw.setPenRadius(0.001);
			for (Edge e : g.edges()) {
				int v = e.either();
				int u = e.other(v);
				PennDraw.line((trees[u].getLat() - minLat) / latRange, (trees[u].getLong() - minLong) / longRange, 
						(trees[v].getLat() - minLat) / latRange, (trees[v].getLong() - minLong) / longRange);
			}
		}
		
		PennDraw.setPenColor(PennDraw.GREEN);
		PennDraw.setPenRadius(0.01);
		for (Tree t: trees) {
			PennDraw.point((t.getLat() - minLat) / latRange, (t.getLong() - minLong) / longRange);
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
	
//	public Graph hamiltonianPath(Tree s) {
//		HashSet<Tree> visited = new HashSet<Tree>();
//		Tree curr = s;
//		Tree next = null;
//		Graph path = new Graph(trees.length);
//		double dist = Double.MAX_VALUE;
//		while(visited.size() < trees.length) {
//			for (Tree t : trees) {
//				if (!visited.contains(t) && t.distanceTo(curr) < dist) {
//					next = t;
//					dist = t.distanceTo(curr);
//				}
//			}
//			visited.add(next);
//			curr = next;
//			next = null;
//		}
//	}
	
	public static void main(String[] args) {
		
		TreeGraph treeG = new TreeGraph(GeoJsonParser.parseTreeData());
		
		treeG.draw(null);
	}
}
