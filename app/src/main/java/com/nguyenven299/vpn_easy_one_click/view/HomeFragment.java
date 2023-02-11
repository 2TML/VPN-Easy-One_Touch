package com.nguyenven299.vpn_easy_one_click.view;

import static android.app.Activity.RESULT_OK;

import static com.nguyenven299.vpn_easy_one_click.helpers.Utils.getImgURL;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.VpnService;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nguyenven299.vpn_easy_one_click.R;
import com.nguyenven299.vpn_easy_one_click.databinding.FragmentHomeBinding;
import com.nguyenven299.vpn_easy_one_click.helpers.CheckInternetConnection;
import com.nguyenven299.vpn_easy_one_click.interfaces.ChangeServer;
import com.nguyenven299.vpn_easy_one_click.model.Server;
import com.nguyenven299.vpn_easy_one_click.viewmodel.HomeViewModel;


import de.blinkt.openvpn.OpenVpnApi;
import de.blinkt.openvpn.core.OpenVPNService;
import de.blinkt.openvpn.core.OpenVPNThread;
import de.blinkt.openvpn.core.VpnStatus;

import com.nguyenven299.vpn_easy_one_click.helpers.SharedPreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements ChangeServer {
private HomeViewModel viewModel;
private FragmentHomeBinding databinding;

    private Server server;
    private CheckInternetConnection connection;
    private OpenVPNThread vpnThread;
    private OpenVPNService vpnService;
    boolean vpnStart = false;
    private SharedPreference preference;

    private ArrayList<Server> serverLists;
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
        init();
        return databinding.getRoot();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databinding.tapConnectButton.setOnClickListener((v)->{
            if (vpnStart) {
                confirmDisconnect();
            }else {
                prepareVpn();
            }
        });

        // Checking is vpn already running or not
        isServiceRunning();
        VpnStatus.initLogCache(getActivity().getCacheDir());
    }
    private void init() {
        vpnService = new OpenVPNService();
        vpnThread = new OpenVPNThread();
        vpnService.setNotificationActivityClass(getActivity().getClass());
        serverLists = getServer();

        preference = new SharedPreference(getContext());
        server = preference.getServer();
        Log.d("TAGGGGGGGName", server.getCountry());

        // Update current selected server icon
        updateCurrentServerIcon(server.getFlagUrl());

        connection = new CheckInternetConnection();
    }

    private ArrayList getServer() {
        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server("Test",getImgURL(R.mipmap.ic_app_information),"vpn_test.ovpn","vpn","vpn"));
        servers.add(new Server("Test1",getImgURL(R.mipmap.ic_app_information),"vpn_test.ovpn","vpn","vpn"));
        servers.add(new Server("Test2",getImgURL(R.mipmap.ic_app_information),"vpn_test.ovpn","vpn","vpn"));
        return servers;
    }

    public void confirmDisconnect(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.app_name));

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                stopVpn();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Prepare for vpn connect with required permission
     */
    private void prepareVpn() {
        if (!vpnStart) {
            if (getInternetStatus()) {

                // Checking permission for network monitor
                Intent intent = VpnService.prepare(getContext());

                if (intent != null) {
                    startActivityForResult(intent, 1);
                } else startVpn();//have already permission

                // Update confection status
                status("connecting");

            } else {

                // No internet connection available
                showToast("you have no internet connection !!");
            }

        } else if (stopVpn()) {

            // VPN is stopped, show a Toast message.
            showToast("Disconnect Successfully");
        }
    }

    /**
     * Stop vpn
     * @return boolean: VPN status
     */
    public boolean stopVpn() {
        try {
            vpnThread.stop();
            status("connect");
            vpnStart = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAGGGGGGGE", e.getLocalizedMessage());
        }

        return false;
    }

    /**
     * Taking permission for network access
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //Permission granted, start the VPN
            startVpn();
        } else {
            showToast("Permission Deny !! ");
        }
    }

    /**
     * Internet connection status.
     */
    public boolean getInternetStatus() {
        return connection.netCheck(getContext());
    }

    /**
     * Get service status
     */
    public void isServiceRunning() {
        setStatus(vpnService.getStatus());
    }

    /**
     * Start the VPN
     */
    private void startVpn() {
        try {
            // .ovpn file
            InputStream conf = getActivity().getAssets().open(server.getOvpn());
            InputStreamReader isr = new InputStreamReader(conf);
            BufferedReader br = new BufferedReader(isr);
            String config = "";
            String line;

            while (true) {
                line = br.readLine();
                if (line == null) break;
                config += line + "\n";
            }

            br.readLine();
            OpenVpnApi.startVpn(getContext(), config, server.getCountry(), server.getOvpnUserName(), server.getOvpnUserPassword());

            // Update log
//            binding.logTv.setText("Connecting...");
            Log.d("TAGGGGGGG","Connecting...");

            vpnStart = true;

        } catch (IOException | RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Status change with corresponding vpn connection status
     * @param connectionState
     */
    public void setStatus(String connectionState) {
        if (connectionState!= null)
            switch (connectionState) {
                case "DISCONNECTED":
                    status("connect");
                    vpnStart = false;
                    vpnService.setDefaultStatus();
                    Log.d("TAGGGGGGG","");
                    break;
                case "CONNECTED":
                    vpnStart = true;// it will use after restart this activity
                    status("connected");
                    Log.d("TAGGGGGGG","Connected");
                    break;
                case "WAIT":
                    Log.d("TAGGGGGGG","waiting for server connection!!");
                    break;
                case "AUTH":
                    Log.d("TAGGGGGGG","server authenticating!!");
                    break;
                case "RECONNECTING":
                    status("connecting");
                    Log.d("TAGGGGGGG","Reconnecting...");
                    break;
                case "NONETWORK":
                    Log.d("TAGGGGGGG","No network connection");
                    break;
            }

    }

    /**
     * Change button background color and text
     * @param status: VPN current status
     */
    public void status(String status) {
        Log.d("TAGGGGGGG1",status);

//        if (status.equals("connect")) {
//            binding.vpnBtn.setText(getContext().getString(R.string.connect));
//        } else if (status.equals("connecting")) {
//            binding.vpnBtn.setText(getContext().getString(R.string.connecting));
//        } else if (status.equals("connected")) {
//
//            binding.vpnBtn.setText(getContext().getString(R.string.disconnect));
//
//        } else if (status.equals("tryDifferentServer")) {
//
//            binding.vpnBtn.setBackgroundResource(R.drawable.button_connected);
//            binding.vpnBtn.setText("Try Different\nServer");
//        } else if (status.equals("loading")) {
//            binding.vpnBtn.setBackgroundResource(R.drawable.button);
//            binding.vpnBtn.setText("Loading Server..");
//        } else if (status.equals("invalidDevice")) {
//            binding.vpnBtn.setBackgroundResource(R.drawable.button_connected);
//            binding.vpnBtn.setText("Invalid Device");
//        } else if (status.equals("authenticationCheck")) {
//            binding.vpnBtn.setBackgroundResource(R.drawable.button_connecting);
//            binding.vpnBtn.setText("Authentication \n Checking...");
//        }

    }

    /**
     * Receive broadcast message
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                setStatus(intent.getStringExtra("state"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                String duration = intent.getStringExtra("duration");
                String lastPacketReceive = intent.getStringExtra("lastPacketReceive");
                String byteIn = intent.getStringExtra("byteIn");
                String byteOut = intent.getStringExtra("byteOut");

                if (duration == null) duration = "00:00:00";
                if (lastPacketReceive == null) lastPacketReceive = "0";
                if (byteIn == null) byteIn = " ";
                if (byteOut == null) byteOut = " ";
                updateConnectionStatus(duration, lastPacketReceive, byteIn, byteOut);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * Update status UI
     * @param duration: running time
     * @param lastPacketReceive: last packet receive time
     * @param byteIn: incoming data
     * @param byteOut: outgoing data
     */
    public void updateConnectionStatus(String duration, String lastPacketReceive, String byteIn, String byteOut) {
//        binding.durationTv.setText("Duration: " + duration);
//        binding.lastPacketReceiveTv.setText("Packet Received: " + lastPacketReceive + " second ago");
//        binding.byteInTv.setText("Bytes In: " + byteIn);
//        binding.byteOutTv.setText("Bytes Out: " + byteOut);
    }

    /**
     * Show toast message
     * @param message: toast message
     */
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * VPN server country icon change
     * @param serverIcon: icon URL
     */
    public void updateCurrentServerIcon(String serverIcon) {
//        Glide.with(getContext())
//                .load(serverIcon)
//                .into(binding.selectedServerIcon);
    }

    /**
     * Change server when user select new server
     * @param server ovpn server details
     */
    @Override
    public void newServer(Server server) {
        this.server = server;
        updateCurrentServerIcon(server.getFlagUrl());

        // Stop previous connection
        if (vpnStart) {
            stopVpn();
        }

        prepareVpn();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("connectionState"));

        if (server == null) {
            server = preference.getServer();
            Log.d("TAGGGGGGGName1", server.getCountry());
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    /**
     * Save current selected server on local shared preference
     */
    @Override
    public void onStop() {
        if (server != null) {
            preference.saveServer(server);
        }

        super.onStop();
    }
}