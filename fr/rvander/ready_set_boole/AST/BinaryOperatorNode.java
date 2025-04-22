package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashMap;


public class BinaryOperatorNode extends AstNode {

	private char operator;


	protected BinaryOperatorNode (char operator) {
		super(AstNodeType.BINARY_OP);
		this.operator = operator;
		this.programSymbol = String.valueOf(operator);
		switch (operator) {
			case '&': this.mathSymbol = "∧"; break;
			case '|': this.mathSymbol = "∨"; break;
			case '^': this.mathSymbol = "⊕"; break;
			case '>': this.mathSymbol = "⇒"; break;
			case '=': this.mathSymbol = "⇔"; break;
			default: this.mathSymbol = "[symbol not found]";
		}
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		boolean result;
		boolean left = this.operands[0].evaluate(hypothesis);
		boolean right = this.operands[1].evaluate(hypothesis);

		switch (this.operator) {
			case '&': result = left && right ? true : false; break;
			case '|': result = left || right ? true : false; break;
			case '^': result = !left && right || left && !right ? true : false; break;
			case '>': result = !left || right ? true : false; break;
			case '=': result = left == right ? true : false; break;
			default:
				System.err.println("Warning! Error at evaluation : unknown operator '"
					+ this.operator + "'. False was returned by default.");
				result = false;
		}

		return result;
	}


	protected AstNode rewriteNnf() {
		AstNode newSubtree;

		switch (this.operator) {
			case '>':
				newSubtree = NnfSubtrees.materialCondition(
					this.operands[0], this.operands[1]);
				break;
			case '=':
				newSubtree = NnfSubtrees.equivalence(
					this.operands[0], this.operands[1]);
				break;
			default:
				newSubtree = null;
		}

		if (newSubtree == null) {
			this.operands[0] = this.operands[0].rewriteNnf();
			this.operands[1] = this.operands[1].rewriteNnf();
			return this;
		}
		else {
			return newSubtree.rewriteNnf();
		}
	}


	protected AstNode copySubtree() {
		AstNode copy = new BinaryOperatorNode(this.operator);
		AstNode[] operandsCopy = new AstNode[2];
		operandsCopy[0] = this.operands[0].copySubtree();
		operandsCopy[1] = this.operands[1].copySubtree();
		copy.setOperands(operandsCopy);
		return copy;
	}
}