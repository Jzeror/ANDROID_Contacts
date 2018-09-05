package app.jzero.com.myapp11contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Member_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member__list);
        final Context ctx = Member_List.this;
        findViewById(R.id.go_add).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(ctx, Member_Add.class));
                }
        );
        findViewById(R.id.go_detail).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(ctx, Member_Detail.class));
                }
        );
    }
}
