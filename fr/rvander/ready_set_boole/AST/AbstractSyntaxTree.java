package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;


public class AbstractSyntaxTree {
	private AstNode root;
	private String[] variables;
	private int nbVars;


	protected AbstractSyntaxTree(AstNode root) {
		this.root = root;
		this.updateVariables();
	}


	public boolean evaluate() {
		if (this.variables.length != 0) {
			System.err.println(
				"Warning! Can't evaluate an expression containing variables. "
				+ "False was returned by default.");
			return false;
		}
		return this.root.evaluate(null);
	}


	protected final void updateVariables() {
		HashSet<String> varsSet = this.root.getVariables(new HashSet<String>());
		String[] variables = new String[0];
		if (!varsSet.isEmpty()) {
			variables = varsSet.toArray(variables);
			Arrays.sort((Object[]) variables);
		}
		this.variables = variables;
		this.nbVars = variables.length;
	}


	public void printTruthTable() {
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


	public AbstractSyntaxTree rewriteNnf() {
		this.root = this.root.rewriteNnf();
		return this;
	}


	public AbstractSyntaxTree rewriteCnf() {
		this.root = this.root.rewriteNnf();
		this.root = this.root.rewriteCnf();
		return this;
	}


	public String getFormula() {
		return this.root.getFormula();
	}


	public void visualize() {
		this.root.visualize(0, "", false);
	}
}