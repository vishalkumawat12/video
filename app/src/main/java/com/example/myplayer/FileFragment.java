package com.example.myplayer;

import static com.example.myplayer.MainActivity.videoModelArrayList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myplayer.databinding.FragmentFileBinding;

import java.util.ArrayList;


public class FileFragment extends Fragment {
    FragmentFileBinding binding;
    ArrayList<videoModel> videomodel=new ArrayList<videoModel>();
    VideoAdapter videoAdapter;
    public FileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentFileBinding.inflate(inflater,container,false);

         if (videoModelArrayList !=null && videoModelArrayList.size()>0){
             videoAdapter =new VideoAdapter(getContext(),videoModelArrayList);
             binding.filesRv.setAdapter(videoAdapter);
             binding.filesRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));


         }

         return binding.getRoot();
    }
}