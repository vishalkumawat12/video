package com.example.myplayer;

import static com.example.myplayer.MainActivity.folderList;
import static com.example.myplayer.MainActivity.videoModelArrayList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myplayer.databinding.FragmentFolderBinding;


public class FolderFragment extends Fragment {
FolderAdapter folderAdapter;
FragmentFolderBinding binding;


    public FolderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentFolderBinding.inflate(inflater,container,false);
       if(folderList!=null && folderList.size()>0 && videoModelArrayList!=null){
           folderAdapter=new FolderAdapter(folderList,videoModelArrayList,getContext());
           binding.folrv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
           binding.folrv.setAdapter(folderAdapter);
       }
       return binding.getRoot();
    }
}