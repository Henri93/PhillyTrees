
public class TreeGraph {
	private Graph G;
	private Tree[] trees;
	
	public TreeGraph(Tree[] trees) {
		this.trees = trees;
//		this.G =new Graph(trees.length - 1);
//		
//		for (int i = 0; i < trees.length - 1; i++) {
//			for (int j = i; j < trees.length - 1; j++) {
//				//System.out.println(trees[i]);
//				//System.out.println(trees[j]);
//				//Edge e = new Edge(i, j, trees[i].distanceTo(trees[j]));
//				//G.addEdge(e);
//			}
//		}
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
		PennDraw.setPenRadius(0.001);
		int count = 0;
		for (Tree t: trees) {
			count++;
			PennDraw.point((t.getLat() - minLat), (t.getLong() - minLong));
			System.out.println("Trees Plotted: " + count);
		}
	}
	
	public Tree findClosestTree(double lat, double longitude) {
		Tree position = new Tree(1, lat, longitude, Species.GINKGO);
		double minDist = Double.MAX_VALUE;
		Tree closestTree = null;
		for (Tree t : trees) {
			closestTree = (position.distanceTo(t) < minDist) ? t : closestTree;
			minDist = (position.distanceTo(t) < minDist) ? position.distanceTo(t) : minDist;
		}
		return closestTree;
	}
	
	public static void main(String[] args) {
		
		TreeGraph treeG = new TreeGraph(GeoJsonParser.parseTreeData());
		
		treeG.draw(null);
	}
}
