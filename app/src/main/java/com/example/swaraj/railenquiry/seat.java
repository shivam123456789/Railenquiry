package com.example.swaraj.railenquiry;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


public class seat extends Fragment {
       Spinner s,P;
    EditText e1,e2,e3,e4;
   public Button b;
    TextView t;

    public seat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_seat, container, false);


         s=(Spinner)view.findViewById(R.id.s);
        e1=(EditText)view.findViewById(R.id.e1);
        e2=(EditText)view.findViewById(R.id.e2);
        e3=(EditText)view.findViewById(R.id.e3);
        e4=(EditText)view.findViewById(R.id.e4);
        b=(Button)view.findViewById(R.id.b);
        P=(Spinner)view.findViewById(R.id.P);
        t=(TextView)view.findViewById(R.id.t);

        b.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new async().execute("http://api.railwayapi.com/check_seat/train/"+e1.getText().toString()+"/source/"+e2.getText().toString()+"/dest/"+e3.getText().toString()+"/date/"+e4.getText().toString()+"/class/"+s.getSelectedItem().toString()+"/quota/"+P.getSelectedItem().toString()+"/apikey/26ca1jl39j/");

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

                StringBuffer buffer1=new StringBuffer();

                int x=jsonObject.getInt("response_code");
                if (x==204)
                {buffer1.append("error");}

                JSONArray jsonArray=jsonObject.getJSONArray("availability");

                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String Sta = jsonObject1.getString("date");
                    String s = jsonObject1.getString("status");
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
            t.setText(result);
        }
    }

}
