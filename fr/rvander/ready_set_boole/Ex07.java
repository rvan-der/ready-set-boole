package fr.rvander.ready_set_boole;

import fr.rvander.ready_set_boole.AST.*;


public class Ex07 {

	public static boolean sat(String formula) {
		try {
			return AstBuilder
				.getAstBuilder()
				.astFromString(formula)
				.isSatisfiable();
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
}