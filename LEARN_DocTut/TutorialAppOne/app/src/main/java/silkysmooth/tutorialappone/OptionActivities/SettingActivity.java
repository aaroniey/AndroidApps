package silkysmooth.tutorialappone.OptionActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import silkysmooth.tutorialappone.MainActivity;
import silkysmooth.tutorialappone.R;


public class SettingActivity extends ActionBarActivity {

    private int optionOne;
    private String optionTwo;
    private TextView optionOneView;
    private TextView optionTwoView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();

        //load data
        optionOne = intent.getIntExtra(MainActivity.OPTION_ONE, MainActivity.DEFAULT_OPTION_ONE);
        optionTwo = intent.getStringExtra(MainActivity.OPTION_TWO);
        if(optionTwo == null){ optionTwo = MainActivity.DEFAULT_OPTION_TWO; }

        //loadViews
        optionOneView = (TextView)findViewById(R.id.OptionOneTextView);
        optionTwoView = (TextView)findViewById(R.id.OptionTwoTextView);
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void updateUI(){
        optionOneView.setText(Integer.toString(optionOne));
        optionTwoView.setText(optionTwo);
    }
}
