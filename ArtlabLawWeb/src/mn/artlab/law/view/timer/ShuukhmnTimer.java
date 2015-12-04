package mn.artlab.law.view.timer;

import commonj.timers.Timer;
import commonj.timers.TimerListener;

import java.io.Serializable;

public class ShuukhmnTimer  implements Serializable, TimerListener {
    public ShuukhmnTimer() {
        super();
    }
    public static int COUNT = 0;

    @Override
    public void timerExpired(Timer timer) {
        System.out.println("Timer");
        COUNT ++;
    }
}
