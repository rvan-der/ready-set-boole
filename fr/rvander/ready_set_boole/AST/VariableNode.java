package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashMap;


public class VariableNode extends AstNode {

	protected String name;


	protected VariableNode (String name) {
		super(AstNodeType.VARIABLE);
		this.name = name;
		this.programSymbol = name;
		this.mathSymbol = name;
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		if (hypothesis == null) {
			System.err.println(
				"Warning! Missing hypothesis during evaluation of formula "
				+ "containing variables. False was returned by default.");
			return false;
		}
		Boolean result = hypothesis.get(this.name);
		if (result == null) {
			System.err.println(
				"Warning! Invalid hypothesis during evaluation of formula "
				+ "containing variables. False was returned by default.");
			return false;
		}
		return result.booleanValue();
	}


	protected AstNode rewriteNnf() {
		return this;
	}


	protected AstNode copySubtree() {
		return new VariableNode(this.name);
	}
}