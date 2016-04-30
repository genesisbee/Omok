package edu.utep.cs.cs4330cs.hw4.omok;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


public class Network_Mode_Fragment extends Fragment {
    private LinearLayout networkLayout;
    public ArrayList<String> buttonList = new ArrayList<String>();


    public void printArray(){
        for (int i=0; i<buttonList.size();i++){Log.d("ButtonList",buttonList.get(i));}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.network_mode_fragment,container,false);
        //networkLayout = (LinearLayout) view.findViewById(R.id.buttonList);
        return view;
    }

    private void runStrategy(int mode){}
}
