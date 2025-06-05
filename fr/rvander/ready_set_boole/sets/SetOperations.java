package fr.rvander.ready_set_boole.sets;


public class SetOperations {

	public static boolean contains(int[] set, int value) {
		for (int elem : set) {
			if (elem == value) {
				return true;
			}
		}
		return false;
	}


	public static int[] combine(int[][] sets) {
		SetVector[] vectors = new SetVector[sets.length];
		for (int i = 0; i < vectors.length; i++) {
			vectors[i] = new SetVector(sets[i].length, true);
		}
		for (int iSet = 0; iSet < sets.length; iSet++) {
			for (int iCompare = 0; iCompare < iSet; iCompare++) {
				for (int iElem = 0; iElem < sets[iSet].length; iElem++) {
					if (contains(sets[iCompare], sets[iSet][iElem])) {
						vectors[iSet].set(iElem, false);
					}
				}
			}
		}
		int size = 0;
		for (SetVector v : vectors) {
			size += v.cardinality();
		}
		int[] result = new int[size];
		int head = 0;
		for (int iSet = 0; iSet < sets.length; iSet++) {
			int[] indices = vectors[iSet].trueIndices();
			for (int iElem : indices) {
				result[head++] = sets[iSet][iElem];
			}
		}
		return result;
	}


	public static int[] complement(int[] set, int[] globalSet) {
		int[] result = new int[globalSet.length - set.length];
		int head = 0;
		for (int element : globalSet) {
			if (!contains(set, element)) {
				result[head++] = element;
			}
		}
		return result;
	}


	public static int[] substract(int[] left, int[] right) {
		SetVector vector = new SetVector(left.length, true);
		for (int i = 0; i < left.length; i++) {
			if (contains(right, left[i])) {
				vector.set(i, false);
			}
		}
		int[] result = new int[vector.cardinality()];
		int head = 0;
		for (int i = 0; i < left.length; i++) {
			if (vector.get(i)) {
				result[head++] = left[i];
			}
		}
		return result;
	}


	// public static int[] add(int[] left, int[] right) {
	// 	SetVector vector = new SetVector(right.length, true);
	// 	for (int i = 0; i < right.length; i++) {
	// 		if (contains(left, right[i])) {
	// 			vector.set(i, false);
	// 		}
	// 	}
	// 	int[] result = new int[left.length + vector.cardinality()];
	// 	int head = 0;
	// 	for (int i = 0; i < left.length; i++) {
	// 		result[head++] = left[i];
	// 	}
	// 	for (int i = 0; i < right.length; i++) {
	// 		if (vector.get(i)) {
	// 			result[head++] = right[i];
	// 		}
	// 	}
	// 	return result;
	// }


	
}
