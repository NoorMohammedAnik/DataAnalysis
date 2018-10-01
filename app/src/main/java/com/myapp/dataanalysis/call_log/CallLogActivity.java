package com.myapp.dataanalysis.call_log;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.myapp.dataanalysis.R;

import java.util.Date;

public class CallLogActivity extends AppCompatActivity {

    TextView txtCallHistory;
    private ProgressDialog loading;

    public int numberOfCall=0;
    public int numberOfMissedCall=0;
    public int numberOfOutgoingCall=0;
    public int numberOfIncommingCall=0;
    public int totalTalkTime=0;

    TextView txtData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);


        txtCallHistory=findViewById(R.id.txt_call_history);

        txtData=findViewById(R.id.txt_data);

//        loading = new ProgressDialog(this);
//        // loading.setIcon(R.drawable.wait_icon);
//        loading.setTitle("Call Log");
//        loading.setMessage("Please wait....");
//        loading.show();

        getCallDetails();


    }


    //method for read call logs
    private void getCallDetails() {
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log :");


        while (managedCursor.moveToNext()) {
            String contactName = managedCursor.getString(name);
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            totalTalkTime+=Integer.valueOf(callDuration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    numberOfOutgoingCall++;
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    numberOfIncommingCall++;
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    numberOfMissedCall++;
                    break;
            }
            sb.append("\nContact Name:---"+contactName+"\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");

            numberOfCall++;
        }


//        loading.dismiss();
        //managedCursor.close();
        txtCallHistory.setText(sb);

        txtData.setText("All Details:\nTotal Outgoing Call: "+numberOfOutgoingCall+"\nTotal Incoming Call: "+numberOfIncommingCall+"\nTotal Missed Call: "+numberOfMissedCall+"\nTotal Talk Time: "+(totalTalkTime)/60 +" min \n--------------");

    }

}