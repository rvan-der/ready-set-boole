package fr.rvander.ready_set_boole;

import java.util.*;


public class Test {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Set argument missing.");
			return;
		}
		int[][] sets = parseSets(args[0]);
		if (sets == null) {
			System.err.println("Invalid argument format.");
			return;
		}
		if (sets.length != 1) {
			System.err.println("Only one set must be provided.");
			return;
		}
		int[] setArg = sets[0];
		int[][] powerSet = Ex08.powerset(setArg);
		for (int[] set : powerSet) {
			System.out.print("{");
			for (int i = 0; i < set.length; i += 1) {
				System.out.print(String.format("%d%s", set[i], i == set.length - 1 ? "" : ", "));
			}
			System.out.println("}");
		}
	}

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
}