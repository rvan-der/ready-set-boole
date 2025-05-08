package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


public class VariableNode extends AstNode {

	private static final long serialVersionUID = -3433790913846854619L;
	protected String tName;


	protected VariableNode (String name) {
		super(AstNodeType.VARIABLE);
		tName = name;
		tProgramSymbol = name;
		tMathSymbol = name;
	}


	protected HashSet<String> getVariables(HashSet<String> varsSet) {
		varsSet.add(tName);
		return varsSet;
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		if (hypothesis == null) {
			System.err.println(
				"Warning! Missing hypothesis during evaluation of formula "
				+ "containing variables. False was returned by default.");
			return false;
		}
		Boolean result = hypothesis.get(tName);
		if (result == null) {
			System.err.println(
				"Warning! Invalid hypothesis during evaluation of formula "
				+ "containing variables. False was returned by default.");
			return false;
		}
		return result.booleanValue();
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
		return new VariableNode(tName);
	}
}