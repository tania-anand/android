package com.example.tania.scannercode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    static String scanData ="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, ScannerFragment.newInstance(MainActivity.this),"MainFragment")
                .addToBackStack("MainFragment")
                .commit();
    }

    @Override
    public void onBackPressed()
    {

        if (getFragmentManager().getBackStackEntryCount() >= 0)
        {
            getFragmentManager().popBackStackImmediate();
        }
        else
        {
            super.onBackPressed();
        }
    }


}
