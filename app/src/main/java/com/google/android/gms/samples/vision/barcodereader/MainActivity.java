/*
 * Copyright (C) The Android Open Source Project
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
package com.google.android.gms.samples.vision.barcodereader;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import java.util.Calendar;
/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */

public class MainActivity extends Activity {//implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private Button mRegularScanButton;
    private Button mHomeScanButton;

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;
    private TextView txtView1;
    private TextView txtView2;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegularScanButton = (Button)findViewById(R.id.regularButton);
        mHomeScanButton = (Button)findViewById(R.id.homeButton);





        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);
        txtView1 = (TextView)findViewById(R.id.current_time);
        txtView2 = (TextView)findViewById(R.id.current_time1);

        mRegularScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BarcodeCaptureActivity.class);
                intent.putExtra(getString(R.string.scan_mode),"regularScan");
                startActivity(intent);
            }
        });


        mHomeScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        //autoFocus = (CompoundButton) findViewById(R.id.auto_focus);






    }

    public void add_to_database(View v) {
        InsertEntry ins_d = new InsertEntry();


        try {
            ins_d.execute(new String[] {
                    URLEncoder.encode(barcodeValue.getText().toString(), "UTF-8"),
                    URLEncoder.encode(txtView2.getText().toString() + "UTF-8"),
                    URLEncoder.encode(txtView1.getText().toString(), "UTF-8"),
                    URLEncoder.encode(txtView2.getText().toString(), "UTF-8"),
                    URLEncoder.encode(txtView1.getText().toString(), "UTF-8") });
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    /*@Override
    public void onClick(View v) {

        if (v.getId() == R.id.button1) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            //intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
        if (v.getId()==R.id.button2){
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
        }
    }
*/


    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => "+c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");

                    String formattedDate = df.format(c.getTime());
                    String formattedDate1 = df1.format(c.getTime());
                    // formattedDate have current date/time
                    Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

                    statusMessage.setText(R.string.barcode_success);

                    barcodeValue.setText(barcode.displayValue);
                    txtView1.setText("" + formattedDate);
                    txtView2.setText(""+ formattedDate1);
                   // txtView.setGravity(Gravity.CENTER);
                    txtView1.setTextSize(20);
                    txtView2.setTextSize(20);

                            Log.d(TAG, "Barcode read: " + barcode.displayValue);

                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
