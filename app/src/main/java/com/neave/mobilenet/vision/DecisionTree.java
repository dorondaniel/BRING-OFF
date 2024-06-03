package com.neave.mobilenet.vision;
public class DecisionTree {
    private double splitValue;
    private int splitFeature;
    private double leftValue;
    private double rightValue;

    public void fit(double[][] X, double[] y) {
        // For simplicity, use the first feature to split
        splitFeature = 0;
        splitValue = findSplitValue(X, y, splitFeature);
        double leftSum = 0, rightSum = 0;
        int leftCount = 0, rightCount = 0;

        for (int i = 0; i < X.length; i++) {
            if (X[i][splitFeature] <= splitValue) {
                leftSum += y[i];
                leftCount++;
            } else {
                rightSum += y[i];
                rightCount++;
            }
        }

        leftValue = leftSum / leftCount;
        rightValue = rightSum / rightCount;
    }

    private double findSplitValue(double[][] X, double[] y, int feature) {
        double mean = 0;
        for (double[] row : X) {
            mean += row[feature];
        }
        return mean / X.length;
    }

    public double predict(double[] x) {
        return x[splitFeature] <= splitValue ? leftValue : rightValue;
    }
}
