package android.silcott.aaron.termproject.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.silcott.aaron.termproject.MainActivity;
import android.silcott.aaron.termproject.R;
import android.silcott.aaron.termproject.contentprovider.ColorContentProvider;
import android.silcott.aaron.termproject.db.ColorTable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Asilcott on 4/23/2015.
 */
public class ResultsFragment extends Fragment {

    private float startHue;
    private float endHue;
    private float saturationPercent;
    private float valuePercent;
    private float saturationDelta;
    private float valueDelta;
    private String[] sortOrder = new String[3];
    Cursor results;
    View v;

    private static String[] PROJECTION = {
            ColorTable.COLUMN_ID,
            ColorTable.COLUMN_NAME,
            ColorTable.COLUMN_HUE,
            ColorTable.COLUMN_SATURATION,
            ColorTable.COLUMN_VALUE
    };

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.results_layout, null);

        /*get data*/
        saturationPercent = getArguments().getFloat("saturationPercent");
        valuePercent = getArguments().getFloat("valuePercent");
        startHue = getArguments().getFloat("startHue");
        endHue = getArguments().getFloat("endHue");
        saturationDelta = getArguments().getFloat("saturationDelta");
        valueDelta = getArguments().getFloat("valueDelta");
        sortOrder = getArguments().getStringArray("sortOrder");

        /*update the ui*/
        TextView hueSelection = (TextView) v.findViewById(R.id.hueDisplay);
        TextView satSelection = (TextView) v.findViewById(R.id.satDisplay);
        TextView valSelection = (TextView) v.findViewById(R.id.valDisplay);
        Button button = (Button) v.findViewById(R.id.sortOptions);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog();
            }
        });
        hueSelection.setText("Hue Range Selected: " + startHue + " to " + endHue);
        satSelection.setText("Saturation selected: " + (saturationPercent* 100) + " %");
        valSelection.setText("Value selected: " + (valuePercent*100) + " %");
        float[] leftColor = {startHue, saturationPercent, valuePercent};
        float[] rightColor = {endHue, saturationPercent, valuePercent};
        v.setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {Color.HSVToColor(leftColor),Color.HSVToColor(rightColor) } ));


        queryForInformation();
        updateUI();

        /*
        if (cursor == null){
            Toast.makeText(getActivity(), "null cursor", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() < 1) {
            Toast.makeText(getActivity(), "empty cursor", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getActivity(), "non null cursor " + cursor.getCount(), Toast.LENGTH_LONG).show();
        }*/


        return v;
    }

    private void queryForInformation(){
        String hueComparison;
        if(startHue > endHue){
            hueComparison = "(" + ColorTable.COLUMN_HUE + ">=" + startHue + " OR "
                    + ColorTable.COLUMN_HUE + " <= " + endHue + ") and ";
        }
        else if(startHue == endHue){
            hueComparison = "";
        }
        else{
            hueComparison = ColorTable.COLUMN_HUE + ">=" + startHue + " AND "
                    + ColorTable.COLUMN_HUE + " <= " + endHue + " and ";
        }


        String selection  = hueComparison
                + ColorTable.COLUMN_SATURATION + " <= " + (saturationPercent + saturationDelta) + " AND "
                + ColorTable.COLUMN_SATURATION + " >= " + (saturationPercent - saturationDelta) + " AND "
                + ColorTable.COLUMN_VALUE + " <= " + (valuePercent + valueDelta) + " AND "
                + ColorTable.COLUMN_VALUE + " >= " + (valuePercent - valueDelta);
        results = getActivity().getContentResolver().query(ColorContentProvider.CONTENT_URI,
                PROJECTION,
                selection, null, sortOrder[0] + " ASC, " + sortOrder[1] + " ASC, " + sortOrder[2]  + " ASC");
    }

    public void updateUI(){
        ColorCursorAdapter cursorAdapter = new ColorCursorAdapter(getActivity(), results, 0);
        ListView listView =(ListView) v.findViewById(R.id.query_results);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                results.moveToPosition(position);
                Toast.makeText(getActivity(), "Name: " + results.getString(1) +" Hue: "+  results.getFloat(2)+ " Saturation: " + results.getFloat(3)+ " Value: " + results.getFloat(4), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showDialog(){
        final CharSequence[] items = {"Hue, Saturation, Value", "Hue, Value, Saturation", "Saturation, Hue, Value", "Saturation, Value, Hue", "Value, Hue, Saturation", "Value, Saturation, Hue"};

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Sort Order Selection");

        alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                //Toast.makeText(this, "You selected item No." + item + ": " + items[item], Toast.LENGTH_SHORT).show();

                if (items[item].equals("Hue, Saturation, Value")) {
                    choseSortMethod(1);
                }
                else if (items[item].equals("Hue, Value, Saturation")) {
                    choseSortMethod(2);
                }
                else if (items[item].equals("Saturation, Hue, Value")) {
                    choseSortMethod(3);
                }
                else if (items[item].equals("Saturation, Value, Hue")) {
                    choseSortMethod(4);
                }
                else if (items[item].equals("Value, Hue, Saturation")) {
                    choseSortMethod(5);
                }
                else if (items[item].equals("Value, Saturation, Hue")) {
                    choseSortMethod(6);
                }

                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    private void choseSortMethod(int i){
        if(sortOrder == null){
            Toast.makeText(getActivity(), "fuckkkk",Toast.LENGTH_SHORT).show();
        } else {
            switch (i) {
                case 1:
                    sortOrder[0] = "hue";
                    sortOrder[1] = "saturation";
                    sortOrder[2] = "value";
                    break;
                case 2:
                    sortOrder[0] = "hue";
                    sortOrder[1] = "value";
                    sortOrder[2] = "saturation";
                    break;
                case 3:
                    sortOrder[0] = "saturation";
                    sortOrder[1] = "hue";
                    sortOrder[2] = "value";
                    break;
                case 4:
                    sortOrder[0] = "saturation";
                    sortOrder[1] = "value";
                    sortOrder[2] = "hue";
                    break;
                case 5:
                    sortOrder[0] = "value";
                    sortOrder[1] = "hue";
                    sortOrder[2] = "saturation";
                    break;
                case 6:
                    sortOrder[0] = "value";
                    sortOrder[1] = "hue";
                    sortOrder[2] = "saturation";
                    break;
            }
        }
        queryForInformation();
        updateUI();
        ((MainActivity)getActivity()).giveSortOrder(sortOrder);
    }

}
