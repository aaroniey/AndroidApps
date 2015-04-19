package mycompany.playingwiththreads;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private TextView testNum;
    String mCurTestAmt;
    String mAlreadyRunning;
    String mTooBig;
    String mTooSmall;
    String mNeedNonZero;
    TextView mPiTextView;
    MainApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testNum = (TextView) findViewById(R.id.TestAmount);
        mCurTestAmt = getResources().getString(R.string.current_test_number);
        mAlreadyRunning = getResources().getString(R.string.already_running);
        mTooBig = getResources().getString(R.string.to_large);
        mTooSmall = getResources().getString(R.string.to_small);
        mNeedNonZero = getResources().getString(R.string.zero_estimation_run);
        testNum.setText(mCurTestAmt + "0");
        mPiTextView = (TextView) findViewById(R.id.Estimation);
        mPiTextView.setText("Pi: 0");

    }
    @Override
    protected void onStart(){
        super.onStart();
        app = (MainApplication) getApplication();
        app.reAttach(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addButtonHandler(View view){
        if(!app.isThreadRunning()){
            if(Long.MAX_VALUE - 100000 > app.getmCount()) {
                app.addAmount(100000);
            }
            else{
                ToastMaker(mTooBig);
            }
        }
        else{
            ToastMaker(mAlreadyRunning);
        }

    }
    public void SubButtonHandler(View view){
        if(!app.isThreadRunning()){
            if(app.getmCount() >= 100000) {
                app.subAmount(100000);
            }
            else{
                ToastMaker(mTooSmall);
            }
        }
        else{
            ToastMaker(mAlreadyRunning);
        }

    }
    public void startButtonHandler(View view){
        if(!app.isThreadRunning()){
            if(app.getmCount() == 0){
                ToastMaker(mNeedNonZero);
            }
            else{
                app.StartRunningThread();
            }
        }
        else{
            ToastMaker(mAlreadyRunning);
        }
    }
    public void updateResultValue(final double result){

        testNum.post(new Runnable() {
            @Override
            public void run() {
                mPiTextView.setText("Pi: " + String.valueOf(result));
            }
        });
    }
    public void updateTestValue(final int count){

        testNum.post(new Runnable() {
            @Override
            public void run() {
                testNum.setText(mCurTestAmt + String.valueOf(count));
            }
        });
    }
    private void ToastMaker(CharSequence message){
        Context context = getApplicationContext();
        Toast toast =  Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
