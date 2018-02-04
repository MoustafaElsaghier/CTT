package elsaghier.example.com.compilertimetable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MaterialSpinner yearSpinner, sectionSpinner, departmentSpinner;
    Button displayTable;
    ArrayList<String> yearItems, departmentItems, sectionNums;
    ImageView ic_face;

    int academicYear, academicSection, academicDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        ic_face = (ImageView) findViewById(R.id.ic_face);
        yearSpinner = (MaterialSpinner) findViewById(R.id.academic_year);
        sectionSpinner = (MaterialSpinner) findViewById(R.id.academic_section);
        departmentSpinner = (MaterialSpinner) findViewById(R.id.academic_department);
        displayTable = (Button) findViewById(R.id.showTable);
        yearItems = new ArrayList<>();
        departmentItems = new ArrayList<>();
        sectionNums = new ArrayList<>();

        sectionSpinner.setEnabled(false);
        setSectionSpinner(0); // for first time display section spinner in Enabled mode false with text
        setYear();
        displayTable();
    }


    public void fillDerpartment() {
        // re filling department array
        departmentItems.clear();
        departmentItems.add("اختر القسم");
        departmentItems.add("Cs");
        departmentItems.add("Is");
        departmentItems.add("It");
        if (!yearSpinner.getText().toString().equals("الفرقه الرابعه"))
            departmentItems.add("Or");
        departmentSpinner.setItems(departmentItems);
    }

    public void setDepartment() {

        departmentSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (departmentSpinner.getSelectedIndex() != 0) {
                    displayTable.setEnabled(true);
                }
                academicSection = -1;
                sectionSpinner.setEnabled(true);
                sectionSpinner.setVisibility(View.VISIBLE);

                // specifiy nuber of sectionSpinner in every departmentSpinner
                if (yearSpinner.getText().toString().equals("الفرقه الثالثه")) {
                    switch (departmentSpinner.getText().toString()) {
                        case "Cs":
                            setSectionSpinner(5);
                            break;
                        case "Is":
                            setSectionSpinner(2);
                            break;
                        case "It":
                            setSectionSpinner(5);
                            break;
                        case "Or":
                            setSectionSpinner(1);
                            break;
                    }
                } else if (yearSpinner.getText().toString().equals("الفرقه الرابعه")) {
                    switch (departmentSpinner.getText().toString()) {
                        case "Cs":
                            setSectionSpinner(4);
                            break;
                        case "Is":
                            setSectionSpinner(3);
                            break;
                        case "It":
                            setSectionSpinner(5);
                            break;
                    }
                }
            }
        });
    }

    public void setSectionSpinner(int num) {
        sectionNums.clear();
        sectionNums.add("اختر رقم السكشن");
        for (int i = 0; i < num; ++i)
            sectionNums.add(String.valueOf(i + 1));
        sectionSpinner.setItems(sectionNums);
    }

    public void setYear() {
        yearItems.clear(); // for reusing items and not add on each others
        yearItems.add("اختر فرقتك");
        yearItems.add("الفرقه الاولى");
        yearItems.add("الفرقه الثانيه");
        yearItems.add("الفرقه الثالثه");
        yearItems.add("الفرقه الرابعه");
        yearSpinner.setItems(yearItems);

        final boolean[] firstTime = {false};

        yearSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                /*
                 * the next commented section for removing first item in year spinner
                 */

                // for remove choose year
                if (!firstTime[0]) {
                    yearItems.remove(0);
                    firstTime[0] = true;
                    yearSpinner.setSelectedIndex(position - 1);
                    yearSpinner.setItems(yearItems);
                }
                academicDepartment = -1;
                academicSection = -1;
                setSectionSpinner(0);

                switch (yearSpinner.getText().toString()) {
                    case "الفرقه الثالثه":
                    case "الفرقه الرابعه":
                        sectionSpinner.setEnabled(false); // must select department for enable it
                        sectionSpinner.setSelectedIndex(0);
                        displayTable.setEnabled(false);
                        departmentSpinner.setSelectedIndex(0);
                        fillDerpartment();
                        setDepartment();
                        departmentSpinner.setVisibility(View.VISIBLE);
                        break;
                    case "الفرقه الاولى":
                        displayTable.setEnabled(true);
                        sectionSpinner.setEnabled(true);
                        departmentSpinner.setVisibility(View.GONE);
                        setSectionSpinner(14);
                        break;
                    case "الفرقه الثانيه":
                        displayTable.setEnabled(true);
                        sectionSpinner.setEnabled(true);
                        departmentSpinner.setVisibility(View.GONE);
                        setSectionSpinner(12);
                        break;
                }
            }
        });
    }

    public void displayTable() {
        displayTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                academicYear = yearSpinner.getSelectedIndex(); // 0 1 2 3 4 no need zero
                academicSection = sectionSpinner.getSelectedIndex();// 0 1 2 3 4 5 6 no need zero
                academicDepartment = departmentSpinner.getSelectedIndex();// 0 1 2 3 4 no need zero
                Intent intent = new Intent(MainActivity.this, TableWebView.class);
                intent.putExtra("yearSpinner", academicYear + 1);
                intent.putExtra("dept", academicDepartment);
                intent.putExtra("sec", academicSection);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reset();
    }

    public void reset() {
        academicYear = -1;
        academicSection = -1;
        academicDepartment = -1;

        setYear();
        departmentSpinner.setSelectedIndex(0);
        departmentSpinner.setVisibility(View.GONE);

        displayTable.setEnabled(false);
        sectionSpinner.setEnabled(false);
        sectionSpinner.setSelectedIndex(0);
        yearSpinner.setSelectedIndex(0);
        displayTable();
    }


    public void faceBookPage(View view) {
        //
        String fb_page = "https://www.facebook.com/compilercommunity";
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fb_page));
        Intent chooser = Intent.createChooser(sendIntent, " Choose Your Browser");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}
