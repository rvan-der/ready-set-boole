package fr.rvander.ready_set_boole;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.NoSuchElementException;

import fr.rvander.ready_set_boole.sets.SetOperations;


public class Ex09 {

	public static int[] eval_set(String formula, int[][] sets) {
		if (formula == null) {
			System.err.println("Received null instead of String in eval_set.");
			return null;
		}
		if (sets.length > 26) {
			System.err.println("Too many sets. Support for only up to 26 sets.");
			return null;
		}
		if (formula.length() == 0) {
			System.err.println("The formula is invalid.");
			return null;
		}

		HashMap<String, int[]> setMap = new HashMap<>();
		for (int i = 0; i < sets.length; i++) {
			String name = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i + 1);
			setMap.put(name, sets[i]);
		}
		setMap.put("global", SetOperations.combine(sets));
		int setUid = 0;

		ArrayDeque<String> stack = new ArrayDeque<>();

		for (String token : formula.split("")) {
			if (token.length() != 1) {
				System.err.println("The formula is invalid.");
				return null;
			}

			if (token.equals("!")) {
				String operandName = "";
				try {
					operandName = stack.pop();
				} catch(NoSuchElementException e) {
					System.err.println("The formula is invalid.");
					return null;
				}

				int[] operand = setMap.get(operandName);
				if (operand == null) {
					System.err.println("The formula is invalid.");
					return null;
				}

				String resultName = String.valueOf(setUid);
				setUid++;
				stack.push(resultName);
				setMap.put(resultName,
					SetOperations.complement(operand, setMap.get("global")));
			}

			else if ("&|^>=".contains(token)) {
				String leftName = "";
				String rightName = "";
				try {
					rightName = stack.pop();
					leftName = stack.pop();
				} catch(NoSuchElementException e) {
					System.err.println("The formula is invalid.");
					return null;
				}

				int[] left = setMap.get(leftName);
				int[] right = setMap.get(rightName);
				if (left == null || right == null) {
					System.err.println("The formula is invalid.");
					return null;
				}

				String resultName = String.valueOf(setUid);
				setUid++;
				stack.push(resultName);
				switch (token) {
				case "&":
					setMap.put(resultName,
						SetOperations.intersection(left, right));
					break;
				case "|":
					setMap.put(resultName,
						SetOperations.union(left, right));
					break;
				case "^":
					setMap.put(resultName,
						SetOperations.xor(left, right));
					break;
				case ">":
					setMap.put(resultName,
						SetOperations.implication(left, right, setMap.get("global")));
					break;
				case "=":
					setMap.put(resultName,
						SetOperations.equivalence(left, right, setMap.get("global")));
					break;
				default:
					System.err.println("The formula is invalid.");
					return null;
				}
			}

			else if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(token)) {
				stack.push(token);
			}

			else {
				System.err.println("The formula is invalid.");
				return null;
			}
		}

		if (stack.size() != 1) {
			System.err.println("The formula is invalid.");
			return null;
		}

		int[] result = setMap.get(stack.pop());
		if (result == null) {
			System.err.println("The formula is invalid.");
			return null;
		}

		return result;
	}
}