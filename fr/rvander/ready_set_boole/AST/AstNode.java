package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;
import java.io.Serializable;


public abstract class AstNode implements Serializable {

	private static final long serialVersionUID = -7509745474094255803L;
	protected AstNode[] tOperands;
	protected String tMathSymbol;
	protected String tProgramSymbol;
	protected AstNodeType tType;


	protected AstNode (AstNodeType type) {
		tType = type;
		tOperands = null;
	}


	protected abstract boolean evaluate(HashMap<String, Boolean> hypothesis);


	protected abstract AstNode rewriteOnlyJunctions();
	

	//protected abstract AstNode rewriteDnf();
	

	protected abstract AstNode rewriteNnf();
	

	protected abstract AstNode rewriteCnf();


	protected abstract AstNode copySubtree();


	protected abstract HashSet<String> getVariables(HashSet<String> varsSet);


	protected void setOperands(AstNode[] operands) {
		tOperands = operands;
	}


	protected void setOperandAt(AstNode operand, int index) {
		tOperands[index] = operand;
	}
	

	protected String getFormula() {
		String formula = "";
		for (int i = 0; i < tType.nbOperands(); i += 1) {
			formula += tOperands[i].getFormula();
		}
		return formula + tProgramSymbol;
	}


	protected void visualize(int depth, String branches, boolean unary) {
		if (depth > 0 && !unary) {
			for (int i = 0; i < depth - 1; i += 1) {
				System.out.print(branches.substring(i, i + 1) + "  ");
			}
			System.out.print(branches.charAt(depth - 1) == '│' ? "├─╴" : "└─╴");
		}
		if (depth > 0 && unary) {
			System.out.print("╶╴");
		}
		System.out.print(tMathSymbol);
		if (tType.nbOperands() != 1) {
			System.out.print("\n");
		}
		for (int i = tType.nbOperands() - 1; i >= 0; i -= 1) {
			tOperands[i].visualize(depth + 1,
				branches + (i > 0 ? "│" : " "),
				tType.nbOperands() == 1 ? true : false);
		}
	}
}