package com.example.fragmentexample1updated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {

    private Button openButton;
    private Boolean isFragmentDisplayed = false;

    private final String FRAGMENT_STATE = "fragment-state";
    private int mCurrentChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openButton = findViewById(R.id.open_btn);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFragmentDisplayed){
                    openFragment();
                }else{
                    closeFragment();
                }
            }
        });

        if(savedInstanceState != null){
            isFragmentDisplayed = savedInstanceState.getBoolean(FRAGMENT_STATE);
            if(isFragmentDisplayed){
                openButton.setText(R.string.close);
            }
        }
    }

    private void openFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SimpleFragment simpleFragment = SimpleFragment.newInstance(mCurrentChoice);
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();

        isFragmentDisplayed = true;
        openButton.setText(R.string.close);
    }

    private void closeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        fragmentTransaction.remove(simpleFragment).commit();

        isFragmentDisplayed = false;
        openButton.setText(R.string.open);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FRAGMENT_STATE, isFragmentDisplayed);
    }

    @Override
    public void onRadioButtonChoiceChecked(int choice) {
        mCurrentChoice = choice;
        Toast.makeText(this, "choice is " + String.valueOf(choice), Toast.LENGTH_SHORT).show();
    }
}