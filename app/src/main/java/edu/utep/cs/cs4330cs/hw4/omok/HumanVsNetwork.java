package edu.utep.cs.cs4330cs.hw4.omok;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HumanVsNetwork extends AbstractPlayerActivity{
    String strategy;
    String p; //pid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        int s = extra.getInt("strategy");
        if(s ==0) this.strategy ="Smart";
        else this.strategy="Random";
            String url_2= "http://www.cs.utep.edu/cheon/cs4330/project/omok/new/?strategy="+strategy;
            ServerStrategy search = new ServerStrategy();
        try {
            this.p= search.execute(url_2).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        //REFERENCE UI COMPONENTS
        currentPlayer = (TextView) findViewById(R.id.current_player);
        boardView = (BoardView) findViewById(R.id.boardView);
        resetButton = (Button) findViewById(R.id.reset);
        startGame();
    }

    //****************Connection to Game Server***************
    private class ServerStrategy extends AsyncTask<String, Void, String> {
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
                JSONObject gameObject = new JSONObject(result);
                    return gameObject.getString("pid");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "Failed";
        }
    }

    public void startGame(){
        boardView.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                    String url_3= "http://www.cs.utep.edu/cheon/cs4330/project/omok/play/?pid="+p+"&move="+x+","+y;
                    Log.d("URL_3",url_3);
                    PlayServer play = new PlayServer();
                    try {
                        String results = play.execute(url_3).get().toString();
                        Log.d("Results play",results);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    catch (ExecutionException e) {
                        e.printStackTrace();
                    }
            }
        });

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
