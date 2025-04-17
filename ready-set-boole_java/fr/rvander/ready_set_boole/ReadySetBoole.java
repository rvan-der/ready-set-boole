package fr.rvander.ready_set_boole;

import java.util.*;
import java.lang.reflect.*;
import fr.rvander.ready_set_boole.ex00.Adder;
import fr.rvander.ready_set_boole.ex01.Multiplier;
import fr.rvander.ready_set_boole.ex02.GrayCode;
import fr.rvander.ready_set_boole.AST.*;


public class ReadySetBoole {

	public static void main(String args[]) {
		if (args.length == 0) {
			System.err.println("Missing method and arguments.");
			return;
		}
		Method method = null;
		try {
			for (Method m : ReadySetBoole.class.getDeclaredMethods()) {
				if (m.getName().equals(args[0])) {
					method = m;
				}
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
		if (method == null || args[0].equals("main")) {
			System.err.println("Method '" + args[0] + "' doesn't exist.");
			return;
		}
		int paramCount = method.getParameterCount();
		if (paramCount != args.length - 1) {
			System.err.println("Wrong number of parameters for '" + method.getName() + "'. Found " + (args.length - 1) + " but " + paramCount + " are needed.");
			return;
		}
		try {
			method.invoke(new ReadySetBoole(), (Object[]) Arrays.copyOfRange(args, 1, args.length));
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	public void adder(String a, String b) {
		int aInt;
		int bInt;
		try {
			aInt = Integer.parseUnsignedInt(a);
			bInt = Integer.parseUnsignedInt(b);
			System.out.println(Integer.toUnsignedString(Adder.adder(aInt, bInt)));
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	public void multiplier(String a, String b) {
		int aInt;
		int bInt;
		try {
			aInt = Integer.parseUnsignedInt(a);
			bInt = Integer.parseUnsignedInt(b);
			System.out.println(Integer.toUnsignedString(Multiplier.multiplier(aInt, bInt)));
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	public void gray_code(String n) {
		int nInt;
		try {
			nInt = Integer.parseUnsignedInt(n);
			System.out.println(Integer.toUnsignedString(GrayCode.gray_code(nInt)));
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	public void test() {
		try {
			AstNode node = new AstNode("operator", 2, "+");
			for (AstNode n : node.operands) {
				System.out.println(n);
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}