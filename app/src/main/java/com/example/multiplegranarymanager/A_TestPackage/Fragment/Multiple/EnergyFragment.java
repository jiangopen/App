package com.example.multiplegranarymanager.A_TestPackage.Fragment.Multiple;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.multiplegranarymanager.Body.ProductDetial.ParamsBody;
import com.example.multiplegranarymanager.R;
import com.example.multiplegranarymanager.Util.Util1;
import com.google.gson.Gson;

import java.util.ArrayList;

public class EnergyFragment extends Fragment {
    TextView text;
    private Util1 util = new Util1();
    private Bundle bundle = new Bundle();
    private Gson gson = new Gson();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_energy, container, false);
        text = view.findViewById(R.id.text1);
        Bundle bundle = getArguments();
        assert bundle != null;
        if (bundle.getParcelableArrayList("params")!=null){
            String params = "";
            String function = bundle.getString("function");
            ArrayList<ParamsBody> bodies = bundle.getParcelableArrayList("params");
            for (ParamsBody data : bodies){
                params += data.getProductName()+"\n"+data.getProductKey()+"\n"+data.getDeviceKey()+"\n"+data.getDeviceType()+"\n";
            }
            text.setText(function+"\n"+params);
        }
        return view;
    }
}