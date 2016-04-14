package edu.utep.cs.cs4330cs.hw4.omok;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class Player extends AppCompatActivity{
    //Declaring boardGame UI components
    public Board board;
    public Button resetButton;
    public BoardView boardView;

    /*All players are able to set a stone to the Omok Game Board
     */
    public void setStone(){}
}