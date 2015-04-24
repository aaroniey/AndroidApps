package android.silcott.aaron.termproject.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.silcott.aaron.termproject.MainActivity;
import android.silcott.aaron.termproject.R;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;


public class HueFragment extends ListFragment {



    private int hueSwatchCount;
    private int firstSwatchHue;
    View v;
    LayoutInflater mInflator;
    private ArrayList<HueSwatchItem> arr = new ArrayList<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //create the view of the container
        mInflator = inflater;
        v = mInflator.inflate(R.layout.hue_list_layout, null);


        hueSwatchCount = getArguments().getInt("hueSwatchCount");
        firstSwatchHue = getArguments().getInt("firstSwatchHue");
        Button button= (Button)v.findViewById(R.id.HueConfig);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertOpener();
            }
        });

        updateOptions();
        updateUI();
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Toast.makeText(getActivity(), Float.toString(arr.get(position).START_HUE) +" " + Float.toString(arr.get(position).END_HUE), Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).giveHue(arr.get(position).START_HUE, arr.get(position).END_HUE);

    }

    private void AlertOpener(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View alert = mInflator.inflate(R.layout.hue_double_slider, null);

        alertDialogBuilder.setTitle("Hue Configuration");
        final SeekBar seekBarStart = (SeekBar) alert.findViewById(R.id.centralHueSeek);
        final SeekBar seekBarCount = (SeekBar) alert.findViewById(R.id.hueSwatchCount);
        final TextView prv = (TextView) alert.findViewById(R.id.configPrev);


        seekBarStart.setProgress(firstSwatchHue);
        seekBarCount.setProgress(hueSwatchCount-1);
        float[] color = {seekBarStart.getProgress(), 1, 1};
        prv.setBackground(new ColorDrawable(Color.HSVToColor(color)));


        seekBarStart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float[] color = {progress, 1, 1};
                prv.setBackground(new ColorDrawable(Color.HSVToColor(color)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //nothing
            }
        });

        alertDialogBuilder.setPositiveButton("Submit Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCount(seekBarStart.getProgress(), seekBarCount.getProgress());
                updateOptions();
                updateUI();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        alertDialogBuilder.setView(alert);

        alertDialogBuilder.show();
    }

    private void changeCount(int i, int j){
        firstSwatchHue = i;
        hueSwatchCount = j+1; //+1 is cause slider is from 0->35
        ((MainActivity)getActivity()).giveHueCount(firstSwatchHue, hueSwatchCount);
    }

    public void updateUI(){
        SwatchAdapter adapter = new SwatchAdapter(mInflator.getContext(), R.layout.swatch_row,arr);
        setListAdapter(adapter);
    }
    public void updateOptions(){
        arr.clear();
        int counting  = firstSwatchHue;
        int degOfSep = 360/hueSwatchCount;
        for(int i = 0; i < hueSwatchCount; i++){
            float left = counting-(degOfSep/2);
            float right = counting+(degOfSep/2);
            if (left < 0){
                left = 360 + left;
            }
            if(right > 360){
                right = right - 360;
            }
            arr.add(new HueSwatchItem(left, right));
            counting+=degOfSep;
            if(counting >360){
                counting = counting-360;
            }
        }
    }
}
