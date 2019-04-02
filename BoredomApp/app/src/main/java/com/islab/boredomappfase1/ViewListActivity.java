package com.islab.boredomappfase1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        ListView listView = findViewById(R.id.listview);
        final String[] values = new String[] { "Nível de bateria","Notificações de Apps Sociais","Notificações de Apps Chatting","Notificações de Outras Apps","Notificações de Apps Atuais","Número de ativições do ercã","Número de chamadas feitas","Número de chamadas recebidas","Proximidade","Número de SMS recebidas","Luminusidade","Orientação","Número de clicks no botão Home","Número de clicks no botão Recentes","Ligado ao Wi-fi?","Dados móveis ligados?"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChartActivity.class);
                intent.putExtra("posS",values[position]);
                intent.putExtra("posI", position);
                startActivity(intent);
            }
        });
    }
}
