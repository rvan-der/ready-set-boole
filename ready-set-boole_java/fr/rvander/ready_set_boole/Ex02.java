package fr.rvander.ready_set_boole;


public class Ex02 {

	public static int gray_code(int n) {
		return n ^ (n >>> 1);
	}
}