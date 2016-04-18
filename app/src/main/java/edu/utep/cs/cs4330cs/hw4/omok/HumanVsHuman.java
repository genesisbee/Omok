package edu.utep.cs.cs4330cs.hw4.omok;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //game UI components referenced
        currentPlayer = (TextView) findViewById(R.id.current_player);
        boardView = (BoardView) findViewById(R.id.boardView);
        resetButton = (Button) findViewById(R.id.reset);
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
}