package fr.rvander.ready_set_boole;


public class Ex01 {

	public static int multiplier(int a, int b) {
		int result = 0;

		if (a == 0) {
			return 0;
		}
		while (b != 0) {
			if ((b & 1) != 0) {
				result = Ex00.adder(a, result);
			}
			a = a << 1;
			b = b >>> 1;
		}
		return result;
	}
}