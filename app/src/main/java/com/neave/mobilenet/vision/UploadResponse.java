package com.neave.mobilenet.vision;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {
    @SerializedName("predictions")
    private String[] predictions;

    public String[] getPredictions() {
        return predictions;
    }
}

