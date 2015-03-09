package jokse.lektion2;

import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements  View.OnClickListener {
    private Button ok;
    private TextView word,usedword, endText;
    private EditText bogstav;
    private ImageView IM;
    int wins;
    public static Galgelogik game = new Galgelogik();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bogstav = (EditText) findViewById(R.id.editText);
        word = (TextView) findViewById(R.id.word);
        usedword = (TextView) findViewById(R.id.usedword);
        endText = (TextView) findViewById(R.id.endText);
        ok = (Button) findViewById(R.id.ok);
        IM = (ImageView) findViewById(R.id.imageView);
        ok.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUp();

    }

    public void setUp(){
        word.setText("Ord: " + game.getSynligtOrd());
        usedword.setText("Forsøg: " + game.getBrugteBogstaver());
        boolean igang = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("igang", false);
        boolean fixOrd = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("fixOrd", false);
        if(!igang || fixOrd){
            nulstil();
        }

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
            Intent setup = new Intent(MainActivity.this,Indstillinger.class );
            startActivity(setup);
            return true;

        }else if(id == R.id.select){
            Intent list = new Intent(MainActivity.this,List.class );
            list.putExtra("word",game.muligeOrd);
            startActivity(list);
            System.out.println("Lukker menu");

            return true;
        }else if(id == R.id.restart){
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putBoolean("igang",false)
                    .commit();
            setUp();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }public void nulstil(){
        boolean brug = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("brugDR", true);
        wins = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("wins", 0);
        game = new Galgelogik();
        boolean fixOrd = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("fixOrd", false);
        if(fixOrd){
            game.setOrd(PreferenceManager.getDefaultSharedPreferences(this)
                    .getString("ord", "h"));
            IM.setImageResource(R.drawable.galge);
            word.setText("Ord: " + game.getSynligtOrd());
            usedword.setText("Forsøg: " + game.getBrugteBogstaver());
        }
        else if(brug) {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object... arg0) {
                    try {

                        game.hentOrdFraDr();
                        return "ord blev hentet";
                    } catch (Exception e) {
                        game.nulstil();
                        e.printStackTrace();
                        return e;
                    }
                }

                @Override
                protected void onPostExecute(Object titler) {
                    IM.setImageResource(R.drawable.galge);
                    word.setText("Ord: " + game.getSynligtOrd());
                    usedword.setText("Forsøg: " + game.getBrugteBogstaver());
                }
            }.execute();
        }else{
            game.nulstil();
            IM.setImageResource(R.drawable.galge);
            word.setText("Ord: " + game.getSynligtOrd());
            usedword.setText("Forsøg: " + game.getBrugteBogstaver());
        }
        endText.setText("");
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean("igang",true)
                .putBoolean("fixOrd",false)
                .commit();
    }

    @Override
    public void onClick(View v) {
        if(v == ok && 6 >game.getAntalForkerteBogstaver()){
            game.gætBogstav(""+bogstav.getText());
            bogstav.setText("");
            word.setText("Ord: " + game.getSynligtOrd());
            usedword.setText("Forsøg: " + game.getBrugteBogstaver());
            if(game.getAntalForkerteBogstaver()==1)IM.setImageResource(R.drawable.forkert1);
            if(game.getAntalForkerteBogstaver()==2)IM.setImageResource(R.drawable.forkert2);
            if(game.getAntalForkerteBogstaver()==3)IM.setImageResource(R.drawable.forkert3);
            if(game.getAntalForkerteBogstaver()==4)IM.setImageResource(R.drawable.forkert4);
            if(game.getAntalForkerteBogstaver()==5)IM.setImageResource(R.drawable.forkert5);
            if(game.getAntalForkerteBogstaver()==6){
                IM.setImageResource(R.drawable.forkert6);
                endText.setText("Game Over");
                word.setText("Ord: " + game.getOrdet());
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putBoolean("igang",false)
                        .commit();
            }if(game.erSpilletVundet()){
                wins++;
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putInt("wins", wins)
                        .putBoolean("igang",false)
                        .commit();

                endText.setText("Hurra, du har vundet " +wins + " gange");
            }


        }

    }
}
