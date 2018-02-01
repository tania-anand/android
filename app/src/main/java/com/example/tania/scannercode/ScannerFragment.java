package com.example.tania.scannercode;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class ScannerFragment extends Fragment
{
    static ScannerFragment fragment = new ScannerFragment();
    static Context context1;


    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;


    public ScannerFragment()
    {
        // Required empty public constructor
    }


    public static ScannerFragment newInstance(Context context)
    {
        context1 = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    SurfaceView cameraView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);


        cameraView = (SurfaceView) view.findViewById(R.id.surface_view);
        button = (Button) view.findViewById(R.id.button);

        barcodeDetector = new BarcodeDetector.Builder(context1).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(context1, barcodeDetector).setRequestedPreviewSize(640, 480).build();

        if(!barcodeDetector.isOperational())
            Log.i("show","barcode not operational");

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                try
                {
                    if (ActivityCompat.checkSelfPermission(context1, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>()
        {
            @Override
            public void release()
            {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections)
            {
                Log.i("show","recieveDetections");

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if(barcodes.size()!=0)
                {
                    String barcodeInfo = String.valueOf(barcodes.valueAt(0).displayValue);

                    Log.i("show",barcodeInfo);
                    MainActivity.scanData=barcodeInfo;



                }

            }
        });

        return view;

    }








}
