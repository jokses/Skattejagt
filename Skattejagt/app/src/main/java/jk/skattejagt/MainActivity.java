package jk.skattejagt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {
//public Array al = new Array();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        opgave o1 = new opgave();
        o1.setFindOpgave("find");
        o1.setSolveOpgave("sol");
        o1.setKeyword("key");
        o1.setLocationLat(2.5);
        o1.setLocationLong(2.3);

        //al.add(o1);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "LUNmoIVz27fIl5872ntEL9un4Xa2bkvm4MbD1UBd", "ZKY7SlqYqAScVeRiVDrE3Jp86NR7xpdkyQIiIRRC");
        ParseObject Game = new ParseObject("Game");
        int a =11;
        Game.put("name", "DTU Game");
        Game.put("find", "Find a");
        Game.put("key", "password");
        Game.put("solve", "Du er her nu");
        Game.put("lat", 23);
        Game.put("long", 25);
        Game.put("number", a);
        Game.saveInBackground();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
