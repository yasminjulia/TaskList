package com.example.tasklist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final List<String> lix = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        TextAdapter adpte = new TextAdapter();
        adpte.setData(lix);
        listView.setAdapter(adpte);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Deletar essa tarefa?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lix.remove(position);
                                adpte.setData(lix);
                            }
                        })
                        .setNegativeButton("Não", null)
                        .create();
                dialog.show();

            }
        });

        final Button newTaskBtn = findViewById(R.id.newTaskbtn);
        newTaskBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final EditText taskInput = new EditText(MainActivity.this);
                taskInput.setSingleLine();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Adicionar nova tarefa")
                        .setMessage("Qual é a tarefa?")
                        .setView(taskInput)
                        .setPositiveButton("Adicionar tarefa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lix.add(taskInput.getText().toString());
                                adpte.setData(lix);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
            }
        });
    }
    private void salvarInfo(){
        try{
            File file = new File(this.getFilesDir(), "Salvo");
            FileOutputStream fOut = new FileOutputStream(file);
            BufferedWriter b = new BufferedWriter(new OutputStreamWriter(fOut));

            for(int i = 0; i< lix.size(); i++){
                b.write(lix.get(i));
                b.newLine();
            }
            b.close();
            fOut.close();
    } catch(Exception e){
            e.printStackTrace();
        }

    class TextAdapter extends BaseAdapter{

        List<String> list = new ArrayList<>();
        void setData(List<String>mList){
            list.clear();
            list.addAll(mList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           final LayoutInflater inflater = (LayoutInflater)
                    MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.item, parent, false);
            final TextView textView = rowView.findViewById(R.id.task);
            textView.setText(list.get(position));
            return rowView;
        }
    }
}
