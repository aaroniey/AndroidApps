package com.intetech;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyActivity
    extends Activity
    implements OnClickListener
{
    Button addButton, removeButton;

    MyFrag myFrag;

    List<MyFrag> myFragList = new ArrayList<MyFrag> ();

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_layout);
        addButton = (Button) findViewById (R.id.addButton);
        removeButton = (Button) findViewById (R.id.removeButton);
        addButton.setOnClickListener (this);
        removeButton.setOnClickListener (this);
    }

    @Override
    public void onClick (View v)
    {
        if (v.equals (addButton))
        {
            FragmentManager fragmentManager = getFragmentManager ();
            FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction ();
            myFrag = new MyFrag ();
            myFrag
                .setSpecialText ("Frag time:  " + System.currentTimeMillis ());
            myFragList.add (myFrag);
            fragmentTransaction.add (R.id.myFrame, myFrag);
            fragmentTransaction.addToBackStack ("myFrag");
            fragmentTransaction.commit ();
        }
        else
        {
            if ((myFragList.size () - 1) >= 0)
            {
                FragmentManager fragmentManager = getFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction ();
                MyFrag lastFrag = myFragList.get (myFragList.size () - 1);
                fragmentTransaction.remove (lastFrag);
                myFragList.remove (lastFrag);
                fragmentTransaction.commit ();
            }

        }

    }
}
