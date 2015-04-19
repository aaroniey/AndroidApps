package mycompany.pausablethread;

import android.app.Application;
import android.os.Bundle;

/**
 * Created by Asilcott on 2/25/2015.
 */
public class CounterApplication extends Application{
    private CounterThread  mCntThread = null;
    private MainActivity mCurrActivity = null;
    private long mCount;

    @Override
    public void onCreate(){
        super.onCreate();
        mCntThread = new CounterThread(this);
        mCntThread.start();
        mCount = 0;
    }
    public void onAttach(MainActivity ma){
        mCurrActivity = ma;
        updateUI(Long.toString(mCount));
    }
    public void requestFilp(){
        mCntThread.flip();
    }
    public void updateCount(){
        ++mCount;
        updateUI(Long.toString(mCount));
    }
    public void updateUI(final String mStrCount){
        if(mCurrActivity != null){
            mCurrActivity.updateCountTextViewValue(mStrCount);
        }
    }

}
