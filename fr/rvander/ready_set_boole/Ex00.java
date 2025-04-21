package fr.rvander.ready_set_boole;


public class Ex00 {

	public static int adder(int a, int b) {
		int tmp;
		while (b != 0) {
			tmp = a ^ b;
			b = (a & b) << 1;
			a = tmp;
		}
		return a;
	}
}