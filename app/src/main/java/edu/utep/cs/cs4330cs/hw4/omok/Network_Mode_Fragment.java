package edu.utep.cs.cs4330cs.hw4.omok;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Network_Mode_Fragment extends Fragment {
    private LinearLayout networkLayout;
    //Interface listener
    private StrategyModeListener listener;

    public interface StrategyModeListener{
        void setStrategy(int s);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            if(context instanceof StrategyModeListener)
                listener = (StrategyModeListener) context;
        }
        catch (Exception e){
            throw new ClassCastException(context.toString()+
                    "must implement MyListFragment.StrategyModeListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String url_1= "http://www.cs.utep.edu/cheon/cs4330/project/omok/info";
        ConnectServer connect = new ConnectServer();
        connect.execute(url_1);
        View view = inflater.inflate(R.layout.network_mode_fragment,container,false);
        networkLayout = (LinearLayout) view.findViewById(R.id.buttonList);
        return view;
    }

    private void createButtons(JSONArray arr){
            for (int i=0; i<arr.length();i++){
                Button btn = new Button(getActivity());
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layout.gravity= Gravity.CENTER_HORIZONTAL;
                btn.setLayoutParams(layout);
                btn.setId(i);
                try {
                    btn.setText(arr.getString(i));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setStrategy(v.getId());}
                });
                networkLayout.addView(btn);
            }
    }

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
                    createButtons(arr);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
