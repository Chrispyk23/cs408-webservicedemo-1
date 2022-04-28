package edu.jsu.mcis.cs408.webservicedemo;

import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.jsu.mcis.cs408.webservicedemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private WebServiceDemoViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        model = new ViewModelProvider(this).get(WebServiceDemoViewModel.class);

        // Create Observer (to update the UI with response data)

        final Observer<JSONObject> jsonObserver = new Observer<JSONObject>() {
            @Override
            public void onChanged(@Nullable final JSONObject newJSON) {
                if (newJSON != null)
                    setOutputText(newJSON.toString());
            }
        };

        // Observe the JSON LiveData

        model.getJsonData().observe(this, jsonObserver);

        // Set Button Listeners (to initiate GET/POST requests)

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.sendDeleteRequest();
            }
        });

        // Set Button Listener (to initiate POST requests)

        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.sendPostRequest();
                model.setPostMessage(binding.input.toString());
            }
        });

    }

    // Update Output Text

    private void setOutputText(String s) {
        binding.output.setText(s);
    }

