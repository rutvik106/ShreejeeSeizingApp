package in.fusionbit.shreejeeseizingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportsActivity extends BaseActivity {

    @BindView(R.id.et_fromDate)
    TextInputEditText etFromDate;
    @BindView(R.id.et_toDate)
    TextInputEditText etToDate;

    private Calendar formDate = Calendar.getInstance();
    private DatePickerDialog datePickerFormDate;

    private Calendar toDate = Calendar.getInstance();
    private DatePickerDialog datePickerToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Reports");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        setupFromDateListeners();

        setupToDateListeners();

    }

    private void setupFromDateListeners() {

        /*etFromDate.setText(formDate.get(Calendar.DAY_OF_MONTH) + "/" + (formDate.get(Calendar.MONTH) + 1) + "/" +
                formDate.get(Calendar.YEAR));*/

        datePickerFormDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                formDate.set(Calendar.YEAR, year);
                formDate.set(Calendar.MONTH, monthOfYear);
                formDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etFromDate.setText(date);
            }
        }, formDate.get(Calendar.YEAR), formDate.get(Calendar.MONTH),
                formDate.get(Calendar.DAY_OF_MONTH));

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFormDate.show();
            }
        });

        etFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    datePickerFormDate.show();
                }
            }
        });

    }

    private void setupToDateListeners() {

        /*etToDate.setText(toDate.get(Calendar.DAY_OF_MONTH) + "/" + (toDate.get(Calendar.MONTH) + 1) + "/" +
                toDate.get(Calendar.YEAR));*/

        datePickerToDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                toDate.set(Calendar.YEAR, year);
                toDate.set(Calendar.MONTH, monthOfYear);
                toDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                etToDate.setText(date);
            }
        }, toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH),
                toDate.get(Calendar.DAY_OF_MONTH));

        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerToDate.show();
            }
        });

        etToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    datePickerToDate.show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    int getLayoutResourceId() {
        return R.layout.activity_report;
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, ReportsActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_generate)
    public void onViewClicked() {

        GeneratedReportActivity.start(this, etFromDate.getText().toString(), etToDate.getText().toString());

    }
}
