package gosuninjas.koinbox;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/** Class for allowing user to sign in by using username and password stored as ids of the user. 
 * 
 * Once successful, the app returns the user to the home screen.
 * 
 **/
public class Koinbox extends Activity implements OnClickListener {
	public static String username;
	public static String password;
	EditText username_box, password_box;
	Button Login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Login = (Button)findViewById(R.id.login);
        
        Login.setOnClickListener(this);
        username_box = (EditText) findViewById(R.id.username);
        
        password_box = (EditText) findViewById(R.id.password);
		Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView txt = (TextView) findViewById(R.id.title);  
		txt.setTypeface(font);
		Typeface sIN = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView si = (TextView) findViewById(R.id.login);  
		si.setTypeface(sIN);
		
        Shader textShader=new LinearGradient(2, 0, 4, 60,
                new int[]{Color.parseColor("#000000"),Color.parseColor("#000000"),Color.parseColor("#000000")},
                new float[]{0, 3,1}, TileMode.MIRROR);
        txt.getPaint().setShader(textShader);
    }
    
    public void onClick(View v){
    	switch (v.getId()){
    		/**
    	 * If forms are filled correctly (meaning it pass all conditions) main class will call checkuser() to 
    	 * look for username and password in the databse by connecting to web app through the API and getting information from there. 
    	 * 
    	 */
    	case R.id.login:
    		Intent i =new Intent(this, Home.class);
    		username = username_box.getText().toString();
    		password = password_box.getText().toString();
    		try {
				if (!username.equals("") && !password.equals("") &&checkuser(username)){
						HttpClient client;
						client = new DefaultHttpClient();
						StringBuilder url = new StringBuilder("http://myapp-gosuninjas.dotcloud.com/api/v1/userprofile/?format=json");
						UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username,password);    
						HttpGet get = new HttpGet(url.toString());
						get.addHeader(BasicScheme.authenticate(creds, "UTF-8", false));
						HttpResponse r=null;
						
						try {
							r = client.execute(get);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						int status = r.getStatusLine().getStatusCode();
						
						if (status != 200){
							new AlertDialog.Builder(Koinbox.this)
								.setMessage("Username and password don't match!")
								.show();
						}
						else if (status == 200) {
							startActivity(i);
							break;}
				}
				else if (username.equals("") || password.equals("")) {
					new AlertDialog.Builder(Koinbox.this)
					.setMessage("Please fill all forms!")
					.show();
				} else
					try {
						if (!checkuser(username)){
							new AlertDialog.Builder(Koinbox.this)
							.setMessage("Username not found!")
							.show();
						
						}
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
    
     /** checks whether the user name id and password id match. it also checks if the user id exists or not.
     * 
     * @param username
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws JSONException
     */
    public static boolean checkuser(String username) throws ClientProtocolException, IOException, JSONException{
    	HttpClient client = new DefaultHttpClient();
    	boolean result = false;
    	HttpGet get = new HttpGet("http://myapp-gosuninjas.dotcloud.com/api/v1/user/?format=json");
		HttpResponse r = client.execute(get);
		HttpEntity e = r.getEntity();
		String data = EntityUtils.toString(e);
		JSONObject input = new JSONObject(data);
		JSONArray user_list = input.getJSONArray("objects");
		for (int i=0;i<user_list.length();i++){
			if (username.equals(user_list.getJSONObject(i).getString("username"))){
				result=true;
				break;
					
			}}
		return result;
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
}

