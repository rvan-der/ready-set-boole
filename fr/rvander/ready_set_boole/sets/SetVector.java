package fr.rvander.ready_set_boole.sets;

import java.util.BitSet;


public class SetVector extends BitSet {

	private static final long serialVersionUID = -5552905034643187698L;
	private int tSize;


	public SetVector(int p_size) {
		super(p_size);
		this.tSize = p_size;
	}


	public boolean increment() {
		if (cardinality() == tSize) {
			return false;
		}

		boolean carry = true;
		for (int i = 0; carry; i += 1) {
			if (super.get(i)) {
				super.set(i, false);
			} else {
				super.set(i, true);
				carry = false;
			}
		}

		return true;
	}


	public boolean decrement() {
		if (cardinality() == 0) {
			return false;
		}

		int i = 0;
		while (!super.get(i)) {
			super.set(i, true);
			i += 1;
		}
		super.set(i, false);

		return true;
	}


	@Override
	public boolean get(int bitIndex) {
		if (bitIndex >= tSize) {
			throw new IndexOutOfBoundsException();
		}
		return super.get(bitIndex);
	}


	@Override
	public BitSet get(int fromIndex, int toIndex) {
		if (toIndex > tSize) {
			throw new IndexOutOfBoundsException();
		}
		return super.get(fromIndex, toIndex);
	}


	@Override
	public void set(int bitIndex) {
		if (bitIndex >= tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.set(bitIndex);
	}


	@Override
	public void set(int fromIndex, int toIndex) {
		if (toIndex > tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.set(fromIndex, toIndex);
	}


	@Override
	public void set(int bitIndex, boolean value) {
		if (bitIndex >= tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.set(bitIndex, value);
	}


	@Override
	public void set(int fromIndex, int toIndex, boolean value) {
		if (toIndex > tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.set(fromIndex, toIndex, value);
	}


	@Override
	public void flip(int bitIndex) {
		if (bitIndex >= tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.flip(bitIndex);
	}


	@Override
	public void flip(int fromIndex, int toIndex) {
		if (toIndex > tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.flip(fromIndex, toIndex);
	}


	@Override
	public void clear(int bitIndex) {
		if (bitIndex >= tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.clear(bitIndex);
	}


	@Override
	public void clear(int fromIndex, int toIndex) {
		if (toIndex > tSize) {
			throw new IndexOutOfBoundsException();
		}
		super.clear(fromIndex, toIndex);
	}


	@Override
	public int size() {
		return this.tSize;
	}
}