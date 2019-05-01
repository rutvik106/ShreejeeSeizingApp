package in.fusionbit.shreejeeseizingapp.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.fusionbit.shreejeeseizingapp.apimodels.Report;
import in.fusionbit.shreejeeseizingapp.viewholder.VhReportItem;

public class ReportsAdapter extends RecyclerView.Adapter {

    private final ArrayList<Report> reports;
    private final Context context;

    public ReportsAdapter(final Context context, ArrayList<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VhReportItem.create(context, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VhReportItem.bind((VhReportItem) holder, reports.get(position));
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }
}
