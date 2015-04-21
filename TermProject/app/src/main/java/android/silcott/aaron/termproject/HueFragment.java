package android.silcott.aaron.termproject;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class HueFragment extends ListFragment {



    private int hueSwatchCount;
    private ArrayList<HueSwatchItem> arr = new ArrayList<>();

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hueSwatchCount = getArguments().getInt("hueSwatchCount");
        int degOfSep = 360/hueSwatchCount;
        for(int i = 0; i < hueSwatchCount; i++){
            arr.add(new HueSwatchItem(i*degOfSep, (i+1)*degOfSep));
        }
        SwatchAdapter adapter = new SwatchAdapter(inflater.getContext(), R.layout.swatch_row,arr);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), Float.toString(arr.get(position).START_HUE) +" " + Float.toString(arr.get(position).END_HUE), Toast.LENGTH_SHORT).show();
    }
}
