package br.senai.sp.cotia.todolistapp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;


import java.time.Month;
import java.time.Year;
import java.util.Calendar;

import br.senai.sp.cotia.todolistapp.databinding.FragmentCadTarefaBinding;


public class CadTarefaFragment extends Fragment {
    private FragmentCadTarefaBinding binding;
    // variável para o datepicker
    DatePickerDialog datePicker;
    // Variáveis para o dia, mês e ano
    int year, month, day;
    // variável para a data atual
    Calendar dataAtual;
    // variável para a data formatada
    String dataEscolhida = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  instancia o binding
        binding = FragmentCadTarefaBinding.inflate(inflater, container, false);

        // instancia a data atual
        dataAtual = Calendar.getInstance();

        // descobre o dia, mês e ano atuais
        year = dataAtual.get(Calendar.YEAR);
        month = dataAtual.get(Calendar.MONTH);
        day = dataAtual.get(Calendar.DAY_OF_MONTH);

        // instanciar o datepicker
        datePicker = new DatePickerDialog(getContext(), (view, ano, mes,dia) ->{
            // cai aqui toda vez que clica no OK do datePicker
        }, year,month,day);

        // listener do botão de data
        binding.btData.setOnClickListener(v ->{
            // abre o datepicker
            datePicker.show();

        });

        // retorna a view raiz do binding
        return binding.getRoot();
    }
}