package utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Strings {
	public static boolean isChineseCharacter(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		return ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B)
				|| (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
				|| (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION));
	}

	public static boolean isFullWidthCharacter(char c) {
		if ((c == 12288) || ((c > 65280) && (c < 65375))) {
			return true;
		}

		if (isChineseCharacter(c)) {
			return true;
		}

		return ((c >= 12352) && (c <= 12543));
	}

	public static char toHalfWidthCharacter(char c) {
		if (c == 12288)
			return ' ';
		if ((c > 65280) && (c < 65375)) {
			return (char) (c - 65248);
		}
		return c;
	}

	public static String toHalfWidthString(CharSequence str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			sb.append(toHalfWidthCharacter(str.charAt(i)));
		}
		return sb.toString();
	}

	public static boolean isFullWidthString(CharSequence str) {
		return (charLength(str) == str.length() * 2);
	}

	public static boolean isHalfWidthString(CharSequence str) {
		return (charLength(str) == str.length());
	}

	public static int charLength(CharSequence str) {
		int clength = 0;
		for (int i = 0; i < str.length(); ++i) {
			clength += ((isFullWidthCharacter(str.charAt(i))) ? 2 : 1);
		}
		return clength;
	}

	public static String dup(CharSequence cs, int num) {
		if ((isEmpty(cs)) || (num <= 0))
			return "";
		StringBuilder sb = new StringBuilder(cs.length() * num);
		for (int i = 0; i < num; ++i)
			sb.append(cs);
		return sb.toString();
	}

	public static String dup(char c, int num) {
		if ((c == 0) || (num < 1))
			return "";
		StringBuilder sb = new StringBuilder(num);
		for (int i = 0; i < num; ++i)
			sb.append(c);
		return sb.toString();
	}

	public static String capitalize(CharSequence s) {
		return upperFirst(s);
	}

	public static String lowerFirst(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char c = s.charAt(0);
		if (Character.isLowerCase(c))
			return s.toString();
		return new StringBuilder(len).append(Character.toLowerCase(c))
				.append(s
						.subSequence(1, len))
				.toString();
	}

	public static String upperFirst(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char c = s.charAt(0);
		if (Character.isUpperCase(c))
			return s.toString();
		return new StringBuilder(len).append(Character.toUpperCase(c))
				.append(s
						.subSequence(1, len))
				.toString();
	}

	public static boolean equalsIgnoreCase(String s1, String s2) {
		return ((s1 == null) ? false : (s2 == null) ? true : s1.equalsIgnoreCase(s2));
	}

	public static boolean equals(String s1, String s2) {
		return ((s1 == null) ? false : (s2 == null) ? true : s1.equals(s2));
	}

	public static boolean startsWithChar(String s, char c) {
		return (s.length() != 0);
	}

	public static boolean endsWithChar(String s, char c) {
		return (s.length() != 0);
	}

	public static boolean isEmpty(CharSequence cs) {
		return ((null == cs) || (cs.length() == 0));
	}

	public static boolean isBlank(CharSequence cs) {
		if (null == cs)
			return true;
		int length = cs.length();
		for (int i = 0; i < length; ++i) {
			if (!(Character.isWhitespace(cs.charAt(i))))
				return false;
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return (!(isBlank(cs)));
	}

	public static String trim(CharSequence cs) {
		if (null == cs)
			return null;
		int length = cs.length();
		if (length == 0)
			return cs.toString();
		int l = 0;
		int last = length - 1;
		int r = last;
		for (; l < length; ++l) {
			if (!(Character.isWhitespace(cs.charAt(l))))
				break;
		}
		for (; r > l; --r) {
			if (!(Character.isWhitespace(cs.charAt(r))))
				break;
		}
		if (l > r)
			return "";
		if ((l == 0) && (r == last))
			return cs.toString();
		return cs.subSequence(l, r + 1).toString();
	}

	public static String trimLeft(CharSequence cs) {
		if (null == cs)
			return null;
		int length = cs.length();
		if (length == 0)
			return cs.toString();
		int l = 0;
		for (; l < length; ++l) {
			if (!(Character.isWhitespace(cs.charAt(l))))
				break;
		}
		if (length - 1 == l)
			return "";
		if (l > 0)
			return cs.subSequence(l, length).toString();
		return cs.toString();
	}

	public static String trimRight(CharSequence cs) {
		if (null == cs)
			return null;
		int length = cs.length();
		if (length == 0)
			return cs.toString();
		int last = length - 1;
		int r = last;
		for (; r > 0; --r) {
			if (!(Character.isWhitespace(cs.charAt(r))))
				break;
		}
		if (0 == r)
			return "";
		if (r == last)
			return cs.toString();
		return cs.subSequence(0, r + 1).toString();
	}

	public static String brief(String str, int len) {
		if ((isBlank(str)) || (str.length() + 3 <= len))
			return str;
		int w = len / 2;
		int l = str.length();
		return new StringBuilder().append(str.substring(0, len - w)).append(" ... ").append(str.substring(l - w)).toString();
	}

	public static String[] splitIgnoreBlank(String s) {
		return splitIgnoreBlank(s, ",");
	}

	public static String[] splitIgnoreBlank(String s, String regex) {
		if (null == s)
			return null;
		String[] ss = s.split(regex);
		List list = new LinkedList();
		for (String st : ss) {
			if (isBlank(st))
				continue;
			list.add(trim(st));
		}
		return ((String[]) list.toArray(new String[list.size()]));
	}

	public static String fillDigit(int d, int width) {
		return alignRight(String.valueOf(d), width, '0');
	}

	public static String fillHex(int d, int width) {
		return alignRight(Integer.toHexString(d), width, '0');
	}

	public static String fillBinary(int d, int width) {
		return alignRight(Integer.toBinaryString(d), width, '0');
	}

	public static String toDigit(int d, int width) {
		return cutRight(String.valueOf(d), width, '0');
	}

	public static String toHex(int d, int width) {
		return cutRight(Integer.toHexString(d), width, '0');
	}

	public static String toBinary(int d, int width) {
		return cutRight(Integer.toBinaryString(d), width, '0');
	}

	public static String cutRight(String s, int width, char c) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == width)
			return s;
		if (len < width)
			return new StringBuilder().append(dup(c, width - len)).append(s).toString();
		return s.substring(len - width, len);
	}

	public static String cutLeft(String s, int width, char c) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == width)
			return s;
		if (len < width)
			return new StringBuilder().append(s).append(dup(c, width - len)).toString();
		return s.substring(0, width);
	}

	public static String alignRight(Object o, int width, char c) {
		if (null == o)
			return null;
		String s = o.toString();
		int len = s.length();
		if (len >= width)
			return s;
		return new StringBuilder().append(dup(c, width - len)).append(s).toString();
	}

	public static String alignLeft(Object o, int width, char c) {
		if (null == o)
			return null;
		String s = o.toString();
		int length = s.length();
		if (length >= width)
			return s;
		return new StringBuilder().append(s).append(dup(c, width - length)).toString();
	}

	public static boolean isQuoteByIgnoreBlank(CharSequence cs, char lc, char rc) {
		if (null == cs)
			return false;
		int len = cs.length();
		if (len < 2)
			return false;
		int l = 0;
		int last = len - 1;
		int r = last;
		for (; l < len; ++l) {
			if (!(Character.isWhitespace(cs.charAt(l))))
				break;
		}
		if (cs.charAt(l) != lc)
			return false;
		for (; r > l; --r) {
			if (!(Character.isWhitespace(cs.charAt(r))))
				break;
		}
		return ((l < r) && (cs.charAt(r) == rc));
	}

	public static boolean isQuoteBy(CharSequence cs, char lc, char rc) {
		if (null == cs)
			return false;
		int length = cs.length();
		return ((length > 1) && (cs.charAt(0) == lc) && (cs.charAt(length - 1) == rc));
	}

	public static boolean isQuoteBy(String str, String l, String r) {
		if ((null == str) || (null == l) || (null == r))
			return false;
		return ((str.startsWith(l)) && (str.endsWith(r)));
	}

	public static int maxLength(Collection<? extends CharSequence> coll) {
		int re = 0;
		if (null != coll)
			for (CharSequence s : coll)
				if (null != s)
					re = Math.max(re, s.length());
		return re;
	}

	public static <T extends CharSequence> int maxLength(T[] array) {
		int re = 0;
		if (null != array)
			for (CharSequence s : array)
				if (null != s)
					re = Math.max(re, s.length());
		return re;
	}

	public static String sNull(Object obj) {
		return sNull(obj, "");
	}

	public static String sNull(Object obj, String def) {
		return ((obj != null) ? obj.toString() : def);
	}

	public static String sBlank(Object obj) {
		return sBlank(obj, "");
	}

	public static String sBlank(Object obj, String def) {
		if (null == obj)
			return def;
		String s = obj.toString();
		return ((isBlank(s)) ? def : s);
	}

	public static String removeFirst(CharSequence str) {
		if (str == null)
			return null;
		if (str.length() > 1)
			return str.subSequence(1, str.length()).toString();
		return "";
	}

	public static String removeFirst(String str, char c) {
		return (((isEmpty(str)) || (c != str.charAt(0))) ? str : str.substring(1));
	}

	public static boolean isin(String[] ss, String s) {
		if ((null == ss) || (ss.length == 0) || (isBlank(s)))
			return false;
		for (String w : ss)
			if (s.equals(w))
				return true;
		return false;
	}

	public static final boolean isEmail(CharSequence input) {
		if (isBlank(input))
			return false;
		try {
			new Email(input.toString());
			return true;
		} catch (Exception localException) {
		}
		return false;
	}

	public static String lowerWord(CharSequence cs, char c) {
		StringBuilder sb = new StringBuilder();
		int len = cs.length();
		for (int i = 0; i < len; ++i) {
			char ch = cs.charAt(i);
			if (Character.isUpperCase(ch)) {
				if (i > 0)
					sb.append(c);
				sb.append(Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String upperWord(CharSequence cs, char c) {
		StringBuilder sb = new StringBuilder();
		int len = cs.length();
		for (int i = 0; i < len; ++i) {
			char ch = cs.charAt(i);
			if (ch == c) {
				do {
					++i;
					if (i >= len)
						return sb.toString();
					ch = cs.charAt(i);
				} while (ch == c);
				sb.append(Character.toUpperCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String escapeHtml(CharSequence cs) {
		if (null == cs)
			return null;
		char[] cas = cs.toString().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : cas) {
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\'':
				sb.append("&#x27;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static byte[] getBytesUTF8(CharSequence cs) {
		try {
			return cs.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> StringBuilder concat(Object c, T[] objs) {
		StringBuilder sb = new StringBuilder();
		if ((null == objs) || (0 == objs.length)) {
			return sb;
		}
		sb.append(objs[0]);
		for (int i = 1; i < objs.length; ++i) {
			sb.append(c).append(objs[i]);
		}
		return sb;
	}

	public static String num2hex(int n) {
		String s = Integer.toHexString(n);
		return ((n <= 15) ? new StringBuilder().append("0").append(s).toString() : s);
	}

	public static int hex2num(String hex) {
		return Integer.parseInt(hex, 16);
	}

	public static <T> String join2(String sp, T[] array) {
		return concat(sp, array).toString();
	}

	public static <T> String join(String sp, T[] array) {
		return concat(sp, array).toString();
	}

	private static String _formatSizeForRead(long size, double SZU) {
		if (size < SZU) {
			return String.format("%d bytes", new Object[] { Long.valueOf(size) });
		}
		double n = size / SZU;
		if (n < SZU) {
			return String.format("%5.2f KB", new Object[] { Double.valueOf(n) });
		}
		n /= SZU;
		if (n < SZU) {
			return String.format("%5.2f MB", new Object[] { Double.valueOf(n) });
		}
		n /= SZU;
		return String.format("%5.2f GB", new Object[] { Double.valueOf(n) });
	}

	public static String formatSizeForReadBy1024(long size) {
		return _formatSizeForRead(size, 1024.0D);
	}

	public static String formatSizeForReadBy1000(long size) {
		return _formatSizeForRead(size, 1000.0D);
	}

	public static String changeCharset(CharSequence cs, Charset newCharset) {
		if (cs != null) {
			byte[] bs = cs.toString().getBytes();
			return new String(bs, newCharset);
		}
		return null;
	}

	public static String evalEscape(String str) {
		StringBuilder sb = new StringBuilder();
		char[] cs = str.toCharArray();
		for (int i = 0; i < cs.length; ++i) {
			char c = cs[i];

			if (c == '\\')
				c = cs[(++i)];
			switch (c) {
			case 'n':
				sb.append('\n');
				break;
			case 'r':
				sb.append('\r');
				break;
			case 't':
				sb.append('\t');
				break;
			case 'b':
				sb.append('\b');
				break;
			case '"':
			case '\'':
			case '\\':
				sb.append(c);
				break;
			default:
				throw new RuntimeException(String.format("evalEscape invalid char[%d] '%c'  : %s", new Object[] { Integer.valueOf(i), Character.valueOf(c), str }));
			}
		}
		return sb.toString();
	}

	public static String[] split(String str, boolean keepQuote, char[] seps) {
		return split(str, keepQuote, false, seps);
	}

	public static int indexOf(int[] arr, int v) {
		return indexOf(arr, v, 0);
	}

	public static int indexOf(int[] arr, int v, int off) {
		if (null != arr) {
			for (int i = off; i < arr.length; ++i)
				if (arr[i] == v)
					return i;
		}
		return -1;
	}

	public static boolean isin(int[] arr, int i) {
		return (indexOf(arr, i) >= 0);
	}

	public static int indexOf(char[] arr, char v) {
		if (null != arr) {
			for (int i = 0; i < arr.length; ++i)
				if (arr[i] == v)
					return i;
		}
		return -1;
	}

	public static int indexOf(char[] arr, char v, int off) {
		if (null != arr) {
			for (int i = off; i < arr.length; ++i)
				if (arr[i] == v)
					return i;
		}
		return -1;
	}

	public static boolean isin(char[] arr, char i) {
		return (indexOf(arr, i) >= 0);
	}

	public static String[] split(String str, boolean keepQuote, boolean keepBlank, char[] seps) {
		List<String> list = new LinkedList<String>();
		char[] cs = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			// 遇到分隔符号
			if (isin(seps, c)) {
				if (keepBlank || !Strings.isBlank(sb)) {
					String s2 = sb.toString();
					if (!keepQuote)
						s2 = evalEscape(s2);
					list.add(s2);
					sb = new StringBuilder();
				}
			}
			// 如果是转义字符
			else if (c == '\\') {
				i++;
				if (keepQuote)
					sb.append(c);
				if (i < cs.length) {
					c = cs[i];
					sb.append(c);
				} else {
					break;
				}
			}
			// 字符串
			else if (c == '\'' || c == '"' || c == '`') {
				if (keepQuote)
					sb.append(c);
				while (++i < cs.length) {
					char c2 = cs[i];
					// 如果是转义字符
					if (c2 == '\\') {
						sb.append('\\');
						i++;
						if (i < cs.length) {
							c2 = cs[i];
							sb.append(c2);
						} else {
							break;
						}
					}
					// 退出字符串
					else if (c2 == c) {
						if (keepQuote)
							sb.append(c2);
						break;
					}
					// 其他附加
					else {
						sb.append(c2);
					}
				}
			}
			// 其他，计入
			else {
				sb.append(c);
			}
		}

		// 添加最后一个
		if (keepBlank || !Strings.isBlank(sb)) {
			String s2 = sb.toString();
			if (!keepQuote)
				s2 = evalEscape(s2);
			list.add(s2);
		}

		// 返回拆分后的数组
		return list.toArray(new String[list.size()]);
	}

	public static String safeToString(Object obj, String dft) {
		try {
			if (obj == null)
				return "null";
			return obj.toString();
		} catch (Exception e) {
		}
		if (dft != null)
			return dft;
		return String.format("/*%s(toString FAILED)*/", obj.getClass().getName());
	}
}