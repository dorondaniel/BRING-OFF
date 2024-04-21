package com.neave.mobilenet.vision;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.neave.mobilenet.InfoViewFactory;
import com.neave.mobilenet.R;

public class OffloadingActivity extends AppCompatActivity {
    public static final String flag = "0";
    Button offbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offloading);

        offbtn = findViewById(R.id.offloadbtn);

        offbtn.setOnClickListener(v -> {
            final String off_flag = getIntent().getStringExtra(flag);
            assert off_flag != null;
            switch (off_flag) {
                case "1": {
                    final Intent intent = new Intent(OffloadingActivity.this, ImageClassificationActivity.class);
                    intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME,
                            "mobilenet_v2.pt");
                    intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                            InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                    startActivity(intent);
                    break;
                }
                case "2": {
                    final Intent intent = new Intent(OffloadingActivity.this, CaptureActivity.class);
                    intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "mobilenet_v2.pt");
                    intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                            InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                    startActivity(intent);
                    break;
                }
                case "3": {
                    final Intent intent = new Intent(OffloadingActivity.this, LoadActivity.class);
                    intent.putExtra(ImageClassificationActivity.INTENT_MODULE_ASSET_NAME, "mobilenet_v2.pt");
                    intent.putExtra(ImageClassificationActivity.INTENT_INFO_VIEW_TYPE,
                            InfoViewFactory.INFO_VIEW_TYPE_IMAGE_CLASSIFICATION_QMOBILENET);
                    startActivity(intent);
                    break;
                }
            }
        });
    }
}
