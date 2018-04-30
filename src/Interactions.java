import java.util.HashMap;

//class to be used for project interactions
public class Interactions {
	
	
	/*
	 * Interaction class for the project. Run with first argument being the question you want answered, 
	 * and then follow up with additional arguments that are necessary
	 */
	public static void main(String[] args) {
		TreeGraph g = new TreeGraph(GeoJsonParser.parseTreeData());
		
		switch(Integer.parseInt(args[0])) {
		case 1: 
			/*
			 * Returns the types of trees in a 100 meter radius of given lat/long
			 */
			HashMap<Species, Integer> question1 = g.getTreesAroundMe(Double.parseDouble(args[1]), 
					Double.parseDouble(args[2]));
			System.out.println(question1.toString());
			return;
			
		case 2:
			/*
			 * Returns closest tree of a given species to the given lat/long
			 */
			Tree question2 = g.findClosestTree(Double.parseDouble(args[1]), Double.parseDouble(args[2]),
					Species.parseSpecies(args[3]));
			System.out.print(question2.toString());
			return;
			
		case 3:
			/*
			 * Returns route through all trees starting from the tree closest to given lat/long.
			 *  Route is in the form of a graph
			 */
			Graph question3 = g.hamiltonianPath(g.findClosestTree(Double.parseDouble(args[1]),
					Double.parseDouble(args[2]), null));
			g.draw(question3);
			return;
		}
	}
}
