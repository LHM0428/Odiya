package nhm.com.odiya.menu;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

public class MenuActivity extends AppCompatActivity  {

    TextView menuCoffee,menuBeverage, menuFlatccino, menuAde, menuTea, menuBubble, menuShake,menuTv,textView;
    LinearLayout coffeeLayout,beverageLayout,adeLayout;
    LayoutInflater inflater = null;

    LinearLayout contentLayout;


    private ArrayList<String> groupList=null;
    private ArrayList<String[]> menuList = null;
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


        coffeeLayout = (LinearLayout) findViewById(R.id.coffeeLayout);
        beverageLayout = (LinearLayout) findViewById(R.id.beverageLayout);
        adeLayout = (LinearLayout) findViewById(R.id.adeLayout);

        //menuTv = (LinearLayout) findViewById(R.id.menuTv);

        inflater = LayoutInflater.from(this);

        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        groupList = new ArrayList<String>();
        menuList = new ArrayList<String[]>();
        childList = new ArrayList<ArrayList<LinearLayout>>();
        childListContent = new ArrayList<LinearLayout>();

        groupList.add("Coffee");
        String[] coffeeMenu = {"아메리카노","카페라떼","카푸치노","카페모카","캬라멜 마끼아또","바닐라 라떼","화이트 초콜릿 모카","민트 모카","시나몬 모카"};
        for(int i=0;i<coffeeMenu.length;i++){
            childListContent.add(contentLayout);
        }
        childList.add(childListContent);
        menuList.add(coffeeMenu);


        groupList.add("Beverage");
        String[] beverageMenu = {"복숭아 아이스티","레몬 아이스티","차이 티 라떼","녹차 라떼","이곡 라떼","토피 넛 라떼","초콜릿","화이트 초콜릿","민트 초콜릿","시나몬 초콜릿"};
        for(int i=0;i<beverageMenu.length;i++){
            childListContent.add(contentLayout);
        }
        childList.add(childListContent);
        menuList.add(beverageMenu);


        groupList.add("Flatccino");
        String[] flatcchinoMenu = {"커피 플랫치노","모카 플랫치노","카라멜 플랫치노","망고 플랫치노","자몽 플랫치노","유자 플랫치노","그린애플 플랫치노",
        "녹차 플랫치노","초콜릿 칩 플랫치노","민트 초콜릿 칩 플랫치노","플레인 요거트 플랫치노","블루베리 요거트 플랫치노","딸기 요거트 플랫치노",
                "녹차 요거트 플랫치노","그린애플 요거트 플랫치노"};
        for(int i=0;i<flatcchinoMenu.length;i++){
            childListContent.add(contentLayout);
        }
        childList.add(childListContent);
        menuList.add(flatcchinoMenu);


        groupList.add("BubbleTea");
        String[] bubbleMenu = {"밀크 버블티","밀크 버블티","초코 버블티","토피넛 버블티","카페 버블티"};
        for(int i=0;i<bubbleMenu.length;i++){
            childListContent.add(contentLayout);
        }
        childList.add(childListContent);
        menuList.add(bubbleMenu);

        groupList.add("Shake");
        String[] shakeMenu = {"오리진 쉐이크","초코쿠키 쉐이크","커피 쉐이크","바나나 쉐이크","딸기 쉐이크","밀크티 쉐이크"};
        for(int i=0;i<shakeMenu.length;i++){
            childListContent.add(contentLayout);
        }
        childList.add(childListContent);
        menuList.add(shakeMenu);


        groupList.add("Ade");
        String[] adeMenu = {"레몬 에이드","블루레몬 에이드","자몽에이드"};
        for(int i=0;i<adeMenu.length;i++){
            childListContent.add(contentLayout);
        }
        childList.add(childListContent);
        menuList.add(adeMenu);

        groupList.add("Tea");
        String[] teaMenu = {"어린잎 녹차","얼그레이 홍차","로즈 쟈스민 티","캐모마일 레드티",
                "페퍼민트 티","밀크 티","유자 뷰티","레몬 뷰티","자몽 뷰티"};
        for(int i=0;i<teaMenu.length;i++){
            childListContent.add(contentLayout);
        }
        childList.add(childListContent);
        menuList.add(teaMenu);



       /* childListContent.add(contentLayout);
        childListContent.add(contentLayout);
        childListContent.add(contentLayout);
        childListContent.add(contentLayout);
        childListContent.add(contentLayout);
        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);
        childList.add(childListContent);*/


        listView.setAdapter(new MenuExpadableAdapter(this, groupList, childList,menuList));

       // listView.set

    }

    private void setLayout(){
        listView = (ExpandableListView) findViewById(R.id.menuElv);
    }

}
