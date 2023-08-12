package com.example.finance.Calculadora;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finance.R;
import com.example.finance.Calculadora.CalculadoraAPI;

public class CalculadoraFragment extends Fragment {

    Handler mainHandler = new Handler(Looper.getMainLooper());

    //Variables para extraer de caja
    EditText cifraPrincipal;
    EditText cifraSecundaria;
    EditText operacion;
    TextView resultado;

    EditText cifraPrincipalReglaDeTres;
    EditText cifraSecundariaReglaDeTres;
    EditText cifraTerciariaReglaDeTres;
    TextView resultadoReglaDeTres;

    String responseCopy = " ";

    public CalculadoraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculadora, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCalcular = (Button) getView().findViewById(R.id.buttonCalcular);
        Button btnCalcularReglaDeTres = (Button) getView().findViewById(R.id.buttonCalcularReglaTres);

        //Extraer el contenido de las cajas------------------------------------------
        cifraPrincipal = (EditText) getView().findViewById(R.id.cifraPrincipal);
        cifraSecundaria = (EditText) getView().findViewById(R.id.cifraSecundaria);
        operacion = (EditText) getView().findViewById(R.id.operacionEntrada);
        resultado = (TextView) getView().findViewById(R.id.resultadoCalculo);

        cifraPrincipalReglaDeTres = (EditText) getView().findViewById(R.id.cifraPrincipalReglaTres);
        cifraSecundariaReglaDeTres = (EditText) getView().findViewById(R.id.cifraSecundariaReglaTres);
        cifraTerciariaReglaDeTres = (EditText) getView().findViewById(R.id.cifraTerciariaReglaTres);
        resultadoReglaDeTres = (TextView) getView().findViewById(R.id.resultadoReglaTres);


        btnCalcularReglaDeTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //AQUI VA EL CODIGO A EJECUTAR AL PRESCIONAR EL BOTON DE CALCULAR REGLA DE TRES

                boolean verificacionPasada = true;
                String c1 = cifraPrincipalReglaDeTres.getText().toString();
                String c2 = cifraSecundariaReglaDeTres.getText().toString();
                String c3 = cifraTerciariaReglaDeTres.getText().toString();


                //verificar que las cifras sean numeros
                if (!(isNumeric(c1))) {
                    verificacionPasada = false;
                    resultadoReglaDeTres.setText("ERROR de datos");
                    System.out.println("error de datos en c1. Regla de tres");
                } else if (!(isNumeric(c2))) {
                    verificacionPasada = false;
                    resultadoReglaDeTres.setText("ERROR de datos");
                    System.out.println("error de datos en c2. Regla de tres");
                }else if (!(isNumeric(c3))) {
                    verificacionPasada = false;
                    resultadoReglaDeTres.setText("ERROR de datos");
                    System.out.println("error de datos en c3. Regla de tres");
                }

                //Calcular con la API--------------------------------------------------------
                if (verificacionPasada) {
                    Toast.makeText(requireContext(),"Calculando regla de tres, espere..", Toast.LENGTH_SHORT).show();
                    String operationRequest = "("+c3+"*"+c2+")"+"/"+c1;
                    new CalculadoraAPI().verificarPalabra(new CalculadoraAPI.ApiResponse() {
                        @Override
                        public void onSuccess(String response) {
                            System.out.println("success: " + response);

                            //Hace que el resultado se tome en cuenta en el hilo principal -------
                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    resultadoReglaDeTres.setText(response);
                                }
                            };
                            mainHandler.post(myRunnable);
                            //--------------------------------------------------------------------

                        }

                        @Override
                        public void onError(String error) {
                            System.out.println("error: " + error);
                        }
                    }, operationRequest);


                }
                //---------------------------------------------------------------------------

            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //AQUI VA EL CODIGO A EJECUTAR AL PRESCIONAR EL BOTON DE CALCULAR

                boolean verificacionPasada = true;
                String c1 = cifraPrincipal.getText().toString();
                String c2 = cifraSecundaria.getText().toString();
                String op = operacion.getText().toString();

                //verificar que las cifras sean numeros
                if (!(isNumeric(c1))) {
                    verificacionPasada = false;
                    resultado.setText("ERROR de datos");
                    System.out.println("error de datos en c1");
                } else if (!(isNumeric(c2))) {
                    verificacionPasada = false;
                    resultado.setText("ERROR de datos");
                    System.out.println("error de datos en c2");
                }

                //verificar que la operacion este correcta
                switch (op){
                    case "+": break;
                    case "-": break;
                    case "*": break;
                    case "/": break;
                    case "%": op = "%*"; break;
                    default: verificacionPasada = false; resultado.setText("ERROR de datos"); System.out.println("error de datos en operacion");
                }
                //---------------------------------------------------------------------------

                //Calcular con la API--------------------------------------------------------
                if (verificacionPasada) {
                    Toast.makeText(requireContext(),"Calculando, espere..", Toast.LENGTH_SHORT).show();
                    String operationRequest = c1+op+c2;
                    new CalculadoraAPI().verificarPalabra(new CalculadoraAPI.ApiResponse() {
                        @Override
                        public void onSuccess(String response) {
                            System.out.println("success: " + response);

                            //Hace que el resultado se tome en cuenta en el hilo principal -------
                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    resultado.setText(response);
                                }
                            };
                            mainHandler.post(myRunnable);
                            //--------------------------------------------------------------------

                        }

                        @Override
                        public void onError(String error) {
                            System.out.println("error: " + error);
                        }
                    }, operationRequest);
                }
                //---------------------------------------------------------------------------

            }
        });

    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }




}