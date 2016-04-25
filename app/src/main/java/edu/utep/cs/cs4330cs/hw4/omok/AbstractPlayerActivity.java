package edu.utep.cs.cs4330cs.hw4.omok;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public abstract class AbstractPlayerActivity extends AppCompatActivity {
    //game view UI components
    public Board board = new Board();
    public Button resetButton;
    public BoardView boardView;
    TextView currentPlayer;
    //Sounds
    SoundPool soundPool;
    MediaPlayer mediaPlayer;
    AudioAttributes aa;
    int clickID;
    int winnerID;
    int loserID;

    public abstract void startGame();

    public void setbackgroundMusic(){ mediaPlayer = MediaPlayer.create(this,R.raw.super_mario_bros);}

    public void getbackgroundMusic(){mediaPlayer.start();}

    public void setSoundEffects(){
        //Set stone audio sound effects
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(100)
                    .setAudioAttributes(aa)
                    .build();
            clickID = soundPool.load(this, R.raw.click, 1);
            winnerID = soundPool.load(this, R.raw.winner, 2);
            loserID = soundPool.load(this, R.raw.lost, 3);

        } else {
            soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 1);
            clickID = soundPool.load(this, R.raw.click, 1);
            winnerID = soundPool.load(this, R.raw.winner, 2);
            loserID = soundPool.load(this, R.raw.lost, 3);
        }
    }

    /*
     * Method to confirm a new game when the button New Game is touched
     * This will clear the text where the last winner was displayed and
     * clear the current board as well update the view based on the new board*/
    public void resetButton(View view) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to start a new Game");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                board.eraseBoard();
                boardView.currentBoard(board.getPlace());
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    @Override
    protected  void onPause(){
        super.onPause();
        mediaPlayer.release();
    }
    //Menu code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Customize
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //menu main option items
        switch (item.getItemId()) {
            //customize board
            case R.id.theme1:{
                //set board background
                boardView.usingBitMap = true;
                boardView.backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.gb1);
                boardView.currentBoard(board.getPlace());
                //set music background
                getbackgroundMusic();
                //set stones
                boardView.playerOneStone = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
                boardView.playerTwoStone = BitmapFactory.decodeResource(getResources(), R.drawable.luigi);
                break;
            }
        }
        return false;
    }

}
