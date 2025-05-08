package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


public class UnaryOperatorNode extends AstNode {

	private static final long serialVersionUID = 4659253008544725917L;
	private char tOperator;


	protected UnaryOperatorNode (char operator) {
		super(AstNodeType.UNARY_OP);
		tOperator = operator;
		tProgramSymbol = String.valueOf(operator);
		switch (operator) {
		case '!': tMathSymbol = "¬"; break;
		default: tMathSymbol = "[symbol not found]";
		}
	}


	protected HashSet<String> getVariables(HashSet<String> varsSet) {
		return tOperands[0].getVariables(varsSet);
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		boolean result;
		boolean operand = tOperands[0].evaluate(hypothesis);

		switch (tOperator) {
		case '!': result = !operand ? true : false; break;
		default:
			System.err.println("Warning! Error during evaluation : unknown operator '"
				+ tOperator + "'. False was returned by default.");
			result = false;
		}

		return result;
	}


	protected AstNode rewriteOnlyJunctions() {
		return this;
	}


	protected AstNode rewriteNnf() {
		AstNode newSubtree;

		switch (tOperator) {
			case '!':
				switch (tOperands[0].tProgramSymbol) {
					case "!":
						newSubtree = tOperands[0].tOperands[0];
						break;
					case "|":
						newSubtree = RewriteSubtrees.deMorgansLaws(
							tOperands[0].tOperands[0],
							tOperands[0].tOperands[1], '|');
						break;
					case "&":
						// System.out.println("plop");
						newSubtree = RewriteSubtrees.deMorgansLaws(
							tOperands[0].tOperands[0],
							tOperands[0].tOperands[1], '&');
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
			tOperands[0] = tOperands[0].rewriteNnf();
			return this;
		}
		else {
			return newSubtree.rewriteNnf();
		}
	}


	protected AstNode rewriteCnf() {
		tOperands[0] = tOperands[0].rewriteCnf();
		return this;
	}


	protected AstNode copySubtree() {
		AstNode copy = new UnaryOperatorNode(tOperator);
		AstNode[] operandsCopy = new AstNode[1];
		operandsCopy[0] = tOperands[0].copySubtree();
		copy.setOperands(operandsCopy);
		return copy;
	}
}