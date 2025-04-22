package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashSet;
import java.util.HashMap;


public abstract class AstNode {

	protected AstNode[] operands = null;
	protected String mathSymbol;
	protected String programSymbol;
	protected AstNodeType type;


	protected AstNode (AstNodeType type) {
		this.type = type;
	}


	protected abstract boolean evaluate(HashMap<String, Boolean> hypothesis);


	protected abstract AstNode rewriteNnf();
	

	protected abstract AstNode rewriteCnf();


	protected abstract AstNode copySubtree();


	protected abstract HashSet<String> getVariables(HashSet<String> varsSet);


	protected void setOperands(AstNode[] operands) {
		this.operands = operands;
	}


	protected void setOperandAt(AstNode operand, int index) {
		this.operands[index] = operand;
	}
	

	protected String getFormula() {
		String formula = "";
		for (int i = 0; i < this.type.nbOperands(); i += 1) {
			formula += this.operands[i].getFormula();
		}
		return formula + this.programSymbol;
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
		System.out.print(this.mathSymbol);
		if (this.type.nbOperands() != 1) {
			System.out.print("\n");
		}
		for (int i = this.type.nbOperands() - 1; i >= 0; i -= 1) {
			this.operands[i].visualize(depth + 1,
				branches + (i > 0 ? "│" : " "),
				this.type.nbOperands() == 1 ? true : false);
		}
	}
}