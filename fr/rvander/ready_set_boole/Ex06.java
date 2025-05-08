package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex06 {

	public static String conjunctive_normal_form(String formula) throws AstException {
		try {
			return AstBuilder
				.getAstBuilder()
				.astFromString(formula)
				.rewriteCnf()
				.getFormula();
		} catch (Exception e) {
			System.err.println(e);
		}
		return "";
	}
}