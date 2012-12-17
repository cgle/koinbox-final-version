package gosuninjas.koinbox.Core;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gosuninjas.koinbox.Home;
import gosuninjas.koinbox.Koinbox;
import gosuninjas.koinbox.MainMenu;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.OtherUser.OtherUserProfile;
import gosuninjas.koinbox.OtherUser.OtherUserProfile.Read;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;
import gosuninjas.koinbox.R.string;
import gosuninjas.koinbox.User.UserProfile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
	/**
	  * This is the class that returns the matched users based on our algorithm. User A and user B has to have a 
	  * matching pair of home - destination cities and 80% or more similarity in interests
	  * There will be a progressDialog running at the start of the screen till every information is loaded
	  * Typeface() method will implement our customized UI to corresponding textviews
	  * @author Cuong
	 */
public class MyKoinbox extends Activity implements OnClickListener {
	 final Context context = this;
	 Button mkbProf, mkbMKB, mkbFriend, mkbLogout;
	 View mback;
	 HttpClient client;
	 JSONArray jsonarray;
	 ProgressDialog p;
	 
	 public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.mykoinbox);
			client = new DefaultHttpClient();
	        new Read().execute();
	        p = ProgressDialog.show(MyKoinbox.this, "", "Loading...");
			
			Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView txt = (TextView) findViewById(R.id.mTitle);  
			txt.setTypeface(font);
			Shader textShader=new LinearGradient(2, 0, 4, 60,
	                new int[]{Color.parseColor("#ffffff"),Color.parseColor("#ffffff"),Color.parseColor("#ffffff")},
	                new float[]{0, 3,1}, TileMode.MIRROR);
	        txt.getPaint().setShader(textShader);
	        mkbProf = (Button) findViewById(R.id.mProf);
			mkbProf.setOnClickListener(this);
			mkbMKB = (Button) findViewById(R.id.mMKB);
			mkbMKB.setOnClickListener(this);
			mkbFriend = (Button) findViewById(R.id.mFriends);
			mkbFriend.setOnClickListener(this);
			mback = (View) findViewById(R.id.mBack);
			mback.setOnClickListener(this);
			
			Typeface mbac = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView mba = (TextView) findViewById(R.id.mBack);  
			mba.setTypeface(mbac);
			Typeface mkbPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView mkbP = (TextView) findViewById(R.id.mProf);  
			mkbP.setTypeface(mkbPro);
			Typeface mMKB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView mkbK = (TextView) findViewById(R.id.mMKB);  
			mkbK.setTypeface(mMKB);
			Typeface mkbFR = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView mkbF = (TextView) findViewById(R.id.mFriends);  
			mkbF.setTypeface(mkbFR);
			Typeface mkbLO = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView mkbL = (TextView) findViewById(R.id.mLogOut);  
			mkbL.setTypeface(mkbLO);
			mkbLogout = (Button) findViewById(R.id.mLogOut);
			mkbLogout.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View varg) {
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

			
		}
		public void onClick(View v) {
			switch (v.getId()){
			case R.id.mProf:
				Intent mP = new Intent(this, UserProfile.class);
				startActivity(mP);
				break;
			case R.id.mMKB:
				Intent mMK = new Intent(this, MyKoinbox.class);
				startActivity(mMK);
				break;
			case R.id.mFriends:
				Intent mFRN = new Intent(this, MyFriends.class);
				startActivity(mFRN);
				break;
			case R.id.mBack:
				Intent mBK = new Intent(this, Home.class);
				startActivity(mBK);
				break;
				
			
			}
		

		}
	
	/**
	 * This class uses an HTTP client to send HTTP GET query to the API. First, it will pass username and password
	 * to satisfy the basic authentication. Then it will get to the API address and requests HttpGet the matched
	 * user lists. API transfers this request to server and after getting the results, it will return JSON Array
	 * mykoinbox
	 */
	 public JSONArray mykoinbox(String username) throws ClientProtocolException, IOException, JSONException{
		    final HttpPost httppost = new HttpPost("http://myapp-gosuninjas.dotcloud.com/login_page/"); 

		    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		    postParameters.add(new BasicNameValuePair("username", Koinbox.username));
		    postParameters.add(new BasicNameValuePair("password", Koinbox.password));
		    httppost.setEntity(new UrlEncodedFormEntity(postParameters));
		    HttpResponse response = client.execute(httppost);
            HttpGet get1 = new HttpGet("http://myapp-gosuninjas.dotcloud.com/koinbox/");
            HttpResponse r1 = client.execute(get1);
			HttpGet get = new HttpGet("http://myapp-gosuninjas.dotcloud.com/api/v1/mykoinbox/?format=json&username="+username);
			HttpResponse r = client.execute(get);
			
			
			int status = r.getStatusLine().getStatusCode();
			
			if (status == 200){
				
				HttpEntity e = r.getEntity();
				String data = EntityUtils.toString(e);
				JSONObject input = new JSONObject(data);
				JSONArray mykoinbox_stream= input.getJSONArray("objects");
				return mykoinbox_stream;
			}else{
				Toast.makeText(MyKoinbox.this, "error", Toast.LENGTH_SHORT).show();
				return null;
			}
		}
	 
	 public void ShowAlertDialog(String strErrorMessage)
	    {
	        AlertDialog adMessage = new AlertDialog.Builder(this).create();
	        adMessage.setMessage(strErrorMessage);
	        adMessage.setButton("Ok", new DialogInterface.OnClickListener() {
	            
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	            }
	        });
	        adMessage.show();
	    }
	/**
	 * This class Read extends AsyncTask to copmlete mykoinbox function in the main class. It this way, right when
	 * this app actiivity is started, mykoinbox will start, saving unnessary loading time.
	 * jsonarray is JSONArray returned from mykoinxbo.java
	 * 
	 * Each username appears on the MyKoinbox screen is clickable and linked to the respective profile page.
	 * 
	 */
	 public class Read extends AsyncTask<String, Integer, String[]>{
			@Override
			protected String[] doInBackground(String... params) {
				
				try {
					jsonarray = mykoinbox(Koinbox.username);
					String[] result_array = new String[jsonarray.length()];
					
					for (int i=0;i<jsonarray.length();i++){
					
						result_array[i]=jsonarray.getJSONObject(i).getString("koinbox_username");
					}
					p.dismiss();
					return result_array;
				
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
				
				return null;
				}
			
			@Override
			protected void onPostExecute(String[] result){
				final TableLayout layout = (TableLayout) findViewById(R.id.mykoinbox_layout);
				if (jsonarray.length()==0){
					TextView tx1 = new TextView(MyKoinbox.this);
					tx1.setText("You currently don't have any matched users!");
					layout.addView(tx1);
					Typeface kp = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
					tx1.setTypeface(kp);
				}
				else{
					TextView tx1 = new TextView(MyKoinbox.this);
					tx1.setText("*Tap the username to see the profile");
					layout.addView(tx1);
					Typeface kp = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
					tx1.setTypeface(kp);
				TextView[] tx = new TextView[jsonarray.length()];
					for (int i=0;i<tx.length;i++){
						tx[i]=new TextView(MyKoinbox.this);
						final String k = result[i];
						tx[i].setTextSize(16);
						tx[i].setBackgroundColor(Color.WHITE);
						tx[i].setText(result[i]);
						tx[i].setOnClickListener(new TextView.OnClickListener() {  
							   public void onClick(View v) {
								   OtherUserProfile.other_username=k;
								   Intent i = new Intent(MyKoinbox.this, OtherUserProfile.class);
								   startActivity(i);
							   }
							   });
						tx[i].setTypeface(kp);
						layout.addView(tx[i]);
					}
				}
			}
			
		}
	 
	 
}
