package CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReference02 {

   private static AtomicReference<DebitCard1> dc =
            new AtomicReference<>(new DebitCard1("申高洁", 0));

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread("T--->" + i) {

                @Override
                public void run() {
                    while (true) {
                        DebitCard1 ocd = dc.get();
                        DebitCard1 ncd = new DebitCard1(ocd.getAccount(), ocd.getAmount() + 10);
                        if (dc.compareAndSet(ocd, ncd))
                            System.out.println(ncd);

                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }.start();
        }

    }

}

class DebitCard1{

    public String account;
    public int amount;

    public DebitCard1(String account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public int getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return "DebitCard{" +
                "account='" + account + '\'' +
                ", amount=" + amount +
                '}';
    }
}

