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

import eu.kreijack.afortune.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;

public class AFortuneProvider extends AppWidgetProvider {
	
	protected String TAG = "AFortuneProvider";
	private String fortuneMsg = "New text";
	protected int width, height;
	private static final int defaultBGColor = Color.BLACK;
	private static final int defaultFGColor = Color.BLACK;
	
	public AFortuneProvider(int w, int h){
		super();
		width=w;
		height=h;
	}
	public AFortuneProvider(){
		super();
		width=4;
		height=1;
	}
	
	private void startAFortuneService(Context context){
		Intent i = new Intent(Const.START_SERVICE);
		i.setClass(context, AFortuneService.class);
		context.startService(i);
	}

	public void onEnabled(Context context){
		//Log.d(TAG,"onEnabled");
		// start service
		startAFortuneService(context);
	}

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.d(TAG,"onUpdate");    	
    	updateAllWidgets(context);
    	
/*    	
        final int N = appWidgetIds.length;

        startAFortuneService(context);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            
            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.afortune_layout);
            views.setTextViewText(R.id.textView1, fortuneMsg);
 
            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, AFortuneFortune.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);            
            views.setOnClickPendingIntent(R.id.textView1, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
*/
    }
    
    public void onReceive(Context context, Intent intent){
		Log.d(TAG,"ReceivedIntent:"+intent.getAction());    	
    	if(Const.UPDATE_FORTUNE.equals(intent.getAction())){
    		fortuneMsg = intent.getStringExtra("msg");
    		updateAllWidgets(context);
    	}else{
    		super.onReceive(context,intent);
    	}
    }

    protected ComponentName getComponentName(Context context){
    	return new ComponentName(context, AFortuneProvider.class);
    }
    private void updateAllWidgets(Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
        			getComponentName(context));
        SharedPreferences settings = context.getSharedPreferences(Const.SHARED_PREFERENCES, 0);
        //final int N = appWidgetIds.length;
        //Log.d(TAG,"AFortuneProvider.updateAllWidgetse:"+String.valueOf(N));

        float textSize = settings.getFloat("TextSize", -1);
        int   transparent = settings.getInt("Transparent", -1);
        int   fgcolor = settings.getInt("FGColor", defaultFGColor);
        int   bgcolor = settings.getInt("BGColor", defaultBGColor);
        
        //Log.d(TAG,String.format("fgcolor=0x%08x; bgcolor=0x%08x; transparent=%d", 
        //			fgcolor, bgcolor, transparent));
        
        
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.afortune_layout);
            views.setTextViewText(R.id.textView1, fortuneMsg);
            if(textSize != -1)
            	views.setFloat(R.id.textView1, "setTextSize",textSize );
            if(transparent != -1){
            	views.setInt(R.id.textView1, "setBackgroundColor",
            			Color.argb(transparent, Color.red(bgcolor), Color.green(bgcolor), 
            			Color.blue(bgcolor)));
            }
        	views.setInt(R.id.textView1, "setTextColor",
        			Color.rgb(Color.red(fgcolor), Color.green(fgcolor), 
        			Color.blue(fgcolor)));

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, AFortuneFortune.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);            
            views.setOnClickPendingIntent(R.id.textView1, pendingIntent);       	
        	
            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }      
    }
 
    
}
