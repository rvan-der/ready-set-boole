package fr.rvander.ready_set_boole.AST;

import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
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


	public AbstractSyntaxTree rewriteCnf() {
		tRoot = tRoot.rewriteOnlyJunctions();
		tRoot = tRoot.rewriteNegations();
		tRoot = tRoot.distributeJunctions("|");
		tRoot = tRoot.simplify();
		tRoot = tRoot.alignJunctions();
		return this;
	}


	public String getFormula() {
		return tRoot.getFormula();
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
				values += 1) {
				for (int i = 0; i < tNbVars; i += 1) {
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