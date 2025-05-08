package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex04 {

	public static void print_truth_table(String formula) {
		try {
			AstBuilder
			.getAstBuilder()
			.astFromString(formula)
			.printTruthTable();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}