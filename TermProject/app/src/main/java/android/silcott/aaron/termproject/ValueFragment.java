package android.silcott.aaron.termproject;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ValueFragment extends ListFragment {



    private float startHue;
    private float endHue;
    private float saturationPercent;
    private int valueCount;

    private ArrayList<HueSwatchItem> arr = new ArrayList<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        valueCount = getArguments().getInt("valueCount");
        startHue = getArguments().getFloat("startHue");
        saturationPercent = getArguments().getFloat("saturationPercent");
        endHue = getArguments().getFloat("endHue");
        float valDiff = 1/(float)valueCount;
        for(int i = 0; i < valueCount; i++){
            if(i+1 == valueCount){
                arr.add(new HueSwatchItem(startHue, endHue,saturationPercent, 1));
            }
            else {
                arr.add(new HueSwatchItem(startHue, endHue, saturationPercent, (i + 1) * valDiff ));
            }
        }
        SwatchAdapter adapter = new SwatchAdapter(inflater.getContext(), R.layout.swatch_row,arr);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(),
            Float.toString(arr.get(position).START_HUE) + " " +
            Float.toString(arr.get(position).END_HUE) + " " +
            Float.toString(arr.get(position).SATURATION_PERCENT) + " " +
            Float.toString(arr.get(position).VALUE_PERCENT), Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).giveVal(arr.get(position).VALUE_PERCENT);
    }
}
