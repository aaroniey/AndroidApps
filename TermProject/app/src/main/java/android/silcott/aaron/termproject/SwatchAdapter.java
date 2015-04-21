package android.silcott.aaron.termproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
            viewHolder.swatchView.setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {Color.HSVToColor(leftColor),Color.HSVToColor(rightColor) } ));
        }

        return convertView;
    }
}
