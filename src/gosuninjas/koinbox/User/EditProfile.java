package gosuninjas.koinbox.User;

import gosuninjas.koinbox.CheckLocation;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * this class edits the profile like name, age, university, home city and destination city. 
 * This activity goes with the edit_profile.xml layout.
 * @author shivangi
 *
 */

public class EditProfile extends Activity implements OnClickListener{
	 Button save_profile;
	 EditText editname,editage,edituniversity,edithome,editaway;
	 CheckLocation lCheck = new CheckLocation();
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.edit_profile);
	     editname = (EditText) findViewById(R.id.edit_name);
	     editage = (EditText) findViewById(R.id.edit_age);
	     edituniversity = (EditText) findViewById(R.id.edit_university);
	     edithome = (EditText) findViewById(R.id.edit_home);
	     editaway = (EditText) findViewById(R.id.edit_away);
	     editname.setText(UserProfile.myname);
	     editage.setText(""+UserProfile.myage);
	     edituniversity.setText(UserProfile.myuniversity);
	     edithome.setText(UserProfile.myhome);
	     editaway.setText(UserProfile.myaway);
	     save_profile = (Button) findViewById(R.id.submit_edit_profile);
	     save_profile.setOnClickListener(this);
	     Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView txt = (TextView) findViewById(R.id.editTitle);  
			txt.setTypeface(font);
		Typeface eNA = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView na = (TextView) findViewById(R.id.nameTitle);  
			na.setTypeface(eNA);
		Typeface eAG = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView ag = (TextView) findViewById(R.id.ageTitle);  
			ag.setTypeface(eAG);
		Typeface eUN = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView un = (TextView) findViewById(R.id.universityTitle);  
			un.setTypeface(eUN);
		Typeface eHC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView hc = (TextView) findViewById(R.id.homeTitle);  
			hc.setTypeface(eHC);
		Typeface eDC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView dc = (TextView) findViewById(R.id.destTitle);  
			dc.setTypeface(eDC);
		Typeface eSEP = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView sep = (TextView) findViewById(R.id.submit_edit_profile);  
			sep.setTypeface(eSEP);
	 }
	 
	 public void onClick(View v){
		switch (v.getId()){
		/**
	    	 * main class will call editProfile() to 
	    	 * edit name, age, university, home city, away city.
	    	 * 
	    	 */
		case R.id.submit_edit_profile:
			UserProfile.myname = editname.getText().toString();
			UserProfile.myage =  Integer.parseInt(editage.getText().toString());
			UserProfile.myuniversity = edituniversity.getText().toString();
			UserProfile.myhome = edithome.getText().toString();
			UserProfile.myaway = editaway.getText().toString();
			if (!lCheck.check(UserProfile.myhome)) {
    			Dialog d = new Dialog(this);
    			d.setTitle("Invalid home city");
    			TextView tv = new TextView(this);
    			d.setContentView(tv);
    			d.show();
    			break;
    		}
			if (!lCheck.check(UserProfile.myaway)) {
    			Dialog d = new Dialog(this);
    			d.setTitle("Invalid destination city");
    			TextView tv = new TextView(this);
    			d.setContentView(tv);
    			d.show();
    			break;
    		}
			try {
				editProfile(UserProfile.myname,UserProfile.myage,UserProfile.myuniversity,UserProfile.myhome,UserProfile.myaway);
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
			Intent i = new Intent(this,UserProfile.class);
			startActivity(i);
			break;
		}
	 }
	 
	 /**
	  * this class takes in edited name, age,university, home and away city and puts the edited name, age, university,
	  * home and away into the user profile. The Http
	 * Client sends an HTTP PUT request to the API to update the name, age, university, home, away. 
	 * The API sends information which is stored 
	 * in Android via JSON to the webserver. 
	  * @param name
	  * @param age
	  * @param university
	  * @param home
	  * @param away
	  * @throws JSONException
	  * @throws ClientProtocolException
	  * @throws IOException
	  */
	 
	 public void editProfile(String name, int age, String university, String home, String away) throws JSONException, ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();  
	    HttpPut put = new HttpPut("http://myapp-gosuninjas.dotcloud.com/api/v1/createprofile/"+UserProfile.myuri.substring(20));
	    put.setHeader("Content-type", "application/json");
	    put.setHeader("Accept", "application/json");
	    JSONObject obj = new JSONObject();
	    obj.put("name", name);
	    obj.put("age", age);
	    obj.put("university", university);
	    obj.put("home_city", home);
	    obj.put("away_city", away);
	    obj.put("user", UserProfile.myuserid);
	   
	    try {
			put.setEntity(new StringEntity(obj.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
			HttpResponse response = client.execute(put);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
			
		}

}
