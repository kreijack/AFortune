package eu.kreijack.afortune;

import eu.kreijack.afortune.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class AFortuneProvider extends AppWidgetProvider {
	
	protected String TAG = "AFortuneProvider";
	private String fortuneMsg = "New text";
	protected int width, height;
	
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
		Log.d(TAG,"onEnabled");
		// start service
		startAFortuneService(context);
	}

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        Log.d(TAG,"onUpdate");
        startAFortuneService(context);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            //Intent intent = new Intent(context, ExampleActivity.class);
            //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.afortune_layout);
            views.setTextViewText(R.id.textView1, fortuneMsg);
            
            //views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
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
        
        final int N = appWidgetIds.length;
        Log.d(TAG,"AFortuneProvider.updateAllWidgetse:"+String.valueOf(N));

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            //Intent intent = new Intent(context, ExampleActivity.class);
            //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.afortune_layout);
            views.setTextViewText(R.id.textView1, fortuneMsg);
            
            //views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }      
    }
 
    
}
