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

abstract class Function {

	abstract Object invoke0(List args);

	public Object invoke(List args) {
		Object val = invoke0(args);
		while (val instanceof Call)
			val = ((Call) val).fun.invoke0(((Call) val).args);
		return val;
	}

	public Object invoke1(Object arg) {
		return invoke(List.list(arg));
	}

	public boolean isSpecialForm() {
		return false;
	}
}
