package com.example.healthcareapp.DashboardFragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcareapp.Api.ApiServices;
import com.example.healthcareapp.Api.RetrofitClient;
import com.example.healthcareapp.Model.covidModel;
import com.example.healthcareapp.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidFragment extends Fragment {
TextView txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_covid, container, false);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        PieChart mPieChart = (PieChart)root.findViewById(R.id.pieChart);
        txt = root.findViewById(R.id.list);

        txt.setText("");
        ApiServices api = new RetrofitClient().getRetrofit().create(ApiServices.class);
        Call<covidModel> call = api.getValue();
        call.enqueue(new Callback<covidModel>() {
            @Override
            public void onResponse(Call<covidModel> call, Response<covidModel> response) {
                covidModel covidModels = response.body();
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    txt.append(covidModels.getUpdated()+
                            "\n"+covidModels.getCases()+"\n"+covidModels.getTodayCases()
                    +"\n"+covidModels.getDeaths()+"\n"+covidModels.getTodayDeaths()
                    +"\n"+covidModels.getRecovered()+"\n"+covidModels.getTodayRecovered()
                    +"\n"+covidModels.getActive()+"\n"+covidModels.getCritical()+"\n"+covidModels.getAffectedCountries());
                }
            }

            @Override
            public void onFailure(Call<covidModel> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong !!",Toast.LENGTH_SHORT).show();
                return;
            }
        });

         covidModel covidModels = new covidModel();
        mPieChart.addPieSlice(new PieModel("Total Cases",636894241, Color.parseColor("#0730FF")));
        mPieChart.addPieSlice(new PieModel("Active Cases", 14010953, Color.parseColor("#F44336")));
        mPieChart.addPieSlice(new PieModel("Recovered", 616281449, Color.parseColor("#4CAF50")));
        mPieChart.addPieSlice(new PieModel("Deaths", 6601839, Color.parseColor("#FF040D")));
        mPieChart.startAnimation();
        return root;
    }
}