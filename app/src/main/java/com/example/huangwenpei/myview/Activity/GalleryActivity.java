package com.example.huangwenpei.myview.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.huangwenpei.myview.Bean.GetAllDevicesBean;
import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.AndroidUtil;
import com.example.huangwenpei.myview.Util.LogUtil;
import com.example.huangwenpei.myview.View.CenterLayoutManager;
import com.example.huangwenpei.myview.View.careIndicatorView.CareIndicatorAdapter;
import com.example.huangwenpei.myview.View.careIndicatorView.FancyCoverFlow;
import com.example.huangwenpei.myview.View.recycle.HorizontalPageLayoutManager;
import com.example.huangwenpei.myview.View.recycle.PagingScrollHelper;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends Activity  implements PagingScrollHelper.onPageChangeListener{
    private static final String TAG = "HealthActivity";
    private FancyCoverFlow fancyCoverFlow;
    private CareIndicatorAdapter careIndicatorAdapter;
    private List<GetAllDevicesBean.DataBean> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView pagerecycle;
    BaseQuickAdapter<GetAllDevicesBean.DataBean, BaseViewHolder> myAdapter;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (careIndicatorAdapter != null) {
                        careIndicatorAdapter.notifyDataSetChanged();
                        Toast.makeText(GalleryActivity.this,"hehhe", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        initView();
    }

    private void initView() {
        fancyCoverFlow =  findViewById(R.id.fancyCoverFlow_health);
        GetAllDevicesBean bean = new GetAllDevicesBean();
        List<GetAllDevicesBean.DataBean> data = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            GetAllDevicesBean.DataBean dataBean = new GetAllDevicesBean.DataBean();
            dataBean.setStrOwnerName("a"+i);
            dataBean.setStrNickName("b"+i);
            dataBean.setNRoleID("0");
            data.add(dataBean);
        }
        bean.setData(data);
        getdata(bean);


        pagerecycle = findViewById(R.id.pagerecycle);
        HorizontalPageLayoutManager horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 4);
        myAdapter = new BaseQuickAdapter<GetAllDevicesBean.DataBean, BaseViewHolder>(R.layout.item_card_robot_fourth_abnormal_dot_item, data) {
            @Override
            protected void convert(BaseViewHolder helper, GetAllDevicesBean.DataBean item) {
                helper.setIsRecyclable(false);
                TextView deviceName = helper.getView(R.id.v_employee_name);

                deviceName.setText(item.getStrOwnerName());
            }
        };
        pagerecycle.setLayoutManager(horizontalPageLayoutManager);
        pagerecycle.setAdapter(myAdapter);
        scrollHelper.setUpRecycleView(pagerecycle);
        scrollHelper.setOnPageChangeListener(this);


        recyclerView = findViewById(R.id.recyclerView);
        CenterLayoutManager manager = new CenterLayoutManager(this);
        manager.setOrientation(CenterLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        BaseQuickAdapter<GetAllDevicesBean.DataBean, BaseViewHolder> adapter = new BaseQuickAdapter<GetAllDevicesBean.DataBean, BaseViewHolder>(R.layout.item_card_robot_fourth_abnormal_dot_item, data) {
            @Override
            protected void convert(BaseViewHolder helper, GetAllDevicesBean.DataBean item) {
                helper.setIsRecyclable(false);
                TextView deviceName = helper.getView(R.id.v_employee_name);

                deviceName.setText(item.getStrOwnerName());

                if ("1".equals(item.getNRoleID())) {
//                    helper.itemView.setScaleX(1f);
//                    helper.itemView.setScaleY(1f);
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(helper.itemView.getLayoutParams());
//                    lp.setMargins(0, 0, 0, ScreenUtils.dp2px(GalleryActivity.this,-10));
//                    helper.itemView.setLayoutParams(lp);

                    helper.setBackgroundRes(R.id.v_abnormal_dot_bg, R.drawable.robot_abnormal_team_item_bg_select);
                }else {
//                    helper.itemView.setScaleX(0.8f);
//                    helper.itemView.setScaleY(0.8f);
//                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(helper.itemView.getLayoutParams());
//                    lp.setMargins(-ScreenUtils.dp2px(GalleryActivity.this,22), ScreenUtils.dp2px(GalleryActivity.this,10), -ScreenUtils.dp2px(GalleryActivity.this,22), ScreenUtils.dp2px(GalleryActivity.this,-8));
//                    helper.itemView.setLayoutParams(lp);

                    helper.setBackgroundRes(R.id.v_abnormal_dot_bg, R.drawable.robot_abnormal_team_item_bg);
                }

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int[] a=new int[2] ;
                view.getLocationOnScreen(a);

                Log.e("点点", a[1]+"===" + a[0]);


                //更新选中状态
                List<GetAllDevicesBean.DataBean> data1 = adapter.getData();
                for (int i = 0; i < data1.size(); i++) {
                    if (i == position) {
                        data1.get(i).setNRoleID("1");
                    } else {
                        data1.get(i).setNRoleID("0");
                    }
                }
                manager.smoothScrollToPosition(recyclerView, new RecyclerView.State(),position);
                adapter.notifyDataSetChanged();
            }
        });
        List<int[]> location = new ArrayList<>();
        int centerX = AndroidUtil.getScreenWidth(this)/2;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                    location.clear();
                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                        View child = recyclerView.getChildAt(i);
                        int[] a = new int[2];
                        child.getLocationOnScreen(a);
                        location.add(a);
                        Log.e("点点", i+"位置===" + a[0]);


                    }
                List<GetAllDevicesBean.DataBean> data1 = adapter.getData();
                    for (int j = 0; j < location.size(); j++) {
                        int[] point = location.get(j);
                        View child = recyclerView.getChildAt(j);
                        int center = point[0] + child.getWidth() / 2;

                        if (centerX - child.getWidth() / 2 < center &&  center < centerX + child.getWidth() / 2 ) {
//                            child.findViewById(R.id.v_abnormal_dot_bg).setScaleX(1f);
//                            child.findViewById(R.id.v_abnormal_dot_bg).setScaleY(1f);
//                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(child.findViewById(R.id.v_abnormal_dot_bg).getLayoutParams());
//                            lp.setMargins(0, 0, 0, ScreenUtils.dp2px(GalleryActivity.this,-10));
//                            child.findViewById(R.id.v_abnormal_dot_bg).setLayoutParams(lp);
//                            data1.get(j).setNRoleID("1");
                            child.findViewById(R.id.v_abnormal_dot_bg).setBackgroundResource( R.drawable.robot_abnormal_team_item_bg_select);
                        }else{
//                            child.findViewById(R.id.v_abnormal_dot_bg).setScaleX(0.8f);
//                            child.findViewById(R.id.v_abnormal_dot_bg).setScaleY(0.8f);
//                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(child.findViewById(R.id.v_abnormal_dot_bg).getLayoutParams());
//                            lp.setMargins(-ScreenUtils.dp2px(GalleryActivity.this,22), ScreenUtils.dp2px(GalleryActivity.this,10), -ScreenUtils.dp2px(GalleryActivity.this,22), ScreenUtils.dp2px(GalleryActivity.this,-8));
//                            child.findViewById(R.id.v_abnormal_dot_bg).setLayoutParams(lp);
//                            data1.get(j).setNRoleID("0");
                            child.findViewById(R.id.v_abnormal_dot_bg).setBackgroundResource(R.drawable.robot_abnormal_team_item_bg);
                        }
                    }



//
//                int horizontalScrollOffset = recyclerView.computeHorizontalScrollOffset();
//                Log.e("滚呀滚", "===" + newState);//0 静止，1拖拽，2飞翔
//                Log.e("滚呀滚", "===" + horizontalScrollOffset);
//                if (newState == 0) {
//
//                    //获取最后一个可见view的位置
//                    int lastItemPosition = manager.findLastVisibleItemPosition();
//                    //获取第一个可见view的位置
//                    int firstItemPosition = manager.findFirstVisibleItemPosition();
//
//                    int position = (lastItemPosition + firstItemPosition) / 2;
//
//                    //更新选中状态
//                    List<GetAllDevicesBean.DataBean> data1 = adapter.getData();
//                    for (int i = 0; i < data1.size(); i++) {
//                        if (i == position) {
//                            data1.get(i).setNRoleID("1");
//                        } else {
//                            data1.get(i).setNRoleID("0");
//                        }
//                    }
//                    adapter.notifyDataSetChanged();

//                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.e("滚呀滚", "dx ===" + dx+"dy ===" + dy);
            }
        });

    }



    public void getdata(GetAllDevicesBean bean) {
        list.clear();
        if (bean.getData() != null) {
            list.addAll(bean.getData());
        }

        careIndicatorAdapter = new CareIndicatorAdapter(GalleryActivity.this,list);
        fancyCoverFlow.setAdapter(careIndicatorAdapter);
        fancyCoverFlow.setUnselectedAlpha(1f);// 未选中的透明度
        fancyCoverFlow.setUnselectedSaturation(1f);//设置未选中的饱和度
        fancyCoverFlow.setUnselectedScale(0.3f);//设置未选中的规模
        fancyCoverFlow.setSpacing(0);//设置间距
        fancyCoverFlow.setMaxRotation(0);//设置最大旋转
        fancyCoverFlow.setScaleDownGravity(1f);// //未选择项的下对齐比例 0f：上对齐 0.5f：居中对齐  1f：下对齐
        fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        careIndicatorAdapter.notifyDataSetChanged();
        fancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.i(TAG, "InteractFragment.onItemSelected>position:" + position);
                handler.removeMessages(2);
                handler.sendEmptyMessageDelayed(2, 300);
                if (careIndicatorAdapter != null) {
                    careIndicatorAdapter.setCurrentChoosePosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        careIndicatorAdapter.setCurrentChoosePosition(200);
        careIndicatorAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPageChange(int index) {
//        myAdapter.updateData();
        myAdapter.notifyDataSetChanged();
        //滚动到第一页
        scrollHelper.scrollToPosition(index);
    }
}
