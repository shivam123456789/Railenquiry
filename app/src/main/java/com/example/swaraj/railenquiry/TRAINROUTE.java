package com.example.swaraj.railenquiry;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.swaraj.railenquiry.models.trainroute;

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
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TRAINROUTE extends Fragment {

    EditText no;
    TextView t1;
    ListView L;

    public TRAINROUTE() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trainroute, container, false);

        t1=(TextView)view.findViewById(R.id.t1);
        no=(EditText)view.findViewById(R.id.no);
         L=(ListView)view.findViewById(R.id.L);


        Button button=(Button)view.findViewById(R.id.button) ;

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new async().execute("http://api.railwayapi.com/route/train/"+no.getText().toString()+"/apikey/26ca1jl39j/");
                    }
                }
        );


        return view;
    }

   public class async extends AsyncTask<String,String,List<trainroute>>
    {


        @Override
        protected List<trainroute> doInBackground(String... strings) {

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
                JSONArray jsonArray=jsonObject.getJSONArray("route");
                List<trainroute> listItems =new ArrayList<>();


                for(int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    trainroute train=new trainroute();

                    train.setNo(jsonObject1.getInt("no"));
                    train.setDistance(jsonObject1.getInt("distance"));

              //      train.setSta(jsonObject1.getString("state"));
                    train.setCity(jsonObject1.getString("fullname"));
                    train.setArr(jsonObject1.getString("scharr"));
                    train.setDep(jsonObject1.getString("schdep"));
                    listItems.add(train);
                }

                return listItems;
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
        protected void onPostExecute(List<trainroute> route) {
            super.onPostExecute(route);
           custom s =new custom(getContext(),R.layout.custom,route);
            L.setAdapter(s);

        }
    }

}

class custom extends ArrayAdapter {

    private List<trainroute> q;
    private int res;
    public LayoutInflater inflater;
    Context ctx;
    public custom(Context context, int resource, List<trainroute> objects) {
        super(context, resource, objects);
        q=objects;
        this.res=resource;
        ctx=context;


    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.custom, parent, false);
        }
        TextView t1 =(TextView) view.findViewById(R.id.t1);
        TextView t2 =(TextView) view.findViewById(R.id.t2);
        TextView t3 =(TextView) view.findViewById(R.id.t3);
        TextView t4 =(TextView) view.findViewById(R.id.t4);
        TextView t5 =(TextView) view.findViewById(R.id.t5);
        TextView t6 =(TextView) view.findViewById(R.id.t6);
        t1.setText(Integer.toString(q.get(position).getNo()));
        t2.setText(Integer.toString(q.get(position).getDistance()));
       // t3.setText(q.get(position).getSta());
        t4.setText(q.get(position).getCity());
        t5.setText(q.get(position).getArr());
        t6.setText(q.get(position).getDep());

        return view;
    }

}

