package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex05 {

	public static String negation_normal_form(String formula) throws AstException {
		return AstBuilder
			.getAstBuilder()
			.astFromString(formula)
			.rewriteNnf()
			.getFormula();
	}
}