package gosuninjas.koinbox;

import gosuninjas.koinbox.Core.AboutUs;
import gosuninjas.koinbox.Core.Inbox;
import gosuninjas.koinbox.Core.MyFriends;
import gosuninjas.koinbox.Core.MyKoinbox;
import gosuninjas.koinbox.Core.Outbox;
import gosuninjas.koinbox.User.UserProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Typeface;	
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;

/** Home class. Displays the seven buttons profile, friends, koinbox, inbox, outbox, aboutus, logout. 
 * Clicking on either of the buttons will  take you to the respective screens
 * The class has one static array list that will be used throughout the app: myfriends. This contains the friends of the 
 * user.
 * @author Cuong
 *
 */


public class Home extends Activity implements OnClickListener {
	Button myprofile, mykoinbox, my_friends, aboutus,logout,inbox,outbox;
	HttpClient client;
	final Context context = this;
	public static List<String> myfriends = new ArrayList<String>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        myprofile = (Button) findViewById(R.id.my_profile);
        
        
        mykoinbox = (Button) findViewById(R.id.my_koinbox);
        my_friends = (Button) findViewById(R.id.my_friends);
        inbox = (Button) findViewById(R.id.my_inbox);
        outbox = (Button) findViewById(R.id.my_outbox);
        aboutus = (Button) findViewById(R.id.about_us);
        Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView txt = (TextView) findViewById(R.id.homeTitle);  
		txt.setTypeface(font);
		Typeface hPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView hP = (TextView) findViewById(R.id.my_profile);  
		hP.setTypeface(hPro);
		Typeface HMKB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView mK = (TextView) findViewById(R.id.my_koinbox);  
		mK.setTypeface(HMKB);
		Typeface hFR = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView hF = (TextView) findViewById(R.id.my_friends);  
		hF.setTypeface(hFR);
		Typeface hAB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView hA = (TextView) findViewById(R.id.about_us);  
		hA.setTypeface(hAB);
		Typeface hLO = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView hL = (TextView) findViewById(R.id.log_out);
		hL.setTypeface(hLO);
		
		Typeface tInbox= Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView hIn = (TextView) findViewById(R.id.my_inbox);  
		hIn.setTypeface(tInbox);
		Typeface tOutbox= Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView hOut = (TextView) findViewById(R.id.my_outbox);  
		hOut.setTypeface(tOutbox);
        logout = (Button) findViewById(R.id.log_out);
        client = new DefaultHttpClient();
        myprofile.setOnClickListener(this);
        mykoinbox.setOnClickListener(this);
        my_friends.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        inbox.setOnClickListener(this);
        outbox.setOnClickListener(this);
        logout.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View arg) {
				
					new AlertDialog.Builder(context)
		        	.setMessage(R.string.LOtext)
		               .setPositiveButton(R.string.bye, new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   Intent mfLO = new Intent(context, MainMenu.class);
		                	   startActivity(mfLO);
		                	   
				
			}
		})
        	

	              
	               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               })
	               .show();

			}
		});
        myfriends.clear();
        try {
			friendlist();
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
        Shader textShader=new LinearGradient(2, 0, 4, 60,
                new int[]{Color.parseColor("#000000"),Color.parseColor("#000000"),Color.parseColor("#000000")},
                new float[]{0, 3,1}, TileMode.MIRROR);
        txt.getPaint().setShader(textShader);

	
        }
        
	
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.my_profile:
			Intent i =new Intent(this, UserProfile.class);
			startActivity(i);
			break;
		case R.id.my_koinbox:
			i =new Intent(this, MyKoinbox.class);
			startActivity(i);
			break;
		case R.id.my_friends:
			i =new Intent(this, MyFriends.class);
			startActivity(i);
			break;
		case R.id.my_inbox:
			i =new Intent(this, Inbox.class);
			startActivity(i);
			break;
		case R.id.my_outbox:
			i =new Intent(this, Outbox.class);
			startActivity(i);
			break;
		case R.id.about_us:
			i =new Intent(this, AboutUs.class);
			startActivity(i);
			break;
		case R.id.log_out:
		    i =new Intent(this, Koinbox.class);
			startActivity(i);
			break;
		}
		
	}
	/**
	 * This function keeps track of the friends that the user have. Http Client will send HTTP Query to API to find 
	 * friend list of the given user, in this case, Koinbox.username. API delivers the query and retrieve information
	 * from the webserver in JSONArray. 
	 * 
	 * The loop runs through the JSONArray, for each item, it will insert the friend_username into the friendlist
	 * 
	 */
	public void friendlist() throws ClientProtocolException, IOException, JSONException{
		HttpGet get = new HttpGet("http://myapp-gosuninjas.dotcloud.com/api/v1/friend/?format=json&username="+Koinbox.username);
		HttpResponse r = client.execute(get);
		HttpEntity e = r.getEntity();
		String data = EntityUtils.toString(e);
		JSONObject input = new JSONObject(data);
		JSONArray friendlist_stream = input.getJSONArray("objects");
		UserProfile.myuserid = friendlist_stream.getJSONObject(0).getString("user");
		for (int i=0;i<friendlist_stream.length();i++){
			myfriends.add(friendlist_stream.getJSONObject(i).getString("friend_username"));
		}
	}
	
}
