package com.example.finance.Nucleo;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finance.R;
import com.example.finance.RepartirPagos.ControlArchivosRepartirPagos;
import com.example.finance.databinding.ActivityMainBinding;
import com.example.finance.ConsultarHistorial.ControlHistorial;
import com.example.finance.RepartirPagos.DivisionPago;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static UnidadMonetaria pref = UnidadMonetaria.USD;

    public static Historial        historial  = new Historial();
    public static Monedero         monedero   = new Monedero(0, UnidadMonetaria.USD);
    public static List<Transaccion> transaccionesPendientes = new ArrayList<Transaccion>();
    public static DivisionPago pagos = new DivisionPago();

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ControlArchivos controladorArchivos = new ControlArchivos();
        ControlArchivosRepartirPagos filespagos = new ControlArchivosRepartirPagos();
        ControlHistorial manejador = new ControlHistorial();

        File archivo;
        String rutaCarpeta = this.getFilesDir() + "/";

        //Verificar si existe un archivo de monedero Previo
        archivo = new File(rutaCarpeta, "fileMonedero.txt");
        if (!archivo.exists()){
            controladorArchivos.guardarMonedero(this, this.monedero);
        } else {
            controladorArchivos.cargarMonedero(this, this.monedero);
        }

        //Verificar si existe un archivo de transacciones Previo
        archivo = new File(rutaCarpeta, "fileTransacciones.txt");
        if (archivo.exists()){

            //Guardo el historial del .txt en una copia
            Historial historialTemp = controladorArchivos.cargarHistorial(this);

            for (int i = 29; i >-1 ; i--) {

                Historial trans = historialTemp;

                if (trans.getHistorico(i) != null) {
                    //manejador.agregarHistorial(monederoTemp, historial, 50, tipoTransaccion.egreso, "gasto ", LocalDateTime.now());
                    System.out.println("El caso del valor es " + trans.getHistorico(i).getValor());
                    manejador.agregarHistorial(this.monedero, this.historial, trans.getHistorico(i).getValor(),
                                               trans.getHistorico(i).getTipo(), trans.getHistorico(i).getDescripcion(),
                                               trans.getHistorico(i).getFecha());
                }
            }

            //Verificar si existe un archivo de Grupos (con sus personas) previo
            archivo = new File(rutaCarpeta, "fileGrupos.txt");
            if (archivo.exists())
                filespagos.cargarGruposPersonas(this);

        }
        //////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////
        //                    Creacion de Interfaz                      //
        //////////////////////////////////////////////////////////////////
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar); //toolbar problem
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inicioFragment, R.id.monedaFragment, R.id.estadisticasFragment,R.id.calculadoraFragment,R.id.pagosFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static void eliminarTransaccion(int i){
        MainActivity.transaccionesPendientes.remove(i);
    }

}