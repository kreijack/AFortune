/*
    AFortune: show a fortune cookie

    Copyright (C) 2011  Goffredo Baroncelli <kreijack@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
