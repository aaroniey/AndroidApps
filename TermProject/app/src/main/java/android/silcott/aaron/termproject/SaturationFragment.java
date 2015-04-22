package android.silcott.aaron.termproject;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SaturationFragment extends ListFragment {



    private float startHue;
    private float endHue;
    private int saturationCount;

    private ArrayList<HueSwatchItem> arr = new ArrayList<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        saturationCount = getArguments().getInt("saturationCount");
        startHue = getArguments().getFloat("startHue");
        endHue = getArguments().getFloat("endHue");
        float satDiff = 1/(float)saturationCount;
        for(int i = 0; i < saturationCount; i++){
            if(i+1 == saturationCount){
                arr.add(new HueSwatchItem(startHue, endHue,1));
            }
            else {
                arr.add(new HueSwatchItem(startHue, endHue, (i + 1) * satDiff));
            }
        }
        SwatchAdapter adapter = new SwatchAdapter(inflater.getContext(), R.layout.swatch_row,arr);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), Float.toString(arr.get(position).START_HUE) +" " + Float.toString(arr.get(position).END_HUE) + " " + Float.toString(arr.get(position).SATURATION_PERCENT), Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).giveSat(arr.get(position).SATURATION_PERCENT);
    }
}
