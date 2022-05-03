package br.senai.sp.cotia.todolistapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.model.Tarefa;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {
    // lista de tarefas
    private List<Tarefa> tarefas;
    // variável para o Context
    private Context context;

    // construtor pra receber os valores
    public TarefaAdapter(List<Tarefa> lista, Context contexto){
        this.tarefas = lista;
        this.context = contexto;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infla o layout do adapter
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_tarefas, parent, false);
        // retorna um novo ViewHolder com a view
        return new TarefaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        // obtém a tarefa pela position
        Tarefa t = tarefas.get(position);
        // exibe o título da tarefa no text view
        holder.tvTitulo.setText(t.getTitulo());
        // exibe a descrição da tarefa
        holder.tvDescricao.setText(t.getDescricao());
        // se estiver concluída
        if(t.isConcluida()){
            holder.tvStatus.setText(R.string.tfConcluida);
            holder.tvStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
        }else{
            holder.tvStatus.setText(R.string.tfAberta);
            holder.tvStatus.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        }
        // formata a data de long pra String
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvData.setText(formatador.format(t.getDataPrevista()));



    }

    @Override
    // retorna a quantidade de elementos a serem exibidos na lista
    public int getItemCount() {
        if(tarefas != null){
            return tarefas.size();
        }
        return 0;
    }

    // classe ViewHolder para mapear os componentes dos XML
    class TarefaViewHolder extends RecyclerView.ViewHolder{
        // avriáveis para acessar os componentes do XML
        TextView tvTitulo,tvDescricao, tvData, tvStatus;

        public TarefaViewHolder(View view){
            // chama o construtor da super classe
            super(view);
            // passar para as variáveis os componentes do XML
            tvTitulo = view.findViewById(R.id.tv_titulo);
            tvDescricao = view.findViewById(R.id.tv_descricao);
            tvData = view.findViewById(R.id.tv_data);
            tvStatus = view.findViewById(R.id.tv_data);
        }
    }
}
