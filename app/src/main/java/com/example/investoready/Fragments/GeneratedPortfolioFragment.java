package com.example.investoready.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investoready.Database.PortfolioDetails;
import com.example.investoready.Database.SavedPortfolio;
import com.example.investoready.Database.StockInfo;
import com.example.investoready.Home;
import com.example.investoready.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneratedPortfolioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneratedPortfolioFragment extends Fragment {
    public Home home;
    static TextView name1, name2, name3, name4, name5, name6;
    static TextView sector1, sector2, sector3, sector4, sector5, sector6;
    static TextView amount1, amount2, amount3, amount4, amount5, amount6;
    static TextView price1, price2, price3, price4, price5, price6;
    static TextView quantity1, quantity2, quantity3, quantity4, quantity5, quantity6;
    static TextView invested, remaining, text;
    static DecimalFormat formater = new DecimalFormat("#.##");
    EditText budget;
    Button go, save;
    ImageButton reload;
    static Double budget1;
    static List<PortfolioDetails> stockList;
    SavedPortfolio savedPortfolio = new SavedPortfolio();
    String[] sectorNames = new String[6];
    ArrayList<Object> key = new ArrayList<Object>();
    List<SavedPortfolio> list;

//    public int index=-1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GeneratedPortfolioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneratedPortfolioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneratedPortfolioFragment newInstance(String param1, String param2) {
        GeneratedPortfolioFragment fragment = new GeneratedPortfolioFragment();
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
        return inflater.inflate(R.layout.fragment_generated_portfolio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        home = (Home) getActivity();
        home.toolbar.setVisibility(View.INVISIBLE);


        name1 = view.findViewById(R.id.name1);
        name2 = view.findViewById(R.id.name2);
        name3 = view.findViewById(R.id.name3);
        name4 = view.findViewById(R.id.name4);
        name5 = view.findViewById(R.id.name5);
        name6 = view.findViewById(R.id.name6);

        sector1 = view.findViewById(R.id.sector1);
        sector2 = view.findViewById(R.id.sector2);
        sector3 = view.findViewById(R.id.sector3);
        sector4 = view.findViewById(R.id.sector4);
        sector5 = view.findViewById(R.id.sector5);
        sector6 = view.findViewById(R.id.sector6);

        amount1 = view.findViewById(R.id.amount1);
        amount2 = view.findViewById(R.id.amount2);
        amount3 = view.findViewById(R.id.amount3);
        amount4 = view.findViewById(R.id.amount4);
        amount5 = view.findViewById(R.id.amount5);
        amount6 = view.findViewById(R.id.amount6);

        price1 = view.findViewById(R.id.price1);
        price2 = view.findViewById(R.id.price2);
        price3 = view.findViewById(R.id.price3);
        price4 = view.findViewById(R.id.price4);
        price5 = view.findViewById(R.id.price5);
        price6 = view.findViewById(R.id.price6);

        quantity1 = view.findViewById(R.id.quantity1);
        quantity2 = view.findViewById(R.id.quantity2);
        quantity3 = view.findViewById(R.id.quantity3);
        quantity4 = view.findViewById(R.id.quantity4);
        quantity5 = view.findViewById(R.id.quantity5);
        quantity6 = view.findViewById(R.id.quantity6);

        budget = view.findViewById(R.id.budget);
        go = view.findViewById(R.id.go);
        invested = view.findViewById(R.id.invested);
        remaining = view.findViewById(R.id.remaining);
        text = view.findViewById(R.id.text);
        save = view.findViewById(R.id.save);
        reload = view.findViewById(R.id.reload);


        portfolioExists(home.email);
//        Toast.makeText(home, "index2: "+index, Toast.LENGTH_SHORT).show();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("PortfolioData");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long value=snapshot.getChildrenCount();
                GenericTypeIndicator<List<PortfolioDetails>> genericTypeIndicator =new GenericTypeIndicator<List<PortfolioDetails>>(){};
                stockList=snapshot.getValue(genericTypeIndicator);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail!", Toast.LENGTH_SHORT).show();
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = budget.getText().toString().trim();
                if(temp.isEmpty())
                    Toast.makeText(getContext(), "Enter Budget!", Toast.LENGTH_SHORT).show();
                else
                {
                    budget1 = Double.parseDouble(temp);
                    PortfolioGeneration obj = new PortfolioGeneration();
                    obj.generatePortfolio("");
                }
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = budget.getText().toString().trim();
                if(temp.isEmpty())
                    Toast.makeText(getContext(), "Enter budget!", Toast.LENGTH_SHORT).show();
                else
                {
                    budget1 = Double.parseDouble(temp);
                    PortfolioGeneration obj = new PortfolioGeneration();
                    obj.generatePortfolio("");
                }
            }
        });
    }

    public class PortfolioGeneration {
        public void generatePortfolio(String updatekey) {

            int noOfSectors = 6, i, j, k;

            double sectoralAmount = budget1 / noOfSectors;
            int upperBound = 3;
            int lowerBound = 0;

//            String[] sectorNames = new String[6];
            String[] stockNames = new String[18];

            sectorNames[0] = stockList.get(0).getSector();
            sectorNames[1] = stockList.get(3).getSector();
            sectorNames[2] = stockList.get(6).getSector();
            sectorNames[3] = stockList.get(9).getSector();
            sectorNames[4] = stockList.get(12).getSector();
            sectorNames[5] = stockList.get(15).getSector();


            for(i=0;i<18;i++)
                stockNames[i] = stockList.get(i).getSymbol();

            String sectoralStocks[][] = new String[6][3];//get values from DB
            Double[][] sectoralStocksPrice = new Double[6][3];//get values from DB
            k=0;
            for (i = 0; i < 6; i++) {
                for (j = 0; j < 3; j++) {
                    sectoralStocks[i][j] = stockList.get(k).getSymbol();
                    k++;
                }
            }
            k=0;
            for (i = 0; i < 6; i++) {
                for (j = 0; j < 3; j++) {
                    sectoralStocksPrice[i][j] = stockList.get(k).getPrice();
                    k++;
                }
            }

            Random rand = new Random();

            String topSectorWise[] = new String[noOfSectors]; // to be printed
            Double topSectorWisePrice[] = new Double[noOfSectors]; // to be printed
            int quantity[] = new int[6]; // to be printed
            Double totalSectoralWiseAllotted[] = new Double[6]; // to be printed
            Double remainder[] = new Double[6];
            Double totalAllocated = 0.0;

            for (i = 0; i < noOfSectors; i++) {
                int random_integer = rand.nextInt(upperBound - lowerBound) + lowerBound;
                topSectorWise[i] = sectoralStocks[i][random_integer];
                topSectorWisePrice[i] = sectoralStocksPrice[i][random_integer];
                quantity[i] = (int) (sectoralAmount / topSectorWisePrice[i]);
                remainder[i] = sectoralAmount % (topSectorWisePrice[i] * quantity[i]);
                totalSectoralWiseAllotted[i] = topSectorWisePrice[i] * quantity[i];
                totalAllocated += totalSectoralWiseAllotted[i];
            }

            Double totalRemaining = budget1 - totalAllocated;

            Double min = Double.MAX_VALUE;

            int index = 0;
            for (i = 0; i < noOfSectors; i++) {
                if (totalRemaining > topSectorWisePrice[i] && topSectorWisePrice[i] < min) {
                    min = topSectorWisePrice[i];
                    index = i;
                }
            }

            Double finalTotalAllocated = totalAllocated;
            Double finalTotalRemaining = totalRemaining;
            if (min != 0.0) {
                int additionalQuantity = 0;
                while (totalRemaining > additionalQuantity * min) {
                    additionalQuantity++;
                }
                additionalQuantity--;
                System.out.println(additionalQuantity);
                quantity[index] += additionalQuantity;
                finalTotalAllocated = finalTotalAllocated + additionalQuantity * min;
                finalTotalRemaining = budget1 - finalTotalAllocated;
                totalSectoralWiseAllotted[index] = totalSectoralWiseAllotted[index] + additionalQuantity * min;

            }

            name1.setText(topSectorWise[0]);
            name2.setText(topSectorWise[1]);
            name3.setText(topSectorWise[2]);
            name4.setText(topSectorWise[3]);
            name5.setText(topSectorWise[4]);
            name6.setText(topSectorWise[5]);

            sector1.setText(sectorNames[0]);
            sector2.setText(sectorNames[1]);
            sector3.setText(sectorNames[2]);
            sector4.setText(sectorNames[3]);
            sector5.setText(sectorNames[4]);
            sector6.setText(sectorNames[5]);

            price1.setText(String.valueOf(formater.format(topSectorWisePrice[0])));
            price2.setText(String.valueOf(formater.format(topSectorWisePrice[1])));
            price3.setText(String.valueOf(formater.format(topSectorWisePrice[2])));
            price4.setText(String.valueOf(formater.format(topSectorWisePrice[3])));
            price5.setText(String.valueOf(formater.format(topSectorWisePrice[4])));
            price6.setText(String.valueOf(formater.format(topSectorWisePrice[5])));


            quantity1.setText(String.valueOf(formater.format(quantity[0])));
            quantity2.setText(String.valueOf(formater.format(quantity[1])));
            quantity3.setText(String.valueOf(formater.format(quantity[2])));
            quantity4.setText(String.valueOf(formater.format(quantity[3])));
            quantity5.setText(String.valueOf(formater.format(quantity[4])));
            quantity6.setText(String.valueOf(formater.format(quantity[5])));


            amount1.setText(String.valueOf(formater.format(totalSectoralWiseAllotted[0])));
            amount2.setText(String.valueOf(formater.format(totalSectoralWiseAllotted[1])));
            amount3.setText(String.valueOf(formater.format(totalSectoralWiseAllotted[2])));
            amount4.setText(String.valueOf(formater.format(totalSectoralWiseAllotted[3])));
            amount5.setText(String.valueOf(formater.format(totalSectoralWiseAllotted[4])));
            amount6.setText(String.valueOf(formater.format(totalSectoralWiseAllotted[5])));

            remaining.setText(String.valueOf(formater.format(finalTotalRemaining)));
            invested.setText(String.valueOf(formater.format(finalTotalAllocated)));
            text.setText("TOTAL AMOUNT INVESTED");

            save.setVisibility(View.VISIBLE);
            reload.setVisibility(View.VISIBLE);

            //saving generated portfolio
            Double finalTotalAllocated1 = finalTotalAllocated;
            Double finalTotalRemaining1 = finalTotalRemaining;
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    savedPortfolio.setEmail(home.email.toString());

                    savedPortfolio.setName1(topSectorWise[0]);
                    savedPortfolio.setName2(topSectorWise[1]);
                    savedPortfolio.setName3(topSectorWise[2]);
                    savedPortfolio.setName4(topSectorWise[3]);
                    savedPortfolio.setName5(topSectorWise[4]);
                    savedPortfolio.setName6(topSectorWise[5]);

                    savedPortfolio.setAmount1(totalSectoralWiseAllotted[0]);
                    savedPortfolio.setAmount2(totalSectoralWiseAllotted[1]);
                    savedPortfolio.setAmount3(totalSectoralWiseAllotted[2]);
                    savedPortfolio.setAmount4(totalSectoralWiseAllotted[3]);
                    savedPortfolio.setAmount5(totalSectoralWiseAllotted[4]);
                    savedPortfolio.setAmount6(totalSectoralWiseAllotted[5]);

                    savedPortfolio.setPrice1(topSectorWisePrice[0]);
                    savedPortfolio.setPrice2(topSectorWisePrice[1]);
                    savedPortfolio.setPrice3(topSectorWisePrice[2]);
                    savedPortfolio.setPrice4(topSectorWisePrice[3]);
                    savedPortfolio.setPrice5(topSectorWisePrice[4]);
                    savedPortfolio.setPrice6(topSectorWisePrice[5]);

                    savedPortfolio.setQuantity1(quantity[0]);
                    savedPortfolio.setQuantity2(quantity[1]);
                    savedPortfolio.setQuantity3(quantity[2]);
                    savedPortfolio.setQuantity4(quantity[3]);
                    savedPortfolio.setQuantity5(quantity[4]);
                    savedPortfolio.setQuantity6(quantity[5]);

                    savedPortfolio.setAllocated(finalTotalAllocated1);
                    savedPortfolio.setRemaining(finalTotalRemaining1);

                    FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance();
                    DatabaseReference databaseRef = firebaseDb.getReference().child("SavedPortfolio");

                    if(updatekey.equals(""))
                    {
                        databaseRef.push().setValue(savedPortfolio);
                        Toast.makeText(home, "Portfolio Saved", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        databaseRef.child(updatekey).setValue(savedPortfolio);
                        Toast.makeText(home, "Portfolio updated!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void portfolioExists(String email)
    {
        FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDb.getReference().child("SavedPortfolio");
        final int[] index = new int[1];
        final int[] value = new int[1];

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value[0] = (int) snapshot.getChildrenCount();
                list = new ArrayList<SavedPortfolio>();
                for (DataSnapshot child: snapshot.getChildren()) {
                    list.add(child.getValue(SavedPortfolio.class));
                    key.add(child.getKey());
                }

                int i;

                for(i=0; i< value[0]; i++)
                {
                    if(list.get(i).getEmail().equals(email))
                    {
                        index[0] = i;
                        break;
                    }
                }
                if(i == value[0])
                {
                    Toast.makeText(home, "Portfolio doesnt exist", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(home, "Portfolio exists", Toast.LENGTH_SHORT).show();
                    printPortfolioData(index[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(home, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void printPortfolioData(int index)
    {
        sector1.setText("BANKNIFTY");
        sector2.setText("FINNIFTY");
        sector3.setText("NIFTYAUTO");
        sector4.setText("NIFTYIT");
        sector5.setText("NIFTYMCG");
        sector6.setText("NIFTYPHARMA");

        name1.setText(list.get(index).getName1());
        name2.setText(list.get(index).getName2());
        name3.setText(list.get(index).getName3());
        name4.setText(list.get(index).getName4());
        name5.setText(list.get(index).getName5());
        name6.setText(list.get(index).getName6());

        price1.setText(String.valueOf(formater.format(list.get(index).getPrice1())));
        price2.setText(String.valueOf(formater.format(list.get(index).getPrice2())));
        price3.setText(String.valueOf(formater.format(list.get(index).getPrice3())));
        price4.setText(String.valueOf(formater.format(list.get(index).getPrice4())));
        price5.setText(String.valueOf(formater.format(list.get(index).getPrice5())));
        price6.setText(String.valueOf(formater.format(list.get(index).getPrice6())));

        quantity1.setText(String.valueOf(formater.format(list.get(index).getQuantity1())));
        quantity2.setText(String.valueOf(formater.format(list.get(index).getQuantity2())));
        quantity3.setText(String.valueOf(formater.format(list.get(index).getQuantity3())));
        quantity4.setText(String.valueOf(formater.format(list.get(index).getQuantity4())));
        quantity5.setText(String.valueOf(formater.format(list.get(index).getQuantity5())));
        quantity6.setText(String.valueOf(formater.format(list.get(index).getQuantity6())));

        amount1.setText(String.valueOf(formater.format(list.get(index).getAmount1())));
        amount2.setText(String.valueOf(formater.format(list.get(index).getAmount2())));
        amount3.setText(String.valueOf(formater.format(list.get(index).getAmount3())));
        amount4.setText(String.valueOf(formater.format(list.get(index).getAmount4())));
        amount5.setText(String.valueOf(formater.format(list.get(index).getAmount5())));
        amount6.setText(String.valueOf(formater.format(list.get(index).getAmount6())));

        remaining.setText(String.valueOf(formater.format(list.get(index).getRemaining())));
        invested.setText(String.valueOf(formater.format(list.get(index).getAllocated())));
        text.setText("TOTAL AMOUNT INVESTED");

        save.setVisibility(View.VISIBLE);
        reload.setVisibility(View.VISIBLE);

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generating new portfolio
                String temp = budget.getText().toString().trim();
                if(temp.isEmpty())
                    Toast.makeText(home, "Enter Budget!", Toast.LENGTH_SHORT).show();
                else
                {
                    budget1 = Double.parseDouble(temp);
                    PortfolioGeneration obj = new PortfolioGeneration();
                    obj.generatePortfolio(String.valueOf(key.get(index)));
                }
                //saving newly generated portfolio

            }
        });
    }
}

