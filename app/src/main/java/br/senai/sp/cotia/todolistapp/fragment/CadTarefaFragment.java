package br.senai.sp.cotia.todolistapp.fragment;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;


import java.time.Month;
import java.time.Year;
import java.util.Calendar;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.database.AppDataBase;
import br.senai.sp.cotia.todolistapp.databinding.FragmentCadTarefaBinding;
import br.senai.sp.cotia.todolistapp.model.Tarefa;


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
    // variável para acessar a database
    AppDataBase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // instanciar a appdatabase
        database = AppDataBase.getDatabase(getActivity());

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
            // passa para as variáveis globais
            year = ano;
            month = mes;
            day = dia;
            // formata a String da dataEscolhida
            dataEscolhida = String.format("%02d/%02d/%04d", day, month+1, year);
            // jogar a String no botão
            binding.btData.setText(dataEscolhida);
        }, year,month,day);

        // listener do botão de data
        binding.btData.setOnClickListener(v ->{
            // abre o datepicker
            datePicker.show();

        });

        // listener do botão salvar
        binding.btSalva.setOnClickListener(v -> {
            // validar os campos
            if (binding.tituloTarefa.getText().toString().isEmpty()){
                binding.tituloTarefa.setError(getString(R.string.infoTarefa));
                binding.tituloTarefa.requestFocus();
            }else if (dataEscolhida.isEmpty()){
                Toast.makeText(getContext(), R.string.infoData,Toast.LENGTH_SHORT).show();

            }else{
                //*******alterar a tarefa, só criar um new vazio//********
                // criar um objeto Tarefa
                Tarefa tarefa = new Tarefa();
                // popular a tarefa
                tarefa.setTitulo(binding.tituloTarefa.getText().toString());
                tarefa.setDescricao(binding.descricaoTarefa.getText().toString());
                // cria um Calendar e popula com a data que foi selecionada
                Calendar dataRealizacao = Calendar.getInstance();
                // ******se fosse verificar se a data é valida, compararia com a data atual*********
                dataRealizacao.set(year, month, day);
                // passar para tarefa os milessegundos da data
                tarefa.setDataPrevista(dataRealizacao.getTimeInMillis());
                // criar um Calendar para a data atual
                Calendar dataAtual = Calendar.getInstance();
                tarefa.setDataCriacao(dataAtual.getTimeInMillis());
                // salvar a tarefa no BD
                new InsertTarefa().execute(tarefa);

            }

        });

        // retorna a view raiz do binding
        return binding.getRoot();
    }

    // classe para a Task de Inserir Tarefa
    private class InsertTarefa extends AsyncTask<Tarefa, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.w("PASSOU", "no OnPreExecute");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.w("PASSOU", "no OnProgressUpdate");
        }

        // insere a tarefa
        @Override
        protected String doInBackground(Tarefa... tarefas) {
            Log.w("PASSOU", "no doInBackground");
            // extrair a Tarefa do vetor
            Tarefa t = tarefas[0];
            try {
                // tenta inserir
                database.getTarefaDao().insert(t);
                // retorna ok caso tenha dado certo
                return "ok";
            }catch (Exception e){
                e.printStackTrace();
                // retorna a mensagem de erro caso tenha dado erro
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String msg) {
           if (msg.equals("ok")){
               Log.w("MSG", "IUUUPIIII");
           }else {
               Log.w("MSG", msg);
               Toast.makeText(getContext(), "DEU RUIM"+msg, Toast.LENGTH_SHORT).show();
           }

        }
    }
}