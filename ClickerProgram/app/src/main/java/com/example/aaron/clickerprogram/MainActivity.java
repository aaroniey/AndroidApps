package com.example.aaron.clickerprogram;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private long mCurrentCount = 0;
    private TextView textRep = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textRep = (TextView)findViewById(R.id.ClickValue);
        updateCounterText();
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
    public void incrementButtonHandler(View view){
        mCurrentCount++;
        updateCounterText();
    }
    public void resetButtonHandler(View view){
        mCurrentCount = 0;
        updateCounterText();
    }
    private void updateCounterText(){
        textRep.setText(String.valueOf(mCurrentCount) + ((mCurrentCount == 1) ? "Click" : "Clicks"));
    }


}
