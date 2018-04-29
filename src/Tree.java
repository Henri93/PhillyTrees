
public class Tree implements Comparable {
	private int id;
	private double latitude;
	private double longitude;
	private Species species;
	
	public Tree(int id, double latitude, double longitude, Species species) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.species = species;
	}
	
	/*
	 * Standard Getters
	 */
	
	public int getId() {
		return this.id;
	}
	
	public double getLat() {
		return this.latitude;
	}
	
	public double getLong() {
		return this.longitude;
	}
	
	public Species getSpecies() {
		return this.species;
	}
	
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * @returns Distance in Meters
	 */
	public Double distanceTo(Tree otherTree) {
		
		final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(otherTree.latitude - latitude);
	    double lonDistance = Math.toRadians(otherTree.longitude - longitude);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(otherTree.latitude))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    //dont account for elevation
	    double height = 0;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	@Override
	public String toString() {
		return "id: " + this.id + Graph.NEWLINE + "lat: " + this.latitude 
				+ Graph.NEWLINE + "long: " + this.longitude + Graph.NEWLINE
				+ "species: " + this.species.toString() + Graph.NEWLINE;
	}
	
	@Override
	public int hashCode() {
		return (this.id);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Tree) {
			Tree otherTree = (Tree) o;
			return Integer.compare(id, otherTree.getId());
		}
		return -1;
		
	}
	
}
