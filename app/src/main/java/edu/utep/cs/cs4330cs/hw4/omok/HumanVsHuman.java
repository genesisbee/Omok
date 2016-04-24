package edu.utep.cs.cs4330cs.hw4.omok;

import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HumanVsHuman extends AppCompatActivity {
    //game view UI components
    public Board board = new Board();
    public Button resetButton;
    public BoardView boardView;
    TextView currentPlayer;
    //Sounds
    SoundPool soundPool;
    AudioAttributes aa;
    int clickID;
    int winnerID;
    int loserID;
    boolean loaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //game UI components referenced
        currentPlayer = (TextView) findViewById(R.id.current_player);
        boardView = (BoardView) findViewById(R.id.boardView);
        resetButton = (Button) findViewById(R.id.reset);
        //SOUNDS
        //Set stone audio
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

        startGame();
    }

    public void startGame() {
        //Add listener to boardView
        boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                if (!board.foundWinner()) {
                    //Check if a stone can be placed at the current X & Y touched
                    if (board.setStone(x, y, board.getPlayerTurn())) {
                        //audio for setting the stone
                        //Play set stone sound
                        soundPool.play(clickID, 1, 1, 1, 0, 2f);
                        //Check if there is a winner at the last stone placed
                        if (board.winner(x, y, board.getPlayerTurn())) {
                            String winner = board.getPlayerName();
                            //play winner audio
                            soundPool.play(winnerID, 1, 1, 1, 0, 1);
                            // display the text for the winning player
                            Toast.makeText(getApplicationContext(), "Player " + winner +
                                    " has won!", Toast.LENGTH_LONG).show();
                            board.increaseScore(board.getPlayerName());
                        }
                        //Check for a draw and display the message
                        else if (board.gameDraw()) {
                            //play lost audio
                            soundPool.play(loserID, 1, 1, 1, 0, 1);
                            Toast.makeText(getApplicationContext(), "Draw",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //If there is no winner flip the player
                        else {
                            board.flipTurn();
                            //Update the next players turn
                            currentPlayer.setText(board.getPlayerName());
                            //update the view based on the new board
                            boardView.currentBoard(board.getPlace());
                        }
                    }
                }
            }
        });
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
        switch (item.getItemId()) {
            //customize board
            case R.id.customOptions: {
             switch (){

             }
            }
        }
        return false;
    }
}