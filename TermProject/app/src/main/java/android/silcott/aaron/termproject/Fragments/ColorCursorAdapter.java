package android.silcott.aaron.termproject.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.silcott.aaron.termproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class ColorCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    static private final int ID = 0;
    static private final int NAME = 1;
    static private final int HUE = 2;
    static private final int SATURATION = 3;
    static private final int VALUE = 4;

    public ColorCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View row = mInflater.inflate(R.layout.preview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.preview = (TextView) row.findViewById(R.id.preview);
        viewHolder.nameSlot = (TextView) row.findViewById(R.id.nameSlot);
        row.setTag( viewHolder);
        return row;
    }
    static private class ViewHolder {
        TextView preview;
        TextView nameSlot;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        //id, name, hue, sat, val
        float[] color = {cursor.getFloat(HUE),cursor.getFloat(SATURATION),cursor.getFloat(VALUE) };
        viewHolder.preview.setBackground(new ColorDrawable(Color.HSVToColor(color)));
        viewHolder.nameSlot.setText(cursor.getString(NAME));
    }
}
