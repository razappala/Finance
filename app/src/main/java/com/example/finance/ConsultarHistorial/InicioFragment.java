package com.example.finance.ConsultarHistorial;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.Nucleo.MainActivity;
import com.example.finance.Nucleo.Transaccion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {
    //=====================// Atributos \\=====================\\
    public TextView textMonedero;
    public ListView textHistorial;
    public TextView txtVacio;
    private Button   buttonTrans;
    private Button   buttonTranTiempo;
    private Button  buttonOrganizador;
    private Button  buttonBuscarTrans;

    public static Mediador m;
    private EnumOrden orden = EnumOrden.reciente;
    /////////////////////////////////////////////////////////////

    public InicioFragment() {}

    public EnumOrden getOrden(){
        return this.orden;
    }

    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = new MediadorTrans(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        textMonedero      = (TextView)view.findViewById(R.id.saldo_monedero1);
        textHistorial     = (ListView)view.findViewById(R.id.saldo_historial);
        buttonTrans       = (Button)view.findViewById(R.id.saldo_nuevaTrans);
        buttonTranTiempo  = (Button)view.findViewById(R.id.saldo_nuevaTransTiempo);
        buttonOrganizador = (Button)view.findViewById(R.id.saldo_Organizador);
        txtVacio          = (TextView)view.findViewById(R.id.saldo_vacio);
        buttonBuscarTrans = (Button) view.findViewById(R.id.saldo_buscarTrans);

        //Mostrar Informacion en pantalla
        m.actualizarDatos();
        m.actualizarVacio();
        /////////////////////////////////////////////////////////////

        buttonBuscarTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actTrans = new Intent(getActivity(), FiltrarResultados.class);
                startActivity(actTrans);
            }
        });


        //Mas reciente, Menos reciente, A a Z, Z a A, Mayor Valor, Menor Valor, Ingresos, Egresos
        buttonOrganizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (orden){
                    case reciente:
                        buttonOrganizador.setText("Más Antiguas");
                        orden = EnumOrden.antiguo;
                        break;
                    case antiguo:
                        buttonOrganizador.setText("A - Z");
                        orden = EnumOrden.alfabeticamente;
                        break;
                    case alfabeticamente:
                        buttonOrganizador.setText("Z - A");
                        orden = EnumOrden.zA;
                        break;
                    case zA:
                        buttonOrganizador.setText("Mayor monto");
                        orden = EnumOrden.mayor;
                        break;
                    case mayor:
                        buttonOrganizador.setText("Menor monto");
                        orden = EnumOrden.menor;
                        break;
                    case menor:
                        buttonOrganizador.setText("Ingresos");
                        orden = EnumOrden.ingresos;
                        break;
                    case ingresos:
                        buttonOrganizador.setText("Egresos");
                        orden = EnumOrden.egresos;
                        break;
                    case egresos:
                        buttonOrganizador.setText("Más reciente");
                        orden = EnumOrden.reciente;
                        break;
                }
                m.actualizarDatos();
            }
        });

        buttonTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actTrans = new Intent(getActivity(), ActTransacciones.class);
                startActivity(actTrans);
            }
        });

        buttonTranTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actTrans = new Intent(getActivity(), ActTransaccionesTiempo.class);
                startActivity(actTrans);
            }
        });
        verificarTransaccionFutura();

        return view;
    }


    //Codigo para agregar una transaccion planificada
   public void verificarTransaccionFutura(){

        //public static List<Transaccion> transaccionesPendientes = new ArrayList<Transaccion>();

        //Generar LocalDateTime del dia actual
        LocalDateTime hoy = LocalDateTime.now();
        //Generar manejador de historial
        ControlHistorial manejador  = new ControlHistorial();
        //Generar un holder de transaccion
        Transaccion nuevaTransaccion;
        //Generar un almacenador de posiciones de transacciones completadas
        List<Integer> posicionesRemover = new ArrayList<Integer>();

        if (MainActivity.transaccionesPendientes.size() != 0) {

            for (int i = 0; i < MainActivity.transaccionesPendientes.size(); i++) {
                //Obtener la transaccion pendiente
                nuevaTransaccion = MainActivity.transaccionesPendientes.get(i);

                //Verificar si la fecha de la transaccion corresponde a la del dia actual
                if (nuevaTransaccion.getFecha().isBefore(hoy)) {

                    //Agregar transaccion al historial de la app
                    manejador.agregarHistorial(MainActivity.monedero, MainActivity.historial, nuevaTransaccion.getValor(), nuevaTransaccion.getTipo(), nuevaTransaccion.getDescripcion(), hoy);
                    //Agregar posicion de transaccion ya revisada para su eliminacion
                    posicionesRemover.add(i);
                }
            }

            //Verificar si existen posiciones a remover
            if (posicionesRemover.size() != 0) {
                for (int i = 0; i < posicionesRemover.size(); i++) {
                    //Eliminar la transaccion ya registrada de la cola
                    MainActivity.eliminarTransaccion(posicionesRemover.get(i)); ;
                }
            }

        }
    }
}