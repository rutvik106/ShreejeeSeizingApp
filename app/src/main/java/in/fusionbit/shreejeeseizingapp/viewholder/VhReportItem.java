package in.fusionbit.shreejeeseizingapp.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.fusionbit.shreejeeseizingapp.R;
import in.fusionbit.shreejeeseizingapp.ViewImageActivity;
import in.fusionbit.shreejeeseizingapp.apimodels.Report;

public class VhReportItem extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_vdNo)
    AppCompatTextView tvVdNo;
    @BindView(R.id.tv_model)
    AppCompatTextView tvModel;
    @BindView(R.id.tv_customerName)
    AppCompatTextView tvCustomerName;
    @BindView(R.id.tv_date)
    AppCompatTextView tvDate;
    @BindView(R.id.tv_sizerName)
    AppCompatTextView tvSizerName;
    @BindView(R.id.tv_vehicleLocation)
    AppCompatTextView tvVehicleLocation;
    @BindView(R.id.tv_remarks)
    AppCompatTextView tvRemarks;
    @BindView(R.id.tv_rcBookImage)
    AppCompatTextView tvRcBookImage;
    @BindView(R.id.tv_vehicleImage)
    AppCompatTextView tvVehicleImage;

    private Report model;

    private Context context;

    private VhReportItem(@NonNull View itemView, final Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public static VhReportItem create(final Context context, ViewGroup parent) {
        VhReportItem vh = new VhReportItem(LayoutInflater.from(context)
                .inflate(R.layout.vh_report_item, parent, false), context);
        return vh;
    }

    public static void bind(VhReportItem view, Report model) {
        view.model = model;

        view.tvVdNo.setText(view.model.getVd_no());
        view.tvModel.setText(view.model.getVehicle_model());
        view.tvCustomerName.setText(view.model.getCustomer_name());
        view.tvDate.setText(view.model.getSeizing_date());
        view.tvSizerName.setText(view.model.getSeizer_name());
        view.tvVehicleLocation.setText(view.model.getVehicle_location());
        view.tvRcBookImage.setText("View Image");
        view.tvVehicleImage.setText("View Image");
    }

    @OnClick(R.id.tv_rcBookImage)
    public void onTvRcBookImageClicked() {
        ViewImageActivity.start(context, model.getRc_book_image());
    }

    @OnClick(R.id.tv_vehicleImage)
    public void onTvVehicleImageClicked() {
        ViewImageActivity.start(context, model.getVehicle_image());
    }
}
