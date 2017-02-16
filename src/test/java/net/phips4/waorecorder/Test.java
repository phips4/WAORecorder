package net.phips4.waorecorder;

import net.phips4.waorecorder.util.Util;

import java.util.concurrent.TimeUnit;

/**
 * Created by phips4
 */
public class Test {

    public static void main(String[] args) {
        final long start = System.currentTimeMillis();

        Runnable run = new Runnable() {
            @Override
            public void run() {

                while (true) {
                    System.out.println(Util.formatMillis(start));

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(run).start();
    }

}
