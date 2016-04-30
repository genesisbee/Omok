package edu.utep.cs.cs4330cs.hw4.omok;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HumanVsNetwork extends AppCompatActivity{
    private final String URL_BASE = "http://www.cs.utep.edu/cheon/cs4330/project/omok/";

    //****************Connection to Game Server***************
    private class ConnectServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection = null;
            try{
                url = new URL (urls[0]);
                //open connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //download content
                InputStream in = urlConnection.getInputStream();
                //read content URI
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                //read the entire file
                while(data != -1){
                    char current = (char)data;
                    result += Character.toString(current);
                    data = reader.read();
                }
                return result;
            }
            catch(Exception e){
                e.printStackTrace();
            }
        return "Failed";
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(result != "Failed") {
                try {
                    //create name/value mapping from the JSON String
                    JSONObject gameObject = new JSONObject(result);
                    //returns the vale mapped by strategies
                    String strategiesInfo = gameObject.getString("strategies");
                    //add list of strategies to JSON array
                    JSONArray arr = new JSONArray(strategiesInfo);
                    Network_Mode_Fragment networkFragment = new Network_Mode_Fragment();
                    for(int i=0; i<arr.length();i++) {networkFragment.buttonList.add(arr.getString(i));}
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Failed Connection",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectServer connect = new ConnectServer();
        //************Game server URLS********************
        ConnectServer connection = new ConnectServer();
        String url1 = URL_BASE +"info";
        //STEP 1: Connect to server
        connection.execute(url1);
    }
}

