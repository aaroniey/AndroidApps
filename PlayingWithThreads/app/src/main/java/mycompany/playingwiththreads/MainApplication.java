package mycompany.playingwiththreads;

import android.app.Application;

import java.util.Random;

/**
 * Created by Asilcott on 2/19/2015.
 */
public class MainApplication extends Application{
    private MainActivity currentInterface = null;
    public boolean mRunning = false;
    private double currentResult;
    private int mCount;

    @Override
    public void onCreate(){
        super.onCreate();
        currentResult = 0;
        mCount = 0;
    }

    public void reAttach(MainActivity ma){
        currentInterface = ma;
        updatePiTextView();
        updateCountTextView();
    }

    public void StartRunningThread(){
        new Thread(new Runnable(){
            public void run(){
                mRunning = true;
                long inside = 0, total = 0;
                Random rg = new Random();
                while(mCount > 0){
                    double x = rg.nextDouble();
                    double y = rg.nextDouble();
                    if((x*x)+(y*y) <= 1){
                        inside++;
                    }
                    total++;
                    if(mCount%1000 == 0){
                        currentResult = 4*((double)inside/(double)total);;
                        new Thread( new Runnable(){
                            public void run(){
                                currentInterface.updateResultValue(currentResult);
                                currentInterface.updateTestValue(mCount);
                            }
                        }).start();
                    }
                    mCount--;
                }
                mRunning = false;
                currentInterface.updateTestValue(mCount);
            }
        }).start();
    }
    public boolean isThreadRunning(){
        return mRunning;
    }
    public void addAmount(int count){
        mCount += count;
        updateCountTextView();
    }
    public void subAmount(int count){
        mCount -= count;
        updateCountTextView();
    }
    public int getmCount(){
        return mCount;
    }

    private void updatePiTextView(){
        if(currentInterface != null){
            currentInterface.updateResultValue(currentResult);
        }
    }
    private void updateCountTextView(){
        if(currentInterface != null){
            currentInterface.updateTestValue(mCount);
        }
    }
}
