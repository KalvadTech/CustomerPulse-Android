package com.customerpulse.customerpulseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.customerpulse.customerpulsesurvey.CustomerPulseSurvey;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button enPageButton = findViewById(R.id.en_page);
        enPageButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "en");
            CustomerPulseSurvey.showSurveyPage(this, "com.kalvad.example", "F/AH/", params,false, 3000);
        });
        Button arPageButton = findViewById(R.id.ar_page);
        arPageButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "ar");
            CustomerPulseSurvey.showSurveyPage(this, "com.kalvad.example", "F/AH/", params, 3000);
        });
        Button arBottomSheetButton = findViewById(R.id.ar_bottom_sheet);
        arBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "ar");
            CustomerPulseSurvey.showSurveyBottomSheet(this, "com.kalvad.example", "F/AH/", params,false, 3000);
        });
        Button enBottomSheetButton = findViewById(R.id.en_bottom_sheet);
        enBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "en");
            CustomerPulseSurvey.showSurveyBottomSheet(this, "com.kalvad.example", "F/AH/", params, false, 3000);
        });
        Button arDismissibleBottomSheetButton = findViewById(R.id.ar_dismissable_bottom_sheet);
        arDismissibleBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "ar");
            CustomerPulseSurvey.showSurveyBottomSheet(this, "com.kalvad.example", "F/AH/", params,true, 3000);
        });
        Button enDismissibleBottomSheetButton = findViewById(R.id.en_dismissable_bottom_sheet);
        enDismissibleBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "en");
            CustomerPulseSurvey.showSurveyBottomSheet(this, "com.kalvad.example", "F/AH/", params, true, 3000);
        });

    }
}