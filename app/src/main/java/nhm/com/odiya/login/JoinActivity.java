package nhm.com.odiya.login;

import android.app.RemoteInput;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import nhm.com.odiya.DTO.UserDTO;
import nhm.com.odiya.R;
import nhm.com.odiya.sqlite.DBManager;

public class JoinActivity extends AppCompatActivity {

    EditText idEdit, passEdit, passReEdit, telEdit, birthEdit;
    TextView idResult, passResult, telResult, birthResult;
    Button joinBtn, cancelBtn,duplicateBtn;
    RadioGroup radioGroup;
    Intent intent;
    int dupliBtnCheck=1,genderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_join);

        idEdit = (EditText) findViewById(R.id.idEdit);
        passEdit = (EditText) findViewById(R.id.passEdit);
        passReEdit = (EditText) findViewById(R.id.passReEdit);
        telEdit = (EditText) findViewById(R.id.telEdit);
        birthEdit = (EditText) findViewById(R.id.birthEdit);

        idResult = (TextView) findViewById(R.id.idResult);
        passResult = (TextView) findViewById(R.id.passResult);
        telResult = (TextView) findViewById(R.id.telResult);
        birthResult = (TextView) findViewById(R.id.birthResult);

        duplicateBtn = (Button) findViewById(R.id.dupliBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        joinBtn.setOnClickListener(new View.OnClickListener() { //join 버튼 클릭 회원가입시
            @Override
            public void onClick(View v) {
                UserDTO userDTO = new UserDTO();

                userDTO.setId(idEdit.getText().toString());
                userDTO.setPass(passEdit.getText().toString());
                userDTO.setTel(telEdit.getText().toString());
                userDTO.setBirth(birthEdit.getText().toString());
                  genderId = radioGroup.getCheckedRadioButtonId();
                  RadioButton gender = (RadioButton) findViewById(genderId);
                userDTO.setGender(gender.getText().toString());
                DBManager dbManager = new DBManager(getApplicationContext());
                 dbManager.insertUser(userDTO);

                intent = new Intent();
                intent.putExtra("id",idEdit.getText().toString());

                setResult(RESULT_OK,intent);
                finish();
            }
        });



        idEdit.setFocusable(true);

        duplicateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duplicateBtn.setEnabled(false); //사용할 수 있으면 꺼놓음
                dupliBtnCheck=0;
            }
        });
        idEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (dupliBtnCheck == 1) { //중복확인을 하지 않은 경우
                        idResult.setText("중복확인을 하세요");
                        //Toast.makeText(getApplicationContext(), "중복확인을 하세요", Toast.LENGTH_LONG).show();
                    }
                    /*else if(){
                        유효하지 않은 아이디를 선택한 경우
                    }*/
                }

            }
        });

        passEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!(passReEdit.getText().toString().length()>0)){ //비밀번호 확인 없는 경우
                        passResult.setText("비밀번호 확인을 입력하세요");
                        passReEdit.setFocusable(true);
                    }
                 }
            }
        });



        passReEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){ //비밀번호 확인에 포커스가 없는 경우

                    if(passReEdit.getText().toString().length()>0) {
                        String pass = passEdit.getText().toString();
                        String passRe = passReEdit.getText().toString();
                        //System.out.println("pass : "+ pass +" , passRe : "+passRe);
                       // System.out.println("이퀄쯔 "+pass.equals(passRe));
                        if(!pass.equals(passRe))
                        {
                            passResult.setText("비밀번호가 일치하지 않습니다");
                            passReEdit.setText("");
                            passReEdit.setFocusable(true);
                        }
                        passResult.setText("");
                    }



                }else{//focus가 들어온경우
                    if(!(passEdit.getText().toString().length()>0)){ //비밀번호가 입력되지 않은 경우
                        passResult.setText("비밀번호를 입력하세요");
                        passEdit.setFocusable(true);
                    }
                }
            }
        });




    }
}