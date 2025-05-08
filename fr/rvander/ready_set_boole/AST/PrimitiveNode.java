package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


public class PrimitiveNode extends AstNode {

	private static final long serialVersionUID = -4828465962740406325L;
	private boolean tValue;


	protected PrimitiveNode (boolean value) {
		super(AstNodeType.PRIMITIVE);
		tValue = value;
		tProgramSymbol = value == true ? "1" : "0";
		tMathSymbol = value == true ? "⊤" : "⊥";
	}


	protected HashSet<String> getVariables(HashSet<String> varsSet) {
		return varsSet;
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		return tValue;
	}


	protected AstNode rewriteOnlyJunctions() {
		return this;
	}


	protected AstNode rewriteNnf() {
		return this;
	}


	protected AstNode rewriteCnf() {
		return this;
	}


	protected AstNode copySubtree() {
		return new PrimitiveNode(tValue);
	}
}