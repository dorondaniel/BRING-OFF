package com.neave.mobilenet.vision;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GradientBoosting {
    private List<DecisionTree> trees;
    private double learningRate;
    private int nEstimators;

    public GradientBoosting(double learningRate, int nEstimators) {
        this.trees = new ArrayList<>();
        this.learningRate = learningRate;
        this.nEstimators = nEstimators;
    }

    public void fit(double[][] X, double[] y) {
        double[] residuals = y.clone();

        for (int i = 0; i < nEstimators; i++) {
            DecisionTree tree = new DecisionTree();
            tree.fit(X, residuals);

            for (int j = 0; j < X.length; j++) {
                residuals[j] -= learningRate * tree.predict(X[j]);
            }

            trees.add(tree);
        }
    }

    public double predict(double[] x) {
        double prediction = 0.0;

        for (DecisionTree tree : trees) {
            prediction += learningRate * tree.predict(x);
        }

        return prediction;
    }

    public int GradBoost(Context context) {
        Log.d("GradientBoosting", "Inside GradBoost function...");
        // Load the dataset
        double[][] X;
        double[] y;

        try {
            List<double[]> featuresList = new ArrayList<>();
            List<Double> labelsList = new ArrayList<>();

            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("dataset/sample_dataset.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] features = new double[values.length - 1];
                for (int i = 0; i < values.length - 1; i++) {
                    features[i] = Double.parseDouble(values[i]);
                }
                double label = Double.parseDouble(values[values.length - 1]);
                featuresList.add(features);
                labelsList.add(label);
            }
            br.close();

            X = featuresList.toArray(new double[0][0]);
            y = labelsList.stream().mapToDouble(Double::doubleValue).toArray();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        // Split the dataset into training and test sets
        double[][] trainX, testX;
        double[] trainY, testY;
        int trainSize = (int) (X.length * 0.8);
        int testSize = X.length - trainSize;

        trainX = new double[trainSize][];
        testX = new double[testSize][];
        trainY = new double[trainSize];
        testY = new double[testSize];

        Random rand = new Random();
        for (int i = 0; i < trainSize; i++) {
            int idx = rand.nextInt(X.length);
            trainX[i] = X[idx];
            trainY[i] = y[idx];
        }
        for (int i = 0; i < testSize; i++) {
            int idx = rand.nextInt(X.length);
            testX[i] = X[idx];
            testY[i] = y[idx];
        }

        // Initialize and train the model
        GradientBoosting gb = new GradientBoosting(0.9, 6);
        gb.fit(trainX, trainY);

        // Predict and calculate accuracy
        int correct = 0;
        for (int i = 0; i < testX.length; i++) {
            double prediction = gb.predict(testX[i]);
            prediction = prediction >= 0.5 ? 1.0 : 0.0;  // Convert to binary prediction
            if (prediction == testY[i]) {
                correct++;
            }
        }

        Log.d("GradientBoosting", "Accuracy: " + (double) correct / testX.length);

        float cpu_per = Params.CPUper();
        float ram_per = Params.getRamUsage(context);
        float bt = Params.getBatteryLevel(context);
        float dbm = Params.getWifiSignalStrength(context);

        double[] inputData = {cpu_per, ram_per, bt, dbm};

        double prediction = gb.predict(inputData);

        prediction = (prediction >= 0.5 ? 1.0 : 0.0);

        Log.d("GradientBoosting", "Prediction: " + prediction);

        return (int) prediction;
    }
}
