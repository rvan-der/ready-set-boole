package fr.rvander.ready_set_boole.AST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.lang.StringBuilder;
import java.lang.Runtime;
import java.io.Serializable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;


public class AbstractSyntaxTree implements Serializable {
	
	private static final long serialVersionUID = 2876891991332221612L;
	private AstNode tRoot;
	private String[] tVariables;
	private byte[] tTruthTable;
	private int tNbVars;


	protected AbstractSyntaxTree(AstNode root) {
		tRoot = root;
		tTruthTable = null;
		updateVariables();
	}


	public String[] getVariables() {
		return tVariables;
	}


	public int getNbVars() {
		return tNbVars;
	}


	public boolean evaluate(HashMap<String, Boolean> hypothesis) {
		return tRoot.evaluate(hypothesis);
	}


	public byte[] getTruthTable() {
		if (tTruthTable == null) {
			this.updateTruthTable();
		}
		return tTruthTable;
	}


	public AbstractSyntaxTree rewriteNnf() {
		tRoot = tRoot.rewriteOnlyJunctions();
		tRoot = tRoot.rewriteNegations();
		return this;
	}


	private void alignJunctions() {
		ArrayDeque<AstNode> stack = new ArrayDeque<>();
		UnaryOperatorNode rootHandle = new UnaryOperatorNode("!");
		rootHandle.setOperand(tRoot, 0);
		stack.push(tRoot);
		AstNode current;
		AstNode parent;

		while (!stack.isEmpty()){
			current = stack.pop();
			parent = current.tParent;
			for (int i = current.tType.nbOperands() - 1; i >= 0; i--) {
				if (!stack.isEmpty() && stack.peek().equals(current.tOperands[i])) {
					continue;
				}
				stack.push(current.tOperands[i]);
			}
			if (!"|&".contains(current.tToken)) {
				continue;
			}
			int combination = (current.tOperands[0].tToken.equals(current.tToken) ? 2 : 0)
						+ (current.tOperands[1].tToken.equals(current.tToken) ? 1 : 0);
			switch (combination) {
			case 2:
				//    &           &
				//   / \   -->   / \
				//  &   A       A   &
				AstNode swap = current.tOperands[0];
				current.setOperand(current.tOperands[1], 0);
				current.setOperand(swap, 1);
				break;
			case 3:
				//     \                                       \                    \
				//      & <-curr.            & <-curr.   root-> &   & <-curr.        & <-root
				//     / \                \ / \                / \ / \              / \
				//    &   &     1)  root-> &   &      2)      A   B   &       3)   A   & <-curr.
				//   / \ / \    -->       / \ / \     -->            / \      -->     / \
				//  A  B C  D            A  B C  D                  C   D            B   &
				//                                                                      / \
				//                                                                     C   D
				// 1)
				AstNode root = current.tOperands[0];
				parent.setOperand(root, current.tIndex);
				// 2)
				current.setOperand(root.tOperands[1], 0);
				// 3)
				root.setOperand(current, 1);
				break;
			default:
				break;
			}
		}

		tRoot = rootHandle.tOperands[0];
		tRoot.setParent(null);
	}


	public AbstractSyntaxTree rewriteCnf() {
		tRoot = tRoot.rewriteOnlyJunctions();
		tRoot = tRoot.rewriteNegations();
		tRoot = tRoot.distributeJunctions("|");
		tRoot = tRoot.simplify();
		alignJunctions();
		return this;
	}


	public String getFormula() {
		ArrayDeque<AstNode> stack = new ArrayDeque<>();
		StringBuilder result = new StringBuilder();
		stack.push(tRoot);
		
		while (!stack.isEmpty()) {
			AstNode current = stack.pop();
			result.append(current.tToken);
			for (int i = 0; i < current.tType.nbOperands(); i++) {
				stack.push(current.tOperands[i]);
			}
		}

		return result.reverse().toString();
	}



	public void visualize() {
		tRoot.visualize(0, "", false);
	}


	public boolean isSatisfiable() {
		AtomicBoolean result = new AtomicBoolean(false);
		SatCalculator calculator =
			new SatCalculator(this, 0, 1 << tNbVars, tTruthTable, result);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(calculator);
        return result.get();
	}


	private void updateTruthTable() {
		int length = 1 << tNbVars;
		tTruthTable = new byte[length];

        TruthTableCalculator calculator =
        	new TruthTableCalculator(this, 0, length, tTruthTable);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(calculator);
	}


	private void updateVariables() {
		HashSet<String> varsSet = tRoot.getVariables(new HashSet<String>());
		tVariables = new String[0];
		if (!varsSet.isEmpty()) {
			tVariables = varsSet.toArray(tVariables);
			Arrays.sort((Object[]) tVariables);
		}
		tNbVars = tVariables.length;
	}


	public void printTruthTable() {
		byte[] table = getTruthTable();

		// number of chars in one line of the table
		// 4 chars per variable column (│.x.), last column (=) has 5 (│.x.│) and +1 for '\n'
		long charsPerLine = tNbVars * 4 + 6;

		// number of lines (not counting header and footer)
		long nbLines = table.length;

		// total number or chars to print (not counting header and footer)
		long nbChars = charsPerLine * nbLines;

		// set max buffer capacity (in chars) to the minimum
		// between available memory and max integer value
		// divide available memory by 2 because a char takes 2 bytes
		long freeSpace = Runtime.getRuntime().freeMemory() / 2l;
		long maxCapacity = freeSpace > (long)Integer.MAX_VALUE ? (long)Integer.MAX_VALUE : freeSpace;

		// the number of lines fitting into maxCapacity
		int maxLinesPerBuffer = (int)(maxCapacity / charsPerLine);

		// set buffer capacity to the minimum between total character count
		// and the max number of lines (times chars per line)
		int bufferCapacity = nbChars <= maxCapacity ?
							(int)nbChars : (int)(charsPerLine * maxLinesPerBuffer);

		// the number of buffers needed to print all chars
		int nbBuffers = (int)(nbChars / bufferCapacity)
						+ (nbChars % bufferCapacity > 0 ? 1 : 0);

		// the buffer
		StringBuilder buffer = new StringBuilder(bufferCapacity);


		// HEADER
		System.out.print("┌");
		for (int i = 0; i < tNbVars - 1; i++) {
			System.out.print("───┬");
		}
		System.out.println("───╥───┐");

		for (String varName : tVariables) {
			System.out.print("│ " + varName + " ");
		}
		System.out.println("║ = │");

		System.out.print("├");
		for (int i = 0; i < tNbVars - 1; i++) {
			System.out.print("───┼");
		}
		System.out.println("───╫───┤");
		

		// BODY
		for (int iBuffer = 0; iBuffer < nbBuffers; iBuffer++) {
			for (int values = iBuffer * maxLinesPerBuffer;
				values < (iBuffer + 1) * maxLinesPerBuffer && values < nbLines;
				values++) {
				for (int i = 0; i < tNbVars; i++) {
					int val = (values >>> (tNbVars - i - 1)) & 1;
					buffer.append("│ " + val + " ");
				}
				buffer.append("║ " + table[values] + " │\n");
			}
			System.out.print(buffer.toString());
			buffer.setLength(0);
		}

		// FOOTER
		System.out.print("└");
		for (int i = 0; i < tNbVars - 1; i++) {
			System.out.print("───┴");
		}
		System.out.println("───╨───┘");
	}
}