package edu.utep.cs.cs4330cs.hw4.omok;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class HumanVsComputer extends AppCompatActivity {
    int gameMode; // 0 is random | 1 is smart.
    public Board board = new Board();
    public Button resetButton;
    public BoardView boardView;
    TextView currentPlayer;
    String winner;
    //Sounds
    SoundPool soundPool;
    AudioAttributes aa;
    int clickID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extra = getIntent().getExtras();
        gameMode = extra.getInt("mode");
        //REFERENCE UI COMPONENTS
        currentPlayer = (TextView) findViewById(R.id.current_player);
        boardView = (BoardView) findViewById(R.id.boardView);
        resetButton = (Button) findViewById(R.id.reset);
        //SOUNDS
        //Set stone audio
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            aa= new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(100)
                    .setAudioAttributes(aa)
                    .build();
            clickID = soundPool.load(this, R.raw.click,1);
        }
        else{
            soundPool = new SoundPool(100,AudioManager.STREAM_MUSIC,1);
            clickID = soundPool.load(this, R.raw.click,1);
        }

        startGame();
    }

  public void startGame() {
      boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
          @Override
          public void onTouch(int x, int y) {
              if (!board.foundWinner()) {
                  //Check if a stone can be placed at the current X & Y touched
                  if (board.setStone(x, y, board.getPlayerTurn())) {
                        //Play set stone sound
                        soundPool.play(clickID,1,1,1,0,1);

                      //Check if there is a winner at the last stone placed
                      if (board.winner(x, y, board.getPlayerTurn())) {
                          String winner = board.getPlayerName();

                          // display the text for the winning player
                          Toast.makeText(getApplicationContext(), "Player " + winner +
                                  " has won!", Toast.LENGTH_LONG).show();
                          board.increaseScore(board.getPlayerName());
                      }
                      //Check for a draw and display the message
                      else if (board.gameDraw()) {
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
                          computerMove();
                      }
                  }
              }
          }
      });
  }
    //COMPUTER'S TURN
    public void computerMove(){
        Log.d("COMPUTER: ",board.getPlayerName());
        //COMPUTER TURN
        //select a stone based on the computers strategy
        if (!foundWinner()) {
            //Check if a stone can be placed at the current X & Y touched
            Coordinates coor = generateCoordinates();
            int x = coor.getX();
            int y = coor.getY();
            //Play set stone sound
            soundPool.play(clickID,1,1,1,0,1);
            if (winner(x, y)) {
                winner = board.getPlayerName();
                // display the text for the winning player
                displayWinner();
                //increasePlayerScore
                //board.increaseScore(board.getPlayerName());
            }
            else if (board.gameDraw()) {
                displayDraw();
            }
            else{
                board.flipTurn();
                //Update the next players turn
                currentPlayer.setText(board.getPlayerName());
                //update the view based on the new board
                boardView.currentBoard(board.getPlace());
            }
        }
    }

//-----------------------METHODS--------------------------------------
    //computer methods----
    public Coordinates generateCoordinates() {
        if (gameMode == 0) {
            RandomCoordinates random = new RandomCoordinates();
            return random.placeToken(board);
        }
//        else{
//            Smart s = new Smart();
           return null;
//        }
    }

    //--------------------------game methods---

    public boolean foundWinner(){
        return board.foundWinner();
    }

    public boolean winner(int x, int y){
        return board.winner(x,y,board.getPlayerTurn());
    }

    public void displayWinner(){
        Toast.makeText(getApplicationContext(), "Player " + winner + " has won!", Toast.LENGTH_LONG).show();
    }

    public void displayDraw(){
        Toast.makeText(getApplicationContext(), "Draw", Toast.LENGTH_SHORT).show();
    }

    public boolean setStone(int x, int y){return board.setStone(x,y,board.getPlayerTurn());}

    public void playerSwitch(){
        board.flipTurn();
        //Update the next players turn
        currentPlayer.setText(board.getPlayerName());
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
}

