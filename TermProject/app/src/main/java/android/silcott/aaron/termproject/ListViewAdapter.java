package android.silcott.aaron.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aaron on 4/18/2015.
 */
public class ListViewAdapter extends ArrayAdapter<ListViewItem>
{
    public ListViewAdapter(Context context, List<ListViewItem> resource) {
        super(context, R.layout.list_view_item_layout, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_view_item_layout, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.colorView = (TextView) convertView.findViewById(R.id.colorText);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ListViewItem item = getItem(position);
        viewHolder.colorView.setText(item.color);

        return convertView;
    }
    private static class ViewHolder {
        TextView colorView;
    }
}
