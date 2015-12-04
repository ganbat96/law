package mn.artlab.law.view.timer;

import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import commonj.timers.TimerManager;


public class TimerServlet extends HttpServlet {
    public TimerServlet() {
        super();
    }

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = 60 * ONE_SECOND;
    private static final long ONE_HOUR   = 60 * ONE_MINUTE;
    private static final long SIX_HOUR   = 6  * ONE_HOUR;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("hhooooop");
        try {
            InitialContext ic = new InitialContext();
            TimerManager tm = (TimerManager)ic.lookup("java:comp/env/tm/TimerManager");
            tm.schedule(new ShuukhmnTimer(), ONE_MINUTE, ONE_MINUTE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
