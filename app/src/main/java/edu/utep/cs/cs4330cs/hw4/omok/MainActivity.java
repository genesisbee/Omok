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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/** Test the BoardView class. */
public class MainActivity extends AppCompatActivity {
    //Declaring listeners to the UI components
    Button human;
    Button comp;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_type);
        //play type UI components referenced
        mainMenuSetUp();
    }

    //Game_Mode_Fragment interface
    public void setMode(int level){
        intent = new Intent(getApplicationContext(),Game.class);
        intent.putExtra("mode",level);
        startActivity(intent);
    }
    public void run(){

    }
    //Main menu button listeners
    public void mainMenuSetUp() {
        human = (Button) findViewById(R.id.humanButton);
        human.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setMode(0);
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
}