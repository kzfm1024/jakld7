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

import java.lang.reflect.Array;

class List {

	Object car, cdr;

	final static List nil = new List();

	static {
		nil.car = nil;
		nil.cdr = nil;
	}

	private final static Boolean T = Boolean.TRUE;
	private final static Boolean F = Boolean.FALSE;

	static {
		Subr.def("List", "pairp", "pair?", 1);
	}

	public static Boolean pairp(Object obj) {
		return obj instanceof Pair ? T : F;
	}

	static {
		Subr.def("List", "listp", "list?", 1);
	}

	public static Boolean listp(Object obj) {
		Object x = obj;
		do {
			if (obj instanceof Pair)
				obj = ((Pair) obj).cdr;
			else
				return obj == nil ? T : F;
			if (obj instanceof Pair)
				obj = ((Pair) obj).cdr;
			else
				return obj == nil ? T : F;
		} while (obj != (x = ((Pair) x).cdr));
		return F;
	}

	static {
		Subr.def("List", "nullp", "null?", 1);
	}

	public static Boolean nullp(Object obj) {
		return obj == nil ? T : F;
	}

	static {
		Subr.def("List", "cons", 2);
	}

	public static Pair cons(Object x, Object y) {
		return new Pair(x, y);
	}

	static {
		Subr.def("List", "car", 1);
	}

	public static Object car(Pair x) {
		return x.car;
	}

	static {
		Subr.def("List", "cdr", 1);
	}

	public static Object cdr(Pair x) {
		return x.cdr;
	}

	static {
		Subr.def("List", "setCar", "set-car!", 2);
	}

	public static Object setCar(Pair x, Object val) {
		return x.car = val;
	}

	static {
		Subr.def("List", "setCdr", "set-cdr!", 2);
	}

	public static Object setCdr(Pair x, Object val) {
		return x.cdr = val;
	}

	static {
		Subr.def("List", "caar", 1);
	}

	public static Object caar(Pair x) {
		return car((Pair) car(x));
	}

	static {
		Subr.def("List", "cadr", 1);
	}

	public static Object cadr(Pair x) {
		return car((Pair) cdr(x));
	}

	static {
		Subr.def("List", "cdar", 1);
	}

	public static Object cdar(Pair x) {
		return cdr((Pair) car(x));
	}

	static {
		Subr.def("List", "cddr", 1);
	}

	public static Object cddr(Pair x) {
		return cdr((Pair) cdr(x));
	}

	static {
		Subr.def("List", "caaar", 1);
	}

	public static Object caaar(Pair x) {
		return car((Pair) car((Pair) car(x)));
	}

	static {
		Subr.def("List", "caadr", 1);
	}

	public static Object caadr(Pair x) {
		return car((Pair) car((Pair) cdr(x)));
	}

	static {
		Subr.def("List", "cadar", 1);
	}

	public static Object cadar(Pair x) {
		return car((Pair) cdr((Pair) car(x)));
	}

	static {
		Subr.def("List", "caddr", 1);
	}

	public static Object caddr(Pair x) {
		return car((Pair) cdr((Pair) cdr(x)));
	}

	static {
		Subr.def("List", "cdaar", 1);
	}

	public static Object cdaar(Pair x) {
		return cdr((Pair) car((Pair) car(x)));
	}

	static {
		Subr.def("List", "cdadr", 1);
	}

	public static Object cdadr(Pair x) {
		return cdr((Pair) car((Pair) cdr(x)));
	}

	static {
		Subr.def("List", "cddar", 1);
	}

	public static Object cddar(Pair x) {
		return cdr((Pair) cdr((Pair) car(x)));
	}

	static {
		Subr.def("List", "cdddr", 1);
	}

	public static Object cdddr(Pair x) {
		return cdr((Pair) cdr((Pair) cdr(x)));
	}

	static {
		Subr.def("List", "caaaar", 1);
	}

	public static Object caaaar(Pair x) {
		return car((Pair) car((Pair) car((Pair) car(x))));
	}

	static {
		Subr.def("List", "caaadr", 1);
	}

	public static Object caaadr(Pair x) {
		return car((Pair) car((Pair) car((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "caadar", 1);
	}

	public static Object caadar(Pair x) {
		return car((Pair) car((Pair) cdr((Pair) car(x))));
	}

	static {
		Subr.def("List", "caaddr", 1);
	}

	public static Object caaddr(Pair x) {
		return car((Pair) car((Pair) cdr((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "cadaar", 1);
	}

	public static Object cadaar(Pair x) {
		return car((Pair) cdr((Pair) car((Pair) car(x))));
	}

	static {
		Subr.def("List", "cadadr", 1);
	}

	public static Object cadadr(Pair x) {
		return car((Pair) cdr((Pair) car((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "caddar", 1);
	}

	public static Object caddar(Pair x) {
		return car((Pair) cdr((Pair) cdr((Pair) car(x))));
	}

	static {
		Subr.def("List", "cadddr", 1);
	}

	public static Object cadddr(Pair x) {
		return car((Pair) cdr((Pair) cdr((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "cdaaar", 1);
	}

	public static Object cdaaar(Pair x) {
		return cdr((Pair) car((Pair) car((Pair) car(x))));
	}

	static {
		Subr.def("List", "cdaadr", 1);
	}

	public static Object cdaadr(Pair x) {
		return cdr((Pair) car((Pair) car((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "cdadar", 1);
	}

	public static Object cdadar(Pair x) {
		return cdr((Pair) car((Pair) cdr((Pair) car(x))));
	}

	static {
		Subr.def("List", "cdaddr", 1);
	}

	public static Object cdaddr(Pair x) {
		return cdr((Pair) car((Pair) cdr((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "cddaar", 1);
	}

	public static Object cddaar(Pair x) {
		return cdr((Pair) cdr((Pair) car((Pair) car(x))));
	}

	static {
		Subr.def("List", "cddadr", 1);
	}

	public static Object cddadr(Pair x) {
		return cdr((Pair) cdr((Pair) car((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "cdddar", 1);
	}

	public static Object cdddar(Pair x) {
		return cdr((Pair) cdr((Pair) cdr((Pair) car(x))));
	}

	static {
		Subr.def("List", "cddddr", 1);
	}

	public static Object cddddr(Pair x) {
		return cdr((Pair) cdr((Pair) cdr((Pair) cdr(x))));
	}

	static {
		Subr.def("List", "identity", "list", 0, true);
	}

	public static Object identity(Object x) {
		return x;
	}

	static Pair list(Object x) {
		return new Pair(x, nil);
	}

	static Pair list(Object x, Object y) {
		return new Pair(x, new Pair(y, nil));
	}

	static Pair list(Object x, Object y, Object z) {
		return new Pair(x, new Pair(y, new Pair(z, nil)));
	}

	static Pair list(Object x, Object y, Object z, Object w) {
		return new Pair(x, new Pair(y, new Pair(z, new Pair(w, nil))));
	}

	static {
		Subr.def("List", "length", 1);
	}

	public static LNumber length(List x) {
		int len = 0;
		for (; x != nil; x = (List) x.cdr)
			len++;
		return new LNumber(Num.makeInt(len));
	}

	static {
		Subr.def("List", "nth", "list-ref", 2);
	}

	public static Object nth(List x, LNumber lnum) {
		int n = lnum.intValue();
		while (--n >= 0)
			x = (List) x.cdr;
		return x.car;
	}

	private final static Pair dummyHeader = new Pair(null, null);

	static {
		Subr.def("List", "append", 0, true);
	}

	public static Object append(List args) {
		if (args == nil)
			return nil;
		Pair last = dummyHeader;
		for (; args.cdr != nil; args = (List) args.cdr) {
			List elem = (List) args.car;
			for (; elem != nil; elem = (List) elem.cdr) {
				Pair x = new Pair(elem.car, null);
				last.cdr = x;
				last = x;
			}
		}
		last.cdr = args.car;
		return dummyHeader.cdr;
	}

	static {
		Subr.def("List", "reverse", 1);
	}

	public static List reverse(List arg) {
		List val = nil;
		for (; arg != nil; arg = (List) arg.cdr) {
			val = new Pair(arg.car, val);
		}
		return val;
	}

	public static List nreverse(List arg) {
		List val = nil;
		while (arg != nil) {
			Pair elem = (Pair) arg;
			arg = (List) arg.cdr;
			elem.cdr = val;
			val = elem;
		}
		return val;
	}

	static {
		Subr.def("List", "memq", 2);
	}

	public static Object memq(Object obj, List list) {
		for (; list != nil; list = (List) list.cdr)
			if (list.car == obj)
				return list;
		return F;
	}

	static {
		Subr.def("List", "memv", 2);
	}

	public static Object memv(Object obj, List list) {
		for (; list != nil; list = (List) list.cdr)
			if (Eval.eqv(list.car, obj) == T)
				return list;
		return F;
	}

	static {
		Subr.def("List", "member", 2);
	}

	public static Object member(Object obj, List list) {
		for (; list != nil; list = (List) list.cdr)
			if (Eval.equal(list.car, obj) == T)
				return list;
		return F;
	}

	static {
		Subr.def("List", "assq", 2);
	}

	public static Object assq(Object obj, List alist) {
		for (; alist != nil; alist = (List) alist.cdr)
			if (((Pair) alist.car).car == obj)
				return alist.car;
		return F;
	}

	static {
		Subr.def("List", "assv", 2);
	}

	public static Object assv(Object obj, List alist) {
		for (; alist != nil; alist = (List) alist.cdr)
			if (Eval.eqv(((Pair) alist.car).car, obj) == T)
				return alist.car;
		return F;
	}

	static {
		Subr.def("List", "assoc", 2);
	}

	public static Object assoc(Object obj, List alist) {
		for (; alist != nil; alist = (List) alist.cdr)
			if (Eval.equal(((Pair) alist.car).car, obj) == T)
				return alist.car;
		return F;
	}

	static {
		Subr.def("List", "vectorp", "vector?", 1);
	}

	public static Boolean vectorp(Object obj) {
		return obj instanceof Object[] ? T : F;
	}

	private final static Object[] emptyVector = new Object[0];

	static {
		Subr.def("List", "makeVector", "make-vector", 1, 1);
	}

	public static Object[] makeVector(LNumber lnum, Object fill) {
		int length = lnum.intValue();
		if (length == 0)
			return emptyVector;
		else {
			if (fill == null)
				fill = nil;
			Object[] v = new Object[length];
			for (int i = 0; i < length; i++)
				v[i] = fill;
			return v;
		}
	}

	static {
		Subr.def("List", "list2vector", "vector", 0, true);
		Subr.def("List", "list2vector", "list->vector", 1);
	}

	public static Object[] list2vector(List list) {
		if (list == nil)
			return emptyVector;
		else {
			Object[] v = new Object[length(list).intValue()];
			for (int i = 0; list != nil; i++, list = (List) list.cdr)
				v[i] = list.car;
			return v;
		}
	}

	static {
		Subr.def("List", "vector2list", "vector->list", 1);
	}

	public static List vector2list(Object[] v) {
		int i = Array.getLength(v);
		List val = nil;
		while ((--i) >= 0)
			val = new Pair(v[i], val);
		return val;
	}

	static {
		Subr.def("List", "vectorLength", "vector-length", 1);
	}

	public static LNumber vectorLength(Object[] v) {
		return new LNumber(Num.makeInt(Array.getLength(v)));
	}

	static {
		Subr.def("List", "vectorRef", "vector-ref", 2);
	}

	public static Object vectorRef(Object[] v, LNumber index) {
		return v[index.intValue()];
	}

	static {
		Subr.def("List", "vectorSet", "vector-set!", 3);
	}

	public static Object vectorSet(Object[] v, LNumber index, Object val) {
		return v[index.intValue()] = val;
	}

	static void init() {
	}

	static void clean() {
		dummyHeader.cdr = null;
	}
}
