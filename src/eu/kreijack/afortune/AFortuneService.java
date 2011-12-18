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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class AFortuneService extends Service {

	private int timeout = 60*60 * 1000; // (15*60*1000); // timeout in ms
	private final String TAG = "eu.kreijack.afortune.AFortuneService";
	private AlarmManager alarmManager = null;
	private PendingIntent alarmIntent;
	private final String ALARM_INTENT = "eu.kreijack.afortune.AFortuneAlarmIntent";
	private BroadcastReceiver eventReceiver;
	private AFortuneDB aFortuneDB;
	private IntentFilter intentFilter;
	private SharedPreferences settings;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// do nothing
		Log.d(TAG, "onCreate");
		settings = getSharedPreferences(Const.SHARED_PREFERENCES, 0);
		aFortuneDB = AFortuneDB.getAFortuneDB(this);
		eventReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				handleIntent(intent);
			}
		};

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent notifyIntent = new Intent();
		notifyIntent.setAction(ALARM_INTENT);
		alarmIntent = PendingIntent.getBroadcast(this, 0, notifyIntent, 0);

		intentFilter = new IntentFilter();
		intentFilter.addAction(ALARM_INTENT);
		intentFilter.addAction(Const.UPDATE_TIMEOUT);		

		registerReceiver(eventReceiver, intentFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		handleIntent(intent);
		return START_STICKY;
	}

	private void doStartService() {
		Log.d(TAG, "doStartService");
		startAlarm();
	}

	private void stopAlarm() {
		alarmManager.cancel(alarmIntent);
	}

	private void startAlarm() {
		long time = System.currentTimeMillis() + timeout;
		Log.d(TAG, "Set alarm to:" + String.valueOf(time));
		alarmManager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
	}

	private void doUpdate() {

		Log.d(TAG, "doUpdate");

		Intent i = new Intent(Const.UPDATE_FORTUNE);
		i.putExtra("msg", aFortuneDB.getRandomFortune());
		sendBroadcast(i);
		//i = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		//sendBroadcast(i);
	}
	private void loadTimeout(){
        timeout = settings.getInt("Timeout",60*60*1000);		
	}
	private void handleIntent(Intent i) {
		if ((i == null ) || (i.getAction() == null)){
			Log.d(TAG,"i||i.getAction()=null");
		}else if (Const.START_SERVICE.equals(i.getAction())) {
			doStartService();
			doUpdate();
		} else if (ALARM_INTENT.equals(i.getAction())) {
			doUpdate();
			startAlarm();
		} else if(Const.UPDATE_TIMEOUT.equals(i.getAction())){
			Log.d(TAG,"Update timeout");
			doUpdate();
	        loadTimeout();
	        stopAlarm();
	        startAlarm();
		}
	}
}
