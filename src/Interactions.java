import java.util.ArrayList;
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
					Double.parseDouble(args[2]), 100);
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
		case 4:
			/*
			 * Returns a route recommendation based on the number of trees of a species
			 * you want to see you want to see. Starts the route with the closest tree near lat/lng.
			 * args[1] = lat
			 * args[2] = lng
			 * args[3] = species
			 * args[4] = number of trees of this species you want on a route
			 */
			Tree closestTreeOfSpecies = g.findClosestTree(Double.parseDouble(args[1]), Double.parseDouble(args[2]),
					Species.parseSpecies(args[3]));
			TreeGraph route = g.getRouteReccomendation(closestTreeOfSpecies, Species.parseSpecies(args[3]), Integer.parseInt(args[4]));
			g.draw(route.graph);
			return;
		case 5:
			/*
			 * returns a route recommendation based on a given lat/lng
			 * by finding trees in your local area and finding the species with least number of tree
			 * then recommends a route with that species and an increased amount
			 */
			HashMap<Species, Integer> treesAroundMe = g.getTreesAroundMe(Double.parseDouble(args[1]), 
					Double.parseDouble(args[2]), 500);
			int lowestCount = Integer.MAX_VALUE;
			Species lowestSpecies = Species.GINKGO;
			for(Species s : Species.values()) {
				if(treesAroundMe.containsKey(s)) {
					int val = treesAroundMe.get(s);
					if(lowestCount > val) {
						lowestCount = val;
						lowestSpecies = s;
					}
				}
				
			}
			System.out.println("Recommending a route to see more: " + lowestSpecies);
			Tree closestTree = g.findClosestTree(Double.parseDouble(args[1]), Double.parseDouble(args[2]),
					lowestSpecies);
			TreeGraph route2 = g.getRouteReccomendation(closestTree, lowestSpecies, lowestCount + 10);
			g.draw(route2.graph);
			return;
		}
	}
}
