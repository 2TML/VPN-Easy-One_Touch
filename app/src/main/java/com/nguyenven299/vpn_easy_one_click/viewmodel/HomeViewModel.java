package com.nguyenven299.vpn_easy_one_click.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> tapConnectButtonText = new MutableLiveData<>("Click to connect VPN");

    public MutableLiveData<String> getTapConnectButtonText() {
        return tapConnectButtonText;
    }

    public void setTapConnectButtonText(String tapConnectButtonText) {
        this.tapConnectButtonText = new MutableLiveData<>(tapConnectButtonText);
    }
}
