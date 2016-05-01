package edu.utep.cs.cs4330cs.hw4.omok;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HumanVsNetwork extends AbstractPlayerActivity{
    final private String URL_BASE = "http://www.cs.utep.edu/cheon/cs4330/project/omok/";
    String strategy;
    String p; //pid
    String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGame();
        setContentView(R.layout.activity_main);
        //REFERENCE UI COMPONENTS
        currentPlayer = (TextView) findViewById(R.id.current_player);
        boardView = (BoardView) findViewById(R.id.boardView);
        resetButton = (Button) findViewById(R.id.reset);
        startGame();
    }

    public void setUpGame(){
        Bundle extra = getIntent().getExtras();
        int s = extra.getInt("strategy");
        if(s ==0) this.strategy ="Smart";
        else this.strategy="Random";
        String url_2= URL_BASE+"new/?strategy="+strategy;
        ServerStrategy search = new ServerStrategy();
        try {
            this.p= search.execute(url_2).get();
            Log.d("PID",p);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void startGame(){
        boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                if (!board.foundWinner()) {
                    //Check if a stone can be placed at the current X & Y touched
                    if (board.setStone(x, y, board.getPlayerTurn())) {
                        //Play set stone sound
                        //Check if there is a winner at the last stone placed
                        if (board.winner(x, y, board.getPlayerTurn())) {
                            winner = board.getPlayerName();
                            //play winner audio
                            // display the text for the winning player
                            Toast.makeText(getApplicationContext(), "Player " + winner +
                                    " has won!", Toast.LENGTH_LONG).show();
                        }
                        //Check for a draw and display the message
                        else if (board.gameDraw()) {
                            //play lost audio
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
                            serverMove(x,y);
                        }
                    }
                }
            }
        });
    }

    //SERVER MOVE
    public void serverMove(int x, int y) {
        if (!foundWinner()) {
            String url_3 = URL_BASE + "play/?pid=" + p + "&move=" + x + "," + y;
                try {
                    PlayServer play = new PlayServer();
                    String result = play.execute(url_3).get().toString();
                    try {
                        //If connected server places turn
                        JSONObject playGame = new JSONObject(result);
                        String humanStone = playGame.getString("ack_move");
                        Log.d("Human Result", humanStone);
                        String severStone = playGame.getString("move");
                        Log.d("Move Result", severStone);

                        JSONObject jsonMoveInfo = new JSONObject(severStone);
                        int getX = Integer.parseInt(jsonMoveInfo.getString("x"));
                        int getY = Integer.parseInt(jsonMoveInfo.getString("y"));

                        if (board.setStone(getX, getY, board.getPlayerTurn())) {
                            if (winner(getX, getY)) {
                                winner = board.getPlayerName();
                                // display the text for the winning player
                                displayWinner();
                            }
                            else if (board.gameDraw()) {
                                displayDraw();
                            }
                            else {
                                board.flipTurn();
                                //Update the next players turn
                                currentPlayer.setText(board.getPlayerName());
                                //update the view based on the new board
                                boardView.currentBoard(board.getPlace());
                            }
                        }
                    }
                    catch(JSONException e){e.printStackTrace();}
                    }
                catch (InterruptedException e) {e.printStackTrace();}
                catch (ExecutionException e) {e.printStackTrace();
                }
            }
        }

    //-----game methods---
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

    //****************Connection to Game Strategy***************
    private class ServerStrategy extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result ="";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                //open connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //download content
                InputStream in = urlConnection.getInputStream();
                //read content URI
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                //read the entire file
                while (data != -1) {
                    char current = (char) data;
                    result += Character.toString(current);
                    data = reader.read();
                }
                Log.d("Result",result);
                JSONObject gameObject = new JSONObject(result);
                return gameObject.getString("pid");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "Failed";
        }
    }

    //****************Play Server***************
    private class PlayServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                //open connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //download content
                InputStream in = urlConnection.getInputStream();
                //read content URI
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                //read the entire file
                while (data != -1) {
                    char current = (char) data;
                    result += Character.toString(current);
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "Failed";
        }
    }
}
