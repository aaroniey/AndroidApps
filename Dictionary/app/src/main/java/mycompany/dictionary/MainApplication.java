package mycompany.dictionary;

import android.app.Application;
import android.database.Cursor;

/**
 * Created by Asilcott on 3/26/2015.
 */
public class MainApplication extends Application{

    private DictionaryDAO db;
    private MainActivity act;
    private boolean running;
    private Cursor cursor;


    @Override
    public void onCreate(){
        super.onCreate();
        db = new DictionaryDAO(this);
        act = null;
        running = false;
        cursor = db.fetchAllEntries();
    }

    public void attach(MainActivity ma){
        act = ma;
    }
    public boolean running(){
        return running;
    }
    public boolean findTerm(String term){

        if(cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                String tempTerm = cursor.getString(cursor.getColumnIndex("term"));
                if (tempTerm.contentEquals(term)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean deleteTerm(String term){
        boolean result =  db.deleteDictionaryEntry(term);
        return result;

    }
    public void addTerm(String term, String def){
        if(findTerm(term)){
            db.updateDictionaryEntry(term, def);
        }
        else{
            db.createDictionaryEntry(term, def);
        }
    }


}
