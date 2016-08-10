package nhm.com.odiya.menu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import nhm.com.odiya.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    TextView menuCoffee,menuBeverage, menuFlatccino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);


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
