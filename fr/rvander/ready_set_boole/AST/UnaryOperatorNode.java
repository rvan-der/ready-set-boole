package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;


public class UnaryOperatorNode extends AstNode {

	private char operator;


	protected UnaryOperatorNode (char operator) {
		super(AstNodeType.UNARY_OP);
		this.operator = operator;
		this.programSymbol = String.valueOf(operator);
		switch (operator) {
		case '!': this.mathSymbol = "¬"; break;
		default: this.mathSymbol = "[symbol not found]";
		}
	}


	protected boolean evaluate() throws AstException {
		if (this.operands == null) {
			throw new AstException(
				"Error at evaluation : missing operand for '"
				+ this.operator + "'. The formula is invalid.");
		}

		boolean result;
		boolean operand = this.operands[0].evaluate();

		switch (this.operator) {
		case '!': result = !operand ? true : false; break;
		default:
			throw new AstException(
				"Error at evaluation : unknown operator '"
				+ this.operator + "'.");
		}

		return result;
	}
}