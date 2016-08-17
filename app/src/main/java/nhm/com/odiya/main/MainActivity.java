package nhm.com.odiya.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import nhm.com.odiya.R;
import nhm.com.odiya.cafeFind.CafeFindActivity;
import nhm.com.odiya.login.LoginActivity;
import nhm.com.odiya.adapter.BaseExpandableAdapter;
import nhm.com.odiya.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {

    ViewFlipper vf_event;
    private ArrayList<String> mGroupList = null;
    private ExpandableListView mListView;
    private GridView gv_child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        vf_event = (ViewFlipper) findViewById(R.id.vf_event);
        System.out.println("vf_event : "+vf_event);
        //Java 코드로도 ViewFlipper에 image를 넣을 수 있다.
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.event_3);
        vf_event.addView(img,2);

        //ViewFlipper의 이미지 변경 Animation을 설정
        // [ 왼쪽으로 슬라이딩 되며 들어온다 ]
        Animation showIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        vf_event.setInAnimation(showIn);
        // [ 오른쪽으로 슬라이딩 되며 퇴장한다 ]
        vf_event.setOutAnimation(this, android.R.anim.slide_out_right);
        // 1.5초 간격으로 ViewFilpper의 view를 자동 교체
        vf_event.setFlipInterval(1500);
        vf_event.startFlipping();
        inItElv(6);
    }


    public void inItElv(final int j) {
        mListView = (ExpandableListView) findViewById(R.id.elv_coupon);
        mGroupList = new ArrayList<String>();
        gv_child = (GridView) findViewById(R.id.gv_child);
        mGroupList.add("***님의 쿠폰 : "+j+" 장");


        mListView.setAdapter(new BaseExpandableAdapter(mGroupList,gv_child, j, this));
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void loginClicked(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("하아아이",true);
        this.startActivity(intent);
    }
    public void menuClicked(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
    }

    public void cafeFindClicked(View v){
        Intent intent = new Intent(this, CafeFindActivity.class);
        this.startActivity(intent);
    }
}
