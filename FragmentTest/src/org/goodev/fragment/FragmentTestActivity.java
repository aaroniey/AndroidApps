package org.goodev.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentTestActivity extends FragmentActivity {
    private FragmentA mListFragment;
    private TextFragment mTextFragment;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextFragment = new TextFragment();
        mListFragment = (FragmentA) getSupportFragmentManager().findFragmentById(R.id.listFragment);
        mListFragment.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.listFragment, mTextFragment,"text");
                ft.commit();
            }
        });
    }
}