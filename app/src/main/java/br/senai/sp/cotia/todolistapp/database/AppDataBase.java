package br.senai.sp.cotia.todolistapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.senai.sp.cotia.todolistapp.model.Tarefa;

@Database(entities = {Tarefa.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    // atributo para acessar a database
    private static AppDataBase database;
    public abstract TarefaDao getTarefaDao();

    // método para acessar o atributo que acessa a database
    public static AppDataBase getDatabase(Context context){
        // verificar se não foi instanciada
        if (database == null){
            // instancia a database............. criar e instanciar a database
            database = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class, "todolist").build();
        }
        // retorna a database
        return database;
    }
}
