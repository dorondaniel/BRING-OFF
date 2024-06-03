package com.neave.mobilenet.vision;

import android.content.Intent;
import android.os.Bundle;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.neave.mobilenet.AbstractListActivity;
import com.neave.mobilenet.InfoViewFactory;
import com.neave.mobilenet.R;

public class VisionListActivity extends AbstractListActivity {
    int cpu_per,ram_per,bt,dbm;
    int res;
    GradientBoosting predictor = new GradientBoosting(0.9, 6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        findViewById(R.id.vision_card_preview_click_area).setOnClickListener(v -> {
            res = 0;
            if(res == 1){
                Intent ointent = new Intent(VisionListActivity.this, OffloadingActivity.class);
                ointent.putExtra(OffloadingActivity.flag,"1");
                startActivity(ointent);
            }
            else {
                final Intent intent = new Intent(VisionListActivity.this, ImageClassificationActivity.class);
                intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME,
                        "mobilenet_v2.pt");
                intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                        InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                startActivity(intent);
            }
        });
        findViewById(R.id.vision_card_capture_click_area).setOnClickListener(v -> {
            res = predictor.GradBoost(this);
            if(res == 1){
                Intent ointent = new Intent(VisionListActivity.this, OffloadingActivity.class);
                ointent.putExtra(OffloadingActivity.flag,"2");
                startActivity(ointent);
            }
            else {
                final Intent intent = new Intent(VisionListActivity.this, CaptureActivity.class);
                intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "mobilenet_v2.pt");
                intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                        InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                startActivity(intent);
            }
        });
        findViewById(R.id.vision_card_load_click_area).setOnClickListener(v -> {
            res = predictor.GradBoost(this);
            if(res == 1){
                Intent ointent = new Intent(VisionListActivity.this, OffloadingActivity.class);
                ointent.putExtra(OffloadingActivity.flag,"3");
                startActivity(ointent);
            }
            else {
                final Intent intent = new Intent(VisionListActivity.this, LoadActivity.class);
                intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "mobilenet_v2.pt");
                intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                        InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                startActivity(intent);
            }
        });

    }
    @Override
    protected int getListContentLayoutRes() {
        return R.layout.vision_list_content;
    }
}
