package com.myapp.dataanalysis.sms;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.myapp.dataanalysis.R;

import java.util.ArrayList;

public class SmsLogActivity extends AppCompatActivity {


    private static SmsLogActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;

    public static SmsLogActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_log);

        smsListView = (ListView) findViewById(R.id.SMSList);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);


        // Add SMS Read Permision At Runtime
        // Todo : If Permission Is Not GRANTED
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            // Todo : If Permission Granted Then Show SMS
            refreshSmsInbox();

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(SmsLogActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }


        smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                try {
                    String[] smsMessages = smsMessagesList.get(pos).split("\n");
                    String address = smsMessages[0];
                    String smsMessage = "";
                    for (int i = 1; i < smsMessages.length; ++i) {
                        smsMessage += smsMessages[i];
                    }

                    String smsMessageStr = address + "\n";
                    smsMessageStr += smsMessage;
                    Toast.makeText(SmsLogActivity.this, smsMessageStr, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

}