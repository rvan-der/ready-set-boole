package fr.rvander.ready_set_boole.sets;


public class SetVector {

	private int tSize;
	private byte[] tVector;
	private int tCardinality;


	public SetVector(int p_size) {
		tSize = p_size;
		tVector = new byte[tSize];
		tCardinality = 0;
	}


	public SetVector(int p_size, boolean initialValue) {
		tSize = p_size;
		tVector = new byte[tSize];
		tCardinality = 0;
		if (initialValue) {
			for (int i = 0; i < tVector.length; i++) {
				tVector[i] = 1;
			}
			tCardinality = tSize;
		}
	}


	public boolean increment() {
		if (tCardinality == tSize) {
			return false;
		}

		boolean carry = true;
		for (int i = 0; carry; i++) {
			if (tVector[i] == 1) {
				tVector[i] = 0;
				tCardinality--;
			} else {
				tVector[i] = 1;
				tCardinality++;
				carry = false;
			}
		}

		return true;
	}


	public boolean decrement() {
		if (tCardinality == 0) {
			return false;
		}

		int i = 0;
		while (tVector[i] == 0) {
			tVector[i] = 1;
			tCardinality++;
			i++;
		}
		tVector[i] = 0;
		tCardinality--;

		return true;
	}


	public int cardinality() {
		return tCardinality;
	}


	public boolean get(int index) {
		if (index >= tSize || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return (tVector[index] == 0 ? false: true);
	}


	public void set(int index, boolean value) {
		if (index >= tSize || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (value) {
			tVector[index] = 1;
			tCardinality++;
			return;
		}
		tVector[index] = 0;
		tCardinality--;
	}


	public int[] trueIndices() {
		int[] result = new int[tCardinality];
		int head = 0;
		for (int i = 0; i < tVector.length; i++) {
			if (tVector[i] == 1) {
				result[head++] = i;
			}
		}
		return result;
	}


	public int size() {
		return this.tSize;
	}
}