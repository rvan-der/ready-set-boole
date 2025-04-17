package fr.rvander.ready_set_boole.ex01;

import fr.rvander.ready_set_boole.ex00.Adder;


public class Multiplier {

	public static int multiplier(int a, int b) {
		int result = 0;

		if (a == 0) {
			return 0;
		}
		while (b != 0) {
			if ((b & 1) != 0) {
				result = Adder.adder(a, result);
			}
			a = a << 1;
			b = b >>> 1;
		}
		return result;
	}
}