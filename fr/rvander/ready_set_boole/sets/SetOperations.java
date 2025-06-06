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


	public static int[] intersection(int[] left, int[] right) {
		int[] smallest = left.length <= right.length ? left : right;
		int[] largest = left.length <= right.length ? right : left;
		SetVector vector = new SetVector(smallest.length, false);

		for (int i = 0; i < smallest.length; i++) {
			if (contains(largest, smallest[i])) {
				vector.set(i, true);
			}
		}
		int[] result = new int[vector.cardinality()];
		int head = 0;
		for (int i = 0; i < smallest.length; i++) {
			if (vector.get(i)) {
				result[head++] = smallest[i];
			}
		}
		return result;
	}


	public static int[] union(int[] left, int[] right) {
		SetVector vector = new SetVector(right.length, true);
		for (int i = 0; i < right.length; i++) {
			if (contains(left, right[i])) {
				vector.set(i, false);
			}
		}
		int[] result = new int[left.length + vector.cardinality()];
		int head = 0;
		for (int i = 0; i < left.length; i++) {
			result[head++] = left[i];
		}
		for (int i = 0; i < right.length; i++) {
			if (vector.get(i)) {
				result[head++] = right[i];
			}
		}
		return result;
	}


	public static int[] xor(int[] left, int[] right) {
		return union(substract(left, right), substract(right, left));
	}


	public static int[] implication(int[] left, int[] right, int[] globalSet) {
		return union(complement(left, globalSet), right);
	}


	public static int[] equivalence(int[] left, int[] right, int[] globalSet) {
		return intersection(
			union(complement(left, globalSet), right),
			union(left, complement(right, globalSet)));
	}
}
