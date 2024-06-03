package com.neave.mobilenet.vision;

import android.content.Context;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

public class predict {
    public int runPythonScript(Context context) {
        Python py = Python.getInstance();
        PyObject pyObj = py.getModule("runpkl");

        // Example input data (replace with actual values)
        float cpu_per = Params.CPUper();
        float ram_per = Params.getRamUsage(context);
        float bt = Params.getBatteryLevel(context);
        float dbm = Params.getWifiSignalStrength(context);

        Log.d("Cpu",String.valueOf(cpu_per));
        Log.d("Ram",String.valueOf(ram_per));
        Log.d("Bt",String.valueOf(bt));
        Log.d("dbm",String.valueOf(dbm));

        // Create an input array
        float[] inputData = {cpu_per, ram_per, bt, dbm};

        // Convert the double array to a Python list
        PyObject inputDataList = PyObject.fromJava(inputData);

        // Call the Python function with the input data
        PyObject result = pyObj.callAttr("main", inputDataList);

        // Print the result
        Log.d("Prediction", "Prediction: " + result.toString());

        return result.toInt();
    }
}
