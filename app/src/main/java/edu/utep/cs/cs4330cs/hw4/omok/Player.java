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

public class Player{
    Board board = new Board();
    String playerName;
    String stoneColor;
    String playerMark;
    int Score =0;

    Player(String name, String color, String mark){
        playerName= name;
        stoneColor = color;
        playerMark = mark;
    }

    public static int getScore(Player p){
        return 1;
    }

    private String getPlayerMark(){return playerMark;}
    private String getPlayerName(){return playerName;}
    private String getStoneColor(){return stoneColor;}


}