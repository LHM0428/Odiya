package nhm.com.odiya.login;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nhm.com.odiya.R;
import nhm.com.odiya.menu.MenuActivity;
import nhm.com.odiya.sqlite.DBManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText idEdit, passEdit;
    Button loginBtn, joinBtn;

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
        System.out.println("onClick이심2");

        if (v.getId() == loginBtn.getId()) {
            //Toast.makeText(v.getContext(), "으하?로그인 ㅂ튼", Toast.LENGTH_LONG).show();

            String id = idEdit.toString();
            String pass = passEdit.toString();

            String[] args = {id,pass};


           DBManager dbManager = new DBManager(this);


            String result = dbManager.printData();
            Toast.makeText(v.getContext(), result, Toast.LENGTH_LONG).show();

           Intent intent = new Intent(this,MenuActivity.class);
            this.startActivity(intent);

            //sql 모델을 호출!!한다는것?
        }

    }
}
