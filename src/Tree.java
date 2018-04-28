
public class Tree {
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
	
}
