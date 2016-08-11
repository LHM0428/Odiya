package nhm.com.odiya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import nhm.com.odiya.R;

/**
 * Created by 이남흔 on 2016-08-11.
 */
public class MenuExpadableAdapter extends BaseExpandableListAdapter{

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<LinearLayout>> childList = null;
    private LayoutInflater inflater = null;
    private Context c;
    private ViewHolder viewHolder = null;


    public MenuExpadableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<LinearLayout>> childList){
        super();
        this.c = c;
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
        //System.out.println("groupList : "+groupList.size());
       // System.out.println("childList : "+childList.size());
    }

    class ViewHolder{
        public ImageView menuImg1,menuImg2,menuImg3,menuImg4,menuImg5,menuImg6,menuImg7;
        TextView menuCoffee,menuBeverage, menuFlatccino, menuAde, menuTea, menuBubble, menuShake;
        ImageView menuContentImg;
        LinearLayout coffeeLayout;
    }


    @Override
    public int getGroupCount() {
        return 5;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
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
        View menuV = inflater.inflate(R.layout.menu_main, parent, false);


        if(v == null){
            //Toast.makeText(c, "getGroupBView호출 v==null",Toast.LENGTH_LONG).show();
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu_group_list,parent,false);
              viewHolder.menuImg1=(ImageView)menuV.findViewById(R.id.menuImg2);
              viewHolder.menuCoffee=(TextView) menuV.findViewById(R.id.menuCoffee);
           v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v  = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu_content, null);
            viewHolder.menuContentImg = (ImageView) v.findViewById(R.id.menuContentImg);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }



        return v;    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
