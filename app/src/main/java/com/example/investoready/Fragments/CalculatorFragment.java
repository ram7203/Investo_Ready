package com.example.investoready.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investoready.Home;
import com.example.investoready.R;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {
    String[] items = {"INFY", "TCS"};
    AutoCompleteTextView auto_complete;
    ArrayAdapter<String> adapterItems;
    Button tcs, infy;
    static TextView currentEval;
    static TextView dividend;
    public static EditText amount;
    static String item;
    static DecimalFormat formater = new DecimalFormat("#.##");
    Spinner spinner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
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
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Home home = (Home) getActivity();
        home.toolbar.setVisibility(View.INVISIBLE);

        tcs = view.findViewById(R.id.tcs);
        infy = view.findViewById(R.id.infy);
        currentEval = view.findViewById(R.id.currentEvaluation);
        dividend = view.findViewById(R.id.dividend);
        amount = view.findViewById(R.id.amount);

        adapterItems = new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        adapterItems.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                item = adapterView.getItemAtPosition(i).toString();
//            }
//        });

        tcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = "TCS";
                ROINew.generate();
            }
        });
        infy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = "INFY";
                ROINew.generate();
            }
        });

    }
    public static class ROINew {
        public static Boolean isBonusYear(int bonusYear[], int year) {
            for (int element : bonusYear) {
                if (element == year) {
                    return true;
                }
            }
            return false;
        }

        public static void Calculator(Double amount, Double[] price, Double dividendFactor, int bonusYear[],
                                      int bonusYearRatioFactor[]) {
            int no_of_years = 17;
            int quantity = (int) (amount / price[no_of_years - 1]);

            Double totalDividend = 0.0;

            int index = 0;
            for (int i = no_of_years - 2, year = 2006; i >= 0; i--, year++) {
                if (isBonusYear(bonusYear, year)) {
                    quantity = quantity * bonusYearRatioFactor[index];
                    index++;
                }
                totalDividend += quantity * ((price[i] * dividendFactor) / 100);
            }
            Double currentEvaluation = 3737.9 * quantity;

//            System.out.println("Current valuation: " + currentEvaluation);
//            System.out.println("Total Dividend Received over 16 years: " + totalDividend);
            currentEval.setText("Current valuation: "+String.valueOf(formater.format(currentEvaluation)));
            dividend.setText("Total Dividend recieved: "+String.valueOf(formater.format(totalDividend)));
        }

        public static void generate() {

//            Scanner sc = new Scanner(System.in);
//            System.out.print("Enter amount:");
//            Double amount = sc.nextDouble();
            String x = amount.getText().toString().trim();
            Double amount = Double.parseDouble(x);

            int TCS_bonus_year[] = { 2006, 2009, 2018 };
            int TCS_bonus_year_ratio_factor[] = { 2, 2, 2 };
            Double TCS_dividendFactor = 1.24;
            Double[] TCSPrice = { 3737.9, 3112.9, 2079.3, 2014.6, 3111.75, 2229.9, 2391.2, 2481.0, 2236.2, 1342.75, 1130.5,
                    1157.15, 735.45, 511.95, 875.25, 1278.6, 1670.1 };

            int INFY_bonus_year[] = { 2006, 2014, 2015 };
            int INFY_bonus_year_ratio_factor[] = { 2, 2, 2 };
            Double INFY_dividendFactor = 1.87;
            Double[] INFYPrice = { 1736.7, 1239.65, 776.35, 749.6, 1150.65, 929.3, 1164.85, 2142.75, 3699.45, 2788.75,
                    2743.35, 3116.3, 2476.7, 1305.5, 1503.9, 2244.45, 2879.7 };

            if(item=="TCS")
                Calculator(amount, TCSPrice, TCS_dividendFactor, TCS_bonus_year, TCS_bonus_year_ratio_factor);
            else
                Calculator(amount, INFYPrice, INFY_dividendFactor, INFY_bonus_year, INFY_bonus_year_ratio_factor);



//            Calculator(amount, TCSPrice, TCS_dividendFactor, TCS_bonus_year, TCS_bonus_year_ratio_factor);
//            Calculator(amount, INFYPrice, INFY_dividendFactor, INFY_bonus_year, INFY_bonus_year_ratio_factor);
        }
    }
}