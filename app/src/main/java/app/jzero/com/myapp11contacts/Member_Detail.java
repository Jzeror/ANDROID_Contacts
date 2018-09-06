package app.jzero.com.myapp11contacts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static app.jzero.com.myapp11contacts.Main.MEMADDR;
import static app.jzero.com.myapp11contacts.Main.MEMEMAIL;
import static app.jzero.com.myapp11contacts.Main.MEMNAME;
import static app.jzero.com.myapp11contacts.Main.MEMPHONE;
import static app.jzero.com.myapp11contacts.Main.MEMPHOTO;
import static app.jzero.com.myapp11contacts.Main.MEMPW;
import static app.jzero.com.myapp11contacts.Main.MEMSEQ;

public class Member_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member__detail);
        final Context ctx = Member_Detail.this;
        Intent intent = this.getIntent();
       // String seq = intent.getExtras().getString("seq");
        ItemRetrieve query = new ItemRetrieve(ctx);
        query.id =intent.getStringExtra("seq");
        Log.d("이름은 :::",query.execute().name);
        Log.d("전화번호는 :::",query.execute().phone);
        findViewById(R.id.go_list).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(ctx, Member_List.class));
                }
        );
        findViewById(R.id.go_update).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(ctx, Member_Update.class));
                }
        );
    }
    private class MemberDetailQuery extends  Main.QueryFactory{
        SQLiteOpenHelper helper;
        public MemberDetailQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private  class ItemRetrieve extends  MemberDetailQuery{
        String id;
        public ItemRetrieve(Context ctx) {
            super(ctx);
        }
        public Main.Member execute(){
            Main.Member m = new Main.Member();
            Cursor cursor = this.getDatabase().rawQuery(String.format(" SELECT * FROM MEMBER WHERE "+Main.MEMSEQ+" LIKE %s",id ),null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    m = new Main.Member();
                    m.seq = cursor.getInt(cursor.getColumnIndex(MEMSEQ));
                    m.name = cursor.getString(cursor.getColumnIndex(MEMNAME));
                    m.pw = cursor.getString(cursor.getColumnIndex(MEMPW));
                    m.email = cursor.getString(cursor.getColumnIndex(MEMEMAIL));
                    m.phone = cursor.getString(cursor.getColumnIndex(MEMPHONE));
                    m.photo = cursor.getString(cursor.getColumnIndex(MEMPHOTO));
                    m.addr = cursor.getString(cursor.getColumnIndex(MEMADDR));//"addr"
                }
            }else {
                Log.d("오류 입","니다");
            }
            return m;
        }
    }
}
