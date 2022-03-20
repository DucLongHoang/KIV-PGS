package farmerWorkers;

import java.util.Comparator;

public class WRComparer implements Comparator<WordRecord> {

	public int compare(WordRecord arg0, WordRecord arg1) {
		
		if (arg0.frequency == arg1.frequency)
			return arg0.word.compareTo(arg1.word);
		
		if (arg0.frequency < arg1.frequency)
			return 1;
		else
			return -1;
	}
}
