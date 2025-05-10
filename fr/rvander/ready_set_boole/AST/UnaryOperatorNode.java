package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


public class UnaryOperatorNode extends AstNode {

	private static final long serialVersionUID = 4659253008544725917L;


	protected UnaryOperatorNode (String token) {
		super(AstNodeType.UNARY_OP, token);
		switch (token) {
			case "!": tMathSymbol = "¬"; break;
			default: tMathSymbol = "[NA]";
		}
	}


	protected boolean evaluate(HashMap<String, Boolean> hypothesis) {
		boolean result;
		boolean operand = tOperands[0].evaluate(hypothesis);

		switch (tToken) {
			case "!": result = !operand ? true : false; break;
			default:
				System.err.println("Warning! Error during evaluation : unknown operator '"
					+ tToken + "'. False was returned by default.");
				result = false;
		}

		return result;
	}


	protected AstNode rewriteOnlyJunctions() {
		tOperands[0] = tOperands[0].rewriteOnlyJunctions();
		return this;
	}


	protected AstNode rewriteNegations() {
		AstNode operand = tOperands[0];

		if (operand.tToken.equals("!")) {
			return operand.tOperands[0].rewriteNegations();
		}
		if ("&|".contains(operand.tToken)) {
			return RewriteSubtrees
				.deMorgansLaws(
					operand.tOperands[0],
					operand.tOperands[1],
					operand.tToken)
				.rewriteNegations();
		}
		tOperands[0] = tOperands[0].rewriteNegations();
		return this;
	}
}