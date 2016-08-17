package nhm.com.odiya.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import nhm.com.odiya.R;

public class MenuProfileActivity extends AppCompatActivity implements View.OnTouchListener{

    RelativeLayout menuProLayout, viewLayout;
    TextView menuProTitle;
    ImageView menuProImg;
    Button cntMinus,cntPlus;
    EditText cntEdit;
    Intent intent;
    ArrayList<String> list;
    int checked=0,count=0,i=0;
    //  Button cntMinus, cntPlus;
  //  EditText cntEdit;
    Spinner menuProSpin1,menuProSpin2;

    //viewFlipper 컴포넌트 객체
    private ViewFlipper viewFlipper;
    //ViewFlipper 안에서 터치된 x축의 좌표값
    private int preTouchPosX=0;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile);

       viewLayout = (RelativeLayout) findViewById(R.id.viewLayout);
       viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setOnTouchListener(this);

        //View Flipper에 서브 레이아웃 추가
        intent = getIntent();
        list = (ArrayList<String>) intent.getSerializableExtra("orderMenuList");

        for( i=0;i<list.size();i++) { //list수 만큼 레이아웃 만들기
            menuProLayout = (RelativeLayout) View.inflate(this, R.layout.menu_profile_content, null);
            menuProTitle = (TextView) menuProLayout.findViewById(R.id.menuProTitle);

            menuProTitle.setText(list.get(i).toString()); //이름 변경하기

            menuProSpin1 = (Spinner) menuProLayout.findViewById(R.id.menuProSpin1);
            menuProSpin2 = (Spinner) menuProLayout.findViewById(R.id.menuProSpin2);

            String[] memoSpin1 = {"사이즈를 선택하세요","Regular(기본)","Extra"}; //메모
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.menu_spin, memoSpin1);
            adapter.setDropDownViewResource(R.layout.menu_spin_dropdown);
            menuProSpin1.setAdapter(adapter);

            String[] memoSpin2 = {"메모를 선택하세요","얼음 많이","얼음 적게","연하게","진하게"}; //메모
            adapter = new ArrayAdapter(getApplicationContext(),R.layout.menu_spin,memoSpin2);
            adapter.setDropDownViewResource(R.layout.menu_spin_dropdown);
            menuProSpin2.setAdapter(adapter);

            cntMinus = (Button) menuProLayout.findViewById(R.id.cntMinus);
            cntPlus = (Button) menuProLayout.findViewById(R.id.cntPlus);

            count = 1;


            cntMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                   // cntEdit = (EditText) v.findViewById(R.id.cntEdit);
                   //System.out.println(cntEdit.getText().toString());
                    if(count>0){
                        count--;
                        ((Button) v).setText(count+"");
                        System.out.println("마이너스 클릭미 크클클릭미"+i);
                    }
                }
            });

            cntPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count<20){
                        count++;
                        System.out.println("플러스 클릭미 크클클릭미"+ i);
                    }
                }
            });

            cntEdit = (EditText) menuProLayout.findViewById(R.id.cntEdit);

            viewFlipper.addView(menuProLayout);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            preTouchPosX = (int) event.getX();
        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            int nTouchPosX = (int)  event.getX();

            if (nTouchPosX < preTouchPosX) {
                if(checked<list.size()-1) {
                    MoveNextView();
                    ++checked;
                }
            } else if (nTouchPosX > preTouchPosX) {
                if(checked>0) {
                    MovewPreviousView();
                    --checked;
                }
            }

            preTouchPosX = nTouchPosX;
        }



        return true;
    }


    private void MoveNextView() {
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.appear_from_right));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.disappear_to_left));
        viewFlipper.showNext();
    }


    private void MovewPreviousView() {
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.appear_from_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.disappear_to_right));
        viewFlipper.showPrevious();
    }


}
