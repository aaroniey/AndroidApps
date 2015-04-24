package android.silcott.aaron.termproject;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.silcott.aaron.termproject.Fragments.HueFragment;
import android.silcott.aaron.termproject.Fragments.ResultsFragment;
import android.silcott.aaron.termproject.Fragments.SaturationFragment;
import android.silcott.aaron.termproject.Fragments.ValueFragment;
import android.view.Menu;
import android.view.View;



public class MainActivity extends FragmentActivity {

    private int hueSwatchCount;
    private int firstSwatchHue;
    private int saturationCount;
    private int valueCount;
    private float startHue;
    private float endHue;
    private float valuePercent;
    private float valueDelta;
    private float saturationPercent;
    private float saturationDelta;
    private String[] sortOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortOrder = new String[3];
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        hueSwatchCount = sharedPreferences.getInt("hueSwatchCount", 36);
        valueCount = sharedPreferences.getInt("valueCount", 10);
        saturationCount = sharedPreferences.getInt("saturationCount", 10);
        firstSwatchHue = sharedPreferences.getInt("firstSwatchHue", 0);
        sortOrder[0] = sharedPreferences.getString("firstSortTerm", "hue");
        sortOrder[1] = sharedPreferences.getString("secondSortTerm", "saturation");
        sortOrder[2] = sharedPreferences.getString("thirdSortTerm", "value");
        if(savedInstanceState != null){

            /*
            Load all Data
            */

            startHue = savedInstanceState.getFloat("startHue", startHue);
            endHue = savedInstanceState.getFloat("endHue", endHue);
            valuePercent = savedInstanceState.getFloat("valuePercent", valuePercent);
            saturationPercent = savedInstanceState.getFloat("saturationPercent", saturationPercent);
            valueDelta = savedInstanceState.getFloat("valueDelta", valueDelta);
            saturationDelta = savedInstanceState.getFloat("saturationDelta", saturationDelta);


            //Pick up where left off
            if((startHue == -1 || endHue == -1)){
                startHue = endHue = -1;
                openHue();
            }
            else if(saturationPercent == -1){
                openSat();
            }
            else if(valuePercent == -1){
                openVal();
            }
            else {
                //run the results fragment
            }

        } else {

            /*
            create arbitrary  data if none is present
            */
            startHue = -1;
            endHue = -1;
            valuePercent = -1;
            saturationPercent = -1;
            valueDelta = .25f;
            saturationDelta = .25f;


            //open hue selection
            openHue();
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's color selections in a bundle
        savedInstanceState.putFloat("startHue", startHue);
        savedInstanceState.putFloat("endHue", endHue);
        savedInstanceState.putFloat("valuePercent", valuePercent);
        savedInstanceState.putFloat("saturationPercent", saturationPercent);
        savedInstanceState.putFloat("valueDelta", valueDelta);
        savedInstanceState.putFloat("saturationDelta", saturationDelta);

        // Save the user's settings in Shared Preferences
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("hueSwatchCount", hueSwatchCount);
        editor.putInt("saturationCount", saturationCount);
        editor.putInt("valueCount", valueCount);
        editor.putString("firstSortTerm", sortOrder[0]);
        editor.putString("secondSortTerm", sortOrder[1]);
        editor.putString("thirdSortTerm", sortOrder[2]);
        editor.putInt("firstSwatchHue", firstSwatchHue);
        editor.apply();

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void giveHue(float f, float e){
        startHue = f;
        endHue = e;
        openSat();
    }
    public void giveSat(float f){
        saturationPercent= f;
        openVal();
    }
    public void giveVal(float f){
        valuePercent= f;
        openResults();
    }
    public void reset(View view){
        startHue = -1;
        endHue = -1;
        saturationPercent = -1;
        valuePercent = -1;
        openHue();
    }
    private void openHue(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = fragmentManager.findFragmentById(R.id.fragment_view);
        HueFragment hueFragment = new HueFragment();
        Bundle args = new Bundle();
        args.putInt("hueSwatchCount", hueSwatchCount);
        args.putInt("firstSwatchHue",firstSwatchHue);
        hueFragment.setArguments(args);
        if(f == null){
            fragmentTransaction.add(R.id.fragment_view,hueFragment).commit();
        }else{
            fragmentTransaction.replace(R.id.fragment_view,hueFragment).commit();
        }
    }
    private void openSat(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = fragmentManager.findFragmentById(R.id.fragment_view);
        SaturationFragment saturationFragment = new SaturationFragment();
        Bundle args = new Bundle();
        args.putFloat("startHue", startHue);
        args.putFloat("endHue", endHue);
        args.putInt("saturationCount", saturationCount);
        saturationFragment.setArguments(args);
        if(f == null){
            fragmentTransaction.add(R.id.fragment_view,saturationFragment).commit();
        }else{
            fragmentTransaction.replace(R.id.fragment_view,saturationFragment).commit();
        }
    }
    private void openVal(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = fragmentManager.findFragmentById(R.id.fragment_view);
        ValueFragment valueFragment = new ValueFragment();
        Bundle args = new Bundle();
        args.putFloat("startHue", startHue);
        args.putFloat("endHue", endHue);
        args.putFloat("saturationPercent", saturationPercent);
        args.putInt("valueCount", valueCount);
        valueFragment.setArguments(args);
        if(f == null){
            fragmentTransaction.add(R.id.fragment_view,valueFragment).commit();
        }else {
            fragmentTransaction.replace(R.id.fragment_view, valueFragment).commit();
        }
    }
    private void openResults(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = fragmentManager.findFragmentById(R.id.fragment_view);
        ResultsFragment resultsFragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putFloat("startHue", startHue);
        args.putFloat("endHue", endHue);
        args.putFloat("saturationPercent", saturationPercent);
        args.putFloat("valuePercent", valuePercent);
        args.putFloat("saturationDelta", saturationDelta);
        args.putFloat("valueDelta", valueDelta);
        args.putStringArray("sortOrder",sortOrder);
        resultsFragment.setArguments(args);
        if(f == null){
            fragmentTransaction.add(R.id.fragment_view,resultsFragment).commit();
        }else {
            fragmentTransaction.replace(R.id.fragment_view, resultsFragment).commit();
        }
    }
    public void giveSortOrder(String[] str){
        sortOrder = str;
    }
    public void giveSatCount(int i){ saturationCount = i;}
    public void giveValCount(int i){ valueCount = i;}
    public void giveHueCount(int i, int j){ firstSwatchHue= i; hueSwatchCount = j;}

}
