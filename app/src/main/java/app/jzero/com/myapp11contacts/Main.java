package app.jzero.com.myapp11contacts;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context ctx = Main.this;
        findViewById(R.id.create_db).setOnClickListener(
                (View v)->{
                    SQLiteHelper helper = new SQLiteHelper(ctx); //helper 객체가 곧 db가 된다.
                }
        );
        findViewById(R.id.go_login).setOnClickListener(
                (View v)->this.startActivity(new Intent(ctx,Login.class))
        );
/*        findViewById(R.id.go_add).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(ctx,Member_Add.class));
                }
        );*/
    }
    static class Member{int seq;String name,pw,email,phone,photo,addr;}
    static interface StatusService{public void perform();}
    static interface ListService{public List<?> perform();}
    static interface RetrieveService{public Object perform();}
    static String DBNAME = "jzero.db";
    static String MEMTAB = "MEMBER";
    static String MEMSEQ = "SEQ";
    static String MEMNAME = "NAME";
    static String MEMPW = "PW";
    static String MEMEMAIL = "EMAIL";
    static String MEMPHONE = "PHONE";
    static String MEMPHOTO = "PHOTO";
    static String MEMADDR = "ADDR";
    static abstract class QueryFactory{
        Context ctx;
        public QueryFactory(Context ctx) {
            this.ctx = ctx;
        }
        public abstract SQLiteDatabase getDatabase();
    }
    static class SQLiteHelper extends SQLiteOpenHelper{
        public SQLiteHelper(Context context) {
            super(context, DBNAME, null, 1);
            this.getWritableDatabase();
        } //여기부터 어떻게 이동함?

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format(
                    " CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT , %s TEXT , %s TEXT , %s TEXT , %s TEXT , %s TEXT)"
                    , MEMTAB, MEMSEQ, MEMNAME, MEMPW, MEMEMAIL, MEMPHONE, MEMPHOTO, MEMADDR  );
            Log.d("실행할 쿼리    ::",sql);
            db.execSQL(sql);
            Log.d("===================","create 쿼리실행완료");
            String[] arr = {"","김진태","김지은","김준","김단아","홍혜리"};
            for(int i = 1; i<=5; i++){
                db.execSQL(String.format("INSERT INTO %s ( %s  , %s , %s  , %s  , %s  , %s ) VALUES ('%s' , '%s' , '%s' , '%s' , '%s' , '%s')", MEMTAB, MEMNAME, MEMPW, MEMEMAIL, MEMPHONE, MEMPHOTO, MEMADDR
                        , arr[i], "1", "peace_"+i+"@test.com", "010-1234-567"+i, "love_"+i+".jpg", "신촌로 "+i+"길"));
            }
            Log.d("===================","insert 쿼리실행완료");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MEMTAB);
            onCreate(db);
        }
    }
}
