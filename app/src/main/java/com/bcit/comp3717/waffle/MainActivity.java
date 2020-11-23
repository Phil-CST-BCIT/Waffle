package com.bcit.comp3717.waffle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import java.lang.reflect.Array;
import java.text.Format;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 107;

    private CodeScanner codeScanner;
    private CodeScannerView csView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPermissions();
        scanCode();
    }

    private void scannerSetUp() {
        csView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, csView);
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.SINGLE);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);
    }


    private void scanCode() {
        scannerSetUp();

        codeScanner.setDecodeCallback(new DecodeCallback() {

            @Override
            public void onDecoded(@NonNull final Result result) {
                final TextView tvScanContent = findViewById(R.id.scanner_view);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();

                        tvScanContent.setText(result.getText());
                    }
                });
            }
        });

        csView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull final Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("error", "camera in main error" + error.getMessage());
                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private void setPermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        String[] arr = {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, arr, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] arrStr, int[] arrInt) {
        super.onRequestPermissionsResult(requestCode, arrStr, arrInt);

        if(requestCode == CAMERA_REQUEST_CODE) {
            if(arrInt.length > 0 && arrInt[0] != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "camera permission needed.", Toast.LENGTH_LONG);
            }
    }

}


