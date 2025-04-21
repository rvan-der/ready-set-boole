package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public class PrimitiveNode extends AstNode {

	private boolean value;


	protected PrimitiveNode (boolean value) {
		super(AstNodeType.PRIMITIVE);
		this.value = value;
		this.programSymbol = value == true ? "1" : "0";
		this.mathSymbol = value == true ? "⊤" : "⊥";
	}


	protected boolean evaluate() {
		return this.value;
	}
}