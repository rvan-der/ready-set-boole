package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.AstException;

import java.util.Arrays;

public class AstNode {

	public AstNode[] operands = null;
	public int nbOperands;
	public String type;
	public String value;

	public AstNode (String type, int nbOperands, String value) throws AstException{
		if (!type.equals("operator") && !type.equals("primitive")) {
			throw new AstException("Unknown type '" + type + "' for new AstNode");
		}
		this.type = type;
		this.value = value;
	}
}