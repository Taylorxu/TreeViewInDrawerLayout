package www.xuzhiguang.com.treeviewindrawable;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.xuzhiguang.com.treeviewindrawable.dummy.DummyContent;
import www.xuzhiguang.com.treeviewindrawable.dummy.DummyContent.DummyItem;

public class RightContentItemFragment extends Fragment {


    public static final String ARG_CONFIG_CODE = "config-code";
    private String configCode;
    private OnListFragmentInteractionListener mListener;

    public RightContentItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //左边配置目录code
        if (getArguments() != null && getArguments().containsKey(ARG_CONFIG_CODE)) {
            configCode = getArguments().getString(ARG_CONFIG_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rightcontentitem_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyRightContentItemAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(DummyItem item);
    }
}
