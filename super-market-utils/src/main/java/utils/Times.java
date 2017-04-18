package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Times {
	private static Pattern _P_TIME = Pattern
			.compile("^((\\d{2,4})([/\\\\-])(\\d{1,2})([/\\\\-])(\\d{1,2}))?(([ T])?(\\d{1,2})(:)(\\d{1,2})((:)(\\d{1,2}))?(([.])(\\d{1,}))?)?(([+-])(\\d{1,2})(:\\d{1,2})?)?$");

	private static Pattern _P_TIME_LONG = Pattern.compile("^[0-9]+(L)?$");

	private static final int[] _MDs = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private static final String[] _MMM = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	private static final DateFormat DF_DATE_TIME_MS = new SimpleDateFormat("y-M-d H:m:s.S");
	private static final DateFormat DF_DATE_TIME_MS2 = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS");
	private static final DateFormat DF_DATE_TIME_MS4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final DateFormat DF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat DF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static final long T_1S = 1000L;
	public static final long T_1M = 60000L;
	public static final long T_1H = 3600000L;
	public static final long T_1D = 86400000L;
	private static String TIME_S_EN = "s";
	private static String TIME_M_EN = "m";
	private static String TIME_H_EN = "h";
	private static String TIME_D_EN = "d";

	private static String TIME_S_CN = "秒";
	private static String TIME_M_CN = "分";
	private static String TIME_H_CN = "时";
	private static String TIME_D_CN = "天";

	public static boolean leapYear(int year) {
		if (year < 4)
			return false;
		return ((year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0)));
	}

	public static int countLeapYear(int year) {
		int span = year - 1;
		return (span / 4 - (span / 100) + span / 400);
	}

	public static int[] T(int sec) {
		int[] re = new int[3];
		re[0] = Math.min(23, sec / 3600);
		re[1] = Math.min(59, (sec - (re[0] * 3600)) / 60);
		re[2] = Math.min(59, sec - (re[0] * 3600) - (re[1] * 60));
		return re;
	}

	public static int T(String ts) {
		String[] tss = Strings.splitIgnoreBlank(ts, ":");
		if (null != tss) {
			if (tss.length == 2) {
				int hh = Integer.parseInt(tss[0]);
				int mm = Integer.parseInt(tss[1]);
				return (hh * 3600 + mm * 60);
			}

			if (tss.length == 3) {
				int hh = Integer.parseInt(tss[0]);
				int mm = Integer.parseInt(tss[1]);
				int ss = Integer.parseInt(tss[2]);
				return (hh * 3600 + mm * 60 + ss);
			}
		}
		throw new RuntimeException(String.format("Wrong format of time string '%s'", new Object[] { ts }));
	}

	public static Date now() {
		return new Date(System.currentTimeMillis());
	}

	public static long ams(String ds) {
		return ams(ds, null);
	}

	public static long ams(String ds, TimeZone tz) {
		Matcher m = _P_TIME.matcher(ds);
		if (m.find()) {
			int yy = _int(m, 2, 1970);
			int MM = _int(m, 4, 1);
			int dd = _int(m, 6, 1);

			int HH = _int(m, 9, 0);
			int mm = _int(m, 11, 0);
			int ss = _int(m, 14, 0);

			int ms = _int(m, 17, 0);

			String str = String.format("%04d-%02d-%02d %02d:%02d:%02d.%03d", new Object[] {
					Integer.valueOf(yy),
					Integer.valueOf(MM),
					Integer.valueOf(dd),
					Integer.valueOf(HH),
					Integer.valueOf(mm),
					Integer.valueOf(ss),
					Integer.valueOf(ms) });

			SimpleDateFormat df = (SimpleDateFormat) DF_DATE_TIME_MS4.clone();

			if ((null == tz) && (!(Strings.isBlank(m.group(18))))) {
				tz = TimeZone.getTimeZone(String.format("GMT%s%s:00", new Object[] { m.group(19), m.group(20) }));
			}

			if (null != tz)
				df.setTimeZone(tz);
			try {
				return df.parse(str).getTime();
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		if (_P_TIME_LONG.matcher(ds).find()) {
			if (ds.endsWith("L"))
				ds.substring(0, ds.length() - 1);
			return Long.parseLong(ds);
		}
		throw new RuntimeException(String.format("Unexpect date format '%s'", new Object[] { ds }));
	}

	public static long ms(String ds, TimeZone tz) {
		return ams(ds, tz);
	}

	public static long ms(Date d) {
		return ms(C(d));
	}

	public static int ms(Calendar c) {
		int ms = c.get(11) * 3600000;
		ms += c.get(12) * 60000;
		ms += c.get(13) * 1000;
		ms += c.get(14);
		return ms;
	}

	public static int ms() {
		return ms(Calendar.getInstance());
	}

	public static String mss(int ms) {
		int sec = ms / 1000;
		ms -= sec * 1000;
		return new StringBuilder().append(secs(sec)).append(".").append(Strings.alignRight(Integer.valueOf(ms), 3, '0')).toString();
	}

	public static String secs(int sec) {
		int hh = sec / 3600;
		sec -= hh * 3600;
		int mm = sec / 60;
		sec -= mm * 60;
		return new StringBuilder().append(Strings.alignRight(Integer.valueOf(hh), 2, '0')).append(":")
				.append(Strings.alignRight(Integer.valueOf(mm),
						2, '0'))
				.append(":")
				.append(Strings.alignRight(Integer.valueOf(sec),
						2, '0'))
				.toString();
	}

	public static int sec(Date d) {
		Calendar c = C(d);
		int sec = c.get(11) * 3600;
		sec += c.get(12) * 60;
		sec += c.get(13);
		return sec;
	}

	public static int sec() {
		return sec(now());
	}

	public static Date D(String ds) {
		return D(ams(ds));
	}

	private static int _int(Matcher m, int index, int dft) {
		String s = m.group(index);
		if (Strings.isBlank(s))
			return dft;
		return Integer.parseInt(s);
	}

	public static int D1970(int yy, int MM, int dd) {
		int year = (yy < 100) ? yy + 1970 : yy;

		int day = (year - 1970) * 365;

		day += countLeapYear(year) - countLeapYear(1970);

		int mi = Math.min(MM - 1, 11);
		boolean isLeapYear = leapYear(yy);
		for (int i = 0; i < mi; ++i) {
			day += _MDs[i];
		}

		if ((isLeapYear) && (MM > 2)) {
			++day;
		}

		day += Math.min(dd, _MDs[mi]) - 1;

		if ((isLeapYear) && (dd == 29)) {
			++day;
		}

		return day;
	}

	public static Date D(long ms) {
		return new Date(ms);
	}

	public static Calendar C(String ds) {
		return C(D(ds));
	}

	public static Calendar C(Date d) {
		return C(d.getTime());
	}

	public static Calendar C(long ms) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ms);
		return c;
	}

	public static String sDTms(Date d) {
		return format(DF_DATE_TIME_MS, d);
	}

	public static String sDTms2(Date d) {
		return format(DF_DATE_TIME_MS2, d);
	}

	public static String sDT(Date d) {
		return format(DF_DATE_TIME, d);
	}

	public static String sD(Date d) {
		return format(DF_DATE, d);
	}

	public static String sT(int sec) {
		int[] ss = T(sec);
		return new StringBuilder().append(Strings.alignRight(Integer.valueOf(ss[0]), 2, '0')).append(":")
				.append(Strings.alignRight(Integer.valueOf(ss[1]),
						2, '0'))
				.append(":")
				.append(Strings.alignRight(Integer.valueOf(ss[2]),
						2, '0'))
				.toString();
	}

	public static String sTmin(int sec) {
		int[] ss = T(sec);
		return new StringBuilder().append(Strings.alignRight(Integer.valueOf(ss[0]), 2, '0')).append(":")
				.append(Strings.alignRight(Integer.valueOf(ss[1]),
						2, '0'))
				.toString();
	}

	public static Date[] week(int off) {
		return week(System.currentTimeMillis(), off);
	}

	public static Date[] week(long base, int off) {
		return weeks(base, off, off);
	}

	public static Date[] weeks(int offL, int offR) {
		return weeks(System.currentTimeMillis(), offL, offR);
	}

	public static Date[] weeks(long base, int offL, int offR) {
		int from = Math.min(offL, offR);
		int len = Math.abs(offL - offR);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(base);

		Date[] re = new Date[2];

		c.add(6, 7 * from);
		c.set(7, 2);
		c.set(11, 0);
		c.set(12, 0);
		c.set(13, 0);
		c.set(14, 0);
		re[0] = c.getTime();

		c.add(6, 7 * (len + 1));
		c.add(14, -1);
		re[1] = c.getTime();

		return re;
	}

	public static String formatForRead(Date d) {
		long ms = System.currentTimeMillis() - d.getTime();

		if (ms < 60000L) {
			return "Just Now";
		}

		if (ms < 3600000L) {
			return new StringBuilder().append("").append(ms / 60000L).append("Min.").toString();
		}

		if (ms < 86400000L) {
			return new StringBuilder().append("").append(ms / 3600000L).append("hr.").toString();
		}

		if (ms < 604800000L) {
			return new StringBuilder().append("").append(ms / 86400000L).append("Day").toString();
		}

		Calendar c = Calendar.getInstance();
		int thisYear = c.get(1);

		c.setTime(d);
		int yy = c.get(1);
		int mm = c.get(2);
		if (thisYear == yy) {
			int dd = c.get(5);
			return String.format("%s %d", new Object[] { _MMM[mm], Integer.valueOf(dd) });
		}

		return String.format("%s %d", new Object[] { _MMM[mm], Integer.valueOf(yy) });
	}

	public static String format(DateFormat fmt, Date d) {
		return ((DateFormat) fmt.clone()).format(d);
	}

	public static String format(String fmt, Date d) {
		return new SimpleDateFormat(fmt, Locale.ENGLISH).format(d);
	}

	public static Date parseq(DateFormat fmt, String s) {
		try {
			return parse(fmt, s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parseq(String fmt, String s) {
		try {
			return parse(fmt, s);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parse(DateFormat fmt, String s)
			throws ParseException {
		return ((DateFormat) fmt.clone()).parse(s);
	}

	public static Date parse(String fmt, String s)
			throws ParseException {
		return new SimpleDateFormat(fmt).parse(s);
	}

	public static long toMillis(String tstr) {
		if (Strings.isBlank(tstr)) {
			return 0L;
		}
		tstr = tstr.toLowerCase();

		String tl = tstr.substring(0, tstr.length() - 1);
		String tu = tstr.substring(tstr.length() - 1);
		if (TIME_S_EN.equals(tu)) {
			return (1000L * Long.valueOf(tl).longValue());
		}
		if (TIME_M_EN.equals(tu)) {
			return (60000L * Long.valueOf(tl).longValue());
		}
		if (TIME_H_EN.equals(tu)) {
			return (3600000L * Long.valueOf(tl).longValue());
		}
		if (TIME_D_EN.equals(tu)) {
			return (86400000L * Long.valueOf(tl).longValue());
		}
		return Long.valueOf(tstr).longValue();
	}

	public static String fromMillis(long mi) {
		return _fromMillis(mi, true);
	}

	public static String fromMillisCN(long mi) {
		return _fromMillis(mi, false);
	}

	private static String _fromMillis(long mi, boolean useEnglish) {
		if (mi <= 1000L) {
			return new StringBuilder().append("1").append((useEnglish) ? TIME_S_EN : TIME_S_CN).toString();
		}
		if ((mi < 60000L) && (mi > 1000L)) {
			return new StringBuilder().append((int) (mi / 1000L)).append((useEnglish) ? TIME_S_EN : TIME_S_CN).toString();
		}
		if ((mi >= 60000L) && (mi < 3600000L)) {
			int m = (int) (mi / 60000L);
			return new StringBuilder().append(m).append((useEnglish) ? TIME_M_EN : TIME_M_CN)
					.append(_fromMillis(mi - (m * 60000L), useEnglish))
					.toString();
		}
		if ((mi >= 3600000L) && (mi < 86400000L)) {
			int h = (int) (mi / 3600000L);
			return new StringBuilder().append(h).append((useEnglish) ? TIME_H_EN : TIME_H_CN)
					.append(_fromMillis(mi - (h * 3600000L), useEnglish))
					.toString();
		}

		int d = (int) (mi / 86400000L);
		return new StringBuilder().append(d).append((useEnglish) ? TIME_D_EN : TIME_D_CN)
				.append(_fromMillis(mi - (d * 86400000L), useEnglish))
				.toString();
	}
}