package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
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


	protected abstract AstNode copySubtree();


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


	protected void visualize(int depth, String branches) {
		if (depth > 0) {
			for (int i = 0; i < depth - 1; i += 1) {
				System.out.print(branches.substring(i, i + 1) + "  ");
			}
			System.out.print(branches.charAt(depth - 1) == '│' ? "├─ " : "└─ ");
		}
		System.out.println(this.mathSymbol);
		for (int i = this.type.nbOperands() - 1; i >= 0; i -= 1) {
			this.operands[i].visualize(depth + 1, branches + (i > 0 ? "│" : " "));
		}
	}
}