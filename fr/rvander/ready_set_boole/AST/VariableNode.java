package fr.rvander.ready_set_boole.AST;

import java.util.HashMap;


public class VariableNode extends AstNode {

	private static final long serialVersionUID = -3433790913846854619L;


	protected VariableNode (String token) {
		super(AstNodeType.VARIABLE, token);
		tMathSymbol = token;
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		if (hypothesis == null) {
			System.err.println(
				"Warning! Missing hypothesis during evaluation of formula "
				+ "containing variables. False was returned by default.");
			return false;
		}
		Boolean result = hypothesis.get(tToken);
		if (result == null) {
			System.err.println(
				"Warning! Invalid hypothesis during evaluation of formula "
				+ "containing variables. False was returned by default.");
			return false;
		}
		return result.booleanValue();
	}
}