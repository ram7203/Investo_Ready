package com.example.investoready.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investoready.Database.StockInfo;
import com.example.investoready.Home;
import com.example.investoready.R;
import com.example.investoready.StockDetails;
import com.example.investoready.StockListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StockListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockListFragment extends Fragment implements StockListAdapter.OnStockListener{
    RecyclerView recyclerView;
    List<StockInfo> stockList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StockListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StockListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockListFragment newInstance(String param1, String param2) {
        StockListFragment fragment = new StockListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewfragment);

        Home home = (Home) getActivity();
        home.toolbar.setVisibility(View.INVISIBLE);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Stocks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long value=snapshot.getChildrenCount();

                GenericTypeIndicator<List<StockInfo>> genericTypeIndicator =new GenericTypeIndicator<List<StockInfo>>(){};
                stockList=snapshot.getValue(genericTypeIndicator);
                addtorecyclerview(stockList, value);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void addtorecyclerview(List<StockInfo> stockList, long value)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new StockListAdapter(stockList, value, this));
    }

    @Override
    public void onStockClick(int position) {
        String ispositive;
        if(stockList.get(position).getChange()>0)
            ispositive="yes";
        else
            ispositive="no";

        Intent intent = new Intent(getContext(), StockDetails.class);

        intent.putExtra("2006", String.valueOf(stockList.get(position).getP2006()));
        intent.putExtra("2007", String.valueOf(stockList.get(position).getP2007()));
        intent.putExtra("2008", String.valueOf(stockList.get(position).getP2008()));
        intent.putExtra("2009", String.valueOf(stockList.get(position).getP2009()));
        intent.putExtra("2010", String.valueOf(stockList.get(position).getP2010()));
        intent.putExtra("2011", String.valueOf(stockList.get(position).getP2011()));
        intent.putExtra("2012", String.valueOf(stockList.get(position).getP2012()));
        intent.putExtra("2013", String.valueOf(stockList.get(position).getP2013()));
        intent.putExtra("2014", String.valueOf(stockList.get(position).getP2014()));
        intent.putExtra("2015", String.valueOf(stockList.get(position).getP2015()));
        intent.putExtra("2016", String.valueOf(stockList.get(position).getP2016()));
        intent.putExtra("2017", String.valueOf(stockList.get(position).getP2017()));
        intent.putExtra("2018", String.valueOf(stockList.get(position).getP2018()));
        intent.putExtra("2019", String.valueOf(stockList.get(position).getP2019()));
        intent.putExtra("2020", String.valueOf(stockList.get(position).getP2020()));
        intent.putExtra("2021", String.valueOf(stockList.get(position).getP2021()));
        intent.putExtra("2022", String.valueOf(stockList.get(position).getP2022()));

        intent.putExtra("symbol", stockList.get(position).getSymbol());
        intent.putExtra("price", String.valueOf(stockList.get(position).getPrice()));
        intent.putExtra("change", String.valueOf(stockList.get(position).getChange()));
        intent.putExtra("ispositive", ispositive);
        intent.putExtra("pe_ratio", String.valueOf(stockList.get(position).getPeRatio()));
        intent.putExtra("pb_ratio", String.valueOf(stockList.get(position).getPbRatio()));
        intent.putExtra("roe", String.valueOf(stockList.get(position).getRoe()));
        intent.putExtra("dividend_yield", String.valueOf(stockList.get(position).getDividendYield()));
        intent.putExtra("debt_to_equity", String.valueOf(stockList.get(position).getDebtToEquity()));
        intent.putExtra("about", stockList.get(position).getAboutCompany());
        startActivity(intent);
    }
}