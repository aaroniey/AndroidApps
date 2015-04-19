package com.example.aaron.threadoptimization;

import android.app.Application;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private ApplicationMain app;
    private TextView mResultTextView;
    private TextView mCountTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTextView = (TextView) findViewById(R.id.ResultTextView);
        mCountTextView = (TextView) findViewById(R.id.CountTextView);
    }

    protected void onStart(){
        super.onStart();
        app = (ApplicationMain)getApplication();
        app.attachActivity(this);
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
        app.addCount();
    }
    public void subButtonHandler(View view){
        app.subCount();
    }
    public void runButtonHandler(View view){
        app.runTest();
    }
    public void updateResultTextView(final double result){
        mResultTextView.post(new Runnable() {
            @Override
            public void run() {
                mResultTextView.setText("result: " + result);
            }
        });
    }
    public void updateCountTextView(final long count){
        mCountTextView.post(new Runnable(){
            @Override
            public void run(){
                mCountTextView.setText("Count: " + count);
            }
        });
    }
}
