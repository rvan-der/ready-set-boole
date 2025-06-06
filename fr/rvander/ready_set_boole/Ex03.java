package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex03 {

	public static boolean eval_formula(String formula) {
		try {
			AbstractSyntaxTree tree = AstBuilder
				.getAstBuilder()
				.astFromString(formula);
			if (tree.getNbVars() > 0) {
				System.err.println("Can't directly evaluate a formula with variables. False was returned by default.");
				return false;
			}
			return tree.evaluate(null);
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
}