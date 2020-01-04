package com.example.huangwenpei.myview.View.careIndicatorView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangwenpei.myview.Bean.GetAllDevicesBean;
import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.GlideUtils;
import com.example.huangwenpei.myview.View.RoundImageViewByXfermode;

import java.util.List;


public class CareIndicatorAdapter extends FancyCoverFlowAdapter {
    private Activity mContext;
    public List<GetAllDevicesBean.DataBean> list;
    private int currentChoosePosition = 0;

    public CareIndicatorAdapter(Activity context, List<GetAllDevicesBean.DataBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public View getCoverFlowItem(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_interact_device_list, null);
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            convertView.setLayoutParams(new FancyCoverFlow.LayoutParams(width / 3, FancyCoverFlow.LayoutParams.WRAP_CONTENT));
            viewHolder = new ViewHolder();
            viewHolder.deviceName = (TextView) convertView.findViewById(R.id.tv_device_name);
            viewHolder.deviceModel = (ImageView) convertView.findViewById(R.id.iv_device_model);
            viewHolder.icon = (RoundImageViewByXfermode) convertView.findViewById(R.id.iv_icon);
            viewHolder.rl = convertView.findViewById(R.id.rl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == currentChoosePosition) {
            viewHolder.rl.setBackgroundResource(R.drawable.bg_ring_stroke);
            viewHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "hahah", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            viewHolder.rl.setBackgroundResource(R.color.transparent);
        }


        GlideUtils.loadImageView(mContext, "http://cdn2.jianshu.io/assets/default_avatar/5-33d2da32c552b8be9a0548c7a4576607.jpg", viewHolder.icon);
//        viewHolder.icon.setImageResource(R.mipmap.logo300);

        viewHolder.deviceName.setText(list.get(position%list.size()).getStrOwnerName());
        return convertView;
    }

    @Override
    public int getCount() {
//        return list.size();

        //实现无限循环，设置count为最大值
        return Integer.MAX_VALUE;
    }

    @Override
    public GetAllDevicesBean.DataBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setCurrentChoosePosition(int currentChoosePosition) {
        this.currentChoosePosition = currentChoosePosition;
    }

    static class ViewHolder {
        TextView deviceName;
        ImageView deviceModel;
        RoundImageViewByXfermode icon;
        View rl;
    }
}
