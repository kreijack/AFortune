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

import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class AFortuneSelectList extends Activity {
	
	private AFortuneDB aFortuneDB;
	private LinearLayout linearLayout;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_selection);
        aFortuneDB = AFortuneDB.getAFortuneDB(this);
        linearLayout = (LinearLayout)findViewById(R.id.llFortunesList);
    }
    public void onStart (){
    	super.onStart();
    	initWidget();
    }
    
    private void initWidget(){    	
    	linearLayout.removeAllViews();
    	aFortuneDB.refresh();
    	String	fortunes[] = aFortuneDB.getDBs(); 
    	Set<String> enabled = AFortuneDB.array2set(aFortuneDB.getEnabledDBs());
    	
    	for(int i = 0 ; i < fortunes.length ; i++){
    		CheckBox	cb = new CheckBox(this);

    	    cb.setText(fortunes[i]);
    	    cb.setId(100+i);
    	    cb.setLayoutParams(new LayoutParams(
    	            LayoutParams.FILL_PARENT,
    	            LayoutParams.WRAP_CONTENT));
    	    
    	    class CallOnClickCB implements CheckBox.OnClickListener {
    	    	private String		fortune;

    	    	CallOnClickCB(String fortune_){
    	    		fortune = fortune_;
    	    	}
				@Override
				public void onClick(View v) {
					aFortuneDB.enabledDBs(((CheckBox)v).isChecked(), fortune);
				}
    	    	
    	    }
        	cb.setOnClickListener(new CallOnClickCB(fortunes[i]));
       		cb.setChecked(enabled.contains(fortunes[i]));
       		
    	    linearLayout.addView(cb);   		
    	}
    }

}
