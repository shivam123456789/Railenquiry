package com.example.swaraj.railenquiry;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class PNR extends Fragment {
     TextView n;
    EditText no;
    TextView t1;

    public PNR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pnr, container, false);
        n=(TextView)view.findViewById(R.id.n);
        t1=(TextView)view.findViewById(R.id.t1);
        no=(EditText)view.findViewById(R.id.no);

        Button button=(Button)view.findViewById(R.id.button) ;

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         new async().execute("http://api.railwayapi.com/pnr_status/pnr/"+no.getText().toString()+"/apikey/26ca1jl39j/");
                    }
                }
        );


        return view;
    }

    class async extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {



            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url=new URL(strings[0]);
                connection=(HttpURLConnection)url.openConnection();
                InputStream stream=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer=new StringBuffer();
                String line="";
                while ((line=reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                String json =   buffer.toString();
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray("passengers");
                StringBuffer buffer1=new StringBuffer();
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String Sta = jsonObject1.getString("current_status");
                    String s = jsonObject1.getString("booking_status");
                    buffer1.append(i+1+"  "+Sta+"   "+s+"\n");

                }


                return buffer1.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null)
                {
                    connection.disconnect();
                }
                if (reader!=null)
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            t1.setText(result);
        }
    }

}
