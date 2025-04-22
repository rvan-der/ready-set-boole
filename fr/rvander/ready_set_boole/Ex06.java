package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex06 {

	public static String conjunctive_normal_form(String formula) throws AstException {
		return AstBuilder
			.getAstBuilder()
			.astFromString(formula)
			.rewriteCnf()
			.getFormula();
	}
}