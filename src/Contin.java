// A Lisp Driver to be embedded in Java Applications

// The contents of this file are subject to the Mozilla Public License
// Version 1.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at
// http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an "AS IS"
// basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
// License for the specific language governing rights and limitations
// under the License.
//
// The Original Code is JAKLD code, released November 26, 2002.
//
// The Initial Developer of the Original Code is Taiichi Yuasa.
// Portions created by Taiichi Yuasa are Copyright (C) 2002
// Taiichi Yuasa. All Rights Reserved.
//
// Contributor(s): Taiichi Yuasa <yuasa@kuis.kyoto-u.ac.jp>

final class Contin extends Function {

	private boolean canCall = true;

	public String toString() {
		return "#<continuation>";
	}

	private static Contin thrower;;
	private static Object value;
	final public static RuntimeException escapeToken = Eval
			.error("escapeToken");

	/*
	 * static { Subr.def("Contin", "callcc", "call/cc", 1); Subr.def("Contin",
	 * "callcc", "call-with-current-continuation", 1); }
	 */

	public static Object callcc(Function fun) {
		Contin cont = new Contin();
		try {
			return fun.invoke1(cont);
		} catch (RuntimeException c) {
			if (c == escapeToken && thrower == cont)
				return value;
			else
				throw c;
		} finally {
			cont.canCall = false;
		}
	}

	public Object invoke0(List args) {
		if (args == List.nil)
			throw Eval.error("too few arguments to continuation");
		else if (args.cdr != List.nil)
			throw Eval.error("too many arguments to continuation");

		if (canCall) {
			thrower = this;
			value = args.car;
			throw escapeToken;
		} else
			throw Eval.error("no continuation receiver");
	}

	static void init() {
	}

	static void clean() {
		thrower = null;
		value = null;
	}
}
