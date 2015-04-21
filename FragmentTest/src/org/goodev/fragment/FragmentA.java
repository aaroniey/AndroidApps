package org.goodev.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentA extends ListFragment {
    
    private OnItemClickListener mOnItemClickListener;
    
    private static final String[] GENRES = new String[] {
        "Action", "Adventure", "Animation", "Children", "Comedy", "Documentary", "Drama",
        "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
    };
    
    private ArrayAdapter<String> mListAdapter;
    
    public FragmentA() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, GENRES);
    }

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(mListAdapter);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        getListView().setOnItemClickListener(listener);
    }

}
