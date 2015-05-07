package silkysmooth.tutorialappone;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import silkysmooth.tutorialappone.OptionActivities.SettingActivity;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "silkysmooth.tutorialappone.MESSAGE";
    public final static String OPTION_ONE = "silkysmooth.tutorialappone.OPTION_ONE";
    public final static String OPTION_TWO = "silkysmooth.tutorialappone.OPTION_TWO";
    public final static int DEFAULT_OPTION_ONE = 1;
    public final static String DEFAULT_OPTION_TWO = "WORD";

    private int optionOne;
    private String optionTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            optionOne = DEFAULT_OPTION_ONE;
            optionTwo = DEFAULT_OPTION_TWO;
        } else {
            optionOne = savedInstanceState.getInt(OPTION_ONE);
            optionTwo = savedInstanceState.getString(OPTION_TWO);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(OPTION_ONE, optionOne);
        outState.putString(OPTION_TWO,optionTwo);

        super.onSaveInstanceState(outState);
    }

    public void sendMessage(View v){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void openSearch(){

    }
    private void openSettings(){
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra(OPTION_ONE, optionOne);
        intent.putExtra(OPTION_TWO, optionTwo);
        startActivity(intent);
    }
}