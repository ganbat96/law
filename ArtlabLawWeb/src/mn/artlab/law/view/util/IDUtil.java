package mn.artlab.law.view.util;

import java.util.concurrent.atomic.AtomicLong;


public class IDUtil {
    private static final long MILLIS_2014_01_01 = 1388505600000L;
    private static final AtomicLong SEQ = new AtomicLong(System.currentTimeMillis() - MILLIS_2014_01_01);

    public IDUtil() {
    }

    public static long next() {
        return SEQ.incrementAndGet();
    }
}
