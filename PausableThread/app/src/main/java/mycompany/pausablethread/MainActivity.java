package mycompany.pausablethread;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView mCountTextView;
    private CounterApplication app;
    private String mCountStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCountTextView = (TextView)findViewById(R.id.CountTextView);
        mCountStr = getResources().getString(R.string.Count_Str);
    }
    protected  void onStart(){
        super.onStart();
        app = (CounterApplication) getApplication();
        app.onAttach(this);
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

    public void onToggleButtonHandler(View view){
        app.requestFilp();
    }
    public void updateCountTextViewValue(final String str){
        mCountTextView.post(new Runnable() {
            @Override
            public void run() {
                mCountTextView.setText(mCountStr + str);
            }
        });
    }


}
