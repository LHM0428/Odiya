package nhm.com.odiya.login;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import nhm.com.odiya.DTO.UserDTO;
import nhm.com.odiya.R;
import nhm.com.odiya.menu.MenuActivity;
import nhm.com.odiya.sqlite.DBManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_ANOTHER=1001;
    EditText idEdit, passEdit;
    Button loginBtn, joinBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);

        idEdit = (EditText) findViewById(R.id.idEdit);
        passEdit = (EditText) findViewById(R.id.passEdit);

        loginBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //System.out.println("onClick이심2");

        if (v.getId() == loginBtn.getId()) {
            //Toast.makeText(v.getContext(), "으하?로그인 ㅂ튼", Toast.LENGTH_LONG).show();
            String id = idEdit.getText().toString();
            String pass = passEdit.getText().toString();

            DBManager dbManager = new DBManager(this);

           UserDTO userDTO = dbManager.printData(id,pass);

            if(userDTO!=null){ //사용자가 있다고 하면?
                Intent intent = new Intent(this,MenuActivity.class);
                intent.putExtra("userDTO",  userDTO);
                this.startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"사용자 정보가 일치하지 않습니다",Toast.LENGTH_LONG);
            }
        }
        else if(v.getId() == joinBtn.getId()){
            intent = new Intent(this, JoinActivity.class);
            startActivityForResult(intent,REQUEST_CODE_ANOTHER);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);

        if(resultCode==REQUEST_CODE_ANOTHER  ){
            Toast.makeText(getApplicationContext(),"REQUEST_CODE_ANOTHER",Toast.LENGTH_LONG).show();
        }else if (resultCode==RESULT_OK){
           // Toast.makeText(getApplicationContext(),"REQUEST_CODE_ANOTHER",Toast.LENGTH_LONG).show();
            String idSetText = intent.getExtras().getString("id");
            idEdit.setText(idSetText);
            idEdit.setFocusable(true);

        }


    }

}
