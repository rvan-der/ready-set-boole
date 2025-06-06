package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.sets.*;


public class Ex08 {

	public static int[][] powerset(int[] set) {
		if (set.length > 30) {
			System.err.println("Can't generate the powerset of a set with cardinality superior to 30 due to Java array size limitations.");
			return null;
		}

		int[][] result = new int[1 << set.length][];
		int resHead = 0;
		SetVector setVector = new SetVector(set.length);

		try {
			while (true) {
				int[] subset = new int[setVector.cardinality()];
				int subHead = 0;
				for (int i = 0; i < set.length; i++) {
					if (setVector.get(i)) {
						subset[subHead++] = set[i];
					}
				}
				result[resHead++] = subset;
				if (!setVector.increment()) {
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

		return result;
	}
}