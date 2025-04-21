package fr.rvander.ready_set_boole.AST;

import fr.rvander.ready_set_boole.AST.*;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.HashSet;
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
				"Received null instead of String in astBuilder.");
		}

		HashSet<String> varsSet = new HashSet<>();
		ArrayDeque<AstNode> stack = new ArrayDeque<>();

		for (char token : formula.toCharArray()) {

			AstNode node = this.newNodeFromToken(token);
			AstNode[] operands;

			switch (node.type) {
				case PRIMITIVE:
					stack.push(node);
					break;
				case VARIABLE:
					varsSet.add(((VariableNode)node).name);
					stack.push(node);
					break;
				case BINARY_OP:
					operands = new AstNode[2];
					try {
						operands[1] = stack.pop();
						operands[0] = stack.pop();
					} catch(NoSuchElementException e) {
						throw new AstException("The forula is invalid.");
					}
					node.setOperands(operands);
					stack.push(node);
					break;
				case UNARY_OP:
					operands = new AstNode[1];
					try {
						operands[0] = stack.pop();
					} catch(NoSuchElementException e) {
						throw new AstException("The forula is invalid.");
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

		String[] variables = new String[0];
		if (!varsSet.isEmpty()) {
			variables = varsSet.toArray(variables);
			Arrays.sort((Object[]) variables);
		}

		return new AbstractSyntaxTree(stack.pop(), variables);
	}


	private AstNode newNodeFromToken(char token) throws AstException {
		AstNode node;
		switch (token) {
		case '0':
		case '1': node = new PrimitiveNode(token == '1' ? true : false); break;
		case '&':
		case '|':
		case '^':
		case '>':
		case '=': node = new BinaryOperatorNode(token); break;
		case '!': node = new UnaryOperatorNode(token); break;
		default:
			if (Character.isUpperCase(token)) {
				node = new VariableNode(Character.toString(token));
			}
			else {
				throw new AstException("Invalid token '" + token
					+ "' found in the formula.");
			}
		}
		return node;
	}
}