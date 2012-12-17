package gosuninjas.koinbox;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * This class allows new user to register for an id.
 * An id in the Koinbox application requires a valid username, password, email, name, age, university, home_city, 
 * away_city (must be valid cities in the city list)
 * Once successful, the app return new user to the home screen.
 * @author Cuong
 */
public class Register extends Activity implements OnClickListener {
	Button register_button;
	String username, email, password, name, age, university, home, away;
	EditText username_box, email_box,age_box, password_box, name_box,university_box, home_box, away_box;
	CheckLocation lCheck = new CheckLocation();
	
	View term;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.register);
	
	register_button = (Button) findViewById(R.id.register_submit);
	register_button.setOnClickListener(this);
	username_box = (EditText) findViewById(R.id.register_username);
	password_box = (EditText) findViewById(R.id.register_password);
	email_box = (EditText) findViewById(R.id.register_email);
	age_box = (EditText) findViewById(R.id.age_input);
	
	name_box = (EditText) findViewById(R.id.name_input);
	university_box = (EditText) findViewById(R.id.university_input);
	home_box = (EditText) findViewById(R.id.home_input);
	away_box = (EditText) findViewById(R.id.away_input);
	Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView txt = (TextView) findViewById(R.id.welcomeTitle);  
	txt.setTypeface(font);
	Typeface pfont = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView ptxt = (TextView) findViewById(R.id.personal);  
	ptxt.setTypeface(pfont);
    Typeface pbac = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pba = (TextView) findViewById(R.id.register_submit); 
	pba.setOnClickListener(this);
	pba.setTypeface(pbac);

	}
	
	public void onClick(View v){
		switch (v.getId()){
    	/**
    	 * If forms are filled correctly (meaning it pass all conditions) main class will call registerUser() to 
    	 * create a basic user with username, password and email. It will also call createProfile() that 
    	 * will create a profile of name,age, university, home and away city.
    	 * 
    	 */
    	case R.id.register_submit:
    		username=username_box.getText().toString();
    		email = email_box.getText().toString();
    		password = password_box.getText().toString();
    		name = name_box.getText().toString();
    		age = age_box.getText().toString();
    		university = university_box.getText().toString();
    		home = home_box.getText().toString();
    		away = away_box.getText().toString();
    		try {
				if (!username.equals("") && !email.equals("") && !password.equals("") &&!name.equals("") &&!age.equals("") && !university.equals("") &&
						!home.equals("") && !away.equals("") && !Koinbox.checkuser(username) &&lCheck.check(home)&&lCheck.check(away)){
					try {
						registerUser(username,password,email);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						createProfile(username, name, age, university, home, away);
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
				/**
				 * Once created, set static password and username to the newly created password and username
				 * in order to log into the system directly and use the services
				 */
		    		Koinbox.password = password;
		    		Koinbox.username = username;
		    		
		    		Intent i =new Intent(this, Home.class);
		    		startActivity(i);
				}
					
				if (username.equals("") || email.equals("") || password.equals("") ||name.equals("") ||age.equals("") || university.equals("") ||
							home.equals("") || away.equals("")){
					new AlertDialog.Builder(Register.this)
					.setMessage("Please fill all forms!")
					.show();
					
				}
				
				else if (Koinbox.checkuser(username)){
					new AlertDialog.Builder(Register.this)
					.setMessage("The username is taken already")
					.show();
				}
				else if (!lCheck.check(away) || !lCheck.check(home)) {
	    			Dialog d = new Dialog(this);
	    			d.setTitle("Invalid city input");
	    			TextView tv = new TextView(this);
	    			d.setContentView(tv);
	    			d.show();}
						
		    		
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
    	}
		
			
	}
	/**
	 * This function creates a new user object in koinbox using valid username, password and email
	 * Once forms are filled in correctly, HTTP Client requests HTTP Post query (using a JSONObject)
	 * to the API so as to create a new user. The API delivers this to webserver and there new user 
	 * row is written in the database.
	 */
	public void registerUser(String username, String password, String email) throws JSONException{
		HttpClient client = new DefaultHttpClient();  
	    HttpPost post = new HttpPost("http://myapp-gosuninjas.dotcloud.com/api/v1/newuser/");
	    post.setHeader("Content-type", "application/json");
	    post.setHeader("Accept", "application/json");
	    JSONObject obj = new JSONObject();
	    obj.put("username", username);
	    obj.put("password", password);
	    obj.put("email", email);
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
	/**
	 * This function creates a new user profile object for the newly created user in koinbox using valid 
	 * name, age, university, home and away city. Once forms are filled in correctly, HTTP Client requests 
	 * HTTP Post query (putting information into a JSONObject) to the API so as to create a new user. 
	 * The API delivers this to webserver and there new user profile row that linked to the new created user
	 * is written in the database.
	 */
	public void createProfile(String username, String name, String age, String university, String home, String away) throws JSONException, ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();  
		HttpGet get = new HttpGet("http://myapp-gosuninjas.dotcloud.com/api/v1/user/?format=json&username="+username);
		HttpResponse r = client.execute(get);
		
		HttpEntity e = r.getEntity();
		String data = EntityUtils.toString(e);
		JSONObject input = new JSONObject(data);
		JSONArray user_stream = input.getJSONArray("objects");
		JSONObject user = user_stream.getJSONObject(0);
		String userid = user.getString("resource_uri");
	    HttpPost post = new HttpPost("http://myapp-gosuninjas.dotcloud.com/api/v1/createprofile/");
	    post.setHeader("Content-type", "application/json");
	    post.setHeader("Accept", "application/json");
	    JSONObject obj = new JSONObject();
	    obj.put("name", name);
	    obj.put("age", age);
	    obj.put("university", university);
	    obj.put("home_city", home);
	    obj.put("away_city", away);
	    obj.put("user", userid);
	   
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
