package edu.utep.cs.cs4330cs.hw4.omok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class HumanVsComputer extends AppCompatActivity {
    int gameMode;
    public Board board = new Board();
    public Button resetButton;
    public BoardView boardView;
    TextView currentPlayer;

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
        resetButton.setEnabled(true);

    }
}

