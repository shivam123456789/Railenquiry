package com.example.swaraj.railenquiry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PartyDetails extends AppCompatActivity {
	TextView partyname,mobile_no,petitioner_names,format,city,state,respondent,order_name,passed_by,passed_in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_party_details);
		partyname=(TextView)findViewById(R.id.name);
		mobile_no=(TextView)findViewById(R.id.mobile_no);
		petitioner_names=(TextView)findViewById(R.id.petitioners);
		format=(TextView)findViewById(R.id.format);
		city=(TextView)findViewById(R.id.city);
		state=(TextView)findViewById(R.id.state);
		respondent=(TextView)findViewById(R.id.repondents);
		order_name=(TextView)findViewById(R.id.order_date);
		passed_by=(TextView)findViewById(R.id.passed_by);
		passed_in=(TextView)findViewById(R.id.passed_in);
		Toast.makeText(this, (getIntent().getExtras().getInt("position")+1)+"", Toast.LENGTH_SHORT).show();

		partyname.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getParty_name());
		mobile_no.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getMobile_no());
		petitioner_names.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getPetitioner_names());
		format.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getFormat());
		city.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getCity());
		state.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getState());
		order_name.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getOrder_date());
		passed_in.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getPassed_in());
		passed_by.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getPassed_by());
		respondent.setText(stationname.models.get(getIntent().getExtras().getInt("position")).getRespondent());
	}
}
