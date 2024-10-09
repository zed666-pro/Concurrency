package Thread.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPool05{
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        Machine machine = new Machine(0);
        Monitor monitor = new Monitor(machine, executorService);

        executorService.scheduleAtFixedRate(machine,1,2, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(monitor,0,1,TimeUnit.SECONDS);
    }
}

class Machine implements Runnable{
    int temperature;

    public Machine(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public void run() {
        perform();
        temperature++;
        System.out.println("机器的当前温度升高，温度为："+temperature);
    }

    public void perform()  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTemperature(){
        return temperature;
    }
}

class Monitor implements Runnable{
    Machine machine;
    ScheduledExecutorService scheduledExecutorService;
    static int n = 0;

    public Monitor(Machine machine, ScheduledExecutorService scheduledExecutorService) {
        this.machine = machine;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void run() {
        if(machine.getTemperature() >= 10){
            System.out.println("机器的温度过高");
            n++;
        }
        if(n > 10){
            System.out.println("提醒次数已经超过限制，终止任务");
            scheduledExecutorService.shutdown();
        }
    }
}


