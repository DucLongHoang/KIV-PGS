package farmerWorkers;

public class FarmerWorkers {
		
	private final static String INPUT_FILE_NAME = "vtipy.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Main - spoustim farmare.");
		Farmer farmer = new Farmer(INPUT_FILE_NAME);
		
		farmer.start();

		try {
			farmer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Main - konci.");
	}
}
