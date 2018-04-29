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
	//Cache a lsit of species values
	private static final List<Species> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	 
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static Species randomSpecies()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}


