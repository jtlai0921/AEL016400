package idv.ron.recyclercardviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        2, StaggeredGridLayoutManager.HORIZONTAL));
        List<Member> memberList = getMemberList();
        recyclerView.setAdapter(new MemberAdapter(this, memberList));
    }

    private class MemberAdapter extends
            RecyclerView.Adapter<MemberAdapter.MyViewHolder> {
        private Context context;
        private List<Member> memberList;

        MemberAdapter(Context context, List<Member> memberList) {
            this.context = context;
            this.memberList = memberList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivImage;
            TextView tvId, tvName;

            MyViewHolder(View itemView) {
                super(itemView);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
                tvId = (TextView) itemView.findViewById(R.id.tvId);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
            }
        }

        @Override
        public int getItemCount() {
            return memberList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.item_view, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            final Member member = memberList.get(position);
            viewHolder.ivImage.setImageResource(member.getImage());
            viewHolder.tvId.setText(String.valueOf(member.getId()));
            viewHolder.tvName.setText(member.getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imageView = new ImageView(context);
                    imageView.setImageResource(member.getImage());
                    Toast toast = new Toast(context);
                    toast.setView(imageView);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
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
