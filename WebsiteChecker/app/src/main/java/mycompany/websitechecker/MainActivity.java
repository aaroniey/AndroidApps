package mycompany.websitechecker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        app = (MyApplication) getApplication();
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
    public void analyzeButtonHandler(View view){
        if(app.threadRunning()){
            Toast.makeText(getApplicationContext(),  "Thread already Running", Toast.LENGTH_SHORT).show();
        }
        else {
            final String website = ((EditText) findViewById(R.id.URLFeild)).getText().toString();
            try {
                URL url = new URL(website);
                app.parseUrl(url);
            } catch (MalformedURLException e) {
                Toast.makeText(getApplicationContext(), website + " cannot be parsed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void postSuccess(final URL url){
        runOnUiThread(new Runnable() {
            public void run() {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.success_toast_layout,
                        (ViewGroup) findViewById(R.id.success_toast_layout_xml));

                Toast toast = new Toast(getApplicationContext());
                toast.setView(view);
                toast.show();

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){

                }
                Uri uri = Uri.parse(url.toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                PackageManager pm = getPackageManager();
                List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
                if (activities.size() > 0){
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "phone has no way of opening webpage", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();

    }
    public void postFail(final URL url){
        runOnUiThread(new Runnable() {
            public void run() {

                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.fail_toast_layout,
                        (ViewGroup) findViewById(R.id.fail_toast_layout_xml));

                Toast toast = new Toast(getApplicationContext());
                toast.setView(view);
                toast.show();
            }
        });


    }
}
