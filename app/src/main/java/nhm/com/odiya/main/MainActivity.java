package nhm.com.odiya.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import nhm.com.odiya.R;
import nhm.com.odiya.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void loginClicked(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("하아아이",true);
        this.startActivity(intent);
    }
}
