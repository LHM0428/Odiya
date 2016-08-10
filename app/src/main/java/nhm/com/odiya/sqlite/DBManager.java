package nhm.com.odiya.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;

//DB를 총괄관리
public class DBManager {

    // DB관련 상수 선언
    private static final String dbName = "korea1.db";
    private static final String tableName = "cafe_user_test";
    public static final int dbVersion = 1;

    // DB관련 객체 선언
    private OpenHelper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context context;

    // 생성자
    public DBManager(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }

    // Opener of DB and Table
    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory,
                          int version) {
            super(context, name, null, version);
            // TODO Auto-generated constructor stub
        }

        // 생성된 DB가 없을 경우에 한번만 호출됨
        @Override
        public void onCreate(SQLiteDatabase db) {
            // String dropSql = "drop table if exists " + tableName;
            // db.execSQL(dropSql);
            System.out.println("onCreate");
            String createSql = "create table " + tableName + " ( id TEXT , pass TEXT )";
            db.execSQL(createSql);
            Toast.makeText(context, "DB is opened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }
    }

    public void insertUser(){
        String sql = "insert into "+tableName+" values ('namni','1234');";
        db.execSQL(sql);
        Toast.makeText(context, "insert", Toast.LENGTH_SHORT).show();
    }



    public String printData(){

        //db = getReadableDatabase();
        String str="";

        Cursor cursor = db.rawQuery("select * from cafe_user_test;",null);

        while(cursor.moveToNext()){

            String id = cursor.getString(0);
            String pass = cursor.getString(1);

            System.out.println("id : "+id +" , pass : "+pass );
            str +="아이디는 "+id+"이고 비밀번호는 "+pass+"";
        }
        return str;
    }
}
