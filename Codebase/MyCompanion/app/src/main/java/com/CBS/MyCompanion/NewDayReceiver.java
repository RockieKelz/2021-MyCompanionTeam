package com.CBS.MyCompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NewDayReceiver extends BroadcastReceiver {
    CheckUpFragment checkUpState;

    public void onReceive(Context context, Intent intent) {
        CheckUpFragment.setCheckUpState(false);
        JournalFragment.setJournalState(false);
    }

}
