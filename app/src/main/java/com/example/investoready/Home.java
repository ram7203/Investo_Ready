package com.example.investoready;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.investoready.Fragments.AccountFragment;
import com.example.investoready.Fragments.CalculatorFragment;
import com.example.investoready.Fragments.PortfolioFragment;
import com.example.investoready.Fragments.StockListFragment;
import com.example.investoready.databinding.ActivityHomeBinding;
import com.example.investoready.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    public ActivityHomeBinding binding;
    public String email;
    public Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = getIntent().getStringExtra("email");
//        Toast.makeText(this, "email: "+email, Toast.LENGTH_SHORT).show();

        replacefragment(new StockListFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.list:
                    replacefragment(new StockListFragment());
                    break;
                case R.id.portfolio:
                    replacefragment(new PortfolioFragment());
                    break;
                case R.id.calculator:
                    replacefragment(new CalculatorFragment());
                    break;
                case R.id.account:
                    replacefragment(new AccountFragment());
                    break;
            }
            return true;
        });
    }
    private void replacefragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.logout)
        {
            firebaseAuth = FirebaseAuth.getInstance();

            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);

            firebaseAuth.signOut();
        }
        else
        {
//            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
            intent.putExtra(Intent.EXTRA_TEXT, "Your application link here");
            startActivity(Intent.createChooser(intent, "Share via"));
        }
        return true;
    }

}