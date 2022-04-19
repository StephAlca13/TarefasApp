package br.senai.sp.cotia.todolistapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import br.senai.sp.cotia.todolistapp.databinding.FragmentCadSubTarefaBinding;

public class CadSubTarefaFragment extends Fragment {
private FragmentCadSubTarefaBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  instancia o binding
        binding = FragmentCadSubTarefaBinding.inflate(inflater, container, false);
        // retorna a view raiz do binding
        return binding.getRoot();
    }
}