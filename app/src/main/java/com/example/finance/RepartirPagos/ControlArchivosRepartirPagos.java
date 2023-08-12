package com.example.finance.RepartirPagos;

import android.content.Context;

import com.example.finance.Nucleo.MainActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ControlArchivosRepartirPagos {

    private String fileGrupos = "fileGrupos.txt";
    private String borde = "---------------";

    public void guardarGruposPersonas(Context ctx){ //Solo se le pasa contexto por que toma del MainActivity.pagos lo que necesita
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
    }

}
