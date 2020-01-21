package idv.ron.listviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ListView lvMember = (ListView) findViewById(R.id.lvMember);
        List<Member> memberList = getMemberList();
        lvMember.setAdapter(new MemberAdapter(this, memberList));
        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Member member = (Member) parent.getItemAtPosition(position);
                String text = "ID = " + member.getId() +
                        ", name = " + member.getName();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class MemberAdapter extends BaseAdapter {
        Context context;
        List<Member> memberList;

        MemberAdapter(Context context, List<Member> memberList) {
            this.context = context;
            this.memberList = memberList;
        }

        @Override
        public int getCount() {
            return memberList.size();
        }

        @Override
        public View getView(int position, View itemView, ViewGroup parent) {
            if (itemView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                itemView = layoutInflater.inflate(R.layout.item_view, parent, false);
            }

            Member member = memberList.get(position);
            ImageView ivImage = (ImageView) itemView
                    .findViewById(R.id.ivImage);
            ivImage.setImageResource(member.getImage());

            TextView tvId = (TextView) itemView
                    .findViewById(R.id.tvId);
            tvId.setText(String.valueOf(member.getId()));

            TextView tvName = (TextView) itemView
                    .findViewById(R.id.tvName);
            tvName.setText(member.getName());
            return itemView;
        }

        @Override
        public Object getItem(int position) {
            return memberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return memberList.get(position).getId();
        }
    }

    public List<Member> getMemberList() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member(23, R.drawable.p01, "John"));
        memberList.add(new Member(75, R.drawable.p02, "Jack"));
        memberList.add(new Member(65, R.drawable.p03, "Mark"));
        memberList.add(new Member(12, R.drawable.p04, "Ben"));
        memberList.add(new Member(92, R.drawable.p05, "James"));
        memberList.add(new Member(103, R.drawable.p06, "David"));
        memberList.add(new Member(45, R.drawable.p07, "Ken"));
        memberList.add(new Member(78, R.drawable.p08, "Ron"));
        memberList.add(new Member(234, R.drawable.p09, "Jerry"));
        memberList.add(new Member(35, R.drawable.p10, "Maggie"));
        memberList.add(new Member(57, R.drawable.p11, "Sue"));
        memberList.add(new Member(61, R.drawable.p12, "Cathy"));
        return memberList;
    }
}
