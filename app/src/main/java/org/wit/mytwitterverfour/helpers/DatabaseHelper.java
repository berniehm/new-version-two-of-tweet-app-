package org.wit.mytwitterverfour.helpers;

/**
 * Created by berni on 1/5/2018.
 * https://www.youtube.com/watch?v=p8TaTgr4uKM
 */
final class DataBasehelper{
        private DataBasehelper() {
        long id;
        String Name;
       String description;
         int imageResourceId;
}
}
/**public class static Dabasehelper implements BaseColumns {
      public static final String DATABASE_NAME ="Users.db";
    public static final String TABLE_NAME = "Users_data_table";
    public static final String COl1 = "Email";
    public static final String COL2 ="NAME";
    public static final String COL3 ="SURNAME";}

   // public DatabaseHelper(Context context) {
     //   super(context, DATABASE_NAME, null ,1);
     //   SQLiteDatabase db =this.getWritableDatabase();




    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table " + TABLE_NAME + "(EMAIL TEXT , PRIMARY KEY , NAME TEXT,SURNAME TEXT");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}
 */