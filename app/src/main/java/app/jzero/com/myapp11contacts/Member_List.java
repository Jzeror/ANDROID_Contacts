package app.jzero.com.myapp11contacts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static app.jzero.com.myapp11contacts.Main.MEMADDR;
import static app.jzero.com.myapp11contacts.Main.MEMEMAIL;
import static app.jzero.com.myapp11contacts.Main.MEMNAME;
import static app.jzero.com.myapp11contacts.Main.MEMPHONE;
import static app.jzero.com.myapp11contacts.Main.MEMPHOTO;
import static app.jzero.com.myapp11contacts.Main.Member;

import static app.jzero.com.myapp11contacts.Main.MEMPW;
import static app.jzero.com.myapp11contacts.Main.MEMSEQ;
import static app.jzero.com.myapp11contacts.Main.MEMTAB;

public class Member_List extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member__list);
        final Context ctx = Member_List.this;
        ItemList query = new ItemList(ctx);
        /*ArrayList<Main.Member> list = (ArrayList<Main.Member>)new Main.ListService(){

            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform();*/ //식을 바로 던져 버린다.
        ListView memberList = findViewById(R.id.memberList);
        memberList.setAdapter(new MemberAdapter(ctx, (ArrayList<Main.Member>)new Main.ListService(){

            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform()));
        memberList.setOnItemClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    Intent intent =new Intent(ctx, Member_Detail.class);
                    Main.Member m = (Main.Member) memberList.getItemAtPosition(i);
                    intent.putExtra("seq", m.seq+"");
                    startActivity(intent);
                }
        );

    }
    private class MemberListQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;

        public MemberListQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class ItemList extends MemberListQuery {
        public ItemList(Context ctx) {
            super(ctx);
        }

        public ArrayList<Main.Member> execute() {
            ArrayList<Main.Member> list = new ArrayList<>();
            Cursor cursor = this.getDatabase().rawQuery(" SELECT * FROM MEMBER ", null);
            Main.Member member = null;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    member = new Main.Member();
                    member.seq = cursor.getInt(cursor.getColumnIndex(MEMSEQ));
                    member.name = cursor.getString(cursor.getColumnIndex(MEMNAME));
                    member.pw = cursor.getString(cursor.getColumnIndex(MEMPW));
                    member.email = cursor.getString(cursor.getColumnIndex(MEMEMAIL));
                    member.phone = cursor.getString(cursor.getColumnIndex(MEMPHONE));
                    member.photo = cursor.getString(cursor.getColumnIndex(MEMPHOTO));
                    member.addr = cursor.getString(cursor.getColumnIndex(MEMADDR));//"addr"
                    list.add(member);
                }
                Log.d("등록된 회원 수가", list.size() + "");
            } else {
                Log.d("등록된 회원이", "없습니다");
            }
            return list;
        }
    }
    private class MemberAdapter extends BaseAdapter{
        ArrayList<Main.Member> list;
        LayoutInflater inflater ;
        public MemberAdapter(Context ctx, ArrayList<Member> list) {
            this.list = list;
            this.inflater = LayoutInflater.from(ctx);
        }
        private int[] photos = {
                R.drawable.profile_1,
                R.drawable.profile_2,
                R.drawable.profile_3,
                R.drawable.profile_4,
                R.drawable.profile_5
        };
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v = inflater.inflate(R.layout.member_item, null);
                holder = new ViewHolder();
                holder.profile = v.findViewById(R.id.profile);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);
            }else{
                holder = (ViewHolder) v.getTag();
            }
            holder.profile.setImageResource(photos[i]);
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView profile;
        TextView name, phone;
    }
}