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



public class SaturationFragment extends ListFragment {



    private float startHue;
    private float endHue;
    private int saturationCount;
    View v;
    LayoutInflater mInflater;

    private ArrayList<HueSwatchItem> arr = new ArrayList<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        v = inflater.inflate(R.layout.sat_list_layout, null);
        saturationCount = getArguments().getInt("saturationCount");
        startHue = getArguments().getFloat("startHue");
        endHue = getArguments().getFloat("endHue");

        Button button = (Button)v.findViewById(R.id.satSwatchSelector);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertOpener();
            }
        });

        float satDiff = 1/(float)saturationCount;
        for(int i = saturationCount; i > 0; i--){
                arr.add(new HueSwatchItem(startHue, endHue, (i) * satDiff));
        }
        updateOptions();
        updateUI();
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Toast.makeText(getActivity(), Float.toString(arr.get(position).START_HUE) +" " + Float.toString(arr.get(position).END_HUE) + " " + Float.toString(arr.get(position).SATURATION_PERCENT), Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).giveSat(arr.get(position).SATURATION_PERCENT);
    }

    private void AlertOpener(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Saturation Configuration");

        final SeekBar seekBar = new SeekBar(getActivity());
        seekBar.setMax(255);
        seekBar.setProgress(saturationCount-1);
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
        saturationCount = i+1;  //+1 is cause slider is from 0->255
        ((MainActivity)getActivity()).giveSatCount(saturationCount);
    }

    private void updateUI(){
        SwatchAdapter adapter = new SwatchAdapter(mInflater.getContext(), R.layout.swatch_row,arr);
        setListAdapter(adapter);

    }

    private void updateOptions(){
        arr.clear();
        float satDiff = 1/((float)saturationCount);
        for(int i = saturationCount; i > 0; i--){
            arr.add(new HueSwatchItem(startHue, endHue, (i) * satDiff));
        }
    }
}
