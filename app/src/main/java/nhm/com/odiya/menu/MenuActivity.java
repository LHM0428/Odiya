package nhm.com.odiya.menu;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import nhm.com.odiya.DTO.UserDTO;
import nhm.com.odiya.R;
import nhm.com.odiya.adapter.MenuExpadableAdapter;

public class MenuActivity extends AppCompatActivity  {

    TextView menuCoffee,menuBeverage, menuFlatccino, menuAde, menuTea, menuBubble, menuShake;
    LinearLayout coffeeLayout,beverageLayout,adeLayout;

    LinearLayout contentLayout;


    private ArrayList<String> groupList=null;
    private ArrayList<ArrayList<LinearLayout>> childList=null;
    private ArrayList<LinearLayout> childListContent=null;
    private ExpandableListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_expand);

        Intent intent = getIntent();
        UserDTO userDTO = (UserDTO) intent.getSerializableExtra("userDTO");

        setLayout();


        coffeeLayout= (LinearLayout) findViewById(R.id.coffeeLayout);
        beverageLayout = (LinearLayout) findViewById(R.id.beverageLayout);
        adeLayout = (LinearLayout) findViewById(R.id.adeLayout);

        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        groupList = new ArrayList<String>();
        childList = new ArrayList<ArrayList<LinearLayout>>();
        childListContent = new ArrayList<LinearLayout>();

        groupList.add("Coffee");
        groupList.add("Beverage");
        groupList.add("Ade");

        childListContent.add(contentLayout);

        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);


        listView.setAdapter(new MenuExpadableAdapter(this, groupList, childList));

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getApplicationContext(),"onGroupClick() : "+groupPosition,Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }

    private void setLayout(){
        listView = (ExpandableListView) findViewById(R.id.menuElv);
    }

}
