package android.silcott.aaron.termproject;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 4/19/2015.
 */
public class ListViewFragment extends ListFragment {
    private List<ListViewItem> mItems;

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);

        mItems = new ArrayList<ListViewItem>();
        mItems.add(new ListViewItem("red"));
        mItems.add(new ListViewItem("black"));
        mItems.add(new ListViewItem("blue"));
        setListAdapter(new ListViewAdapter(getActivity(), mItems));
    }
    @Override
    public void onViewCreated(View view, Bundle sis){
        super.onViewCreated(view, sis);
        getListView().setDivider(null);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        ListViewItem item = mItems.get(position);

        Toast.makeText(getActivity(), item.color, Toast.LENGTH_SHORT).show();

    }

}
