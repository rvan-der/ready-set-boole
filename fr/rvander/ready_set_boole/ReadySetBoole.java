package fr.rvander.ready_set_boole;

import java.util.*;
import java.lang.reflect.*;

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
			return;
		}
		if (method == null || args[0].equals("main") || Modifier.isPrivate(method.getModifiers())) {
			System.err.println("Method '" + args[0] + "' is not available.");
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
			method.invoke(null, (Object[])Arrays.copyOfRange(args, 1, args.length));
		} catch (Exception e) {
			System.err.println(e.getCause());
		}
	}


	public static void adder(String a, String b) {
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


	public static void multiplier(String a, String b) {
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


	public static void graycode(String n) {
		int nInt;
		try {
			nInt = Integer.parseUnsignedInt(n);
		} catch (Exception e) {
			System.err.println(e);
			return;
		}

		System.out.println(Integer.toUnsignedString(Ex02.gray_code(nInt)));
	}


	public static void evaluate(String formula) {
		System.out.println(Ex03.eval_formula(formula));
	}


	public static void table(String formula) {
		Ex04.print_truth_table(formula);
	}


	public static void tableTime(String formula) {
		AbstractSyntaxTree tree = null;
		try {
			tree = AstBuilder
				.getAstBuilder()
				.astFromString(formula);
		} catch (Exception e) {
			System.err.println(e);
			return;
		}
		if (tree.getNbVars() == 0) {
			System.err.println("Can't generate the truth table for a formula without variables.");
			return;
		}
		long start = System.currentTimeMillis();
		tree.getTruthTable();
		long end = System.currentTimeMillis();
		if (tree.getNbVars() < 9) {
			tree.printTruthTable();
		}
		System.out.println(String.format("Truth table generation took %.3f seconds.", (float)(end - start) / 1000));
	}


	public static void nnf(String formula) {
		System.out.println(Ex05.negation_normal_form(formula));
	}


	public static void cnf(String formula) {
		System.out.println(Ex06.conjunctive_normal_form(formula));
	}


	// long unsatisfiable formula:
	// 'AA!&BB!&CC!&DD!&EE!&FF!&GG!&HH!&II!&JJ!&KK!&LL!&MM!&NN!&OO!&PP!&QQ!&RR!&SS!&TT!&UU!&VV!&WW!&XX!&YY!&ZZ!&|||||||||||||||||||||||||'
	public static void sat(String formula) {
		System.out.println(Ex07.sat(formula));
	}


	public static void tree(String formula) {
		try {
			AstBuilder
			.getAstBuilder()
			.astFromString(formula)
			.visualize();
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	// sets argument format:
	// '{int[,...]} [{...}...]'
	private static int[][] parseSets(String arg) {
		ArrayList<int[]> result = new ArrayList<>();
		arg = arg.replaceAll("\\s","");
		if (!arg.startsWith("{") || !arg.endsWith("}")) {
			return null;
		}
		arg = arg.substring(1, arg.length() - 1);
		String[] setStrings = null;
		setStrings = arg.split("\\}\\{");
		for (String s : setStrings) {
			String[] elemStrings = s.split(",");
			int[] set = new int[elemStrings.length];
			for (int i = 0; i < elemStrings.length; i++) {
				try {
					set[i] = Integer.parseInt(elemStrings[i]);
				} catch (Exception e){
					System.err.println(e);
					return null;
				}
			}
			result.add(set);
		}

		return result.toArray(new int[result.size()][]);
	}


	public static void powerset(String setString) {
		int[][] sets = parseSets(setString);
		if (sets == null) {
			System.err.println("Invalid argument format.");
			return;
		}
		if (sets.length != 1) {
			System.err.println("Exactly one set must be provided.");
			return;
		}
		int[] setArg = sets[0];
		long start = System.currentTimeMillis();
		int[][] powerSet = Ex08.powerset(setArg);
		long end = System.currentTimeMillis();
		if (setArg.length < 9) {
			for (int[] set : powerSet) {
				System.out.print("{");
				for (int i = 0; i < set.length; i++) {
					System.out.print(String.format("%d%s", set[i], i == set.length - 1 ? "" : ", "));
				}
				System.out.println("}");
			}
		}
		System.out.println(String.format("Powerset generation took %.3f seconds.", (float)(end - start) / 1000));
	}


	public static void evalset(String formula, String setString) {
		int[][] sets = parseSets(setString);
		if (sets == null) {
			System.err.println("Invalid sets argument format.");
			return;
		}
		if (sets.length < 1) {
			System.err.println("At least one set must be provided.");
			return;
		}
		int[] result = Ex09.eval_set(formula, sets);
		if (result == null) {
			return;
		}
		System.out.print("{");
		for (int i = 0; i < result.length; i++) {
			System.out.print(String.format("%d%s", result[i], i == result.length - 1 ? "" : ", "));
		}
		System.out.println("}");
	}


	// Write your own methods to test any feature !
	// All methods must be static.
	// Only public methods are available on the command line.
}