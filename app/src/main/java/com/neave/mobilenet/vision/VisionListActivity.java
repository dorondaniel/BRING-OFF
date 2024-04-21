package com.neave.mobilenet.vision;

import android.content.Intent;
import android.os.Bundle;

import com.chaquo.python.*;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.neave.mobilenet.AbstractListActivity;
import com.neave.mobilenet.InfoViewFactory;
import com.neave.mobilenet.R;

public class VisionListActivity extends AbstractListActivity {
    int cpu_per,ram_per,bt,dbm;
    int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        cpu_per = Params.CPUper();
        ram_per = Params.getRamUsage(this);
        bt = Params.getBatteryLevel(this);
        dbm = Params.getWifiSignalStrength(this);


        findViewById(R.id.vision_card_preview_click_area).setOnClickListener(v -> {
            Python pyIn = Python.getInstance();
            PyObject pymod = pyIn.getModule("gbclassifier");

            PyObject mod = pymod.get("prediction");
            PyObject val = mod.call(cpu_per,ram_per,bt,dbm);

            res = val.toInt();

            if(res == 1){
                Intent intent = new Intent(VisionListActivity.this, OffloadingActivity.class);
            }

            final Intent intent = new Intent(VisionListActivity.this, ImageClassificationActivity.class);
            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME,
                    "mobilenet_v2.pt");
            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
            startActivity(intent);
        });
        findViewById(R.id.vision_card_capture_click_area).setOnClickListener(v -> {
            Python pyIn = Python.getInstance();
            PyObject pymod = pyIn.getModule("gbclassifier");

            PyObject mod = pymod.get("prediction");
            PyObject val = mod.call(cpu_per,ram_per,bt,dbm);

            res = val.toInt();

            if(res == 1){
                Intent intent = new Intent(VisionListActivity.this, OffloadingActivity.class);
            }
            final Intent intent = new Intent(VisionListActivity.this, CaptureActivity.class);
            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "mobilenet_v2.pt");
            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
            startActivity(intent);
        });
        findViewById(R.id.vision_card_load_click_area).setOnClickListener(v -> {
            Python pyIn = Python.getInstance();
            PyObject pymod = pyIn.getModule("gbclassifier");

            PyObject mod = pymod.get("prediction");
            PyObject val = mod.call(cpu_per,ram_per,bt,dbm);

            res = val.toInt();

            if(res == 1){
                Intent intent = new Intent(VisionListActivity.this, OffloadingActivity.class);
            }

            final Intent intent = new Intent(VisionListActivity.this, LoadActivity.class);
            intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "mobilenet_v2.pt");
            intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                    InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
            startActivity(intent);
        });

    }

    @Override
    protected int getListContentLayoutRes() {
        return R.layout.vision_list_content;
    }
}
