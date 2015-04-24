package android.silcott.aaron.termproject.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.silcott.aaron.termproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SwatchAdapter extends ArrayAdapter<HueSwatchItem> {
    private static class ViewHolder {
        private TextView swatchView;
    }

    public SwatchAdapter(Context context, int textViewResourceId, List<HueSwatchItem> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.swatch_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.swatchView = (TextView) convertView.findViewById(R.id.swatchPreview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HueSwatchItem item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.swatchView.setText(" ");
            float[] leftColor  = {item.START_HUE , item.SATURATION_PERCENT , item.VALUE_PERCENT };
            float[] rightColor  = {item.END_HUE , item.SATURATION_PERCENT , item.VALUE_PERCENT };

            int[] colors;

            if (item.START_HUE < item.END_HUE){
                int count = (int)((item.END_HUE - item.START_HUE)/5);
                colors = new int[count];
                for(int i = 0; i < count; i++){
                    if(i== count-1){
                        float[] temp = {item.END_HUE, item.SATURATION_PERCENT, item.VALUE_PERCENT};
                        colors[i] = Color.HSVToColor(temp);
                    }else {
                        float[] temp = {item.START_HUE + (i * 5), item.SATURATION_PERCENT, item.VALUE_PERCENT};
                        colors[i] = Color.HSVToColor(temp);
                    }
                }
            }
            else if (item.START_HUE > item.END_HUE){
                int count = (int)(( 360 - (item.START_HUE-item.END_HUE))/5);
                colors = new int[count];
                for(int i = 0; i < count; i++){
                    if(i== count-1){
                        float[] temp = {item.END_HUE, item.SATURATION_PERCENT, item.VALUE_PERCENT};
                        colors[i] = Color.HSVToColor(temp);
                    }else {
                        float intermediateVal = item.START_HUE + (i * 5);
                        if(intermediateVal >360){
                            intermediateVal = intermediateVal - 360;
                        }
                        float[] temp = {intermediateVal, item.SATURATION_PERCENT, item.VALUE_PERCENT};
                        colors[i] = Color.HSVToColor(temp);
                    }
                }
            }
            else{
                int count = 360/5;
                colors = new int[count];
                for(int i = 0; i < count; i++){
                    float inter = item.START_HUE + (i*5);
                    if(inter >360){
                        inter = inter - 360;
                    }
                    float[] temp = {inter, item.SATURATION_PERCENT, item.VALUE_PERCENT};
                    colors[i] = Color.HSVToColor(temp);
                }
            }
            viewHolder.swatchView.setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors ));
        }

        return convertView;
    }
}
