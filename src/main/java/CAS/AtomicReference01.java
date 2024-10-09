package CAS;

import java.util.concurrent.TimeUnit;

public class AtomicReference01 {
    static volatile DebitCard debitCard = new DebitCard("申高洁",0);
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    //读取共享变量
                    final DebitCard dc = debitCard;
                    DebitCard card = new DebitCard(dc.getAccount(), dc.getAmount() + 10);
                    System.out.println(card);
                    debitCard = card;

                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }.start();
        }
    }
}

class DebitCard{

    public String account;
    public int amount;

    public DebitCard(String account, int amount) {
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
