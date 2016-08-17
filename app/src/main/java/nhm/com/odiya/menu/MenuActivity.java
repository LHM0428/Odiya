package nhm.com.odiya.menu;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

import nhm.com.odiya.DTO.UserDTO;
import nhm.com.odiya.R;
import nhm.com.odiya.adapter.MenuExpadableAdapter;
import nhm.com.odiya.login.LoginActivity;

public class MenuActivity extends AppCompatActivity  {

    TextView menuCoffee,menuBeverage, menuFlatccino, menuAde, menuTea, menuBubble, menuShake,menuTv,textView;
    LinearLayout coffeeLayout,beverageLayout,adeLayout;
    LayoutInflater inflater = null;
    Button oderBtn;

    LinearLayout contentLayout;


    private ArrayList<String> groupList=null;
    private ArrayList<String[]> menuList = null;
    private Object[] childList=null;
    private ArrayList<CheckBox> childListContent=null;
    private ExpandableListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_expand);

        //intent = getIntent();
       // UserDTO userDTO = (UserDTO) intent.getSerializableExtra("userDTO");

        setLayout();


        inflater = LayoutInflater.from(this);


        oderBtn = (Button) findViewById(R.id.oderBtn);


        groupList = new ArrayList<String>();
        menuList = new ArrayList<String[]>();
        childList = new Object[7];
        childListContent = new ArrayList<CheckBox>();

        groupList.add("Coffee");
        childListContent = new ArrayList<CheckBox>();
        String[] coffeeMenu = {"아메리카노", "카페라떼", "카푸치노", "카페모카", "캬라멜 마끼아또", "바닐라 라떼", "화이트 초콜릿 모카", "민트 모카", "시나몬 모카"};
        for (int i = 0; i < coffeeMenu.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            String name = coffeeMenu[i];
            checkBox.setText(name);
            childListContent.add(checkBox);
        }
        childList[0] = childListContent;
        menuList.add(coffeeMenu);


        groupList.add("Beverage");
        childListContent = new ArrayList<CheckBox>();
        String[] beverageMenu = {"복숭아 아이스티", "레몬 아이스티", "차이 티 라떼", "녹차 라떼", "이곡 라떼", "토피 넛 라떼", "초콜릿", "화이트 초콜릿", "민트 초콜릿", "시나몬 초콜릿"};
        for (int i = 0; i < beverageMenu.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            String name = beverageMenu[i];
            checkBox.setText(name);
            childListContent.add(checkBox);
        }
        childList[1] = childListContent;
        menuList.add(beverageMenu);


        groupList.add("Flatcchino");
        childListContent = new ArrayList<CheckBox>();
        final String[] flatcchinoMenu = {"커피 플랫치노", "모카 플랫치노", "카라멜 플랫치노", "망고 플랫치노", "자몽 플랫치노", "유자 플랫치노", "그린애플 플랫치노",
                "녹차 플랫치노", "초콜릿 칩 플랫치노", "민트 초콜릿 칩 플랫치노", "플레인 요거트 플랫치노", "블루베리 요거트 플랫치노", "딸기 요거트 플랫치노",
                "녹차 요거트 플랫치노", "그린애플 요거트 플랫치노"};
        for (int i = 0; i < flatcchinoMenu.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            String name = flatcchinoMenu[i];
            checkBox.setText(name);
            childListContent.add(checkBox);
        }
        childList[2] = childListContent;
        menuList.add(flatcchinoMenu);


        groupList.add("BubbleTea");
        childListContent = new ArrayList<CheckBox>();
        String[] bubbleMenu = {"밀크 버블티", "타로 버블티", "초코 버블티", "토피넛 버블티", "카페 버블티"};
        for (int i = 0; i < bubbleMenu.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            String name = bubbleMenu[i];
            checkBox.setText(name);
            childListContent.add(checkBox);
        }
        childList[3] = childListContent;
        menuList.add(bubbleMenu);

        groupList.add("Shake");
        childListContent = new ArrayList<CheckBox>();
        String[] shakeMenu = {"오리진 쉐이크", "초코쿠키 쉐이크", "커피 쉐이크", "바나나 쉐이크", "딸기 쉐이크", "밀크티 쉐이크"};
        for (int i = 0; i < shakeMenu.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            String name = shakeMenu[i];
            checkBox.setText(name);
            childListContent.add(checkBox);
        }
        childList[4] = childListContent;
        menuList.add(shakeMenu);


        groupList.add("Ade");
        childListContent = new ArrayList<CheckBox>();
        String[] adeMenu = {"레몬 에이드", "블루레몬 에이드", "자몽에이드"};
        for (int i = 0; i < adeMenu.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            String name = adeMenu[i];
            checkBox.setText(name);
            childListContent.add(checkBox);
        }
        childList[5] = childListContent;
        menuList.add(adeMenu);

        groupList.add("Tea");
        childListContent = new ArrayList<CheckBox>();
        String[] teaMenu = {"어린잎 녹차", "얼그레이 홍차", "로즈 쟈스민 티", "캐모마일 레드티",
                "페퍼민트 티", "밀크 티", "유자 뷰티", "레몬 뷰티", "자몽 뷰티"};
        for (int i = 0; i < teaMenu.length; i++) {
            CheckBox checkBox = new CheckBox(this);
            String name = teaMenu[i];
            checkBox.setText(name);
            childListContent.add(checkBox);
        }
        childList[6] = childListContent;
        menuList.add(teaMenu);

        final MenuExpadableAdapter adapter = new MenuExpadableAdapter(this, groupList, childList, menuList);
        listView.setAdapter(adapter);


        oderBtn = (Button) findViewById(R.id.oderBtn); //주문하기 버튼 클릭
        oderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ArrayList<String> nameListArr = (ArrayList<String>) adapter.getMenuNameList();
                        if(nameListArr!=null) {
                           if(nameListArr.size()>0) {
                               Intent intent = new Intent(getApplicationContext(), MenuProfileActivity.class);
                               intent.putExtra("orderMenuList",nameListArr);
                               startActivity(intent);
                           }
                        }
            }
        });

    }
    private void setLayout(){
        listView = (ExpandableListView) findViewById(R.id.menuElv);
    }

}
