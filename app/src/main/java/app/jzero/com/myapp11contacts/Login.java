package app.jzero.com.myapp11contacts;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static app.jzero.com.myapp11contacts.Main.MEMPW;
import static app.jzero.com.myapp11contacts.Main.MEMSEQ;
import static app.jzero.com.myapp11contacts.Main.MEMTAB;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Context ctx = Login.this;

        findViewById(R.id.loginBtn).setOnClickListener(
                (View v)->{
                   ItemExist query = new ItemExist(ctx);
                   EditText x = findViewById(R.id.input_id);
                   EditText y = findViewById(R.id.input_pw);
                    query.id = x.getText().toString();
                    query.pw = y.getText().toString();
                    new Main.StatusService() {
                        @Override
                        public void perform() {
                            if(query.execute()){
                                /*Intent intent = new Intent(ctx, Member_List.class);
                                intent.putExtra("id",query.id);
                                intent.putExtra("pw",query.pw);*/
                                Toast.makeText(ctx, "로그인성공", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ctx, Member_List.class));
                            }else{
                                Toast.makeText(ctx, "로그인실패", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ctx, Login.class));
                            }
                        }
                    }.perform();
                }
        );
    }
    private class LoginQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;
        public LoginQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemExist extends LoginQuery{
        String id, pw;
        public ItemExist(Context ctx) {
            super(ctx);
        }
        public boolean execute(){
            return super.getDatabase().rawQuery(String.format(" SELECT * FROM %s WHERE %s LIKE '%s' AND %s LIKE '%s' ",
                    MEMTAB,MEMSEQ,id,MEMPW,pw),null).moveToNext()
                    ;
        }
    }
}
