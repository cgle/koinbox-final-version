package gosuninjas.koinbox.User;

import gosuninjas.koinbox.Home;
import gosuninjas.koinbox.Koinbox;
import gosuninjas.koinbox.MainMenu;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.Core.MyFriends;
import gosuninjas.koinbox.Core.MyKoinbox;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;
import gosuninjas.koinbox.R.string;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;

import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
/**
 * This class returns the profile of the user.
 * The class holds important public static variables that will later be utilized in other classes. Variables include 
 * mynname, myuniversity,myage,myhome,myaway, myuserid, interestpoint, type, desciption.
 * Other classes that use these variables: edit profile, edit interests
 * @author Cuong
 */
@SuppressLint("ResourceAsColor")
public class UserProfile extends Activity implements OnClickListener{
	ProgressDialog p;
	TextView name,age,university,home,away;
	Button myinterest,mykoinbox,logout;
	final Context context = this;
	Button proProf, proMKB, proFriend, proLogout;
	View pback;
	HttpClient client;
	JSONObject json;
	JSONArray jsonarray;
	public static String myname,myuniversity,myhome,myaway,myuri,myuserid,interestpoint,type,description;
	public static int myage;


	final static String URL_pre = "http://myapp-gosuninjas.dotcloud.com/api/v1/userprofile/?format=json";
	final static String URL_pre1 = "http://myapp-gosuninjas.dotcloud.com/api/v1/interest/?format=json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.userprofile);
	p = ProgressDialog.show(UserProfile.this, "", "Loading...");
	TextView edit_profile = (TextView) findViewById(R.id.edit_profile);
	edit_profile.setOnClickListener(this);
	TextView add_interest = (TextView) findViewById(R.id.add_interest);
	add_interest.setOnClickListener(this);
	name = (TextView) findViewById(R.id.name);
	age = (TextView) findViewById(R.id.age);
	university = (TextView) findViewById(R.id.university);
	home = (TextView) findViewById(R.id.home_city);
	away = (TextView) findViewById(R.id.away_city);
	client = new DefaultHttpClient();
	new Read().execute("name");
	proProf = (Button) findViewById(R.id.pProf);
	proProf.setOnClickListener(this);
	proMKB = (Button) findViewById(R.id.pMKB);
	proMKB.setOnClickListener(this);
	proFriend = (Button) findViewById(R.id.pFriends);
	proFriend.setOnClickListener(this);
	pback = (View) findViewById(R.id.pBack);
	pback.setOnClickListener(this);
	Typeface pbac = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pba = (TextView) findViewById(R.id.pBack);  
	pba.setTypeface(pbac);
	Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView txt = (TextView) findViewById(R.id.pTitle);  
	txt.setTypeface(font);
	Shader textShader=new LinearGradient(2, 0, 4, 60,
            new int[]{Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF")},
            new float[]{0, 3,1}, TileMode.MIRROR);
    txt.getPaint().setShader(textShader);
	Typeface pPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pP = (TextView) findViewById(R.id.pProf);  
	pP.setTypeface(pPro);
	Typeface PMKB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pK = (TextView) findViewById(R.id.pMKB);  
	pK.setTypeface(PMKB);
	Typeface pFR = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pF = (TextView) findViewById(R.id.pFriends);  
	pF.setTypeface(pFR);
	Typeface pLO = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pL = (TextView) findViewById(R.id.pLogOut);  
	pL.setTypeface(pLO);
	Typeface pNA = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pN = (TextView) findViewById(R.id.name);  
	pN.setTypeface(pNA);
	Typeface pAG = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pA = (TextView) findViewById(R.id.age);  
	pA.setTypeface(pAG);
	Typeface pUN = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pU = (TextView) findViewById(R.id.university);  
	pU.setTypeface(pUN);
	Typeface pHC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pH = (TextView) findViewById(R.id.home_city);  
	pH.setTypeface(pHC);
	Typeface pAC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pC = (TextView) findViewById(R.id.ageTitle);  
	pC.setTypeface(pAC);
	Typeface pNC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pnc = (TextView) findViewById(R.id.nameTitle);  
	pnc.setTypeface(pNC);
	Typeface pUC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView puc = (TextView) findViewById(R.id.universityTitle);  
	puc.setTypeface(pUC);
	Typeface pDC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pdc = (TextView) findViewById(R.id.awayTitle);  
	pdc.setTypeface(pDC);
	Typeface pHT = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pht = (TextView) findViewById(R.id.homeTitle);  
	pht.setTypeface(pHT);
	Typeface pIC = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pic = (TextView) findViewById(R.id.interestTitle);  
	pic.setTypeface(pIC);
	Typeface pD = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
	TextView pd = (TextView) findViewById(R.id.away_city);  
	pd.setTypeface(pD);
	Typeface pEP = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pE = (TextView) findViewById(R.id.edit_profile);  
	pE.setTypeface(pEP);
	Typeface pAI = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView pai = (TextView) findViewById(R.id.add_interest);  
	pai.setTypeface(pAI);
	proLogout = (Button) findViewById(R.id.pLogOut);
	proLogout.setOnClickListener(new View.OnClickListener() 
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
	
	}

	public void onClick(View v){
		switch (v.getId()){
		case R.id.edit_profile:
			Intent i = new Intent(this, EditProfile.class);
			startActivity(i);
			break;
		case R.id.add_interest:
			i = new Intent(this, AddInterest.class);
			startActivity(i);
			break;
		case R.id.pProf:
			Intent pP = new Intent(this, UserProfile.class);
			startActivity(pP);
			break;
		case R.id.pMKB:
			Intent pMK = new Intent(this, MyKoinbox.class);
			startActivity(pMK);
			break;
		case R.id.pFriends:
			Intent pFRN = new Intent(this, MyFriends.class);
			startActivity(pFRN);
			break;
		case R.id.pBack:
			Intent pBK = new Intent(this, Home.class);
			startActivity(pBK);
			break;
		}
	}
	/**
	 * This function returns a JSONObject that is the user profile
	 * The Http Client sends an HTTP Get requests to the API for the profile. JSONObject is returned and read 
	 * by fields such as name, age, university, home_city, away_city
	 */
	public JSONObject userprofile(String username, String password) throws ClientProtocolException, IOException, JSONException{
		StringBuilder url = new StringBuilder(URL_pre);
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username,password);    

		HttpGet get = new HttpGet(url.toString());
		get.addHeader(BasicScheme.authenticate(creds, "UTF-8", false));
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
			Toast.makeText(UserProfile.this, "error", Toast.LENGTH_SHORT).show();
			return null;
		}
	}
	/**
	 * This function returns a JSONArray that is the interest list of the user.
	 * The Http Client sends HTTP Get requests to the API for the interest list of the user (
	 * Koinbox.username). JSONArray is returned. Each JSONObject is an interest. Each object is read by fields of type and desecriptions
	 */
	public JSONArray userinterest(String username, String password) throws ClientProtocolException, IOException, JSONException{
		StringBuilder url = new StringBuilder(URL_pre1);
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username,password);    

		HttpGet get = new HttpGet(url.toString());
		get.addHeader(BasicScheme.authenticate(creds, "UTF-8", false));
		HttpResponse r = client.execute(get);
		int status = r.getStatusLine().getStatusCode();

		if (status == 200){
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			JSONObject input = new JSONObject(data);
			JSONArray interest_stream = input.getJSONArray("objects");
			return interest_stream;
		}else{
			Toast.makeText(UserProfile.this, "error", Toast.LENGTH_SHORT).show();
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
	@SuppressLint("ResourceAsColor")
	public class Read extends AsyncTask<String, Integer, String[]>{
		@Override
		protected String[] doInBackground(String... params) {
			try {
				json = userprofile(Koinbox.username, Koinbox.password);
				jsonarray = userinterest(Koinbox.username, Koinbox.password);
				String[] result_array = new String[jsonarray.length()*3+5];
				result_array[0]=json.getString("name");
				result_array[1]=json.getString("age");
				result_array[2]=json.getString("university");
				result_array[3]=json.getString("home_city");
				result_array[4]=json.getString("away_city");
				myuserid = json.getString("user");
				myuri = json.getString("resource_uri");
				int j = 5;
				for (int i=0;i<jsonarray.length();i++){
					result_array[j]=jsonarray.getJSONObject(i).getString("type_interest");
					result_array[j+1]=jsonarray.getJSONObject(i).getString("description");
					result_array[j+2]=jsonarray.getJSONObject(i).getString("resource_uri").substring(17).replace("/", "");
					j=j+3;
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
		myname = result[0];
		myage = Integer.parseInt(result[1]);
		myuniversity = result[2];
		myhome = result[3];
		myaway = result[4];
		name.setText(result[0]);
		age.setText(result[1]);
		university.setText(result[2]);
		home.setText(result[3]);
		away.setText(result[4]);
		final TableLayout layout = (TableLayout) findViewById(R.id.userprofile_layout);
		TextView[] tx = new TextView[jsonarray.length()];
		if (jsonarray.length()==0){
			TextView tx1 = new TextView(UserProfile.this);
			tx1.setText("You currently don't have any interests!");
			
		}
		else{
		int j = 5;
		for (int i=0;i<tx.length;i++){
			tx[i]=new TextView(UserProfile.this);
			tx[i].setText("("+result[j]+") "+result[j+1]);
			Typeface pAG = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
			tx[i].setTypeface(pAG);
			tx[i].setTextSize(17);
			final String l = result[j];
			final String m = result[j+1];
			final String k = result[j+2];
			tx[i].setOnClickListener(new TextView.OnClickListener() {  
				   		public void onClick(View v) {
						   type = l;
						   description = m;
						   interestpoint=k;
						   Intent i = new Intent(UserProfile.this, EditInterest.class);
						   startActivity(i);
				   }
				   });
			layout.addView(tx[i]);
			j=j+3;
		}
		}
	}

	}

}
