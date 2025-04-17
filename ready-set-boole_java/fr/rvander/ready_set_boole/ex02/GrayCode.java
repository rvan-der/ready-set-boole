package fr.rvander.ready_set_boole.ex02;


public class GrayCode {

	public static int gray_code(int n) {
		return n ^ (n >>> 1);
	}
}