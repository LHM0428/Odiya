package nhm.com.odiya.menu;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import nhm.com.odiya.DTO.UserDTO;
import nhm.com.odiya.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    TextView menuCoffee,menuBeverage, menuFlatccino, menuAde, menuTea, menuBubble, menuShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);

        Intent intent = getIntent();
        UserDTO userDTO = (UserDTO) intent.getSerializableExtra("userDTO");

        Toast.makeText(getApplicationContext(), "아이디:"+userDTO.getId()+"pass : "+userDTO.getPass() , Toast.LENGTH_SHORT).show();



        menuCoffee = (TextView) findViewById(R.id.menuCoffee);
        menuBeverage = (TextView) findViewById(R.id.menuBeverage);
        menuFlatccino = (TextView) findViewById(R.id.menuFlatccino);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==menuCoffee.getId()){


        }

    }
}
