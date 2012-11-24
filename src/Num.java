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

import java.math.BigDecimal;
import java.math.BigInteger;

final class Num {

	private final static int IntPoolSize = 1024;
	private final static Integer[] IntPool = new Integer[IntPoolSize];

	public static Integer makeInt(int n) {
		if (n >= 0 && n < IntPoolSize) {
			Integer x = IntPool[n];
			if (x != null)
				return x;
			else
				return IntPool[n] = new Integer(n);
		} else
			return new Integer(n);
	}

	private final static Integer IntZERO = makeInt(0);
	private final static Integer IntONE = makeInt(1);
	private final static Integer IntMinusONE = new Integer(-1);
	private final static BigInteger BigIntegerONE = BigInteger.valueOf(1);

	private final static Boolean T = Boolean.TRUE;
	private final static Boolean F = Boolean.FALSE;

	public static Number normalize(BigInteger n) {
		if (n.bitLength() < 32)
			return makeInt(n.intValue());
		else
			return n;
	}

	public static int compare(Number num1, Number num2) {
		if (num1 instanceof Integer && num2 instanceof Integer) {
			int x = num1.intValue();
			int y = num2.intValue();
			return x > y ? 1 : (x == y ? 0 : -1);
		} else if (num1 instanceof Double || num2 instanceof Double) {
			double x = num1.doubleValue();
			double y = num2.doubleValue();
			return x > y ? 1 : (x == y ? 0 : -1);
		} else {
			if (num1 instanceof Integer)
				num1 = BigInteger.valueOf(num1.longValue());
			else if (num2 instanceof Integer)
				num2 = BigInteger.valueOf(num2.longValue());
			return ((BigInteger) num1).compareTo((BigInteger) num2);
		}
	}

	public static Boolean zerop(Number num) {
		if (num instanceof Integer)
			return num.intValue() == 0 ? T : F;
		else if (num instanceof BigInteger)
			return F;
		else
			return num.doubleValue() == 0 ? T : F;
	}

	public static Boolean positivep(Number num) {
		if (num instanceof Integer)
			return num.intValue() > 0 ? T : F;
		else if (num instanceof BigInteger)
			return ((BigInteger) num).signum() > 0 ? T : F;
		else
			return num.doubleValue() > 0 ? T : F;
	}

	public static Boolean negativep(Number num) {
		if (num instanceof Integer)
			return num.intValue() < 0 ? T : F;
		else if (num instanceof BigInteger)
			return ((BigInteger) num).signum() < 0 ? T : F;
		else
			return num.doubleValue() < 0 ? T : F;
	}

	public static Boolean oddp(Number num) {
		if (num instanceof Integer)
			return (num.intValue() & 1) != 0 ? T : F;
		else
			return ((BigInteger) num).testBit(0) ? T : F;
	}

	public static Boolean evenp(Number num) {
		if (num instanceof Integer)
			return (num.intValue() & 1) == 0 ? T : F;
		else
			return !((BigInteger) num).testBit(0) ? T : F;
	}

	// public static Number max(Number num, List args) {
	// for (; args != List.nil; args = (List) args.cdr)
	// if (compare((Number) args.car, num) > 0)
	// num = (Number) args.car;
	// return num;
	// }
	//
	// public static Number min(Number num, List args) {
	// for (; args != List.nil; args = (List) args.cdr)
	// if (compare((Number) args.car, num) < 0)
	// num = (Number) args.car;
	// return num;
	// }

	public static Number long2Number(long n) {
		if (n == (int) n)
			return makeInt((int) n);
		else
			return BigInteger.valueOf(n);
	}

	public static Number abs(Number num) {
		if (num instanceof Integer) {
			long x = num.longValue();
			return x >= 0 ? num : long2Number(-x);
		} else if (num instanceof BigInteger)
			return ((BigInteger) num).abs();
		else {
			double x = num.doubleValue();
			return x >= 0 ? num : new Double(-x);
		}
	}

	public static Number quotient(Number num1, Number num2) {
		if (num1 instanceof Integer)
			if (num2 instanceof Integer)
				return makeInt(num1.intValue() / num2.intValue());
			else
				num1 = BigInteger.valueOf(num1.longValue());
		else if (num2 instanceof Integer)
			num2 = BigInteger.valueOf(num2.longValue());

		return normalize(((BigInteger) num1).divide((BigInteger) num2));
	}

	public static Number remainder(Number num1, Number num2) {
		if (num1 instanceof Integer)
			if (num2 instanceof Integer)
				return makeInt(num1.intValue() % num2.intValue());
			else
				num1 = BigInteger.valueOf(num1.longValue());
		else if (num2 instanceof Integer)
			num2 = BigInteger.valueOf(num2.longValue());

		return normalize(((BigInteger) num1).remainder((BigInteger) num2));
	}

	public static Number modulo(Number num1, Number num2) {
		if (num1 instanceof Integer)
			if (num2 instanceof Integer) {
				int y = num2.intValue();
				int r = num1.intValue() % y;
				return makeInt(((long) y) * r < 0 ? r + y : r);
			} else
				num1 = BigInteger.valueOf(num1.longValue());
		else if (num2 instanceof Integer)
			num2 = BigInteger.valueOf(num2.longValue());

		BigInteger X = (BigInteger) num1;
		BigInteger Y = (BigInteger) num2;
		if (Y.signum() < 0)
			return normalize(X.negate().mod(Y.negate()).negate());
		else
			return normalize(X.mod(Y));
	}

	public static Number floor(Number num) {
		if (num instanceof Integer || num instanceof BigInteger)
			return num;
		else {
			double x = Math.floor(num.doubleValue());
			if (x == (int) x)
				return makeInt((int) x);
			else
				return new BigDecimal(x).toBigInteger();
		}
	}

	public static Number ceiling(Number num) {
		if (num instanceof Integer || num instanceof BigInteger)
			return num;
		else {
			double x = Math.ceil(num.doubleValue());
			if (x == (int) x)
				return makeInt((int) x);
			else
				return new BigDecimal(x).toBigInteger();
		}
	}

	public static Number truncate(Number num) {
		if (num instanceof Integer || num instanceof BigInteger)
			return num;
		else {
			double x = num.doubleValue();
			x = (x >= 0 ? Math.floor(x) : Math.ceil(x));
			if (x == (int) x)
				return makeInt((int) x);
			else
				return new BigDecimal(x).toBigInteger();
		}
	}

	public static Number round(Number num) {
		if (num instanceof Integer || num instanceof BigInteger)
			return num;
		else {
			double x = num.doubleValue();
			long n = Math.round(x);
			if ((n - x) == 0.5 && (n & 1) == 1)
				n--;
			return long2Number(n);
		}
	}

	public static LString num2string(Number num, Integer n) {
		int radix = (n == null ? 10 : n.intValue());

		if (num instanceof Integer)
			return new LString(Integer.toString(num.intValue(), radix));
		else if (num instanceof BigInteger)
			return new LString(((BigInteger) num).toString(radix));
		else
			return new LString(num.toString());
	}

	// static {
	// Subr.def("Num", "string2num", "string->number", 1, 1);
	// }

	public static Object string2num(LString s, Integer n) {
		try {
			return IO.readNumber(s.toString(), (n == null ? 10 : n.intValue()));
		} catch (NumberFormatException e) {
			return F;
		}
	}

	public static Double sqrt(Number num) {
		return new Double(Math.sqrt(num.doubleValue()));
	}

	public static Double exp(Number num) {
		return new Double(Math.exp(num.doubleValue()));
	}

	public static Double log(Number num) {
		return new Double(Math.log(num.doubleValue()));
	}

	public static Double sin(Number num) {
		return new Double(Math.sin(num.doubleValue()));
	}

	public static Double cos(Number num) {
		return new Double(Math.cos(num.doubleValue()));
	}

	public static Double tan(Number num) {
		return new Double(Math.tan(num.doubleValue()));
	}

	public static Double asin(Number num) {
		return new Double(Math.asin(num.doubleValue()));
	}

	public static Double acos(Number num) {
		return new Double(Math.acos(num.doubleValue()));
	}

	public static Double atan(Number num1, Number num2) {
		if (num2 == null)
			return new Double(Math.atan(num1.doubleValue()));
		else {
			return new Double(
					Math.atan2(num1.doubleValue(), num2.doubleValue()));
		}
	}

	public static Number expt(Number num1, Number num2) {
		double x = Math.pow(num1.doubleValue(), num2.doubleValue());
		if (num1 instanceof Double || num2 instanceof Double
				|| x >= Double.POSITIVE_INFINITY
				|| x <= Double.NEGATIVE_INFINITY)
			return new Double(x);

		if (num1 instanceof Integer) {
			int m = num1.intValue();
			if (m == 0)
				return num2.doubleValue() == 0 ? IntONE : IntZERO;
			else if (m == 1)
				return IntONE;
			else if (m == -1)
				if (num2 instanceof Integer)
					return (num2.intValue() & 1) != 0 ? IntMinusONE : IntONE;
				else
					return ((BigInteger) num2).testBit(0) ? IntMinusONE
							: IntONE;
			else { // num2 cannot be BigInteger
				int n = num2.intValue();
				return n >= 0 ? expt((long) m, n, (long) 1) : new Double(x);
			}
		} else { // num2 cannot be BigInteger
			int n = num2.intValue();
			return n >= 0 ? expt((BigInteger) num1, n, BigIntegerONE)
					: new Double(x);
		}
	}

	private static Number expt(long m, int n, long val) {
		boolean escape = false;
		while (n > 0) {
			if ((n & 1) == 1) {
				n--;
				val *= m;
				escape = (val != (int) val);
			} else {
				n >>= 1;
				m *= m;
				escape = (m != (int) m);
			}
			if (escape)
				return expt(BigInteger.valueOf(m), n, BigInteger.valueOf(val));
		}
		return makeInt((int) val);
	}

	private static Number expt(BigInteger m, int n, BigInteger val) {
		while (n > 0)
			if ((n & 1) == 1) {
				n--;
				val = val.multiply(m);
			} else {
				n >>= 1;
				m = m.multiply(m);
			}
		return normalize(val);
	}

	static void init() {
	}
}
