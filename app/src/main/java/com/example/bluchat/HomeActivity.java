package com.example.bluchat;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button buttonOn, buttonOff;
    BluetoothAdapter myBluetoothAdapter;

    Intent btEnablingIntent;
    int requestCodeForEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        buttonOn =  (Button) findViewById(R.id.btOn);
        buttonOff = (Button) findViewById(R.id.btOff);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestCodeForEnable = 1;



        bluetoothOnMethod();
        bluetoothOffMethod();
    }

    private void bluetoothOffMethod() {
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBluetoothAdapter.isEnabled())
                {
                    myBluetoothAdapter.disable();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==requestCodeForEnable)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(getApplicationContext(),"Bluetooth is Enabled",Toast.LENGTH_LONG ).show();
                Intent ListIntent = new Intent(HomeActivity.this, ListActivity.class);
                startActivity(ListIntent);

            } else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(),"Bluetooth Enabling Cancelled",Toast.LENGTH_LONG).show();
            }
        }
    }


    private void bluetoothOnMethod() {
        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBluetoothAdapter==null)
                {
                    Toast.makeText(getApplicationContext(),"Bluetooth not supported on this Device",Toast.LENGTH_LONG).show();
                } else
                {
                    if(!myBluetoothAdapter.isEnabled())
                    {
                        startActivityForResult(btEnablingIntent,requestCodeForEnable);
                    }
                }
            }
        });
    }

}
