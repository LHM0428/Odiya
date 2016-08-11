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
import android.widget.ViewFlipper;

import java.util.ArrayList;

import nhm.com.odiya.R;
import nhm.com.odiya.login.LoginActivity;
import nhm.com.odiya.adapter.BaseExpandableAdapter;

public class MainActivity extends AppCompatActivity {

    ViewFlipper vf_event;
    TableLayout tb_coupon;
    Button bt_coupon;
    boolean IsClosed = true;
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
 /*
        tb_coupon = (TableLayout) findViewById(R.id.tb_coupon);
        bt_coupon = (Button) findViewById(R.id.bt_coupon);
        tb_coupon.setVisibility(View.INVISIBLE);

       bt_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsClosed){
                    tb_coupon.setVisibility(View.VISIBLE);
                    IsClosed = false;
                }else{
                    tb_coupon.setVisibility(View.INVISIBLE);
                    IsClosed = true;
                }
            }
        });*/
        inItElv(6);
    }


    public void inItElv(final int j) {
        mListView = (ExpandableListView) findViewById(R.id.elv_coupon);
        mGroupList = new ArrayList<String>();
        gv_child = (GridView) findViewById(R.id.gv_child);
        mGroupList.add("***님의 쿠폰 : "+j+" 장");


        mListView.setAdapter(new BaseExpandableAdapter(mGroupList,gv_child, j, this));
    }

    public void loginClicked(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("하아아이",true);
        this.startActivity(intent);
    }
}
