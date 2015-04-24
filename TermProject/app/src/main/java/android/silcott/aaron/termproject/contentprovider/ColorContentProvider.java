package android.silcott.aaron.termproject.contentprovider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.ContentResolver;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.silcott.aaron.termproject.db.ColorDBHelper;
import android.silcott.aaron.termproject.db.ColorTable;
import android.text.TextUtils;

public class ColorContentProvider extends ContentProvider {

    // database
    private ColorDBHelper database;



    private static final String AUTHORITY
            = "android.silcott.aaron.termproject.provider";
    private static final String BASE_PATH
            = "colors";
    public static final Uri CONTENT_URI
            = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    static final String _ID = "_id";
    static final String NAME = "name";
    static final String HUE = "hue";
    static final String SATURATION = "saturation";
    static final String VAlUE = "value";

    public static final String CONTENT_TYPE
            = ContentResolver.CURSOR_DIR_BASE_TYPE + "/colors";

    public static final String CONTENT_ITEM_TYPE
            = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/color";

    public static final String CONTENT_URI_PREFIX
            = "content://" + AUTHORITY + "/" + BASE_PATH + "/";




    // URI Matcher
    private static final UriMatcher sURIMatcher = new UriMatcher( UriMatcher.NO_MATCH );
    private static final int COLORS = 1;
    private static final int COLOR_ID = 2;   static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, COLORS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", COLOR_ID);
    }

    @Override
    public boolean onCreate() {
        database = new ColorDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // check if the caller has requested a column which does not exists
        ColorTable.validateProjection(projection);

        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables( ColorTable.TABLE_COLOR );

        switch ( sURIMatcher.match(uri) ) {
            case COLORS:
                break;
            case COLOR_ID:
                // add the task ID to the original query
                queryBuilder.appendWhere( ColorTable.COLUMN_ID + "=" + uri.getLastPathSegment() );
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query( db, projection, selection,
                selectionArgs, null, null, sortOrder);

        // notify listeners
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType( Uri uri ) {
        return null;
    }

    @Override
    public Uri insert( Uri uri, ContentValues values ) {

        SQLiteDatabase sqlDB = database.getWritableDatabase();

        long id = 0;
        switch ( sURIMatcher.match(uri) ) {
            case COLORS:
                id = sqlDB.insert(ColorTable.TABLE_COLOR, null, values);
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        getContext().getContentResolver().notifyChange( uri, null );

        return Uri.parse( CONTENT_URI_PREFIX + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch ( sURIMatcher.match(uri) ) {
            case COLORS:
                rowsDeleted = sqlDB.delete(ColorTable.TABLE_COLOR, selection, selectionArgs);
                break;
            case COLOR_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty( selection )) {
                    rowsDeleted = sqlDB.delete( ColorTable.TABLE_COLOR,
                            ColorTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete( ColorTable.TABLE_COLOR,
                            ColorTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch ( sURIMatcher.match(uri) ) {
            case COLORS:
                rowsUpdated = sqlDB.update( ColorTable.TABLE_COLOR,
                        values,
                        selection,
                        selectionArgs);
                break;
            case COLOR_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update( ColorTable.TABLE_COLOR,
                            values,
                            ColorTable .COLUMN_ID + "=" + id,
                            null );
                } else {
                    rowsUpdated = sqlDB.update( ColorTable.TABLE_COLOR,
                            values,
                            ColorTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}