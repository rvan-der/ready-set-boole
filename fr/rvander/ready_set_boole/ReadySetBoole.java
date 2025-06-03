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
		if (method == null || args[0].equals("main") || args[0].charAt(0) == '_') {
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


	// sets argument format:
	// '{int[,...]} [{...}...]'
	private int[][] _parseSets(String arg) {
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
			for (int i = 0; i < elemStrings.length; i += 1) {
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


	private void powerset(String arg) {
		int[][] sets = _parseSets(arg);
		if (sets == null) {
			System.err.println("Invalid argument format.");
			return;
		}
		if (sets.length != 1) {
			System.err.println("Only one set must be provided.");
		}
		int[] setArg = sets[0];
		// long start = System.currentTimeMillis();
		int[][] powerSet = Ex08.powerset(setArg);
		// long end = System.currentTimeMillis();
		// for (int[] set : powerSet) {
		// 	System.out.print("{");
		// 	for (int i = 0; i < set.length; i += 1) {
		// 		System.out.print(String.format("%d%s", set[i], i == set.length - 1 ? "" : ", "));
		// 	}
		// 	System.out.println("}");
		// }
		// System.out.println(String.format("powerset generation took %.3 seconds", (end - start) / 1000));
	}


	// write your own methods to test any feature
	// add '_' as first character of the name to make it unavailable on the command line
}