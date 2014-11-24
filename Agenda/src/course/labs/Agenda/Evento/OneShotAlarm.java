/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package course.labs.Agenda.Evento;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.util.Log;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm that
 * should occur once.
 * <p>
 * When the alarm goes off, we show a <i>Toast</i>, a quick message.
 */

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import course.labs.Agenda.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.net.Uri;

public class OneShotAlarm extends BroadcastReceiver {

	// Notification ID to allow for future updates
	Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt(9);
	private int MY_NOTIFICATION_ID = randomNum;
	private static final String TAG = "AlarmNotificationReceiver";

	// Notification Text Elements
	private final CharSequence tickerText = "Are You Playing Angry Birds Again!";
	private final CharSequence contentTitle = "A Kind Reminder";
	private final CharSequence contentText = "Get back to studying!!";

	// Notification Action Elements
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;

	// Notification Sound and Vibration on Arrival
	private final Uri soundURI = Uri
			.parse("android.resource://course.labs.Agenda/"
					+ R.raw.alarm_rooster);
	private final long[] mVibratePattern = { 0, 200, 200, 300 };

	@Override
	public void onReceive(Context context, Intent intent) {
		// The Intent to be used when the user clicks on the Notification View
		mNotificationIntent = new Intent(context, ManagerEvento.class);
		// The PendingIntent that wraps the underlying Intent
		mContentIntent = PendingIntent.getActivity(context, 0,
				mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

		// Build the Notification
		Notification.Builder notificationBuilder = new Notification.Builder(
				context).setTicker(tickerText)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setAutoCancel(true).setContentTitle(contentTitle)
				.setContentText(contentText).setContentIntent(mContentIntent)
				.setSound(soundURI).setVibrate(mVibratePattern);

		// Get the NotificationManager
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Pass the Notification to the NotificationManager:
		mNotificationManager.notify(MY_NOTIFICATION_ID,
				notificationBuilder.build());
		Log.i(Integer.toString(MY_NOTIFICATION_ID), "as");
		// Log occurence of notify() call
		Log.i(TAG, "Sending notification at:"
				+ DateFormat.getDateTimeInstance().format(new Date()));

	}

}