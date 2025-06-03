package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.sets.*;
import java.util.ArrayList;


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
				for (int i = 0; i < set.length; i += 1) {
					if (setVector.get(i)) {
						subset[subHead] = set[i];
						subHead += 1;
					}
				}
				result[resHead] = subset;
				resHead += 1;
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