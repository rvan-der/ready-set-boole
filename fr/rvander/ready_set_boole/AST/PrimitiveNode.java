package fr.rvander.ready_set_boole.AST;

import java.util.HashMap;


public class PrimitiveNode extends AstNode {

	private static final long serialVersionUID = -4828465962740406325L;
	private boolean tValue;


	protected PrimitiveNode (String token) {
		super(AstNodeType.PRIMITIVE, token);
		tValue = token.equals("1") ? true : false;
		tMathSymbol = token.equals("1") ? "⊤" : "⊥";
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		return tValue;
	}
}