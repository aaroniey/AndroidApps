package mycompany.dictionary;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText termText;
    private EditText defText;
    private MainApplication ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ma = (MainApplication) getApplication();
        ma.attach(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        termText = (EditText) findViewById(R.id.TermField);
        defText = (EditText) findViewById(R.id.DefinitionField);


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
    public void addOnClick(View view){
        if(!ma.running()) {
            String term = termText.getText().toString();
            String def = termText.getText().toString();
            termText.setText("");
            defText.setText("");
            ma.addTerm(term, def);
        }
        else{
            Toast.makeText(this, "search already running", Toast.LENGTH_SHORT).show();
        }
    }
    public void findOnClick(View view){
        if(!ma.running()) {
            String term = termText.getText().toString();
            termText.setText("");
            defText.setText("");
            ma.findTerm(term);
        }
        else{
            Toast.makeText(this, "search already running", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteOnClick(View view){
        if(!ma.running()) {
            String term = termText.getText().toString();
            termText.setText("");
            defText.setText("");
            ma.deleteTerm(term);
        }
        else{
            Toast.makeText(this, "search already running", Toast.LENGTH_SHORT).show();
        }
    }
}
