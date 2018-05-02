package com.example.huangwenpei.myview.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.swipedelete.CommonAdapter;
import com.example.huangwenpei.myview.View.swipedelete.SwipeLayout;
import com.example.huangwenpei.myview.View.swipedelete.SwipeLayoutManager;
import com.example.huangwenpei.myview.View.swipedelete.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SwipeDeleteActivity extends Activity {

    ListView listview;
    private Adapter adapter;

    private ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        initView();

        initData();
        adapter = new Adapter(this,datas,R.layout.item_listview);
        listview.setAdapter(adapter);


        // 侧滑打来的时候滑动没有想到什么好的办法解决，只能这样了。
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // 如果listView跟随手机拖动，关闭已经打开的SwipeLayout
                    SwipeLayoutManager.getInstance().closeOpenInstance();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });


    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        // 初始化SwipeLayout
        SwipeLayoutManager.getInstance().closeOpenInstance();
    }


    class Adapter extends CommonAdapter<String> {

        public Adapter(Context context, List<String> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, final String s) {
            holder.setText(R.id.tv_name,s);

            final SwipeLayout swipeLayout = holder.getView(R.id.swipelayout);

            swipeLayout.setOnSwipeLayoutClickListener(new SwipeLayout.OnSwipeLayoutClickListener() {
                @Override
                public void onClick() {
                    Toast.makeText(SwipeDeleteActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });

            ((LinearLayout)swipeLayout.getDeleteView()).getChildAt(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SwipeDeleteActivity.this, "call", Toast.LENGTH_SHORT).show();
                }
            });



            ((LinearLayout)swipeLayout.getDeleteView()).getChildAt(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SwipeLayoutManager.getInstance().closeOpenInstance();
                    datas.remove(s);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(mContext, "datas.size():" + datas.size(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



    private void initData() {
        for (int i=0;i<20;i++) {
            datas.add(i + "壶浊酒喜相逢");
        }
    }
}
