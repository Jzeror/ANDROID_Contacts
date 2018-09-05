package app.jzero.com.myapp11contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Member_Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_add);
        final Context ctx = Member_Add.this;
        findViewById(R.id.go_list).setOnClickListener(
                (View v)->this.startActivity(new Intent(ctx, Member_List.class))
        );
    }
}
