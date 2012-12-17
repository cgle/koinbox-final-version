package gosuninjas.koinbox.Core;

import gosuninjas.koinbox.Home;
import gosuninjas.koinbox.Koinbox;
import gosuninjas.koinbox.MainMenu;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.Message.ViewMessage;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;
import gosuninjas.koinbox.R.string;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
/**
 * This class conducts the view Outbox activity, which allows users to open his/her outbox and view messages that he/she
 * sent to other users. The class goes with myoutbox.xml layout that for each message, it shows Subject and the recipient.
 * @author Cuong
 */
public class Outbox extends Activity implements OnClickListener {
	HttpClient client;
	ProgressDialog p;
	final Context context = this;
	Button frnProf, frnMKB, frnFriend, frnLogout;
	public List<String> subject_list = new ArrayList<String>();
	public List<String> recipient_list = new ArrayList<String>();
	public List<String> body_list = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myoutbox);
		p = ProgressDialog.show(Outbox.this, "", "Loading...");
		client = new DefaultHttpClient();
		frnProf = (Button) findViewById(R.id.fProf);
		frnProf.setOnClickListener(this);
		frnMKB = (Button) findViewById(R.id.fMKB);
		frnMKB.setOnClickListener(this);
		frnFriend = (Button) findViewById(R.id.fFriends);
	    frnFriend.setOnClickListener(this);
		Typeface pbac = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView pba = (TextView) findViewById(R.id.oBack); 
		pba.setOnClickListener(this);
		pba.setTypeface(pbac);
		Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView txt = (TextView) findViewById(R.id.oTitle);  
		txt.setTypeface(font);
		Shader textShader=new LinearGradient(2, 0, 4, 60,
                new int[]{Color.parseColor("#ffffff"),Color.parseColor("#ffffff"),Color.parseColor("#ffffff")},
                new float[]{0, 3,1}, TileMode.MIRROR);
	    txt.getPaint().setShader(textShader);
		Typeface fPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView fP = (TextView) findViewById(R.id.fProf);  
		fP.setTypeface(fPro);
		Typeface FMKB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView fK = (TextView) findViewById(R.id.fMKB);  
		fK.setTypeface(FMKB);
		Typeface fFR = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView fF = (TextView) findViewById(R.id.fFriends);  
		fF.setTypeface(fFR);
		Typeface fLO = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView fL = (TextView) findViewById(R.id.fLogOut);  
		fL.setTypeface(fLO);
		frnLogout = (Button) findViewById(R.id.fLogOut);
		frnLogout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg) {
				new AlertDialog.Builder(context)
					.setMessage(R.string.LOtext)
						.setPositiveButton(R.string.bye, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent mfLO = new Intent(context, MainMenu.class);
								mfLO.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
		try {
			getOutbox();
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
		
		/**
		 * This part of codes in the main class retrieves information from the three static array lists: subject,
		 * recipients and contents to shows them in the layout.
		 * The subject line of each message is clickable. When clicked, the app will bring the user to see
		 * the full message with sender, recipient, subject and content and even allows the user to reply or not
		 */
		final TableLayout layout = (TableLayout) findViewById(R.id.outbox_layout);
		if (subject_list.size()==0){
			TextView tx1 = new TextView(Outbox.this);
			tx1.setText("You don't have any message in your outbox!");
			layout.addView(tx1);
		}
		else{
		TextView[] tx = new TextView[subject_list.size()*3];
		int j=0;
		for (int i=0;i<subject_list.size();i++){
			tx[j]=new TextView(Outbox.this);
			tx[j+1]=new TextView(Outbox.this);
			tx[j+2]=new TextView(Outbox.this);
			tx[j].setText("To: " +recipient_list.get(i));
			tx[j+1].setText("Subject: "+subject_list.get(i));
			tx[j+2].setText("");
			Typeface pAG = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
			tx[j].setTypeface(pAG);
			tx[j+1].setTypeface(pAG);
			tx[j+2].setTypeface(pAG);
			tx[j].setBackgroundColor(Color.WHITE);
			tx[j+1].setBackgroundColor(Color.WHITE);
			final String l=recipient_list.get(i);
			final String m=subject_list.get(i);
			final String k=body_list.get(i);
			tx[j+1].setOnClickListener(new TextView.OnClickListener() {  
				   public void onClick(View v) {
					   ViewMessage.view_recipient=l;
					   ViewMessage.view_sender=Koinbox.username;
					   ViewMessage.view_subject=m;
					   ViewMessage.view_body=k;
					   Intent i = new Intent(Outbox.this, ViewMessage.class);
					   startActivity(i);
				   }	   
				   });
			layout.addView(tx[j]);
			layout.addView(tx[j+1]);
			layout.addView(tx[j+2]);
			j=j+3;
		}}
	}
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.oBack:
			Intent i = new Intent(this,Home.class);
			startActivity(i);
			break;
		case R.id.fProf:
			Intent fP = new Intent(this, UserProfile.class);
			startActivity(fP);
			break;
		case R.id.fMKB:
			Intent fMK = new Intent(this, MyKoinbox.class);
			startActivity(fMK);
			break;
		case R.id.fFriends:
			Intent fFRN = new Intent(this, MyFriends.class);
			startActivity(fFRN);
			break;
		}
	}
	/**
	 * This function assigns values to the three static array list: subject_list, recipient_list and body_list.
	 * The HTTP Client sends HTTP GET requests to the API and awaits for results. API does the job and return 
	 * JSONArray where each object in the JSONArray is a message with subject, sender, recipient and body.
	 * The function extracts needed information and puts them into the corresponding array lists.
	 * 
	 */
	public void getOutbox() throws ClientProtocolException, IOException, JSONException{
		HttpGet get = new HttpGet("http://myapp-gosuninjas.dotcloud.com/api/v1/message/?format=json&sender="+Koinbox.username);
		HttpResponse r = client.execute(get);
		HttpEntity e = r.getEntity();
		String data = EntityUtils.toString(e);
		JSONObject input = new JSONObject(data);
		JSONArray message_stream = input.getJSONArray("objects");
		for (int i=0;i<message_stream.length();i++){
			subject_list.add(message_stream.getJSONObject(i).getString("subject"));
			recipient_list.add(message_stream.getJSONObject(i).getString("recipient_username"));
			body_list.add(message_stream.getJSONObject(i).getString("body"));
		}
		p.dismiss();
		
	}

}
