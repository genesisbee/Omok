package edu.utep.cs.cs4330cs.hw4.omok;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

/*sets all the actions a human player can do
* */
public class Human extends Player{
    Button restButton;

    public void onCreate(){
        resetButton = (Button) findViewById(R.id.reset);
    }

    public void setStone() {
        boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                //Vaild turn
                board.setStone(x, y, board.getPlayerTurn());
            }
        });
    }
    /*resentButton a method used  to confirm if new game has been requested
     *when the user touched the "New Game" button.
     * This will clear the current board*/
    public void restartGame(View view){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
    }
}
