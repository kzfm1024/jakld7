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

final class Char {

	private final static Character[] chtab = new Character[128];

	static {
		for (int i = 0; i < 128; i++)
			chtab[i] = new Character((char) i);
	}

	static Character makeChar(char c) {
		if (c >= 0 && c < 128)
			return chtab[c];
		else
			return new Character(c);
	}

	private final static Boolean T = Boolean.TRUE;
	private final static Boolean F = Boolean.FALSE;

	static {
		Subr.def("Char", "charp", "char?", 1);
	}

	public static Boolean charp(Object obj) {
		return obj instanceof Character ? T : F;
	}

	static {
		Subr.def("Char", "charEQ", "char=?", 2);
	}

	public static Boolean charEQ(char c1, char c2) {
		return c1 == c2 ? T : F;
	}

	static {
		Subr.def("Char", "charLT", "char<?", 2);
	}

	public static Boolean charLT(char c1, char c2) {
		return c1 < c2 ? T : F;
	}

	static {
		Subr.def("Char", "charGT", "char>?", 2);
	}

	public static Boolean charGT(char c1, char c2) {
		return c1 > c2 ? T : F;
	}

	static {
		Subr.def("Char", "charLE", "char<=?", 2);
	}

	public static Boolean charLE(char c1, char c2) {
		return c1 <= c2 ? T : F;
	}

	static {
		Subr.def("Char", "charGE", "char>=?", 2);
	}

	public static Boolean charGE(char c1, char c2) {
		return c1 >= c2 ? T : F;
	}

	static {
		Subr.def("Char", "charEQci", "char-ci=?", 2);
	}

	public static Boolean charEQci(char c1, char c2) {
		return Character.toLowerCase(c1) == Character.toLowerCase(c2) ? T : F;
	}

	static {
		Subr.def("Char", "charLTci", "char-ci<?", 2);
	}

	public static Boolean charLTci(char c1, char c2) {
		return Character.toLowerCase(c1) < Character.toLowerCase(c2) ? T : F;
	}

	static {
		Subr.def("Char", "charGTci", "char-ci>?", 2);
	}

	public static Boolean charGTci(char c1, char c2) {
		return Character.toLowerCase(c1) > Character.toLowerCase(c2) ? T : F;
	}

	static {
		Subr.def("Char", "charLEci", "char-ci<=?", 2);
	}

	public static Boolean charLEci(char c1, char c2) {
		return Character.toLowerCase(c1) <= Character.toLowerCase(c2) ? T : F;
	}

	static {
		Subr.def("Char", "charGEci", "char-ci>=?", 2);
	}

	public static Boolean charGEci(char c1, char c2) {
		return Character.toLowerCase(c1) >= Character.toLowerCase(c2) ? T : F;
	}

	static {
		Subr.def("Char", "alphabetic", "char-alphabetic?", 1);
	}

	public static Boolean alphabetic(char c) {
		return Character.isLetter(c) ? T : F;
	}

	static {
		Subr.def("Char", "numeric", "char-numeric?", 1);
	}

	public static Boolean numeric(char c) {
		return Character.isDigit(c) ? T : F;
	}

	static {
		Subr.def("Char", "whitespace", "char-whitespace?", 1);
	}

	public static Boolean whitespace(char c) {
		return Character.isWhitespace(c) ? T : F;
	}

	static {
		Subr.def("Char", "upperCase", "char-upper-case?", 1);
	}

	public static Boolean upperCase(char c) {
		return Character.isUpperCase(c) ? T : F;
	}

	static {
		Subr.def("Char", "lowerCase", "char-lower-case?", 1);
	}

	public static Boolean lowerCase(char c) {
		return Character.isLowerCase(c) ? T : F;
	}

	static {
		Subr.def("Char", "char2integer", "char->integer", 1);
	}

	public static Integer char2integer(char c) {
		return Num.makeInt((int) c);
	}

	static {
		Subr.def("Char", "integer2char", "integer->char", 1);
	}

	public static Character integer2char(int n) {
		return makeChar((char) n);
	}

	static {
		Subr.def("Char", "upcase", "char-upcase", 1);
	}

	public static Character upcase(char c) {
		return makeChar(Character.toUpperCase(c));
	}

	static {
		Subr.def("Char", "downcase", "char-downcase", 1);
	}

	public static Character downcase(char c) {
		return makeChar(Character.toLowerCase(c));
	}

	static void init() {
	}
}
