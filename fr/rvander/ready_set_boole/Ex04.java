package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex04 {

	public static void print_truth_table(String formula) {
		try {
			AbstractSyntaxTree tree = AstBuilder
				.getAstBuilder()
				.astFromString(formula);
			if (tree.getNbVars() == 0) {
				System.err.println("Can't generate the truth table for a formula without variables.");
				return;
			}
			tree.printTruthTable();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}