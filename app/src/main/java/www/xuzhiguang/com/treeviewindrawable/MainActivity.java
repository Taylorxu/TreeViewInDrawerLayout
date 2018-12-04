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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.xuzhiguang.com.treeviewindrawable.dummy.DummyContent;

import static www.xuzhiguang.com.treeviewindrawable.RightContentItemFragment.ARG_CONFIG_CODE;

public class MainActivity extends AppCompatActivity implements RightContentItemFragment.OnListFragmentInteractionListener {
    private RecyclerView leftTreeListView;
    private List<LeftTreeDataBean> leftTreeData;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        //todo 获取到数据后，将第一条的配置code 传给fragment
        replaceOrAddFragment("100");
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

    /**
     * @param code 第一个配置的或者 选中的配置的code
     */
    public void replaceOrAddFragment(String code) {
        RightContentItemFragment rightListFragment = new RightContentItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_CONFIG_CODE, code);
        rightListFragment.setArguments(arguments);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_list_fragment, rightListFragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        //todo 跳转到详情
        Log.e("", "");
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
                        triAngleRotate(viewHolder.childListView.getVisibility() == View.GONE, viewHolder.triangleView);
                        viewHolder.childListView.setVisibility(viewHolder.childListView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                    }
                }
            });
            //节点点击时，显示相关数据
            viewHolder.floderFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), data.get(i).getName(), Toast.LENGTH_SHORT).show();
                    replaceOrAddFragment(data.get(i).getCode() + "");
                    // 选完后 关闭drawaable
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
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


        public void triAngleRotate(boolean r, ImageView view) {
            if (r) {
                Animation animation_open = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_open);
                animation_open.setFillAfter(true);
                animation_open.setInterpolator(new AnticipateOvershootInterpolator());
                view.startAnimation(animation_open);
            } else {
                Animation animation_open = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_close);
                animation_open.setFillAfter(true);
                animation_open.setInterpolator(new AnticipateOvershootInterpolator());
                view.startAnimation(animation_open);
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
