package gosuninjas.koinbox;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;



public class Menu extends Activity {
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {  
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.menu, menu);
	   return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_home:
	        	Intent i = new Intent(this, Home.class);
	        	startActivity(i);
	        	break;
	        case R.id.menu_back:
	        	finish();
	        	break;
	        case R.id.menu_logout:
	        	i = new Intent(this,Koinbox.class);
	        	startActivity(i);
	        	break;
	    }
	    return true;
	}

}