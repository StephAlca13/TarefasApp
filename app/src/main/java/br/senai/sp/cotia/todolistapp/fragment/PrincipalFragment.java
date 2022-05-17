package br.senai.sp.cotia.todolistapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.adapter.TarefaAdapter;
import br.senai.sp.cotia.todolistapp.database.AppDataBase;
import br.senai.sp.cotia.todolistapp.databinding.FragmentPrincipalBinding;
import br.senai.sp.cotia.todolistapp.model.Tarefa;

public class PrincipalFragment extends Fragment {
    private FragmentPrincipalBinding binding;
    // variável pra database
    private AppDataBase database;
    // variável para o Adaptar
    private TarefaAdapter adapter;
    // variável pra lista
    private List<Tarefa> tarefas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       //instancia o binding
        binding = FragmentPrincipalBinding.inflate(inflater, container, false);
        // click no botão de adicionar tarefa
        binding.btnAddTarefa.setOnClickListener(v ->{
            NavHostFragment.findNavController(PrincipalFragment.this).navigate(R.id.action_principalFragment_to_cadTarefaFragment);
        });

        // instancia o database
        database = AppDataBase.getDatabase(getActivity());

        // define o layout manager do recycler view
        binding.recyclerTarefas.setLayoutManager(new LinearLayoutManager(getContext()));

        // executar a async task
        new ReadTarefa().execute();

        //retorna a view raiz do binding
        return binding.getRoot();
    }

    class ReadTarefa extends AsyncTask<Void,Void, List<Tarefa>>{


        @Override
        protected List<Tarefa> doInBackground(Void... voids) {
            // guarda na variável tarefas, as tarefas do banco de dados
            tarefas = database.getTarefaDao().getAll();
            // retorna a lista de tarefas
            return tarefas;
        }

        @Override
        protected void onPostExecute(List<Tarefa> tarefas) {
            // intancia o adapter
            adapter = new TarefaAdapter(tarefas, getActivity(), listenerTarefa);
            // aplica o adapter no recyclerview
            binding.recyclerTarefas.setAdapter(adapter);
        }
    }

    // implementação da interface OnTarefaClickListener
    private TarefaAdapter.OnTarefaClickListener listenerTarefa = (view, tarefa) -> {
        // variável para transportar a tarefa (pacote)
        Bundle bundle = new Bundle();
        // "pendurar" a tarefa no pacote
        bundle.putSerializable("tarefa", tarefa);
        // navega para o próximo fragment enviando o bundle
        NavHostFragment.findNavController(PrincipalFragment.this).navigate(R.id.action_principalFragment_to_detalheTarefaFragment, bundle);
    };
}