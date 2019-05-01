package in.fusionbit.shreejeeseizingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.fusionbit.shreejeeseizingapp.adapters.ReportsAdapter;
import in.fusionbit.shreejeeseizingapp.api.Api;
import in.fusionbit.shreejeeseizingapp.apimodels.Report;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneratedReportActivity extends BaseActivity {

    @BindView(R.id.rv_reportList)
    RecyclerView rvReportList;

    private String fromDate, toDate;

    private ArrayList<Report> reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        setTitle("Generating...");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fromDate = getIntent().getStringExtra("from_date");
        toDate = getIntent().getStringExtra("to_date");

        getReports();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getReports() {

        showProgressDialog("Generating Report", "Please Wait...");

        Api.User.generateReport(App.getCurrentUser().getUser().getSession_id(), fromDate, toDate,
                new Callback<ArrayList<Report>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Report>> call, Response<ArrayList<Report>> response) {
                        if (response.isSuccessful()) {
                            reports = response.body();
                            runOnUiThread(() -> {
                                        setTitle("Found (" + reports.size() + ")");
                                    }
                            );
                            generateRecyclerView();
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Report>> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(GeneratedReportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void generateRecyclerView() {
        rvReportList.setLayoutManager(new LinearLayoutManager(this));
        rvReportList.setHasFixedSize(true);
        rvReportList.setAdapter(new ReportsAdapter(this, reports));
    }

    public static void start(final Context context, final String fromDate, final String toDate) {
        final Intent intent = new Intent(context, GeneratedReportActivity.class);
        intent.putExtra("from_date", fromDate);
        intent.putExtra("to_date", toDate);
        context.startActivity(intent);
    }

    @Override
    int getLayoutResourceId() {
        return R.layout.activity_generated_report;
    }
}
