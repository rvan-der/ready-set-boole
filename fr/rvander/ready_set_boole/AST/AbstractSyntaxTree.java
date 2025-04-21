package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.HashMap;


public class AbstractSyntaxTree {
	private AstNode root;
	private String[] variables;
	private int nbVars;


	protected AbstractSyntaxTree(AstNode root, String[] variables) {
		this.root = root;
		this.variables = variables;
		this.nbVars = variables.length;
	}


	public boolean evaluate() throws AstException {
		if (this.variables.length != 0) {
			throw new AstException(
				"Can't evaluate an expression containing variables.");
		}
		return this.root.evaluate(null);
	}


	public void printTruthTable() throws AstException {
		HashMap<String, Boolean> hypothesis = new HashMap<>();

		System.out.print("│");
		for (String varName : this.variables) {
			System.out.print(" " + varName + " │");
		}
		System.out.println(" = │");

		System.out.print("│");
		for (int i = 0; i < this.nbVars; i += 1) {
			System.out.print("───│");
		}
		System.out.println("───│");
		
		for (int values = 0; values < (1 << this.nbVars); values += 1) {
			System.out.print("│");
			for (int i = 0; i < this.nbVars; i += 1) {
				int val = (values >>> (this.nbVars - i - 1)) & 1;
				hypothesis.put(
					this.variables[i],
					val == 1 ? Boolean.valueOf(true) : Boolean.valueOf(false));
				System.out.print(" " + val + " │");
			}
			System.out.println(" "
				+ (this.root.evaluate(hypothesis) == true ? "1" : "0")
				+ " │");
		}
	}


	public void visualize() {
		this.root.visualize(0, "");
	}
}