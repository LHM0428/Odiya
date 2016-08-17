package nhm.com.odiya.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nhm.com.odiya.R;

/**
 * Created by 이남흔 on 2016-08-11.
 */
public class MenuExpadableAdapter extends BaseExpandableListAdapter{

    private ArrayList<String> groupList = null;
    private Object[][] menuParent = null;
    private ArrayList<String> menuChild = null;

    private ArrayList<String> menuNameList = null;
    private Object[] childList = null;
    private LayoutInflater inflater = null;
    private Context c;
    private ArrayList<String[]> menuList=null;
    private ViewHolder viewHolder = null;

    public MenuExpadableAdapter(Context c, ArrayList<String> groupList, Object[] childList, ArrayList<String[]> menuList) {
        super();
        this.c = c;
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
        this.menuList = menuList;
        menuParent = new String[groupList.size()][15];
    }
    class ViewHolder{
        public ImageView menuImg;
        TextView menuTv,contentTv;
        LinearLayout contentLayout, menuGroupLayout,contentMenu;
        CheckBox checkBox;
    }


    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<CheckBox> arr =  (ArrayList<CheckBox>) childList[groupPosition];
        System.out.println("사이즈 : "+ menuList.get(groupPosition).length);
        return menuList.get(groupPosition).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<CheckBox> arr =  (ArrayList<CheckBox>)childList[groupPosition];

        return arr.get(childPosition);
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

        if(v == null){
            //Toast.makeText(c, "getGroupBView호출 v==null",Toast.LENGTH_LONG).show();
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu_group_list,parent,false);
            viewHolder.menuTv = (TextView) v.findViewById(R.id.menuTv);
            viewHolder.menuImg = (ImageView) v.findViewById(R.id.menuProImg);


            v.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.menuTv.setText(groupList.get(groupPosition));


        if(isExpanded){
            viewHolder.menuImg.setBackgroundColor(Color.BLUE);
        }else{
            viewHolder.menuImg.setBackgroundColor(Color.WHITE);
        }

        return v;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, final View convertView, final ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.menu_content, null);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.checkBox = (CheckBox) v.findViewById(R.id.checkBox);


          if(menuNameList==null){
              menuNameList = new ArrayList<String>();
           }

        String[] arr = menuList.get(groupPosition);
        viewHolder.checkBox.setText(arr[childPosition]+"");

        if(menuParent[groupPosition][childPosition]!=null) {
            int i = Integer.parseInt((String) menuParent[groupPosition][childPosition]);
            if (i == 1) {
                viewHolder.checkBox.setChecked(true);
            }else {
                viewHolder.checkBox.setChecked(false);
            }
        }else{
            viewHolder.checkBox.setChecked(false);
        }


       viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   CheckBox check = (CheckBox) v;
                   String name =  menuList.get(groupPosition)[childPosition];
                   if(check==null){
                       return;
                   }
                   if(menuParent[groupPosition][childPosition]==null) {
                       menuParent[groupPosition][childPosition]=1+"";
                   }

                   if(check.isChecked()){
                       menuNameList.add(name);
                       menuParent[groupPosition][childPosition]=1+"";
                       Toast.makeText(c, name + " 선택", Toast.LENGTH_LONG).show();

                   }else{
                       menuParent[groupPosition][childPosition] = 0 + "";
                       for (int i = 0; i < menuNameList.size(); i++) {
                           if (menuNameList.get(i).equals(name)) {
                               menuNameList.remove(i);
                               Toast.makeText(c, name + "해제", Toast.LENGTH_LONG).show();
                           }
                       }
                   }

               }
           });
    return v;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void checkClicked(View v){


        System.out.println("옹클릭");
    }

    public ArrayList<String> getMenuNameList(){
        return menuNameList;
    }


}

