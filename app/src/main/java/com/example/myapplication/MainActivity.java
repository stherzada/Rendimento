package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText numInicial;
    private EditText numAplicacao;
    private EditText numTempo;
    private EditText numTaxa;
    private Button btnCalcular;

    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         EditText[] editTexts = new EditText[]{
                 numInicial = findViewById(R.id.numInicial),
                 numAplicacao = findViewById(R.id.numAplicacao),
                 numTempo = findViewById(R.id.numTempo),
                 numTaxa =  findViewById(R.id.numTaxa)
         };

        txtResultado = findViewById(R.id.txtResultado);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                boolean enableButton = true;

               for (EditText editText:editTexts){
                   if(editText.getText().toString().isEmpty()){
                       enableButton = false;
                       break;
                   }
               }

                btnCalcular.setEnabled(enableButton);
            }
        };

        for(EditText editText: editTexts){
            editText.addTextChangedListener(textWatcher);
        }
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valorInicial = Double.parseDouble(numInicial.getText().toString());
                double aplicacaoMensal = Double.parseDouble(numAplicacao.getText().toString());
                int meses = Integer.parseInt(numTempo.getText().toString());
                double taxa = Double.parseDouble(numTaxa.getText().toString());

                double valorFinal = calcularRendimento(valorInicial, aplicacaoMensal, meses, taxa);

                txtResultado.setText("Resultado: " + String.format("%.2f", valorFinal));
            }
        });
    }
    private double calcularRendimento(double valorInicial, double aplicacaoMensal, int meses, double taxa) {
        double valorFinal = valorInicial;
        double taxaMensal = taxa / 100 / 12; // taxa mensal em decimal

        for (int i = 0; i < meses; i++) {
            valorFinal += aplicacaoMensal; // adiciona a aplicação mensal
            valorFinal *= 1 + taxaMensal; // aplica a taxa de juros mensal
        }

        return valorFinal;
    }
}

