package elsaghier.example.com.compilertimetable;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

public class TableWebView extends AppCompatActivity {
    WebView timeTable;
    int academicYear, academicDepartment, academicSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_web_view);
        timeTable = (WebView) findViewById(R.id.timeTable);
        timeTable.getSettings().setBuiltInZoomControls(true);
        timeTable.getSettings().setDisplayZoomControls(true);
        Intent i = getIntent();
        academicYear = i.getExtras().getInt("yearSpinner");
        academicDepartment = i.getExtras().getInt("dept");
        academicSection = i.getExtras().getInt("sec");

        if(academicDepartment==-1)
            academicDepartment=0;
        if (Build.VERSION.SDK_INT >= 19) {
            timeTable.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            timeTable.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

//        timeTable.loadUrl("file:///android_asset/year" + (academicYear + 1) + "/index_" + (academicDepartment + 1) + "_" + (academicSection + 1) + ".html ");
        timeTable.loadUrl("file:///android_asset/year" + (academicYear) + "/index_" + (academicDepartment) + "_" + (academicSection) + ".html ");
    }
}
