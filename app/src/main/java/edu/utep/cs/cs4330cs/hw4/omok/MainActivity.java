package edu.utep.cs.cs4330cs.hw4.omok;

/**Main Activity class,serves as the control for our Omok
 *application. Main Activity also handles all action events that
 *may accrue durning the game. Such as when a player selects an
 * intersection on the board or when the player wishes to create a new game.
 *In addition MainActivity determines whether a winner or draw as
 *be declared to end the game and display a sound effects.
 *@authors: Juan Razo and Genesis Bejarano.
 */
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/** Test the BoardView class. */
public class MainActivity extends AppCompatActivity implements Game_Mode_Fragment.GameModeListener {
    //Declaring listeners to the UI components
    Button human;
    Button comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_type);
        //Reference Main Menu UI components
        //Set Main menu button listeners
        human = (Button) findViewById(R.id.humanButton);
            human.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent();
                    a = new Intent(getApplicationContext(), HumanVsHuman.class);
                    startActivity(a);
                }
            });

        comp = (Button) findViewById(R.id.compButton);
            comp.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Dynamically display GameMode fragment
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    //Replace the contents of the container with the new fragment
                    ft.replace(R.id.placeholder,new Game_Mode_Fragment());
                    //Complete the changes added above
                    ft.commit();
                }
            });
        }

    //Called when user has selected a computer mode
    public void setMode(int level) {
        Intent b = new Intent();
        b = new Intent(getApplicationContext(), HumanVsComputer.class);
        b.putExtra("mode",level);
        startActivity(b);
    }

}