package pl.kostrowski.pessimistic.util;

public class SleepUtil {

    public static void sleepFor(Integer milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
