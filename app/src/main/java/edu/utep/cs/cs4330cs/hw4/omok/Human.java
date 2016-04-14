package edu.utep.cs.cs4330cs.hw4.omok;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/*sets all the actions a human player can do
* */
public class Human extends Player{
    BoardView boardListener = new BoardView();
    Board board = new Board();

    Human(String name, String color, String mark) {
        super(name, color, mark);
    }


    public void setStone(){
                //Vaildate move
        boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {


            }
        });
    }
}