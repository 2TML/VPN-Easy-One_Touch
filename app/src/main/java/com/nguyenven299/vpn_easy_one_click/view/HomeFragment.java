package com.nguyenven299.vpn_easy_one_click.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nguyenven299.vpn_easy_one_click.R;
import com.nguyenven299.vpn_easy_one_click.databinding.FragmentHomeBinding;
import com.nguyenven299.vpn_easy_one_click.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
private HomeViewModel viewModel;
private FragmentHomeBinding databinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        databinding.setViewModel(viewModel);
        databinding.setLifecycleOwner(this);
        return databinding.getRoot();
    }
}