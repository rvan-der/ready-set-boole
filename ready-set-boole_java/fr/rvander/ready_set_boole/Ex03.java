package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex03 {

	public static boolean eval_formula(String formula) throws AstException {
		return AstBuilder
		.getAstBuilder()
		.astFromString(formula)
		.evaluate();
	}
}