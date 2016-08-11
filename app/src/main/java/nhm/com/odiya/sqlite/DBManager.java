package nhm.com.odiya.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import nhm.com.odiya.DTO.UserDTO;

//DB를 총괄관리
public class DBManager {

    // DB관련 상수 선언
    private static final String dbName = "korea2.db";
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
            String dropSql = "drop table if exists " + tableName;
            db.execSQL(dropSql);
            System.out.println("onCreate");
            String createSql = "create table " + tableName + " ( id TEXT , pass TEXT , tel TEXT, Birth TEXT, gender TEXT)";
            db.execSQL(createSql);
            Toast.makeText(context, "DB is opened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }
    }

    public void insertUser(UserDTO dto){
        String sql = "insert into "+tableName+" values ('"+dto.getId()+"','"+dto.getPass()+"','"+dto.getTel()+"','"+dto.getBirth()+"', '"+dto.getGender()+"');";
      //  String sql = "insert into "+tableName+" values ('"+dto.getId()+"','"+dto.getPass()+"');";
        db.execSQL(sql);
        Toast.makeText(context, "insert", Toast.LENGTH_SHORT).show();
    }



    public UserDTO printData(String id, String pass){
        UserDTO userDTO=null;
        String str="";

        /*SQLiteStatement stmt = db.compileStatement("select * from where id=? and pass=?");
        stmt.bindString(1,"namni");
        stmt.bindString(2,"1234");
        stmt.execute();
 */

        String table = "cafe_user_test";
        String[] columnsToReturn = { "id", "pass" }; //null=*
        String selection = "id=? AND pass=?";
        //String[] selectionArgs = { id  }; // matched to "?" in selection
       // Cursor cursor = db.query(table, columnsToReturn, null, null, null, null, null);
       Cursor cursor = db.query( table , null, selection, new String[] { id, pass } , null, null, null);

        /*String sql = "select * from "+tableName+" where id=? and pass=?";
        Cursor cursor = db.rawQuery(sql, new String[] {id ,pass });
*/

        while(cursor.moveToNext()){
            userDTO = new UserDTO();
            userDTO.setId(cursor.getString(0));
            userDTO.setPass(cursor.getString(1));
           // str +="아이디는 "+id+"이고 비밀번호는 "+pass+"";
        }
        return userDTO;
    }
}
