package com.example.screnshot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int level;
    private TextView textView;
    private Button button;
    private TextView listAppTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        listAppTextView = findViewById(R.id.listAppTextView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText(findAppPath().toString().replace("/","")+
                        "\n"+getAndroidVersion() + "\n" + pathLevel());

                listAppTextView.setText(listApp());

            }
        });
    }

    //region Returns the package path mentioned in the appName variable
    @SuppressLint("SetTextI18n")
    public String findAppPath() {
        String appName = "com.sec.factory.camera";
        JSONObject object = new JSONObject();
        String filePath = findInstalledAppFilePath(appName);
        try {
            if (filePath != null) {
                return "Find File: " + object.put("file found",filePath);
            }
            return "Find File: " + object.put("error", "file not found ");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private String findInstalledAppFilePath(String appName) {
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(appName, 0);
            return appInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region Returns a list of applications installed on the device
    public String listApp() {
        StringBuilder stringBuilder = new StringBuilder();
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> applications = packageManager.getInstalledApplications(0);
        for (ApplicationInfo appInfo : applications) {
            stringBuilder.append(appInfo.packageName).append("\n");
        }
        return "App List" + stringBuilder.toString();
    }
    //endregion

    //region Returns the Android and OS version of the device
    public String getAndroidVersion() {
        JSONObject object =  new JSONObject();
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        try {
            for (int i = 0; i <= 2; i++) {
                object.put("release", release);
                object.put("sdkVersion", sdkVersion);
            }
            return "SDK and OS version: " + object;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region Returns the system path level
    public static String pathLevel(){
        JSONObject object = new JSONObject();
        String pathLevelmy = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pathLevelmy = Build.VERSION.SECURITY_PATCH;
        }
        try {
            object.put("path level", pathLevelmy);

            return "Path Level: " + object;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
}