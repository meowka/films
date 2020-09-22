package com.example.films;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements FragmentList.ClickListener {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentList fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragment = new FragmentList();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, BACK_STACK_ROOT_TAG);
            fragmentTransaction.addToBackStack(BACK_STACK_ROOT_TAG);
            fragmentTransaction.commit();
        }else {
            fragment = (FragmentList) getSupportFragmentManager().findFragmentByTag(BACK_STACK_ROOT_TAG);
        }
    }

    @Override
    public void onBackPressed() {
        int fragmentsInStack = getSupportFragmentManager().getBackStackEntryCount();
        if (fragmentsInStack > 1) { // If we have more than one fragment, pop back stack
            getSupportFragmentManager().popBackStack();
        } else if (fragmentsInStack == 1) { // Finish activity, if only one fragment left, to prevent leaving empty screen
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void ClickListenerFragment() {
            fragment = new FragmentList();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(BACK_STACK_ROOT_TAG);
            fragmentTransaction.commit();
    }
}