package android.silcott.aaron.termproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;


public class MainActivity extends Activity {

    private int hueSwatchCount;
    private int saturationCount;
    private int valueCount;
    private float startHue;
    private float endHue;
    private float valuePercent;
    private float valueDelta;
    private float saturationPercent;
    private float saturationDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(savedInstanceState != null){
            Fragment f = getFragmentManager().findFragmentById(R.id.fragment_view);
            hueSwatchCount = savedInstanceState.getInt("hueSwatchCount", hueSwatchCount);
            startHue = savedInstanceState.getFloat("startHue", startHue);
            endHue = savedInstanceState.getFloat("endHue", endHue);
            valuePercent = savedInstanceState.getFloat("valuePercent", valuePercent);
            saturationPercent = savedInstanceState.getFloat("saturationPercent", saturationPercent);
            valueDelta = savedInstanceState.getFloat("valueDelta", valueDelta);
            saturationDelta = savedInstanceState.getFloat("saturationDelta", saturationDelta);
            valueCount = savedInstanceState.getInt("valueCount", valueCount);
            saturationCount = savedInstanceState.getInt("saturationCount", saturationCount);

            //logic to fill in
            if((startHue == -1 || endHue == -1)){
                startHue = endHue = -1;
                if(f == null){
                    HueFragment hueFragment = new HueFragment();
                    Bundle args = new Bundle();
                    args.putInt("hueSwatchCount", hueSwatchCount);
                    hueFragment.setArguments(args);
                    fragmentTransaction.add(R.id.fragment_view,hueFragment).commit();
                }else{
                    HueFragment hueFragment = new HueFragment();
                    Bundle args = new Bundle();
                    args.putInt("hueSwatchCount", hueSwatchCount);
                    hueFragment.setArguments(args);
                    fragmentTransaction.replace(R.id.fragment_view,hueFragment).commit();
                }
            }
            else if(saturationPercent == -1){
                if(f == null){
                    SaturationFragment saturationFragment = new SaturationFragment();
                    Bundle args = new Bundle();
                    args.putFloat("startHue", startHue);
                    args.putFloat("endHue", endHue);
                    args.putInt("saturationCount", saturationCount);
                    saturationFragment.setArguments(args);
                    fragmentTransaction.add(R.id.fragment_view,saturationFragment).commit();
                }else{
                    SaturationFragment saturationFragment = new SaturationFragment();
                    Bundle args = new Bundle();
                    args.putFloat("startHue", startHue);
                    args.putFloat("endHue", endHue);
                    args.putInt("saturationCount", saturationCount);
                    saturationFragment.setArguments(args);
                    fragmentTransaction.replace(R.id.fragment_view,saturationFragment).commit();
                }
            }
            else if(valuePercent == -1){
                if(f == null){
                    ValueFragment valueFragment = new ValueFragment();
                    Bundle args = new Bundle();
                    args.putFloat("startHue", startHue);
                    args.putFloat("endHue", endHue);
                    args.putFloat("saturationPercent", saturationPercent);
                    args.putInt("valueCount", valueCount);
                    valueFragment.setArguments(args);
                    fragmentTransaction.add(R.id.fragment_view,valueFragment).commit();
                }else {
                    ValueFragment valueFragment = new ValueFragment();
                    Bundle args = new Bundle();
                    args.putFloat("startHue", startHue);
                    args.putFloat("endHue", endHue);
                    args.putFloat("saturationPercent", saturationPercent);
                    args.putInt("valueCount", valueCount);
                    valueFragment.setArguments(args);
                    fragmentTransaction.replace(R.id.fragment_view, valueFragment).commit();
                }
            }
            else{
                //run the results fragment
            }
            /*
            if(f == null){
                HueFragment hueFragment = new HueFragment();
                Bundle args = new Bundle();
                args.putInt("hueSwatchCount", hueSwatchCount);
                hueFragment.setArguments(args);
                fragmentTransaction.add(R.id.fragment_view,hueFragment).commit();
            }else{
                HueFragment hueFragment = new HueFragment();
                Bundle args = new Bundle();
                args.putInt("hueSwatchCount", hueSwatchCount);
                hueFragment.setArguments(args);
                fragmentTransaction.replace(R.id.fragment_view,hueFragment).commit();
            }*/

        } else {
            startHue = -1;
            endHue = -1;
            valuePercent = -1;
            saturationPercent = -1;
            hueSwatchCount = 36;
            saturationCount = 10;
            valueCount = 10;
            valueDelta = .10f;
            saturationDelta = .10f;
            HueFragment hueFragment = new HueFragment();
            Bundle args = new Bundle();
            args.putInt("hueSwatchCount", hueSwatchCount);
            hueFragment.setArguments(args);
            fragmentTransaction.add(R.id.fragment_view,hueFragment).commit();
        }

        /*HueFragment hueFragment = new HueFragment();
        Bundle args = new Bundle();
        args.putLong("key", value);
        yourFragment.setArguments(args);
        fragmentTransaction.add(R.id.fragment_view,hueFragment).commit();*/
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("hueSwatchCount", hueSwatchCount);
        savedInstanceState.putFloat("startHue", startHue);
        savedInstanceState.putFloat("endHue", endHue);
        savedInstanceState.putFloat("valuePercent", valuePercent);
        savedInstanceState.putFloat("saturationPercent", saturationPercent);
        savedInstanceState.putFloat("valueDelta", valueDelta);
        savedInstanceState.putFloat("saturationDelta", saturationDelta);
        savedInstanceState.putInt("saturationCount", saturationCount);
        savedInstanceState.putInt("valueCount", valueCount);

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
    }
    public void giveSat(float f){
        saturationPercent= f;
    }
    public void giveVal(float f){
        valuePercent= f;
    }

}
