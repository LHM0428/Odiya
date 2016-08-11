package nhm.com.odiya.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nhm.com.odiya.R;
import nhm.com.odiya.main.MainActivity;

/**
 * Created by Lee on 2016-08-10.
 */
public class BaseExpandableAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> groupList = null;
    private GridView gv_child = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    private int j;
    private Context mContext;

    class ViewHolder{
        public ImageView iv_image;
        public ImageView iv_arrow;
        public LinearLayout linearLayout_coupon;
        public TextView tv_groupName;
        public GridView gv_child;
    }

    public BaseExpandableAdapter(ArrayList<String> groupList,GridView gv_child,int j, Context c) {
        this.groupList = groupList;
        this.gv_child = gv_child;
        this.j = j;
        this.inflater = LayoutInflater.from(c);
        this.mContext = c;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public GridView getChild(int groupPosition, int childPosition) {
        return gv_child;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v==null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_coupon, parent, false);
            viewHolder.tv_groupName = (TextView) v.findViewById(R.id.tv_group);
            viewHolder.linearLayout_coupon = (LinearLayout) v.findViewById(R.id.linearLayout_coupon);
            viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
            viewHolder.iv_arrow = (ImageView) v.findViewById(R.id.iv_arrow);
            viewHolder.iv_image.setImageResource(R.drawable.coffee_cup);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        if(isExpanded){
            viewHolder.iv_arrow.setImageResource(R.drawable.up_arrow);
            viewHolder.linearLayout_coupon.setBackgroundColor(Color.WHITE);
            viewHolder.tv_groupName.setTextColor((v.getResources().getColor(R.color.mainTheme)));
        }else{
            viewHolder.iv_arrow.setImageResource(R.drawable.down_arrow);
            viewHolder.linearLayout_coupon.setBackgroundColor(v.getResources().getColor(R.color.mainTheme));
            viewHolder.tv_groupName.setTextColor(Color.WHITE);
        }
        viewHolder.tv_groupName.setText(getGroup(groupPosition));
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_coupon, null);
            viewHolder.gv_child = (GridView) v.findViewById(R.id.gv_child);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.gv_child.setAdapter(new ImageAdapter(mContext,j));
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
