import java.util.*;

public enum Species {
	ALASKAN_CEDAR,
	GINKGO,
	KOUSA_DOGWOOD,
	SOUTHERN_MAGNOLIA,
	WHITE_BIRCH,
	ZELKOVA,
	SUGAR_MAPLE,
	CHERRY;
	
	//This is for selecting a random Species
	//Cache a list of species values
	private static final List<Species> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	 
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static Species randomSpecies()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
	public static Species parseSpecies(String s) {
		switch(s) {
		case "ALASKAN_CEDAR": return ALASKAN_CEDAR;
		case "GINKGO": return GINKGO;
		case "KOUSA_DOGWOOD": return KOUSA_DOGWOOD;
		case "SOUTHERN_MAGNOLIA": return SOUTHERN_MAGNOLIA;
		case "WHITE_BIRCH" : return WHITE_BIRCH;
		case "ZELKOVA" : return ZELKOVA;
		case "SUGAR_MAPLE" : return SUGAR_MAPLE;
		case "CHERRY" : return CHERRY;
		default : return null;
		}
	}
}


