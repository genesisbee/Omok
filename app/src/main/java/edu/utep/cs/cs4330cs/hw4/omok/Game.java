package edu.utep.cs.cs4330cs.hw4.omok;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Game is suppose to be a dynmaic way to setup
 * Game players
 * Game mode 0 :Human vs Human
 * Game mode 1:Human vs Computerd
 */
public class Game extends AppCompatActivity {
    Board boardGame = new Board();
    BoardView boardView;
    TextView currentPlayer;
    Player player1;
    Player player2;
    int gameMode;


    //Determines what type of game will be played
    //human vs human
    public void Game(Bundle extra){
        extra = getIntent().getExtras();
        gameMode = extra.getInt("mode");
        initalizePlayers(gameMode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentPlayer = (TextView) findViewById(R.id.current_player);
        boardView = (BoardView) findViewById(R.id.boardView);
    }

    public void startGame() {
        //STEP 2.)If no winner or draw has been announced
        if(!boardGame.foundWinner()) {

            //STEP 3.)Current player set stone on board
                //a.)player 1 turns
                //b.)player 2 turn

            //STEP 4.)Check results
            //a.)Check if there is a winner at last placed stone
            if (boardGame.winner(x, y, boardGame.getPlayerTurn())) {
                String winner = boardGame.getPlayerName();

                //Display the text for the winning player
                Toast.makeText(getApplicationContext(), "Player " + winner + " has won!", Toast.LENGTH_LONG).show();
               // boardGame.increaseScore(boardGame.getPlayerName());
            }

            //b.)Check for a draw
            else if (boardGame.gameDraw()) {
                Toast.makeText(getApplicationContext(), "Draw", Toast.LENGTH_SHORT).show();
            }

            //STEP 5.) Flip player turn
            boardGame.flipTurn();

            //STEP 6.) Update BoardView and repeat STEP 1.
            //a.) Update the next players turn
            currentPlayer.setText(boardGame.getPlayerName());
            //b.) update the view based on the new board
            boardView.currentBoard(boardGame.getPlace());

        }
    }

    /*initalizePlayers method, initalizes players
    *according to the game's mode.
    * Mode 0 = Human vs Human
    * Mode 1 = Human vs Computer, Strategy :Random
    * Mode 2 = Human vs Compputer, Strategy: Smart
    * @param x, represents the game mode.
    * returns game players.
    * */
    public void initalizePlayers(int x ){
        switch (x){
            //Human vs Human
            case 0:{
                this.player1 = null;
                this.player2 = null;
            }
            //Human vs Computer:Random
            case 1:{
                this.player1 = null;
                this.player2 = null;
            }
            //Human vs Computer:Smart
            case 2:{
                this.player1 = null;
                this.player2 = null;
            }
        }
    }
}
