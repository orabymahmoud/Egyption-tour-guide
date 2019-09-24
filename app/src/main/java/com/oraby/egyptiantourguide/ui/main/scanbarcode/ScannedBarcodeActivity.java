package com.oraby.egyptiantourguide.ui.main.scanbarcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.ui.Resource;
import com.oraby.egyptiantourguide.ui.main.detailsView.DetailsView;

import java.io.IOException;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class ScannedBarcodeActivity extends DaggerAppCompatActivity {

    private static final String TAG = "ScannedBarcodeActivity";
    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    boolean isEmail = false;
    ProgressBar progressBar;

    @Inject
    public ScanViewModel scanViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);

        scanViewModel.setContext(this);
        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        progressBar = findViewById(R.id.Progress_scan);


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intentData.length() > 0) {
                    Log.e(TAG, "onClick: " + intentData.substring(7) );
                    scanViewModel.getArtifact(intentData.substring(7));

                    scanViewModel.getArtifact_().observe(ScannedBarcodeActivity.this, new Observer<Resource<Artifact>>() {
                        @Override
                        public void onChanged(Resource<Artifact> artifactResource) {
                            if(artifactResource != null)
                            {
                                switch (artifactResource.status)
                                {
                                    case LOADING:{
                                        progressBar.setVisibility(View.VISIBLE);
                                        break;
                                    }

                                    case SUCCESS:{
                                        progressBar.setVisibility(View.GONE);
                                        Artifact artifact = artifactResource.data;
                                        if(artifact != null)
                                        {

                                            Intent intent = new Intent(ScannedBarcodeActivity.this , DetailsView.class);

                                            intent.putExtra("type","artifact");
                                            intent.putExtra("name",artifact.getName()+ "");
                                            intent.putExtra("likes",artifact.getLikes() + "");
                                            intent.putExtra("photo",artifact.getPhoto()+"");
                                            intent.putExtra("city",artifact.getAdress()+"");
                                            intent.putExtra("description",artifact.getDescription()+"");
                                            intent.putExtra("id",artifact.getId()+"");

                                            startActivity(intent);
                                            finish();
                                        }
                                        break;
                                    }

                                    case ERROR:{
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(ScannedBarcodeActivity.this,artifactResource.message , Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }else{
                                Toast.makeText(ScannedBarcodeActivity.this,artifactResource.message , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }


            }
        });
    }

    private void initialiseDetectorsAndSources() {


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {


                            intentData = barcodes.valueAt(0).displayValue;
                            txtBarcodeValue.setText(intentData.substring(7));
                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();


    }
}
