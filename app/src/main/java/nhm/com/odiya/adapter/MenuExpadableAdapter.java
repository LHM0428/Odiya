package nhm.com.odiya.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.logging.StreamHandler;

import nhm.com.odiya.R;

/**
 * Created by 이남흔 on 2016-08-11.
 */
public class MenuExpadableAdapter extends BaseExpandableListAdapter{

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<LinearLayout>> childList = null;
    private LayoutInflater inflater = null;
    private Context c;
    private ArrayList<String[]> menuList=null;
    private ViewHolder viewHolder = null;
    int length=0,convertLeng=0;


    public MenuExpadableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<LinearLayout>> childList, ArrayList<String[]> menuList){
        super();
        this.c = c;
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
        this.menuList = menuList;
        //System.out.println("groupList : "+groupList.size());
       // System.out.println("childList : "+childList.size());
    }

    class ViewHolder{
        public ImageView menuImg;
        TextView menuTv,contentTv;
        LinearLayout contentLayout, menuGroupLayout,contentMenu;
    }


    @Override
    public int getGroupCount() {
        System.out.println("getGroupCont : " + groupList.size());
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
      //  System.out.println("getChildrenCount : " + childList.get(groupPosition).size());
        return menuList.get(groupPosition).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
       // View menuV = inflater.inflate(R.layout.menu_main, parent, false);

        if(v == null){
            //Toast.makeText(c, "getGroupBView호출 v==null",Toast.LENGTH_LONG).show();
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu_group_list,parent,false);
              viewHolder.menuGroupLayout = (LinearLayout) v.findViewById(R.id.menuGroupLayout);
              viewHolder.menuImg=(ImageView) v.findViewById(R.id.menuImg);
              viewHolder.menuTv=(TextView) v.findViewById(R.id.menuTv);

            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        if(isExpanded){
            viewHolder.menuImg.setBackgroundColor(Color.GREEN);
            String[] arr = menuList.get(groupPosition);
            convertLeng=0;
            length = arr.length;
            System.out.println("menuList Sise ; "+length);
        }else{
            viewHolder.menuImg.setBackgroundColor(Color.RED);
            length=0;

        }

        viewHolder.menuTv.setText((String)getGroup(groupPosition));

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v  = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu_content, null);
            viewHolder.contentLayout = (LinearLayout) v.findViewById(R.id.contentLayout);
            viewHolder.contentTv = (TextView)v.findViewById(R.id.contentTv);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

            if(convertLeng<length) {
                viewHolder.contentTv.setText(menuList.get(groupPosition)[convertLeng++].toString());
            }

        return v;    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public int getMenuListSize(int groupPosition){
        return menuList.get(groupPosition).length;
    }
    public String[] getMenuList(int groupPosition){
        return menuList.get(groupPosition);
    }

    public void childListChange( ArrayList<ArrayList<LinearLayout>> childList){
        this.childList = childList;
    }


}

