package com.example.investoready;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.investoready.databinding.ActivityStockDetailsBinding;

import java.util.Arrays;

public class StockDetails extends AppCompatActivity {
    ImageView back;
    TextView name, symbol, price, change;
    TextView pe_ratio, pb_ratio, roe, roce, dividend_yield, debt_to_equity, about;
    ActivityStockDetailsBinding binding;
//    ImageView graph;
    XYPlot plot;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_stock_details);

        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        symbol = findViewById(R.id.symbol);
        price = findViewById(R.id.price);
        change = findViewById(R.id.change);

        pe_ratio = findViewById(R.id.pe_ratio);
        pb_ratio = findViewById(R.id.pb_ratio);
        roe = findViewById(R.id.roe);
        roce = findViewById(R.id.roce);
        dividend_yield = findViewById(R.id.dividend_yield);
        debt_to_equity = findViewById(R.id.debt_to_equity);
        about = findViewById(R.id.about);

        plot = findViewById(R.id.plot);

//        graph = findViewById(R.id.graph);
//        String ch = getIntent().getStringExtra("symbol");
//
//        switch(ch)
//        {
//            case "RELIANCE.BSE":
//                graph.setImageResource(R.drawable.reliance);
//                break;
//            case "TCS.BSE":
//                graph.setImageResource(R.drawable.tcs);
//                break;
//            case "TECHM.BSE":
//                graph.setImageResource(R.drawable.techm);
//                break;
//            case "HDFCBANK.BSE":
//                graph.setImageResource(R.drawable.hdfc);
//                break;
//            case "ICICIBANK.BSE":
//                graph.setImageResource(R.drawable.icici);
//                break;
//            case "SBIN.BSE":
//                graph.setImageResource(R.drawable.sbin);
//                break;
//            case "INFY.BSE":
//                graph.setImageResource(R.drawable.infy);
//                break;
//            case "HDFC.BSE":
//                graph.setImageResource(R.drawable.hdfc);
//                break;
//            case "BHARTIARTL.BSE":
//                graph.setImageResource(R.drawable.airtel);
//                break;
//            case "ADANIENT.BSE":
//                graph.setImageResource(R.drawable.adanient);
//                break;
//            case "ASIANPAINT.BSE":
//                graph.setImageResource(R.drawable.asianpaint);
//                break;
//            case "ITC.BSE":
//                graph.setImageResource(R.drawable.itc);
//                break;
//            case "LICI.BSE":
//                graph.setImageResource(R.drawable.lici);
//                break;
//            case "BAJAJFINSV.BSE":
//                graph.setImageResource(R.drawable.bajaj);
//                break;
//            case "KOTAKBANK.BSE":
//                graph.setImageResource(R.drawable.kotak);
//                break;
//        }

        String ispositive = getIntent().getStringExtra("ispositive");
        if(ispositive=="yes")
            change.setTextColor(Color.parseColor("#ff0000"));
        else
            change.setTextColor(Color.parseColor("#00ff7f"));

        name.setText(getIntent().getStringExtra("symbol"));
        symbol.setText(getIntent().getStringExtra("symbol"));
        price.setText(getIntent().getStringExtra("price"));
        change.setText(getIntent().getStringExtra("change"));

        pe_ratio.setText(getIntent().getStringExtra("pe_ratio"));
        pb_ratio.setText(getIntent().getStringExtra("pb_ratio"));
        roe.setText(getIntent().getStringExtra("roe"));
        roce.setText(getIntent().getStringExtra("roe"));
        dividend_yield.setText(getIntent().getStringExtra("dividend_yield"));
        debt_to_equity.setText(getIntent().getStringExtra("debt_to_equity"));
        about.setText(getIntent().getStringExtra("about"));


        Number[] price = {Double.parseDouble(getIntent().getStringExtra("2006")), Double.parseDouble(getIntent().getStringExtra("2007")), Double.parseDouble(getIntent().getStringExtra("2008")), Double.parseDouble(getIntent().getStringExtra("2009")), Double.parseDouble(getIntent().getStringExtra("2010")), Double.parseDouble(getIntent().getStringExtra("2011")), Double.parseDouble(getIntent().getStringExtra("2012")), Double.parseDouble(getIntent().getStringExtra("2013")), Double.parseDouble(getIntent().getStringExtra("2014")), Double.parseDouble(getIntent().getStringExtra("2015")), Double.parseDouble(getIntent().getStringExtra("2016")), Double.parseDouble(getIntent().getStringExtra("2017")), Double.parseDouble(getIntent().getStringExtra("2018")), Double.parseDouble(getIntent().getStringExtra("2019")), Double.parseDouble(getIntent().getStringExtra("2020")), Double.parseDouble(getIntent().getStringExtra("2021")), Double.parseDouble(getIntent().getStringExtra("2022"))};
        for(int i=0;i<17;i++)
            Log.d("value", "onDataChange: "+price[i]);
        Number[] year = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(year), Arrays.asList(price), "Series 1");
        LineAndPointFormatter series1Format = new LineAndPointFormatter( Color.GREEN, Color.BLACK, null, null);
        plot.addSeries(series1, series1Format);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StockDetails.this, Home.class);
                startActivity(intent);
            }
        });
    }
}