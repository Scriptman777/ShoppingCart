package uhk.fim.model;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class MainThreads {
    public static void main(String[] args) {
        ClockThread ct = new ClockThread();
        ct.start();


        String[] names = {"David","Honza","Adam","Matěj"};
        for (String name:names) {
            System.out.println(name);
        }

    }
}

class ClockThread extends Thread {

    @Override
    public void run() {
        while (true) {
            DateFormat fmt = DateFormat.getTimeInstance();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(fmt.format(GregorianCalendar.getInstance().getTime()));
        }
    }
}
