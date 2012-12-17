package gosuninjas.koinbox.User;

import gosuninjas.koinbox.R;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * this class allows you to add interest in either of the interest groups: other, music, sports, game, movie. 
 * This class goes with addinterest.xml.
 * @author shivangi
 *
 */

public class AddInterest extends Activity implements OnClickListener,OnItemSelectedListener {
	Button save,add_more;
	EditText editdescription;
	String[] items = {"Music","Sports","Movie","Game","Other"};
	String type,description;
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.addinterest);
	     save = (Button) findViewById(R.id.submit_add_interest);
	     save.setOnClickListener(this);
	     editdescription = (EditText) findViewById(R.id.add_interest);
	     Spinner my_spin=(Spinner)findViewById(R.id.add_type_spinner);
	     my_spin.setOnItemSelectedListener(this);
	     ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,items);
	     aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     my_spin.setAdapter(aa);
	     Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView txt = (TextView) findViewById(R.id.addTitle);  
			txt.setTypeface(font);
		Typeface aTY = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView ty = (TextView) findViewById(R.id.typeTitle);  
			ty.setTypeface(aTY);
		Typeface aDT = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView dt = (TextView) findViewById(R.id.descTitle);  
			dt.setTypeface(aDT);
		Typeface aSAI = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView sai = (TextView) findViewById(R.id.submit_add_interest);  
			sai.setTypeface(aSAI);
	}
	/**
	 * This function assigns the type of the interest the chosen value of the spinner.
	 */
	public void onItemSelected(AdapterView arg0, View arg1, int pos, long arg3) {
		type=items[pos];		
	}

	public void onNothingSelected(AdapterView arg0) {
		// TODO Auto-generated method stub
		type="Music";
		
	}
	
	public void onClick(View v){
		switch (v.getId()){
			
		case R.id.submit_add_interest:
			description = editdescription.getText().toString();
			try {
				addInterest(type,description);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent i = new Intent(this, UserProfile.class);
			startActivity(i);
			break;
		}
	}
	
	/**
	 * this class adds the newly added interest using type_interest and description to your profile. The Http
	 * Client sends an HTTP POST request to the API to create a new interest. The API sends information which is stored 
	 * in Android via JSON to the webserver. Here new interests belonged to the user are created.
	 * @param type
	 * @param description
	 * @throws JSONException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public void addInterest(String type, String description) throws JSONException, ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();  
	    HttpPost post = new HttpPost("http://myapp-gosuninjas.dotcloud.com/api/v1/createinterest/");
	    post.setHeader("Content-type", "application/json");
	    post.setHeader("Accept", "application/json");
	    JSONObject obj = new JSONObject();
	    obj.put("type_interest", type);
	    obj.put("description", description);
	    obj.put("user", UserProfile.myuserid);
	   
	    try {
			post.setEntity(new StringEntity(obj.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			HttpResponse response = client.execute(post);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
			
		}
}
	
