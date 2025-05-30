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
					break;
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


	private void adder(String a, String b) {
		int aInt;
		int bInt;
		try {
			aInt = Integer.parseUnsignedInt(a);
			bInt = Integer.parseUnsignedInt(b);
		} catch (Exception e) {
			System.err.println(e);
			return;
		}

		System.out.println(Integer.toUnsignedString(Ex00.adder(aInt, bInt)));
	}


	private void multiplier(String a, String b) {
		int aInt;
		int bInt;
		try {
			aInt = Integer.parseUnsignedInt(a);
			bInt = Integer.parseUnsignedInt(b);
		} catch (Exception e) {
			System.err.println(e);
			return;
		}

		System.out.println(Integer.toUnsignedString(Ex01.multiplier(aInt, bInt)));
	}


	private void graycode(String n) {
		int nInt;
		try {
			nInt = Integer.parseUnsignedInt(n);
		} catch (Exception e) {
			System.err.println(e);
			return;
		}

		System.out.println(Integer.toUnsignedString(Ex02.gray_code(nInt)));
	}


	private void evaluate(String formula) {
		System.out.println(Ex03.eval_formula(formula));
	}


	private void table(String formula) {
		Ex04.print_truth_table(formula);
	}


	private void nnf(String formula) {
		System.out.println(Ex05.negation_normal_form(formula));
	}


	private void cnf(String formula) {
		System.out.println(Ex06.conjunctive_normal_form(formula));
	}


	private void sat(String formula) {
		System.out.println(Ex07.sat(formula));
	}


	private void tree(String formula) {
		try {
			AstBuilder
			.getAstBuilder()
			.astFromString(formula)
			.visualize();
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	private void test(String formula) {
		AbstractSyntaxTree tree;
		try {
			tree = AstBuilder.getAstBuilder().astFromString(formula);
			byte[] table = tree.getTruthTable();
		} catch (Exception e) {
			System.err.println(e);
			return;
		}
		// feel free to test (and/or abuse) the AST package
	}
}