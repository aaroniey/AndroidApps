package android.silcott.aaron.termproject.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.silcott.aaron.termproject.MainActivity;
import android.silcott.aaron.termproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;


import java.util.ArrayList;


public class ValueFragment extends ListFragment {



    private float startHue;
    private float endHue;
    private float saturationPercent;
    private int valueCount;
    LayoutInflater mInflator;
    View v;
    private ArrayList<HueSwatchItem> arr = new ArrayList<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflator = inflater;
        v = mInflator.inflate(R.layout.val_list_layout, null);
        valueCount = getArguments().getInt("valueCount");
        startHue = getArguments().getFloat("startHue");
        saturationPercent = getArguments().getFloat("saturationPercent");
        endHue = getArguments().getFloat("endHue");

        Button button = (Button)v.findViewById(R.id.valSwatchSelector);
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
        /*Toast.makeText(getActivity(),
            Float.toString(arr.get(position).START_HUE) + " " +
            Float.toString(arr.get(position).END_HUE) + " " +
            Float.toString(arr.get(position).SATURATION_PERCENT) + " " +
            Float.toString(arr.get(position).VALUE_PERCENT), Toast.LENGTH_SHORT).show();*/
            ((MainActivity)getActivity()).giveVal(arr.get(position).VALUE_PERCENT);
    }

    private void AlertOpener(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Saturation Configuration");

        final SeekBar seekBar = new SeekBar(getActivity());
        seekBar.setMax(255);
        seekBar.setProgress(valueCount-1);
        alertDialogBuilder.setPositiveButton("Submit Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCount(seekBar.getProgress());
                updateOptions();
                updateUI();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        alertDialogBuilder.setView(seekBar);

        alertDialogBuilder.show();
    }

    private void changeCount(int i){
        valueCount = i+1;  //+1 is cause slider is from 0->255
        ((MainActivity)getActivity()).giveValCount(valueCount);
    }

    private void updateUI(){
        SwatchAdapter adapter = new SwatchAdapter(mInflator.getContext(), R.layout.swatch_row,arr);
        setListAdapter(adapter);
    }

    private void updateOptions(){
        arr.clear();
        float valDiff = 1/(float)valueCount;
        for(int i = valueCount; i > 0; i--){
            arr.add(new HueSwatchItem(startHue, endHue, saturationPercent, (i) * valDiff ));
        }
    }
}
