package com.example.tfmobile;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    String modelName = "tf_model.pb";
    byte graphDef[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InputStream inputStream;
        try {
            inputStream = getAssets().open("tf_model.pb");
            byte[] buffer = new byte[inputStream.available()];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            graphDef = output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AssetManager am = this.getAssets();
        Graph graph = new Graph();
        graph.importGraphDef(graphDef);
        TensorFlowInferenceInterface tensorFlowInferenceInterface = new TensorFlowInferenceInterface(am, modelName);
        Session sess = new Session(graph);
    }

}
