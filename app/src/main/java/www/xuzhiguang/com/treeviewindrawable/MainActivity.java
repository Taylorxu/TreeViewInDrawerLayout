package www.xuzhiguang.com.treeviewindrawable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView leftTreeListView;
    private List<LeftTreeDataBean> leftTreeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        createLeftTreeData();
        leftTreeListView = (RecyclerView) findViewById(R.id.left_tree);
        leftTreeListView.setLayoutManager(new LinearLayoutManager(this));
        leftTreeListView.setAdapter(new LeftTreeListAdapter(leftTreeData));
    }

    private void createLeftTreeData() {
        leftTreeData = new ArrayList<>();
        List<LeftTreeDataBean> childrenList;
        for (int i = 0; i < 3; i++) {
            childrenList = new ArrayList<>();

            LeftTreeDataBean dataBean = new LeftTreeDataBean();
            dataBean.setCode(i);
            dataBean.setName("配置项Root" + i);
            //子节点集合
            for (int c = 0; c < 6; c++) {
                childrenList.add(new LeftTreeDataBean(c, "配置子节点" + c, null));
            }

            dataBean.setChildren(childrenList);
            leftTreeData.add(dataBean);
        }
        Log.e("XZG", leftTreeData.toString());
    }


    class LeftTreeListAdapter extends RecyclerView.Adapter<LeftTreeListAdapter.MyViewHolder> {

        private List<LeftTreeDataBean> data;

        public LeftTreeListAdapter(List<LeftTreeDataBean> dataParams) {
            this.data = dataParams;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.left_tree_list_item, null, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
            viewHolder.fillData(data.get(i));
            viewHolder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //根节点点击时图标，子节点listView 显示或隐藏
                    if (data.get(i).getChildren() != null && data.get(i).getChildren().size() > 0) {
                        viewHolder.childListView.setVisibility(viewHolder.childListView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    }
                }
            });
            //节点点击时，显示相关数据
            viewHolder.floderFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), data.get(i).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;
            RecyclerView childListView;
            LinearLayout rootLayout;
            ImageView folderFileView;
            ImageView triangleView;
            FrameLayout floderFile;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.item_text);
                childListView = (RecyclerView) itemView.findViewById(R.id.left_tree_item_child_list);
                rootLayout = itemView.findViewById(R.id.left_tree_item_root);
                triangleView = itemView.findViewById(R.id.triangle_iv);
                folderFileView = itemView.findViewById(R.id.folder_file_iv);
                floderFile = itemView.findViewById(R.id.item_name_fl);

            }

            public void fillData(LeftTreeDataBean treeData) {
                textView.setText(treeData.getName());
                List<LeftTreeDataBean> childData = treeData.getChildren();
                if (childData != null && childData.size() > 0) {
                    folderFileView.setBackgroundResource(R.mipmap.folder);
                    triangleView.setVisibility(View.VISIBLE);
                    childListView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    childListView.setAdapter(new LeftTreeListAdapter(childData));
                } else {
                    folderFileView.setBackgroundResource(R.mipmap.file);
                    triangleView.setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
