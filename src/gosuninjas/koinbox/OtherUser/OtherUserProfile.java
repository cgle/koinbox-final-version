package gosuninjas.koinbox.OtherUser;

import gosuninjas.koinbox.Home;
import gosuninjas.koinbox.Koinbox;
import gosuninjas.koinbox.MainMenu;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.Core.MyFriends;
import gosuninjas.koinbox.Core.MyKoinbox;
import gosuninjas.koinbox.Message.SendMessage;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;
import gosuninjas.koinbox.R.string;
import gosuninjas.koinbox.User.UserProfile;
import gosuninjas.koinbox.User.UserProfile.Read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This class returns the profile of an user given his/her username.
 * 2 public static Strings are other_username and userid. These two values are important because in order to open any
 * user profile, other classes in the project just needs to update other_username and startActivity.
 * @author Cuong
 */
public class OtherUserProfile extends Activity implements OnClickListener{
	ProgressDialog p;
	TextView name,age,university,home,away;
	HttpClient client;
	JSONObject json;
	JSONArray jsonarray;
	final Context context = this;
	Button ouproProf, ouproMKB, ouproFriend, ouproLogout;
	View ouback;
	public static String other_username;
	public static String userid;
	
	final static String URL_pre = "http://myapp-gosuninjas.dotcloud.com/api/v1/otheruserprofile/?format=json&user=";
	final static String URL_pre1 = "http://myapp-gosuninjas.dotcloud.com/api/v1/otheruserinterest/?format=json&user=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.otheruser_profile);
	p = ProgressDialog.show(OtherUserProfile.this, "", "Loading...");
	name = (TextView) findViewById(R.id.otheruser_name);
	age = (TextView) findViewById(R.id.otheruser_age);
	university = (TextView) findViewById(R.id.otheruser_university);
	home = (TextView) findViewById(R.id.otheruser_home_city);
	away = (TextView) findViewById(R.id.otheruser_away_city);
	client = new DefaultHttpClient();
	
	ouproProf = (Button) findViewById(R.id.ouProf);
	ouproProf.setOnClickListener(this);
	ouproMKB = (Button) findViewById(R.id.ouMKB);
	ouproMKB.setOnClickListener(this);
	ouproFriend = (Button) findViewById(R.id.ouFriends);
	ouproFriend.setOnClickListener(this);
	ouback = (View) findViewById(R.id.ouBack);
	ouback.setOnClickListener(this);
	Typeface pbac = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pba = (TextView) findViewById(R.id.ouBack);  
	pba.setTypeface(pbac);
	Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView txt = (TextView) findViewById(R.id.ouTitle);  
	txt.setTypeface(font);
	Shader textShader=new LinearGradient(2, 0, 4, 60,
            new int[]{Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF")},
            new float[]{0, 3,1}, TileMode.MIRROR);
    txt.getPaint().setShader(textShader);
	Typeface pPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pP = (TextView) findViewById(R.id.ouProf);  
	pP.setTypeface(pPro);
	Typeface PMKB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pK = (TextView) findViewById(R.id.ouMKB);  
	pK.setTypeface(PMKB);
	Typeface pFR = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pF = (TextView) findViewById(R.id.ouFriends);  
	pF.setTypeface(pFR);
	Typeface pLO = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pL = (TextView) findViewById(R.id.ouLogOut);  
	pL.setTypeface(pLO);
	Typeface pNA = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pN = (TextView) findViewById(R.id.otheruser_name);  
	pN.setTypeface(pNA);
	Typeface pAG = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pA = (TextView) findViewById(R.id.otheruser_age);  
	pA.setTypeface(pAG);
	Typeface pUN = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pU = (TextView) findViewById(R.id.otheruser_university);  
	pU.setTypeface(pUN);
	Typeface pHC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pH = (TextView) findViewById(R.id.otheruser_home_city);  
	pH.setTypeface(pHC);
	Typeface pAW = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pa = (TextView) findViewById(R.id.otheruser_away_city);  
	pa.setTypeface(pAW);
	Typeface pAC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pC = (TextView) findViewById(R.id.ouageTitle);  
	pC.setTypeface(pAC);
	Typeface pNC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pnc = (TextView) findViewById(R.id.onameTitle);  
	pnc.setTypeface(pNC);
	Typeface pUC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView puc = (TextView) findViewById(R.id.ouniversityTitle);  
	puc.setTypeface(pUC);
	Typeface pDC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pdc = (TextView) findViewById(R.id.ouawayTitle);  
	pdc.setTypeface(pDC);
	Typeface pHT = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pht = (TextView) findViewById(R.id.ouhomeTitle);  
	pht.setTypeface(pHT);
	Typeface pD = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pd = (TextView) findViewById(R.id.ouawayTitle);  
	pd.setTypeface(pD);
	ouproLogout = (Button) findViewById(R.id.ouLogOut);
	ouproLogout.setOnClickListener(new View.OnClickListener() 
	{
		
		public void onClick(View arg) {
	        new AlertDialog.Builder(context)
	        	.setMessage(R.string.LOtext)
	               .setPositiveButton(R.string.bye, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   Intent pLO = new Intent(context, MainMenu.class);
	                	   startActivity(pLO);
	                	   
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
	
	final TableLayout layout = (TableLayout) findViewById(R.id.otheruserprofile_layout);
	
	/**
	 * This mini method inside the main class checks whether the user is a friend or not. If the user is not a 
	 * friend, the app will show a clickable textview called "add to friends". Then it will call addFriend() method
	 * Otherwise, the app will show a clickable textview caleed "delete from friends". It will call delateFriend()
	 * method and after deleting the friend successfully, the app returns to Home.
	 */
	new Read().execute("name");
	TextView[] tx = new TextView[2];
	if (!Home.myfriends.contains(other_username)){
		tx[0]=new TextView(OtherUserProfile.this);
		tx[0].setText("Add to friends?");
		TableLayout.LayoutParams para=new TableLayout.LayoutParams(
			    LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
			para.setMargins(0, 0, 10, 0); //left,top,right, bottom
			tx[0].setLayoutParams(para);
		Typeface oUA = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		tx[0].setTypeface(oUA);
		tx[0].setOnClickListener(new TextView.OnClickListener() {  
			   public void onClick(View v) {
				   try {
						addFriend();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   new AlertDialog.Builder(OtherUserProfile.this)
					.setMessage("The program will head back to home screen")
					.show();
					Intent i = new Intent(OtherUserProfile.this,Home.class);
					startActivity(i);
			   }
			   });
		layout.addView(tx[0]);
	}
	else {
		tx[1]=new TextView(OtherUserProfile.this);
		tx[1].setText("Delete from friends?");
		TableLayout.LayoutParams para=new TableLayout.LayoutParams(
			    LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
			para.setMargins(0, 0, 10, 0); //left,top,right, bottom
			tx[1].setLayoutParams(para);
		Typeface k = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		tx[1].setTypeface(k);
		tx[1].setOnClickListener(new TextView.OnClickListener() {  
			   public void onClick(View v) {
				   try {
						deleteFriend();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   new AlertDialog.Builder(OtherUserProfile.this)
					.setMessage("The program will head back to home screen")
					.show();
					Intent i = new Intent(OtherUserProfile.this,Home.class);
					startActivity(i);
			   }
			   });
		layout.addView(tx[1]);
	}
	TextView tx3 = new TextView(OtherUserProfile.this);
	TableLayout.LayoutParams para=new TableLayout.LayoutParams(
		    LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		para.setMargins(0, 2, 10, 5); //left,top,right, bottom
		tx3.setLayoutParams(para);
	Typeface oUA = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	tx3.setTypeface(oUA);
	tx3.setText("Send Message!");
	tx3.setOnClickListener(new TextView.OnClickListener() {  
		public void onClick(View v) {
			SendMessage.recipient=other_username;
			SendMessage.subject="";
			Intent i = new Intent(OtherUserProfile.this,SendMessage.class);
			startActivity(i);
					}
	});
	layout.addView(tx3);
	
	
	}
	
	/**
	 * This function is called when the user is not yet a friend and we want to add friend. After passing basic authenication,
	 * It requests an HttpPOST query to the server in order to add the user to friend list 
	 * 
	 */
	public void addFriend() throws ClientProtocolException, IOException{
		final HttpPost httppost = new HttpPost("http://myapp-gosuninjas.dotcloud.com/login_page/"); 

	    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	    postParameters.add(new BasicNameValuePair("username", Koinbox.username));
	    postParameters.add(new BasicNameValuePair("password", Koinbox.password));
	    httppost.setEntity(new UrlEncodedFormEntity(postParameters));
	    HttpResponse response = client.execute(httppost);
	    
	    HttpGet get1 = new HttpGet("http://myapp-gosuninjas.dotcloud.com/friend/add/?username="+other_username);
        HttpResponse r1 = client.execute(get1);
        
	}
	/**
	 * This function is called when the user is already a friend and we want to delete the friend. After passing basic authenication,
	 * It requests an HttpDELETE query to the server in order to add the user to friend list 
	 * 
	 */
	public void deleteFriend() throws ClientProtocolException, IOException{
		final HttpPost httppost = new HttpPost("http://myapp-gosuninjas.dotcloud.com/login_page/"); 

	    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	    postParameters.add(new BasicNameValuePair("username", Koinbox.username));
	    postParameters.add(new BasicNameValuePair("password", Koinbox.password));
	    httppost.setEntity(new UrlEncodedFormEntity(postParameters));
	    HttpResponse response = client.execute(httppost);
	    
	    HttpGet get = new HttpGet("http://myapp-gosuninjas.dotcloud.com/friend/delete/?username="+other_username);
        HttpResponse r1 = client.execute(get);
        
	}
	/**
	 * This function returns a JSONObject that is the user profile of an username. 
	 * The Http Client sends an HTTP Get requests to the API for the profile. JSONObject is returned and read 
	 * by fields such as name, age, university, home_city, away_city
	 */
	public JSONObject otheruserprofile(String username) throws ClientProtocolException, IOException, JSONException{
		HttpGet get1 = new HttpGet("http://myapp-gosuninjas.dotcloud.com/api/v1/user/?format=json&username="+username);
		HttpResponse r1 = client.execute(get1);
		HttpEntity e1 = r1.getEntity();
		String data1 = EntityUtils.toString(e1);
		JSONObject input1 = new JSONObject(data1);
		JSONArray user_stream = input1.getJSONArray("objects");
		JSONObject user = user_stream.getJSONObject(0);
		userid = user.getString("resource_uri").substring(13).replace("/", "");
		
		StringBuilder url = new StringBuilder(URL_pre);
		HttpGet get = new HttpGet(url.toString()+userid);
		HttpResponse r = client.execute(get);
		int status = r.getStatusLine().getStatusCode();
		
		if (status == 200){
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			JSONObject input = new JSONObject(data);
			JSONArray profile_stream = input.getJSONArray("objects");
			JSONObject profile = profile_stream.getJSONObject(0);
			return profile;
		}else{
			Toast.makeText(OtherUserProfile.this, "error", Toast.LENGTH_SHORT).show();
			return null;
		}
	}
	/**
	 * This function returns a JSONArray that is the interest list of any user (by username). 
	 * The Http Client sends HTTP Get requests to the API for the interest list. JSONArray is returned. Each
	 * JSONObject is an interest. Each object is read by fields of type and desecriptions
	 */
	public JSONArray otheruserinterest(String username) throws ClientProtocolException, IOException, JSONException{
		StringBuilder url = new StringBuilder(URL_pre1);  
		HttpGet get = new HttpGet(url.toString()+userid);
		HttpResponse r = client.execute(get);
		int status = r.getStatusLine().getStatusCode();
		
		if (status == 200){
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			JSONObject input = new JSONObject(data);
			JSONArray interest_stream = input.getJSONArray("objects");
			return interest_stream;
		}else{
			Toast.makeText(OtherUserProfile.this, "error", Toast.LENGTH_SHORT).show();
			return null;
		}
	}
	/**
	 * This function reads data from the resulting JSONArray and JSONObject above and saves information into 
	 * one dimensional array: result_array. The first 5 values are name, age, university, home_city and away_city.
	 * The following values are interests, in particularly type and description for each interest.
	 * 
	 * Textviews are generated to show the values in the array at appropriate positions on the phone
	 */
	public class Read extends AsyncTask<String, Integer, String[]>{
		@Override
		protected String[] doInBackground(String... params) {
			try {
				json = otheruserprofile(other_username);
				jsonarray = otheruserinterest(other_username);
				String[] result_array = new String[jsonarray.length()*2+5];
				result_array[0]=json.getString("name");
				result_array[1]=json.getString("age");
				result_array[2]=json.getString("university");
				result_array[3]=json.getString("home_city");
				result_array[4]=json.getString("away_city");
				
				int j = 5;
				for (int i=0;i<jsonarray.length();i++){
					result_array[j]=jsonarray.getJSONObject(i).getString("type_interest");
					result_array[j+1]=jsonarray.getJSONObject(i).getString("description");
					j=j+2;
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
			name.setText(result[0]);
			age.setText(result[1]);
			university.setText(result[2]);
			home.setText(result[3]);
			away.setText(result[4]);
			final TableLayout layout = (TableLayout) findViewById(R.id.otheruserprofile_layout);
			TextView[] tx = new TextView[jsonarray.length()];
			if (jsonarray.length()==0){
				TextView tx1 = new TextView(OtherUserProfile.this);
				tx1.setText("This user doesn't have any interests!");
				layout.addView(tx1);
				Typeface k = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
				tx1.setTypeface(k);
			}
			else{
			int j = 5;
			for (int i=0;i<tx.length;i++){
				tx[i]=new TextView(OtherUserProfile.this);
				tx[i].setText("("+result[j]+") "+result[j+1]);
				Typeface k = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
				tx[i].setTypeface(k);
				tx[i].setTextSize(17);
				layout.addView(tx[i]);
				j=j+2;
			}
			}
		}
		
	}

	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.ouProf:
			Intent pP = new Intent(this, UserProfile.class);
			startActivity(pP);
			break;
		case R.id.ouMKB:
			Intent pMK = new Intent(this, MyKoinbox.class);
			startActivity(pMK);
			break;
		case R.id.ouFriends:
			Intent pFRN = new Intent(this, MyFriends.class);
			startActivity(pFRN);
			break;
		case R.id.ouBack:
			Intent pBK = new Intent(this, Home.class);
			startActivity(pBK);
			break;
		}
		
	}

}
