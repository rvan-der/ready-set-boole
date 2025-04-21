package fr.rvander.ready_set_boole;

import java.util.*;
import java.lang.reflect.*;
import fr.rvander.ready_set_boole.*;

import fr.rvander.ready_set_boole.AST.*;


public class ReadySetBoole {

	public static void main(String args[]) {
		if (args.length == 0) {
			System.err.println("Missing method and arguments.");
			return;
		}
		Method method = null;
		try {
			for (Method m : ReadySetBoole.class.getDeclaredMethods()) {
				if (m.getName().equals(args[0])) {
					method = m;
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		if (method == null || args[0].equals("main")) {
			System.err.println("Method '" + args[0] + "' doesn't exist.");
			return;
		}
		int paramCount = method.getParameterCount();
		if (paramCount != args.length - 1) {
			System.err.println("Wrong number of parameters for '" 
				+ method.getName() + "'. Found " + (args.length - 1)
				+ " but " + paramCount + " are needed.");
			return;
		}
		try {
			method.invoke(new ReadySetBoole(),
				(Object[]) Arrays.copyOfRange(args, 1, args.length));
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	public void adder(String a, String b) {
		int aInt;
		int bInt;
		try {
			aInt = Integer.parseUnsignedInt(a);
			bInt = Integer.parseUnsignedInt(b);
			System.out.println(Integer.toUnsignedString(Ex00.adder(aInt, bInt)));
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	public void multiplier(String a, String b) {
		int aInt;
		int bInt;
		try {
			aInt = Integer.parseUnsignedInt(a);
			bInt = Integer.parseUnsignedInt(b);
			System.out.println(Integer.toUnsignedString(Ex01.multiplier(aInt, bInt)));
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	public void gray_code(String n) {
		int nInt;
		try {
			nInt = Integer.parseUnsignedInt(n);
			System.out.println(Integer.toUnsignedString(Ex02.gray_code(nInt)));
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	public void eval_formula(String formula) {
		try {
			System.out.println(Ex03.eval_formula(formula));
			AstBuilder.getAstBuilder().astFromString(formula).visualize();
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	// public void test() {
	// 	boolean l = true, r = true;
	// 	System.out.println(l && !r || !l && r);
	// }
}