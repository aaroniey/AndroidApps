package org.goodev.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment {
    TextView mTextView;
    
    public TextFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTextView = new TextView(getActivity());
        mTextView.setTextColor(Color.RED);
        mTextView.setText("textView");
        return mTextView;
    }
    
    

}
