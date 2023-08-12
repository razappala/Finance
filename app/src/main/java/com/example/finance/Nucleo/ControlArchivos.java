package com.example.finance.Nucleo;

import android.content.Context;

import com.example.finance.Nucleo.Historial;
import com.example.finance.Nucleo.Monedero;
import com.example.finance.Nucleo.Transaccion;
import com.example.finance.Nucleo.UnidadMonetaria;
import com.example.finance.Nucleo.tipoTransaccion;
import com.example.finance.RepartirPagos.Grupo;
import com.example.finance.RepartirPagos.Persona;

import java.io.*;
import java.time.LocalDateTime;

public class ControlArchivos {

    //public String rutaPrincipal = "/data/user/0/com.example.finance/files/FinanceData";
    //private String rutaPrincipal = "/data/user/0/com.example.finance/files/";
    private String fileTransacciones = "fileTransacciones.txt";
    private String fileGrupos = "fileGrupos.txt";
    private String fileMonedero = "fileMonedero.txt";
    private String borde = "---------------";

    public void guardarMonedero(Context ctx, Monedero monedero){
        try {
            FileOutputStream archivo = ctx.openFileOutput(this.fileMonedero, Context.MODE_PRIVATE);
            BufferedWriter reader    = new BufferedWriter(new OutputStreamWriter(archivo));

            reader.write(String.valueOf(monedero.getSaldo()));
            reader.newLine();
            reader.write(monedero.getDivisa().toString());
            reader.newLine();
            reader.close();
            archivo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void guardarHistorial(Context ctx, Historial historial){
        try {
            FileOutputStream archivo = ctx.openFileOutput(this.fileTransacciones, Context.MODE_PRIVATE);
            BufferedWriter reader    = new BufferedWriter(new OutputStreamWriter(archivo));

            Historial transacc = historial;

            for (int i = transacc.largoHistorial(); i > -1; i--){
                if (transacc.getHistorico(i) != null) {
                    reader.write(String.valueOf(transacc.getHistorico(i).getValor()));
                    reader.newLine();
                    reader.write(transacc.getHistorico(i).getTipo().toString());
                    reader.newLine();
                    reader.write(transacc.getHistorico(i).getDescripcion());
                    reader.newLine();
                    reader.write(transacc.getHistorico(i).getFecha().toString());
                    reader.newLine();
                    reader.write("---------------");
                    reader.newLine();
                }
            }

            reader.close();
            archivo.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void cargarMonedero(Context ctx, Monedero monedero){
        try {
            FileInputStream archivo = ctx.openFileInput(this.fileMonedero);
            BufferedReader reader = new BufferedReader(new InputStreamReader(archivo));
            String saldo = "";
            String divisa = "";
            UnidadMonetaria unidad = UnidadMonetaria.USD;

            saldo = reader.readLine();
            divisa = reader.readLine();
            switch (divisa){
                case "USD": unidad = UnidadMonetaria.USD; break;
                case "EUR": unidad = UnidadMonetaria.EUR; break;
                case "BsS": unidad = UnidadMonetaria.BsS; break;
                case "GBP": unidad = UnidadMonetaria.GBP; break;
                case "COP": unidad = UnidadMonetaria.COP; break;
                case "PEN": unidad = UnidadMonetaria.PEN; break;
                case "JPY": unidad = UnidadMonetaria.JPY; break;
                case "CHF": unidad = UnidadMonetaria.CHF; break;
                case "SEK": unidad = UnidadMonetaria.SEK; break;
            }

            reader.close();
            archivo.close();

            //monedero.setSaldo(Double.valueOf(saldo));
            monedero.setDivisa(unidad);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Historial cargarHistorial(Context ctx){
        try {
            FileInputStream archivo = ctx.openFileInput(this.fileTransacciones);
            BufferedReader reader = new BufferedReader(new InputStreamReader(archivo));

            Historial historial = new Historial();

            String valor = "";
            String tipo = "";
            String descripcion = "";
            String fecha = "";
            String verificador = "";

            for (int i = 29; i >= 0; i--){

                if ((verificador = reader.readLine()) != null) {
                    Transaccion trans = new Transaccion();
                    valor = verificador;
                    tipo = reader.readLine();
                    descripcion = reader.readLine();
                    fecha = reader.readLine();
                    reader.readLine(); //Para omitir la linea de ---------

                    //************* Conversion de datos ********************

                    //valor numerico
                    trans.setValor(Math.abs(Double.valueOf(valor)));

                    //tipo de transaccion
                    switch (tipo){
                        case "ingreso": trans.setTipo(tipoTransaccion.ingreso); break;
                        case "egreso": trans.setTipo(tipoTransaccion.egreso); break;
                    }

                    //descripcion de la transaccion
                    trans.setDescripcion(descripcion);

                    //fecha de la transaccion
                    LocalDateTime dateTime = LocalDateTime.parse(fecha);
                    trans.setFecha(dateTime);

                    //Aqui se manda la posicion i al historial
                    historial.setHistorico(trans);

                    //*******************************

                } else {
                    break;
                }

            }

            reader.close();
            archivo.close();

            return historial;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Esto tecnicamente nunca pasara
        System.out.println("ERROR DE NULL CARGANDO ARCHIVO DE MONEDERO");
        return null;

    }

    public void generarEspacioGuardado(Context context) {
        try {

            //Crear la ruta del archivo y el nombre del directorio
            File root = new File(context.getFilesDir(), "FinanceData");

            //Verificar la existencia del directorio, si no existe, lo crea
            if (!root.exists()) {
                root.mkdir();
            }

            //Guarda la ruta de la carpeta
            //this.rutaPrincipal = root.toString();

            //Crear el archivo txt de transacciones si no existe
            File f = new File(root, this.fileTransacciones);
            if (!f.exists()){
                f.createNewFile();
            }

            //Crear el archivo txt de grupos si no existe
            f = new File(root, this.fileGrupos);
            if (!f.exists()){
                f.createNewFile();
            }

            //Crear el archivo txt de monedero si no existe
            f = new File(root, this.fileMonedero);
            if (!f.exists()){
                f.createNewFile();
            }


            //Escribir informacion de prueba en el txt
            /*FileWriter writer = new FileWriter(f);
            //sBody es el contenido del archivo
            writer.append(sBody);
            writer.flush();
            writer.close();*/


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/*    public void guardarGruposPersonas(Context ctx){ //Solo se le pasa contexto por que toma del MainActivity.pagos lo que necesita
        try {
            FileOutputStream archivo = ctx.openFileOutput(this.fileGrupos, Context.MODE_PRIVATE);
            BufferedWriter reader    = new BufferedWriter(new OutputStreamWriter(archivo));

            //MainActivity.pagos.getGrupos()

            for (int i = 0; i < MainActivity.pagos.getGrupos().size(); i++) { //Recorrer cada grupo
                reader.write(MainActivity.pagos.getGrupos().get(i).getNombre_grupo());
                reader.newLine();
                for (int j = 0; j < MainActivity.pagos.getGrupos().get(i).getMiembros().size(); j++) { //Recorrer cada miembro de un grupo
                    reader.write(MainActivity.pagos.getGrupos().get(i).getMiembros().get(j).getNombre());
                    reader.newLine();
                }
                reader.write(this.borde);
                reader.newLine();
            }

            reader.close();
            archivo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarGruposPersonas(Context ctx){
        try {
            FileInputStream archivo = ctx.openFileInput(this.fileGrupos);
            BufferedReader reader = new BufferedReader(new InputStreamReader(archivo));
            String nombreGrupo = reader.readLine();
            Grupo grupoAux = new Grupo(nombreGrupo);
            String nombrePersona = "";

            while ((nombrePersona = reader.readLine()) != null) {
                if (!(nombrePersona.equals(this.borde))) {
                    Persona personaAux = new Persona(nombrePersona,0);
                    grupoAux.addMiembro(personaAux);
                } else {
                    MainActivity.pagos.getGrupos().add(grupoAux);
                    nombreGrupo = reader.readLine(); //omitir los "----------"
                    grupoAux = new Grupo(nombreGrupo);
                }
            }

            reader.close();
            archivo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
