package mn.artlab.law.view.util;


public class DateUtil {
    public static java.sql.Date toSqlDate (Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof java.sql.Date) {
            return (java.sql.Date) obj;
        }

        if (obj instanceof java.util.Date) {
            java.util.Date utilDate = (java.util.Date) obj;
            return new java.sql.Date(utilDate.getTime());
        }

        throw new RuntimeException("Can not convert '" + obj.getClass().getName() + "' object to java.sql.Date!");
    }

    public static java.sql.Timestamp currentTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * Аливаа хугацааг хүн ойлгох хэлбэрт оруулахад ашиглана. Хөгжүүлэгчид дотооддоо ашиглахад зориулсан болно.
     *
     * @author Ganbat Bayarbaatar
     * @Date 2015/04/04
     */
    public static enum TimeUnit {
        NANO_SEC(1, 0, true, "ns"),
        MICRO_SEC(1000, 1, true, "µs"),
        MILLI_SEC(1000, 2, true, "ms"),
        SEC(1000, 3, true, "s"),
        MIN(60, 4, false, "m"),
        HOUR(60, 5, false, "h"),
        DAY(24, 6, false, "d"),
        WEEK(7, 7, false, "w");

        private TimeUnit(int magnitude, int order, boolean fraction, String symbol) {
            this.magnitude = magnitude;
            this.order = order;
            this.fraction = fraction;
            this.symbol = symbol;
        }

        public final int magnitude;
        public final int order;
        public final boolean fraction;
        public final String symbol;

        public String toStr (long value) {
            return timeToStr(value, 1, this);
        }

        public static TimeUnit byOrder(int order) {
            for (TimeUnit unit : values()) {
                if (unit.order == order) {
                    return unit;
                }
            }

            return null;
        }

        public static String nanoToStr(long ns) {
            return timeToStr(ns, 1, TimeUnit.NANO_SEC);
        }

        public static String milliToStr(long ns) {
            return timeToStr(ns, 1, TimeUnit.MILLI_SEC);
        }

        private static String timeToStr(long value, long curMagnitude, TimeUnit unit) {
            long aValue = Math.abs(value);
            TimeUnit next = TimeUnit.byOrder(unit.order + 1);
            if (next != null) {
                if (aValue / curMagnitude >= next.magnitude) {
                    return (value < 0L ? "-" : "") + timeToStr(aValue, curMagnitude * next.magnitude, next);
                }
            }

            long div = aValue / curMagnitude;
            long rem = aValue % curMagnitude;

            String whole = String.valueOf(div);
            String frac  = rem == 0 ? "" : String.valueOf(rem);
            String prev  = String.valueOf(rem % unit.magnitude);

            if (unit.fraction) {
                int l = whole.length() < 3 ? whole.length() : 3;
                int s = Math.min(frac.length(), 3 - l);

                return (value < 0L ? "-" : "") + whole + (s != 0 ? "." + (frac.substring(0, s - 1)) : "") + unit.symbol;
            }

            return (value < 0L ? "-" : "") + whole + unit.symbol + prev + TimeUnit.byOrder(unit.order - 1).symbol;
        }

    }
}
