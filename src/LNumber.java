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

import java.math.BigInteger;

final class LNumber {

	private Number num;

	LNumber(Number num) {
		this.num = num;
	}

	public String toString() {
		return num.toString();
	}

	public int intValue() {
		return num.intValue();
	}

	private final static LNumber IntZERO = new LNumber(Num.makeInt(0));
	private final static LNumber IntONE = new LNumber(Num.makeInt(1));
	// private final static LNumber IntMinusONE = new LNumber(new Integer(-1));
	// private final static LNumber BigIntegerONE = new
	// LNumber(BigInteger.valueOf(1));

	private final static Boolean T = Boolean.TRUE;
	private final static Boolean F = Boolean.FALSE;

	static {
		Subr.def("LNumber", "numberp", "number?", 1);
		Subr.def("LNumber", "integerp", "integer?", 1);
		Subr.def("LNumber", "EQ", "=", 1, true);
		Subr.def("LNumber", "LT", "<", 1, true);
		Subr.def("LNumber", "GT", ">", 1, true);
		Subr.def("LNumber", "LE", "<=", 1, true);
		Subr.def("LNumber", "GE", ">=", 1, true);
		Subr.def("LNumber", "zerop", "zero?", 1);
		Subr.def("LNumber", "positivep", "positive?", 1);
		Subr.def("LNumber", "negativep", "negative?", 1);
		Subr.def("LNumber", "oddp", "odd?", 1);
		Subr.def("LNumber", "evenp", "even?", 1);
		Subr.def("LNumber", "max", 1, true);
		Subr.def("LNumber", "min", 1, true);
		Subr.def("LNumber", "add", "+", 0, true);
		Subr.def("LNumber", "mult", "*", 0, true);
		Subr.def("LNumber", "minus", "-", 1, true);
		Subr.def("LNumber", "div", "/", 1, true);
		Subr.def("LNumber", "abs", 1);
		Subr.def("LNumber", "quotient", 2);
		Subr.def("LNumber", "remainder", 2);
		Subr.def("LNumber", "modulo", 2);
		Subr.def("LNumber", "gcd", 0, true);
		Subr.def("LNumber", "lcm", 0, true);
		Subr.def("LNumber", "floor", 1);
		Subr.def("LNumber", "ceiling", 1);
		Subr.def("LNumber", "truncate", 1);
		Subr.def("LNumber", "round", 1);
		Subr.def("LNumber", "num2string", "number->string", 1, 1);
		Subr.def("LNumber", "string2num", "string->number", 1, 1);
		Subr.def("LNumber", "sqrt", 1);
		Subr.def("LNumber", "exp", 1);
		Subr.def("LNumber", "log", 1);
		Subr.def("LNumber", "sin", 1);
		Subr.def("LNumber", "cos", 1);
		Subr.def("LNumber", "tan", 1);
		Subr.def("LNumber", "asin", 1);
		Subr.def("LNumber", "acos", 1);
		Subr.def("LNumber", "atan", 1, 1);
		Subr.def("LNumber", "expt", 2);
	}

	public static Boolean numberp(Object obj) {
		return obj instanceof LNumber ? T : F;
	}

	public static Boolean integerp(Object obj) {
		if (obj instanceof LNumber) {
			LNumber lnum = (LNumber) obj;
			return lnum.num instanceof Integer
					|| lnum.num instanceof BigInteger ? T : F;
		} else
			return F;
	}

	public static int compare(LNumber lnum1, LNumber lnum2) {
		return Num.compare(lnum1.num, lnum2.num);
	}

	public static Boolean EQ(LNumber lnum, List args) {
		for (; args != List.nil; args = (List) args.cdr) {
			if (compare(lnum, (LNumber) args.car) != 0)
				return F;
		}
		return T;
	}

	public static Boolean LT(LNumber lnum, List args) {
		LNumber current = lnum;
		for (; args != List.nil; args = (List) args.cdr) {
			LNumber next = (LNumber) args.car;
			if (compare(current, next) >= 0)
				return F;
			current = next;
		}
		return T;
	}

	public static Boolean GT(LNumber lnum, List args) {
		LNumber current = lnum;
		for (; args != List.nil; args = (List) args.cdr) {
			LNumber next = (LNumber) args.car;
			if (compare(current, next) <= 0)
				return F;
			current = next;
		}
		return T;
	}

	public static Boolean LE(LNumber lnum, List args) {
		LNumber current = lnum;
		for (; args != List.nil; args = (List) args.cdr) {
			LNumber next = (LNumber) args.car;
			if (compare(current, next) > 0)
				return F;
			current = next;
		}
		return T;
	}

	public static Boolean GE(LNumber lnum, List args) {
		LNumber current = lnum;
		for (; args != List.nil; args = (List) args.cdr) {
			LNumber next = (LNumber) args.car;
			if (compare(current, next) < 0)
				return F;
			current = next;
		}
		return T;
	}

	public static Boolean zerop(LNumber lnum) {
		return Num.zerop(lnum.num);
	}

	public static Boolean positivep(LNumber lnum) {
		return Num.positivep(lnum.num);
	}

	public static Boolean negativep(LNumber lnum) {
		return Num.negativep(lnum.num);
	}

	public static Boolean oddp(LNumber lnum) {
		return Num.oddp(lnum.num);
	}

	public static Boolean evenp(LNumber lnum) {
		return Num.evenp(lnum.num);
	}

	public static LNumber max(LNumber lnum, List args) {
		for (; args != List.nil; args = (List) args.cdr)
			if (compare((LNumber) args.car, lnum) > 0)
				lnum = (LNumber) args.car;
		return lnum;
	}

	public static LNumber min(LNumber lnum, List args) {
		for (; args != List.nil; args = (List) args.cdr)
			if (compare((LNumber) args.car, lnum) < 0)
				lnum = (LNumber) args.car;
		return lnum;
	}

	public static LNumber add(List args) {
		if (args == List.nil)
			return IntZERO;

		LNumber val = new LNumber(((LNumber) args.car).num);
		while ((args = (List) args.cdr) != List.nil) {
			LNumber next = (LNumber) args.car;
			if (val.num instanceof Integer && next.num instanceof Integer)
				val.num = Num.long2Number(val.num.longValue()
						+ next.num.longValue());
			else if (val.num instanceof Double || next.num instanceof Double)
				val.num = new Double(val.num.doubleValue()
						+ next.num.doubleValue());
			else {
				if (val.num instanceof Integer)
					val.num = BigInteger.valueOf(val.num.longValue());
				else if (next.num instanceof Integer)
					next.num = BigInteger.valueOf(next.num.longValue());
				val.num = Num.normalize(((BigInteger) val.num)
						.add((BigInteger) next.num));
			}
		}
		return val;
	}

	public static LNumber mult(List args) {
		if (args == List.nil)
			return IntONE;

		LNumber val = new LNumber(((LNumber) args.car).num);
		while ((args = (List) args.cdr) != List.nil) {
			LNumber next = (LNumber) args.car;
			if (val.num instanceof Integer && next.num instanceof Integer)
				val.num = Num.long2Number(val.num.longValue()
						* next.num.longValue());
			else if (val.num instanceof Double || next.num instanceof Double)
				val.num = new Double(val.num.doubleValue()
						* next.num.doubleValue());
			else {
				if (val.num instanceof Integer)
					val.num = BigInteger.valueOf(val.num.longValue());
				else if (next.num instanceof Integer)
					next.num = BigInteger.valueOf(next.num.longValue());
				val.num = Num.normalize(((BigInteger) val.num)
						.multiply((BigInteger) next.num));
			}
		}
		return val;
	}

	public static LNumber minus(LNumber lnum, List args) {
		if (args == List.nil)
			if (lnum.num instanceof Integer)
				return new LNumber(Num.long2Number(-lnum.num.longValue()));
			else if (lnum.num instanceof BigInteger)
				return new LNumber(Num.normalize(((BigInteger) lnum.num)
						.negate()));
			else
				return new LNumber(new Double(-lnum.num.doubleValue()));

		LNumber val = new LNumber(lnum.num);
		do {
			LNumber next = (LNumber) args.car;
			if (val.num instanceof Integer && next.num instanceof Integer)
				val.num = Num.long2Number(val.num.longValue()
						- next.num.longValue());
			else if (val.num instanceof Double || next.num instanceof Double)
				val.num = new Double(val.num.doubleValue()
						- next.num.doubleValue());
			else {
				if (val.num instanceof Integer)
					val.num = BigInteger.valueOf(val.num.longValue());
				else if (next.num instanceof Integer)
					next.num = BigInteger.valueOf(next.num.longValue());
				val.num = Num.normalize(((BigInteger) val.num)
						.subtract((BigInteger) next.num));
			}
		} while ((args = (List) args.cdr) != List.nil);

		return val;
	}

	public static LNumber div(LNumber lnum, List args) {
		if (args == List.nil)
			if (lnum.num instanceof Integer) {
				int x = lnum.num.intValue();
				if (x == 1 || x == -1)
					return lnum;
				else
					return new LNumber(new Double(1.0 / x));
			} else
				return new LNumber(new Double(1.0 / lnum.num.doubleValue()));

		LNumber val = new LNumber(lnum.num);
		do {
			LNumber next = (LNumber) args.car;
			if (val.num instanceof Integer && next.num instanceof Integer) {
				int x = val.num.intValue();
				int y = next.num.intValue();
				if ((x % y) == 0)
					val.num = Num.makeInt(x / y);
				else
					val.num = new Double(((double) x) / ((double) y));
			} else if (val.num instanceof Double || next.num instanceof Double)
				val.num = new Double(val.num.doubleValue()
						/ next.num.doubleValue());
			else {
				if (val.num instanceof Integer)
					val.num = BigInteger.valueOf(val.num.longValue());
				else if (next.num instanceof Integer)
					next.num = BigInteger.valueOf(next.num.longValue());
				BigInteger[] z = ((BigInteger) val.num)
						.divideAndRemainder((BigInteger) next.num);
				if (z[1].signum() == 0)
					val.num = Num.normalize(z[0]);
				else
					val.num = new Double(val.num.doubleValue()
							/ next.num.doubleValue());
			}
		} while ((args = (List) args.cdr) != List.nil);

		return val;
	}

	public static LNumber abs(LNumber lnum) {
		return new LNumber(Num.abs(lnum.num));
	}

	public static LNumber quotient(LNumber lnum1, LNumber lnum2) {
		return new LNumber(Num.quotient(lnum1.num, lnum2.num));
	}

	public static LNumber remainder(LNumber lnum1, LNumber lnum2) {
		return new LNumber(Num.remainder(lnum1.num, lnum2.num));
	}

	public static LNumber modulo(LNumber lnum1, LNumber lnum2) {
		return new LNumber(Num.modulo(lnum1.num, lnum2.num));
	}

	private static int gcd(int m, int n) {
		// m must be non-negative
		if (n < 0)
			n = -n;
		if (m < n) {
			int tmp = m;
			m = n;
			n = tmp;
		}
		for (;;)
			if (n == 0)
				return m;
			else if (n == 1)
				return n;
			else {
				int tmp = n;
				n = m % n;
				m = tmp;
			}
	}

	public static LNumber gcd(List args) {
		if (args == List.nil)
			return IntZERO;

		LNumber val = abs((LNumber) args.car);
		while ((args = (List) args.cdr) != List.nil) {
			LNumber next = (LNumber) args.car;
			if (val.num instanceof Integer)
				if (next.num instanceof Integer) {
					val.num = Num.makeInt(gcd(val.num.intValue(),
							next.num.intValue()));
					continue;
				} else
					val.num = BigInteger.valueOf(val.num.longValue());
			else if (next.num instanceof Integer)
				next.num = BigInteger.valueOf(next.num.longValue());

			val.num = Num.normalize(((BigInteger) val.num)
					.gcd((BigInteger) next.num));
		}
		return val;
	}

	public static LNumber lcm(List args) {
		if (args == List.nil)
			return IntONE;

		LNumber val = abs((LNumber) args.car);
		while ((args = (List) args.cdr) != List.nil) {
			LNumber next = (LNumber) args.car;
			if (val.num instanceof Integer)
				if (next.num instanceof Integer) {
					int m = val.num.intValue();
					int n = next.num.intValue();
					val.num = Num.makeInt((m * (n < 0 ? -n : n)) / gcd(m, n));
					continue;
				} else
					val.num = BigInteger.valueOf(val.num.longValue());
			else if (next.num instanceof Integer)
				next.num = BigInteger.valueOf(next.num.longValue());

			BigInteger X = (BigInteger) val.num;
			BigInteger Y = ((BigInteger) next.num).abs();
			val.num = Num.normalize(X.multiply(Y).divide(X.gcd(Y)));
		}
		return val;
	}

	public static LNumber floor(LNumber lnum) {
		return new LNumber(Num.floor(lnum.num));
	}

	public static LNumber ceiling(LNumber lnum) {
		return new LNumber(Num.ceiling(lnum.num));
	}

	public static LNumber truncate(LNumber lnum) {
		return new LNumber(Num.truncate(lnum.num));
	}

	public static LNumber round(LNumber lnum) {
		return new LNumber(Num.round(lnum.num));
	}

	public static LString num2string(LNumber lnum, LNumber n) {
		int radix = (n == null ? 10 : n.intValue());
		Number num = lnum.num;

		if (num instanceof Integer)
			return new LString(Integer.toString(num.intValue(), radix));
		else if (num instanceof BigInteger)
			return new LString(((BigInteger) num).toString(radix));
		else
			return new LString(num.toString());
	}

	public static Object string2num(LString s, LNumber n) {
		try {
			return IO.readNumber(s.toString(), (n == null ? 10 : n.intValue()));
		} catch (NumberFormatException e) {
			return F;
		}
	}

	public static LNumber sqrt(LNumber lnum) {
		return new LNumber(Num.sqrt(lnum.num));
	}

	public static LNumber exp(LNumber lnum) {
		return new LNumber(Num.exp(lnum.num));
	}

	public static LNumber log(LNumber lnum) {
		return new LNumber(Num.log(lnum.num));
	}

	public static LNumber sin(LNumber lnum) {
		return new LNumber(Num.sin(lnum.num));
	}

	public static LNumber cos(LNumber lnum) {
		return new LNumber(Num.cos(lnum.num));
	}

	public static LNumber tan(LNumber lnum) {
		return new LNumber(Num.tan(lnum.num));
	}

	public static LNumber asin(LNumber lnum) {
		return new LNumber(Num.asin(lnum.num));
	}

	public static LNumber acos(LNumber lnum) {
		return new LNumber(Num.acos(lnum.num));
	}

	public static LNumber atan(LNumber lnum1, LNumber lnum2) {
		return new LNumber(Num.atan(lnum1.num, lnum2.num));
	}

	public static LNumber expt(LNumber lnum1, LNumber lnum2) {
		return new LNumber(Num.expt(lnum1.num, lnum2.num));
	}

	// public static LNumber random(LNumber lnum) {
	// return new LNumber(Num.random(lnum.num));
	// }

	static void init() {
	}
}
