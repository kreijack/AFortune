package eu.kreijack.afortune;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {
	
	private Button		btnClose;
	private TextView	txtVersionName;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        initWidget();

    }

    private void onClose(){
    	finish();
    }
    
    public String getVersionName() 
    {
    	
    	String strVersion;
    	PackageInfo packageInfo;
    	try {
    	    packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
    	    strVersion = "Version Name: " + packageInfo.versionName +" - "
    	       + "Version Code: " + String.valueOf(packageInfo.versionCode);
    	} catch (NameNotFoundException e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	    strVersion = "Cannot load Version!";
    	}
    	
    	return strVersion;
    } 
    
    private void initWidget(){
 
    	btnClose = (Button)findViewById(R.id.btnClose);
    	btnClose.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ onClose(); }
    	});
    	txtVersionName = (TextView)findViewById(R.id.txtVersionName);
    	txtVersionName.setText(" "+getVersionName());
    	
    }
}
