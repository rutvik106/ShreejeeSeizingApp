package in.fusionbit.shreejeeseizingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.cv_seizeVehicle)
    CardView cvSeizeVehicle;
    @BindView(R.id.cv_report)
    CardView cvReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Shreejee");

        ButterKnife.bind(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            logoutMsg();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutMsg() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HomeActivity.this.finish();
                    }
                }).setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    int getLayoutResourceId() {
        return R.layout.activity_home_actvity;
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.cv_seizeVehicle)
    public void onCvSeizeVehicleClicked() {
        SeizingActivity.start(this);
    }

    @OnClick(R.id.cv_report)
    public void onCvReportClicked() {
        ReportsActivity.start(this);
    }

    @Override
    public void onBackPressed() {
        logoutMsg();
    }
}
