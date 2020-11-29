package com.bcit.comp3717.waffle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    //a number for the app to request using camera
    private static final int CAMERA_REQUEST_CODE = 107;

    //an scanner object which is instantiated by using a third-party library
    private CodeScanner codeScanner;

    //the view in xml file for the scanner
    private CodeScannerView csView;

    //the text view under the scanner for displaying the content from the qr code
    private TextView tvScanContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPermissions();
        scanCode();
    }

    /**
     * sets the common attributes for the scanner object
     */
    private void scannerSetUp() {
        csView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, csView);
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);
    }


    /**
     * performs the main logic of the scanner
     */
    private void scanCode() {
        scannerSetUp();

        //when the scanner is ready, the method decodes the qr code
        codeScanner.setDecodeCallback(new DecodeCallback() {

            @Override
            public void onDecoded(@NonNull final Result result) {
                tvScanContent = findViewById(R.id.textViewBarcodeContent);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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

    /**
     * sets the permission for using the camera of the virtual scene
     */
    private void setPermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    /**
     * sends a request for using the camera
     */
    private void makeRequest() {
        String[] arr = {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, arr, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] arrStr, int[] arrInt) {
        super.onRequestPermissionsResult(requestCode, arrStr, arrInt);

        if(requestCode == CAMERA_REQUEST_CODE) {
            if(arrInt.length > 0 && arrInt[0] != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "camera permission needed.", Toast.LENGTH_LONG).show();
            }
    }

}


