package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


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


	protected HashSet<String> getVariables(HashSet<String> varsSet) {
		return this.operands[0].getVariables(varsSet);
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		boolean result;
		boolean operand = this.operands[0].evaluate(hypothesis);

		switch (this.operator) {
		case '!': result = !operand ? true : false; break;
		default:
			System.err.println("Warning! Error during evaluation : unknown operator '"
				+ this.operator + "'. False was returned by default.");
			result = false;
		}

		return result;
	}


	protected AstNode rewriteNnf() {
		AstNode newSubtree;

		switch (this.operator) {
			case '!':
				switch (this.operands[0].programSymbol) {
					case "!":
						newSubtree = this.operands[0].operands[0];
						break;
					case "|":
						newSubtree = RewriteSubtrees.deMorgansLaws(
							this.operands[0].operands[0],
							this.operands[0].operands[1], '|');
						break;
					case "&":
						// System.out.println("plop");
						newSubtree = RewriteSubtrees.deMorgansLaws(
							this.operands[0].operands[0],
							this.operands[0].operands[1], '&');
						// newSubtree.visualize(0, "");
						break;
					default:
						newSubtree = null;
				}
				break;
			default:
				newSubtree = null;
		}

		if (newSubtree == null) {
			this.operands[0] = this.operands[0].rewriteNnf();
			return this;
		}
		else {
			return newSubtree.rewriteNnf();
		}
	}


	protected AstNode rewriteCnf() {
		this.operands[0] = this.operands[0].rewriteCnf();
		return this;
	}


	protected AstNode copySubtree() {
		AstNode copy = new UnaryOperatorNode(this.operator);
		AstNode[] operandsCopy = new AstNode[1];
		operandsCopy[0] = this.operands[0].copySubtree();
		copy.setOperands(operandsCopy);
		return copy;
	}
}