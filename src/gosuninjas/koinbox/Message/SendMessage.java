package gosuninjas.koinbox.Message;

import gosuninjas.koinbox.Home;
import gosuninjas.koinbox.Koinbox;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * This class allows user to send (and reply) message to another user on the system
 * The class uses 3 static variables recipient, subject, body so that whenever creating a new message (regardless of 
 * replying or new message), just need to update/assign values to recipient, subject, and body. 
 * In a away, the class is universal in terms of usage
 * 
 * @author Cuong
 */
public class SendMessage extends Activity implements OnClickListener {
	Button send;
	EditText recipient_edit, subject_edit,body_edit;
	public static String recipient, subject, body;
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.send_message);
	     send = (Button) findViewById(R.id.send_message);
	     recipient_edit = (EditText) findViewById(R.id.recipient);
	     recipient_edit.setText(recipient);
	     
	     subject_edit = (EditText) findViewById(R.id.subject);
	     subject_edit.setText(subject);
	     body_edit = (EditText) findViewById(R.id.body);
	     send.setOnClickListener(this);
	     Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView txt = (TextView) findViewById(R.id.smTitle);  
			txt.setTypeface(font);
		Typeface sM = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
			TextView sm = (TextView) findViewById(R.id.send_message);  
			sm.setTypeface(sM);
		Typeface smr = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView sr = (TextView) findViewById(R.id.smRec);  
			sr.setTypeface(smr);
		Typeface sms = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView ss = (TextView) findViewById(R.id.smSub);  
			ss.setTypeface(sms);
		Typeface smb = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
			TextView sb = (TextView) findViewById(R.id.smBod);  
			sb.setTypeface(smb);
	}
	public void onClick(View v){
		switch (v.getId()){
		case R.id.send_message:
		   /**
		    * Once send button is clicked, static variables subject, body, and recipient are updated by 
		    * user inputs into the EditText.
		    * HTTP Client will send HTTP Post query to the API in order to create a new message from the user
		    * to the recipient. The query is set entity to a JSONObject that has subject, body, recipient,
		    * sender fields. API read the JSONObject, takes the information and transfers them to the webserver
		    * where new messages are written into the database.
		    * 
		    */
			subject=subject_edit.getText().toString();
			body=body_edit.getText().toString();
			HttpClient client = new DefaultHttpClient();  
		    HttpPost post = new HttpPost("http://myapp-gosuninjas.dotcloud.com/api/v1/message/");
		    post.setHeader("Content-type", "application/json");
		    post.setHeader("Accept", "application/json");
		    JSONObject obj = new JSONObject();
		    try {
				obj.put("subject", subject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				obj.put("body",body);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				obj.put("recipient_username", recipient);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				obj.put("sender", Koinbox.username);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
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
		    Intent i = new Intent(SendMessage.this,Home.class);
		    /**
		     * Before return to the home screen, pop up a dialog
		     */
		    new AlertDialog.Builder(SendMessage.this)
			.setMessage("The program will head back to home screen")
			.show();
		    startActivity(i);
			break;
		}
	}
}
