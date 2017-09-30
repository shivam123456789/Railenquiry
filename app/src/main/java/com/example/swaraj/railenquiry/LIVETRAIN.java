package com.example.swaraj.railenquiry;


import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.swaraj.railenquiry.models.livetrain;
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
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class LIVETRAIN extends Fragment {

    Calendar myCalendar = Calendar.getInstance();
    TextInputLayout trno;
    Button B;
    String getdate="";
    TextView t1,mydate;
    ListView L;


    public LIVETRAIN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_livetrain, container, false);


        trno=(TextInputLayout)view.findViewById(R.id.wraptrno);
        mydate=(TextView)view.findViewById(R.id.date);
        t1=(TextView)view.findViewById(R.id.t1) ;
        L=(ListView)view.findViewById(R.id.L);



        final DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        mydate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        B=(Button)view.findViewById(R.id.B) ;

        B.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new async().execute("http://api.railwayapi.com/live/train/"+trno.getEditText().getText().toString()+"/doj/"+ getdate+"/apikey/26ca1jl39j/");
                    }
                }
        );
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel () {
        String myFormat = "yyyyMMdd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        mydate.setText(sdf.format(myCalendar.getTime()));
        getdate= mydate.getText().toString();
    }

    class async extends AsyncTask<Object, Object, List<livetrain>>
    {

        @Override
        protected List<livetrain> doInBackground(Object... strings) {

            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try {
                URL url=new URL((String) strings[0]);
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
                List<livetrain> listItems =new ArrayList<>();


                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    livetrain live =new livetrain();


                    JSONObject Sta = jsonObject1.getJSONObject("station_");

                    live.setNo(jsonObject1.getInt("no"));
                    live.setDistance(jsonObject1.getInt("distance"));
                    live.setName(Sta.getString("name"));
                    live.setScharr(jsonObject1.getString("scharr"));
                    live.setSchdep(jsonObject1.getString("schdep"));
                    live.setActarr(jsonObject1.getString("actarr"));
                    live.setActdep(jsonObject1.getString("actdep"));
                    live.setLatemin(jsonObject1.getString("latemin"));

                    listItems.add(live);



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
        protected void onPostExecute(List<livetrain> result) {
            super.onPostExecute(result);
            cus s=new cus(getContext(),R.layout.cus,result);
            L.setAdapter(s);
        }
    }
}

class cus extends ArrayAdapter {

    private List<livetrain> q;
    private int res;
    public LayoutInflater inflater;
    Context ctx;

    public cus(Context context, int resource, List<livetrain> objects) {
        super(context, resource, objects);
        q = objects;
        this.res = resource;
        ctx = context;


    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.cus, parent, false);
        }
        TextView t1 = (TextView) view.findViewById(R.id.t1);
        TextView t2 = (TextView) view.findViewById(R.id.t2);
        TextView t3 = (TextView) view.findViewById(R.id.t3);
        TextView t4 = (TextView) view.findViewById(R.id.t4);
        TextView t5 = (TextView) view.findViewById(R.id.t5);
        TextView t6 = (TextView) view.findViewById(R.id.t6);
        TextView t7 = (TextView) view.findViewById(R.id.t7);
        TextView t8 = (TextView) view.findViewById(R.id.t8);

        t1.setText(Integer.toString(q.get(position).getNo()));
        t2.setText(Integer.toString(q.get(position).getDistance()));
        t3.setText(q.get(position).getName());
        t4.setText(q.get(position).getScharr());
        t5.setText(q.get(position).getSchdep());
        t6.setText(q.get(position).getActarr());
        t7.setText(q.get(position).getActdep());
        t8.setText(q.get(position).getLatemin());


        return view;
    }
}
