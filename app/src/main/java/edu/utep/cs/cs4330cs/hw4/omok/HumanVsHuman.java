package edu.utep.cs.cs4330cs.hw4.omok;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HumanVsHuman extends AbstractPlayerActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //game UI components referenced
        currentPlayer = (TextView) findViewById(R.id.current_player);
        boardView = (BoardView) findViewById(R.id.boardView);
        resetButton = (Button) findViewById(R.id.reset);
        setbackgroundMusic();
        setSoundEffects();
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
                        //Play set stone sound
                        soundPool.play(clickID, 1, 1, 1, 0, 2f);

                        //Check if there is a winner at the last stone placed
                        if (board.winner(x, y, board.getPlayerTurn())) {
                            String winner = board.getPlayerName();
                            //play winner audio
                            if(mediaPlayer.isPlaying())mediaPlayer.release();
                            soundPool.play(winnerID,1,1,1,0,1f);

                            // display the text for the winning player
                            Toast.makeText(getApplicationContext(), "Player " + winner +
                                    " has won!", Toast.LENGTH_LONG).show();
                           // board.increaseScore(board.getPlayerName());
                        }

                        //Check for a draw and display the message
                        else if (board.gameDraw()) {
                            //play lost audio
                            if(mediaPlayer.isPlaying())mediaPlayer.release();
                            soundPool.play(loserID,1,1,1,0,1f);
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
}