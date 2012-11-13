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

final class LString {

	private String str;

	LString(String s) {
		this.str = s;
	}

	LString(char[] value) {
		this.str = new String(value);
	}

	private final static Boolean T = Boolean.TRUE;
	private final static Boolean F = Boolean.FALSE;

	public String toString() {
		return str;
	}

	static {
		Subr.def("LString", "stringp", "string?", 1);
	}

	public static Boolean stringp(Object obj) {
		return obj instanceof LString ? T : F;
	}

	static {
		Subr.def("LString", "makeString", "make-string", 1, 1);
	}

	public static LString makeString(int length, Character fill) {
		if (length == 0)
			return new LString("");
		else {
			char c = (fill == null ? ' ' : fill.charValue());
			char[] v = new char[length];
			for (int i = 0; i < length; i++)
				v[i] = c;
			return new LString(v);
		}
	}

	static {
		Subr.def("LString", "list2string", "string", 0, true);
		Subr.def("LString", "list2string", "list->string", 1);
	}

	public static LString list2string(List args) {
		if (args == List.nil)
			return new LString("");
		else {
			StringBuffer sb = new StringBuffer();
			do
				sb.append(((Character) args.car).charValue());
			while ((args = (List) args.cdr) != List.nil);
			return new LString(sb.toString());
		}
	}

	static {
		Subr.def("LString", "string2list", "string->list", 1);
	}

	public static List string2list(LString s) {
		List val = List.nil;
		for (int i = s.str.length() - 1; i >= 0; i--)
			val = new Pair(Char.makeChar(s.str.charAt(i)), val);
		return val;
	}

	static {
		Subr.def("LString", "stringLength", "string-length", 1);
	}

	public static Integer stringLength(LString s) {
		return Num.makeInt(s.str.length());
	}

	static {
		Subr.def("LString", "stringRef", "string-ref", 2);
	}

	public static Character stringRef(LString s, int index) {
		return Char.makeChar(s.str.charAt(index));
	}

	static {
		Subr.def("LString", "stringSet", "string-set!", 3);
	}

	public static LString stringSet(LString s, int index, char c) {
		StringBuffer sb = new StringBuffer(s.str);
		sb.setCharAt(index, c);
		s.str = sb.toString();
		return s;
	}

	static {
		Subr.def("LString", "stringEQ", "string=?", 2);
	}

	public static Boolean stringEQ(LString s1, LString s2) {
		return s1.str.equals(s2.str) ? T : F;
	}

	static {
		Subr.def("LString", "stringEQci", "string-ci=?", 2);
	}

	public static Boolean stringEQci(LString s1, LString s2) {
		return s1.str.equalsIgnoreCase(s2.str) ? T : F;
	}

	static {
		Subr.def("LString", "stringLT", "string<?", 2);
	}

	public static Boolean stringLT(LString s1, LString s2) {
		return s1.str.compareTo(s2.str) < 0 ? T : F;
	}

	static {
		Subr.def("LString", "stringGT", "string>?", 2);
	}

	public static Boolean stringGT(LString s1, LString s2) {
		return s1.str.compareTo(s2.str) > 0 ? T : F;
	}

	static {
		Subr.def("LString", "stringLE", "string<=?", 2);
	}

	public static Boolean stringLE(LString s1, LString s2) {
		return s1.str.compareTo(s2.str) <= 0 ? T : F;
	}

	static {
		Subr.def("LString", "stringGE", "string>=?", 2);
	}

	public static Boolean stringGE(LString s1, LString s2) {
		return s1.str.compareTo(s2.str) >= 0 ? T : F;
	}

	static {
		Subr.def("LString", "stringLTci", "string-ci<?", 2);
	}

	public static Boolean stringLTci(LString s1, LString s2) {
		return s1.str.toLowerCase().compareTo(s2.str.toLowerCase()) < 0 ? T : F;
	}

	static {
		Subr.def("LString", "stringGTci", "string-ci>?", 2);
	}

	public static Boolean stringGTci(LString s1, LString s2) {
		return s1.str.toLowerCase().compareTo(s2.str.toLowerCase()) > 0 ? T : F;
	}

	static {
		Subr.def("LString", "stringLEci", "string-ci<=?", 2);
	}

	public static Boolean stringLEci(LString s1, LString s2) {
		return s1.str.toLowerCase().compareTo(s2.str.toLowerCase()) <= 0 ? T
				: F;
	}

	static {
		Subr.def("LString", "stringGEci", "string-ci>=?", 2);
	}

	public static Boolean stringGEci(LString s1, LString s2) {
		return s1.str.toLowerCase().compareTo(s2.str.toLowerCase()) >= 0 ? T
				: F;
	}

	static {
		Subr.def("LString", "stringAppend", "string-append", 0, true);
	}

	public static LString stringAppend(List args) {
		if (args == List.nil)
			return new LString("");

		LString s1 = (LString) args.car;
		args = (List) args.cdr;
		if (args == List.nil)
			return s1;

		LString s2 = (LString) args.car;
		args = (List) args.cdr;
		if (args == List.nil)
			return new LString(s1.str + s2.str);

		StringBuffer sb = new StringBuffer(s1.str);
		sb.append(s2.str);
		do
			sb.append(((LString) args.car).str);
		while ((args = (List) args.cdr) != List.nil);
		return new LString(sb.toString());
	}

	static {
		Subr.def("LString", "substring", 2, 1);
	}

	public static LString substring(LString s, int from, Integer to) {
		if (to == null)
			return new LString(s.str.substring(from));
		else
			return new LString(s.str.substring(from, to.intValue()));
	}

	static void init() {
	}
}
