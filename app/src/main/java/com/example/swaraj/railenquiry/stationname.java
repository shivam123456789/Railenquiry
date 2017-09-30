package com.example.swaraj.railenquiry;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swaraj.railenquiry.models.PartyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class stationname extends Fragment {
   Button b;
   public EditText e;
    TextView te;
    ListView parties;
    List<String> party_name;
   static List<PartyModel> models;
   String resp="OK";

    public stationname() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_stationname, container, false);

        b=(Button)view.findViewById(R.id.b);
        te=(TextView)view.findViewById(R.id.te);
        e=(EditText)view.findViewById(R.id.e);
        parties=(ListView)view.findViewById(R.id.list);
        parties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), position+"", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),PartyDetails.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        b.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new HistoryAsync().execute("http://apptech-interactive.com/History.php");
                    }
                }
        );








        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class HistoryAsync extends AsyncTask<String, String, List<PartyModel>>
    {

        @Override
        protected List<PartyModel> doInBackground(String... strings) {


            party_name=new ArrayList<>();
            models=new ArrayList<>();


            BufferedReader reader=null;
			OkHttpClient client = new OkHttpClient();
			RequestBody body = new FormBody.Builder()
					.add("username", "7879804799")
					.build();
			Request request = new Request.Builder().url("http://apptech-interactive.com/History.php").post(body).build();


			Call call = client.newCall(request);

			call.enqueue(new Callback() {

				@Override
				public void onFailure(Call call, IOException e) {

				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {

					try {
						 resp = response.body().string();
//                    Log.v(TAG_REGISTER, resp);
						System.out.println(resp);


						if (response.isSuccessful()) {
							{
								try {
									JSONObject jsonObject=new JSONObject(resp);
									JSONArray jsonArray=jsonObject.getJSONArray("object_name");
									for(int i=0;i<jsonArray.length();i++) {
										PartyModel partyModel=new PartyModel();
										JSONObject jsonObject1 = jsonArray.getJSONObject(i);
										partyModel.setMobile_no(jsonObject1.getString("mobile_no"));
										partyModel.setParty_name(jsonObject1.getString("party_name"));
										partyModel.setFormat(jsonObject1.getString("format"));
										partyModel.setCity(jsonObject1.getString("city"));
										partyModel.setCourt(jsonObject1.getString("court"));
										partyModel.setPetitioner_names(jsonObject1.getString("petitioner_names"));
										partyModel.setRespondent(jsonObject1.getString("respondent"));
										partyModel.setState(jsonObject1.getString("state"));
										partyModel.setOrder_date(jsonObject1.getString("order_date"));
										partyModel.setPassed_by(jsonObject1.getString("passed_by"));
										partyModel.setPassed_in(jsonObject1.getString("passed_in"));
										Log.e("777777777777",partyModel.getCity());
										models.add(partyModel);
									}Log.e("1111111111111",models.size()+"");
									synchronized (HistoryAsync.this)
									{HistoryAsync.this.notifyAll();}

								} catch (JSONException e1) {
									e1.printStackTrace();
								}

							}
						}
					} catch (IOException e) {
						// Log.e(TAG_REGISTER, "Exception caught: ", e);
						System.out.println("Exception caught" + e.getMessage());
					}
				}


			});
			synchronized (HistoryAsync.this)
			{try {
				HistoryAsync.this.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}}

			Log.e("8888888888888",models.size()+"");
         return models;
        }



        @Override
        protected void onPostExecute(List<PartyModel> result) {
            super.onPostExecute(result);

			Toast.makeText(getContext(), result+"", Toast.LENGTH_SHORT).show();
			ShowPArties adapter=new ShowPArties(getContext(),R.layout.custom,result);
            parties.setAdapter(adapter);
        }

    }



    class ShowPArties extends ArrayAdapter {

        private List<PartyModel> names;
        private int res;
        public LayoutInflater inflater;
        Context ctx;
        public ShowPArties(Context context, int resource, List<PartyModel> objects) {
            super(context, resource, objects);
            names=objects;
            this.res=resource;
            ctx=context;

        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(ctx).inflate(R.layout.custom, parent, false);
            }
            TextView t1 =(TextView) view.findViewById(R.id.t1);
            t1.setText(names.get(position).getCity());
            Toast.makeText(ctx,names.get(position).getParty_name() , Toast.LENGTH_SHORT).show();
            return view;
        }

}
}
// URL url=new URL(strings[0]);
//connection=(HttpURLConnection)url.openConnection();
              /*  InputStream stream=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer=new StringBuffer();
                String line="";
                while ((line=reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                String json =   buffer.toString();
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray("object_name");
                for(int i=0;i<jsonArray.length();i++) {
                    PartyModel partyModel=new PartyModel();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    partyModel.setMobile_no(jsonObject1.getString("mobile_no"));
                    partyModel.setParty_name(jsonObject1.getString("party_name"));
                    partyModel.setFormat(jsonObject1.getString("format"));
                    partyModel.setCity(jsonObject1.getString("city"));
                    partyModel.setCourt(jsonObject1.getString("court"));
                    partyModel.setPetitioner_names(jsonObject1.getString("petitioner_names"));
                    partyModel.setRespondent(jsonObject1.getString("respondent"));
                    partyModel.setState(jsonObject1.getString("state"));
                    partyModel.setOrder_date(jsonObject1.getString("order_date"));
                    partyModel.setPassed_by(jsonObject1.getString("passed_by"));
                    partyModel.setPassed_in(jsonObject1.getString("passed_in"));
                    models.add(partyModel);
                }

                return models;*/
