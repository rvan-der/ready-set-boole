package fr.rvander.ready_set_boole.AST;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;


public class AstBuilder {

	private static AstBuilder instance = null;


	private AstBuilder() {}


	public static AstBuilder getAstBuilder() {
		if (instance == null)
			instance = new AstBuilder();
		return instance;
	}


	public AbstractSyntaxTree astFromString(String formula) throws AstException {
		if (formula == null) {
			throw new AstException(
				"Received null instead of String in AstBuilder.");
		}

		ArrayDeque<AstNode> stack = new ArrayDeque<>();

		for (char token : formula.toCharArray()) {

			AstNode node = newNodeFromToken(String.valueOf(token));
			AstNode[] operands;
			switch (node.tType) {
				case PRIMITIVE:
				case VARIABLE:
					stack.push(node);
					break;
				case BINARY_OP:
					operands = new AstNode[2];
					try {
						operands[1] = stack.pop();
						operands[0] = stack.pop();
					} catch(NoSuchElementException e) {
						throw new AstException("The formula is invalid.");
					}
					node.setOperands(operands);
					stack.push(node);
					break;
				case UNARY_OP:
					operands = new AstNode[1];
					try {
						operands[0] = stack.pop();
					} catch(NoSuchElementException e) {
						throw new AstException("The formula is invalid.");
					}
					node.setOperands(operands);
					stack.push(node);
					break;
				default:
					break;
			}
		}

		if (stack.size() != 1) {
			throw new AstException("The formula is invalid.");
		}

		return new AbstractSyntaxTree(stack.pop());
	}


	private AstNode newNodeFromToken(String token) throws AstException {
		for (AstNodeType type : AstNodeType.values()) {
			if (type.containsToken(token)) {
				return type.createNode(token);
			}
		}
		throw new AstException(
			"Invalid token '" + token + "' found in the formula.");
	}
}