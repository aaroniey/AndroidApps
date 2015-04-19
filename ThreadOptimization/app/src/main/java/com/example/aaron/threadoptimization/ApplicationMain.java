package com.example.aaron.threadoptimization;

import android.app.Activity;
import android.app.Application;

import java.util.Random;

/**
 * Created by Aaron on 2/20/2015.
 */
public class ApplicationMain extends Application {
    private MainActivity mCurrentInterface= null;
    private long mCount;
    private double mResult;
    private boolean mRunning;

    @Override
    public void onCreate(){
        mCount = 0;
        mResult = 0;
    }

    public void attachActivity(MainActivity activity){
        mCurrentInterface = activity;
        mCurrentInterface.updateCountTextView(mCount);
        mCurrentInterface.updateResultTextView(mResult);
    }

    public boolean addCount(){
        if(!mRunning){
            mCount+=1000;
        }
        else{
            return false;
        }
        return true;
    }
    public boolean subCount(){
        if(mCount - 1000 >= 0 && !mRunning){
            mCount-=1000;
        }
        else{
            return false;
        }
        return true;
    }
    public boolean runTest(){
        if(!mRunning && mCount <=0){
            startThread();
        }
        else{
            return false;
        }
        return true;
    }
    private void startThread(){
        mRunning = true;
        new Thread(new Runnable(){
            @Override
            public void run(){
                Random rg = new Random();
                while(mCount < 0){
                    //do the estimation
                    mResult += rg.nextDouble();
                    //update the UI
                    updateUI(mResult, mCount);
                    mCount--;
                }
                mRunning = false;
            }
        }).start();
    }
    private void updateUI(final double result, final long count){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mCurrentInterface.updateResultTextView(result);
                mCurrentInterface.updateCountTextView(count);
            }
        }).start();
    }
}
