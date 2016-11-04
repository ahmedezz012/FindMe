package com.ezz.findme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
           try {
               context.startService(new Intent(context, MyService.class));
           }catch (Exception ex)
           {
               Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
           }
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
