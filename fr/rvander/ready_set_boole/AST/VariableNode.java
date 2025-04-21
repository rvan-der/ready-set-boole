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


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) throws AstException {
		if (hypothesis == null) {
			throw new AstException(
				"Null hypothesis during evluation of formula containing variables.");
		}
		Boolean result = hypothesis.get(this.name);
		if (result == null) {
			throw new AstException(
				"Invalid hypothesis during evluation of formula containing variables.");
		}
		return result.booleanValue();
	}
}