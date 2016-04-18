package edu.utep.cs.cs4330cs.hw4.omok;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HumanVsComputer extends AppCompatActivity {
    int gameMode; // 0 is random | 1 is smart.
    public Board board = new Board();
    public Button resetButton;
    public BoardView boardView;
    TextView currentPlayer;
    String winner;


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
        startGame();
    }

  public void startGame() {
      //HUMANS TURN
      //Add listener to boardView
      boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
          @Override
          public void onTouch(int x, int y) {
              Log.d("Human Mode x: ", Integer.toString(x));
              Log.d("Human Mode y: ",Integer.toString(y));
              //Continue placing stones while there is no winner
              if (!foundWinner()) {

                  //Check if a stone can be placed at the current X & Y touched
                  if (setStone(x, y)) {
                      //Check if there is a winner at the last stone placed
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
                      else updateGame();
                      computerMove();
                  }

                  //For invaded user moves
                  else {
                      Toast.makeText(getApplicationContext(), "Invaild move try again", Toast.LENGTH_SHORT).show();
                  }
              }
          }
      });

  }

//-----------------------METHODS--------------------------------------
    //computer methods----
    public Coordinates generateCoordinates() {
        if (gameMode == 0) {
            Log.d("Game mode is ","random");
            RandomCoordinates random = new RandomCoordinates();
            return random.placeToken(board);
        }
        else{
            Log.d("What are","you doing here ;/");
            Smart s = new Smart();

            return null;
        }
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

    public void updateGame(){
        board.flipTurn();
        //Update the next players turn
        currentPlayer.setText(board.getPlayerName());
        //update the view based on the new board
        boardView.currentBoard(board.getPlace());
    }


    public void computerMove(){
        //COMPUTER TURN
        //select a stone based on the computers strategy
        if (!foundWinner()){
            Log.d("Hey im here yay its"," the computers turn!");
            Coordinates coor = generateCoordinates();
            int x = coor.getX();
            int y = coor.getY();
            Log.d("Computer Mode x: ", Integer.toString(x));
            Log.d("Computer Mode y: ",Integer.toString(y));
            //Check if a stone can be placed at the current X & Y touched
            if (setStone(x, y)) {
                Log.d("Computer mode set ", "the stone!");
                //Check if there is a winner at the last stone placed
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
                else {
                    updateGame();

                }
            }
            else{
                Log.d("Aw man","didnt place the stone!");
            }
            startGame();
        }
    }
}

