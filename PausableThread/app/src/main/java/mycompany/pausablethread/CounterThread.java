package mycompany.pausablethread;

/**
 * Created by Asilcott on 2/25/2015.
 */
public class CounterThread extends Thread {
    private CounterApplication app = null;
    boolean paused = true;

    public CounterThread(CounterApplication application){
        app = application;

    }

    @Override
    public void run(){

        while (true){

            if(!paused){
                //run non-paused code
                //increment count and what not

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        app.updateCount();
                    }
                }).start();
                try {sleep(1000);} catch (InterruptedException e) {}
            }
            else{
                //paused
            }

        }
    }

    public void flip(){
        if(paused){
            //if its paused, unpause it
           paused = false;
        }
        else{
            paused = true;

        }
    }
}
