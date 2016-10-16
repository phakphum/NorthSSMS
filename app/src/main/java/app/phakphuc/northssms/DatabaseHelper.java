package app.phakphuc.northssms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Phakphum on 25-Sep-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "north_database.db";
    public static final String DB_NAME2 = "s1.db";
    public static final String DB_VERSION = "1";
    public static final String DB_LOCATION = "/data/data/app.phakphuc.northssms/databases/";
    public static final String TABLE_SITEDATA = "tb_sitedata";
    public static final String TABLE_VERSION = "tb_version";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }

    // Getting single contact
    public TableSiteData Get_SiteData(String site) {
        openDatabase();
        site = site.toUpperCase();
        String sql = "SELECT * FROM "+TABLE_SITEDATA+" where site='"+site+"'";

        //Toast.makeText(mContext, sql, Toast.LENGTH_LONG).show();
        Cursor cursor = mDatabase.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            TableSiteData cont = new TableSiteData(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getString(7),
                    cursor.getString(8));
            // return contact
            cursor.close();
            closeDatabase();
            return cont;
        }
        return null;
    }
    public TableVersion Get_Version() {
        openDatabase();
        String sql = "SELECT * FROM "+TABLE_VERSION;

        Cursor cursor = mDatabase.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()){
            TableVersion cont = new TableVersion(cursor.getString(0));
            // return contact
            cursor.close();
            closeDatabase();
            return cont;
        }
        return null;
    }
}
