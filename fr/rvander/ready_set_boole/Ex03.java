package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex03 {

	public static boolean eval_formula(String formula) {
		try {
			return AstBuilder
			.getAstBuilder()
			.astFromString(formula)
			.evaluate(null);
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
}