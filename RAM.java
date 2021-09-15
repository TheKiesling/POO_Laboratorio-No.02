/*
#      #    #######    ########   #######   #          #######   ##      #    #########
#     #        #       #          #         #             #      # #     #    #
#    #         #       #          #         #             #      #  #    #    #
####           #       #####      #######   #             #      #   #   #    #    ####
#    #         #       #                #   #             #      #    #  #    #       #
#     #        #       #                #   #             #      #     # #    #       #
#      #    ########   ########   #######   ########   #######   #      ##    #########
*/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class RAM {
    /** 
    @author: José Pablo Kiesling Lange
    Nombre del programa: Vehiculo.java
    @version: 
        - Creación: 09/09/2021
        - Última modificación: 09/09/2021

    Clase que tiene las propiedades y métodos de la memoria capaz de gestionar los programas que están en ella-
    */

    //---------------------------PROPIEDADES-------------------------
    private String tipo;
    private int tamano;
    private File archivo = new File(".\\programas.txt");
    private Programa programa;
    private ArrayList <Programa> DDR;
    private Programa[] SDR;
    private ArrayDeque<Programa> cola = new ArrayDeque<>();
    

    //---------------------------MÉTODOS-----------------------------
    /****************************************************************
     * RAM: Constructor que permite la creación de una memoria de tipo SDR
     * @param tipo
     * @param tamano
     */
    public RAM(String tipo, int tamano){
        this.tipo = tipo;
        int bloques = bloquesMemoria(tamano);
        this.tamano = bloques;
        SDR = new Programa[this.tamano];
    }
    //***************************************************************

    /****************************************************************
     * RAM: Constructor que permite la creación de una memoria de tipo DDR
     * @param tipo
     */
    public RAM(String tipo){
        this.tipo = tipo;
        int bloques = bloquesMemoria(4);
        this.tamano = bloques;
        DDR = new ArrayList <Programa>(tamano);
        for (int i = 0; i < this.tamano; i++){ //Añadir nulls a los nuevos espacios
            DDR.add(null);
        }
    }
    //***************************************************************

    /****************************************************************
     * bloquesMemoria: Cálculo de los bloques de memoria de la memoria
     * @param tamano
     * @return bloques
     */
    private int bloquesMemoria(int tamano){
        int bloques = tamano * 1024 / 64;
        return bloques;
    }
    //***************************************************************

    /****************************************************************
     * ingresarPrograma: Crea un programa y verifica si puede ingresar a la memoria RAM o se queda en la lista de espera. 
     * Retorna true si pudo entrar y false, si no pudo.
     * @param programa
     * @return ingreso
     * @throws Exception
     */
    public boolean ingresarPrograma(Programa programa) throws Exception{
        double espacio = programa.getEspacio();
        double bloques = espacio/64;
        double bloques_programa = Math.ceil(bloques); 
        boolean ingreso = false;
        boolean bandera = false;

        try{
            if (tipo.equals("SDR")){ //Memoria SDR
                int espacio_disponible = 0;
                for (int i = 0; i < SDR.length; i++) //Ciclo para ver el espacio disponible
                    if (SDR[i] == null)
                        espacio_disponible++;
                if (bloques_programa <= espacio_disponible){ //Si hay espacio
                    for (int i = 0; i < SDR.length && bloques_programa != 0; i++){
                        if (SDR[i] == null){ //Ver en que partes de la memoria hay espacio
                            SDR[i] = programa; //Agregar el programa
                            bloques_programa--; //Hasta que no quede ningún programa
                        }
                    }
                    ingreso = true; //Si se pudo ingresar
                }
                else
                    cola.add(programa); //A la cola de espera :(
            }

            if (tipo.equals("DDR")){ //Memoria DDR
                
                while(!bandera){ //Mientras se sepa si se pudo ingresar o no
                    int espacio_disponible = 0;

                    for (int i = 0; i < DDR.size(); i++) //Ciclo para ver el espacio disponible
                        if (DDR.get(i) == null)
                            espacio_disponible++;

                    if (bloques_programa <= espacio_disponible){ //Si hay espacio
                        for (int i = 0; i < DDR.size() && bloques_programa != 0; i++){
                            if (DDR.get(i) == null){ //Ver en que partes de la memoria hay espacio
                                DDR.set(i, programa); //Agregar el programa
                                bloques_programa--; //Hasta que no quede ningún programa
                            }
                        }
                        bandera = true; //Si se sabe que se pudo ingresar
                        ingreso = true; //Si se pudo ingresar
                    }
                    else{
                        int espacio_limite = bloquesMemoria(64); //Ver cuantos bloques de espacio puede aguantar (límite)
                        if (this.tamano < espacio_limite){ //Verificar si el tamano de la memoria es menor al del límite
                            this.tamano *=2; //Aumentar el doble del tamano
                            for (int i = DDR.size(); i < this.tamano; i++){ //Añadir nulls a los nuevos espacios
                                DDR.add(null);
                            }
                        }
                        else{ //El tamaño ya es el limite
                            cola.add(programa); //A la cola de espera :(

                            //Proceso para reducir nuevamente el espacio que se aumentó de forma inutil
                            boolean tamano_correcto = false;
                            int espacio_vacio = 0;
                            while(!tamano_correcto){
                                int espacio_ocupado = 0;
                                for (int i = 0; i < DDR.size(); i++){
                                    if (DDR.get(i) != null)
                                        espacio_ocupado++;
                                }
                                
                                if(this.tamano/2 > espacio_ocupado && this.tamano > bloquesMemoria(4)){
                                    
                                    for (int i = (this.tamano/2); i < this.tamano; i++){
                                        if (DDR.get(i) == null)
                                            espacio_vacio++;
                                    }
                                    this.tamano/=2;
                                }
                                else
                                    tamano_correcto = true;
                            }
                            for (int i = 1024-1; i >=(1024-espacio_vacio); i--){
                                DDR.remove(i);
                            }
                            bandera = true; //Si se sabe que no se pudo ingresar
                        }

                    }
                }
            }
        } catch (Exception e){
            String s = "ERROR: RAM.ingresarPrograma " + e.toString();
			throw new Exception(s);
        }
        return ingreso;
    }
    //***************************************************************

    /****************************************************************
     * cantidadMemoria: Instancia en un arreglo de ints el tamaño del arreglo de la memoria, el valor de los espacios vacíos y el valor de los espacios ocupados.
     * @return cantidadMemoria
     */
    public int[] cantidadMemoria(){
        int[] cantidadMemoria = new int[3];

        //Cantidad de memoria RAM total
        cantidadMemoria[0] = this.tamano*64;

        //Cantidad de memoria disponible
        int memoriaDisponible = 0;
        
        if (tipo.equals("SDR")){
            for (int i = 0; i < SDR.length; i++) {
                if (SDR[i] != null)
                        memoriaDisponible++;
            }
        }

        if (tipo.equals("DDR")){
            for (int i = 0; i < DDR.size(); i++) 
                if (DDR.get(i) != null)
                    memoriaDisponible++;
        }

        cantidadMemoria[2] = memoriaDisponible*64;

        //Cantidad de memoria en uso
        int memoriaUso = this.tamano - memoriaDisponible;
        cantidadMemoria[1] = memoriaUso*64;

        return cantidadMemoria;
    }
    //***************************************************************

    /****************************************************************
     * estadosProgramas: Concatena cada bloque de espacio a un String en donde su valor sea diferente a null, posteriormente retorna el vector
     * @return estadosProgramas
     * @throws Exception
     * @throws ArrayIndexOutOfBoundsException
     */
    public String[] estadosProgramas() throws Exception, ArrayIndexOutOfBoundsException{
        ArrayList<String>  ejecucion = new ArrayList<String>();
        String programasEjecucion = "";
        String programasCola = "";
        String[] estadosProgramas = new String[2]; 
        
        try{
        //Programas en ejecución
            if(tipo.equals("SDR")){
                for (int i = 0; i < SDR.length; i++) {
                    if (SDR[i] != null){
                        Programa programa_actual = SDR[i];
                        String nombre = programa_actual.getNombre();
                        ejecucion.add(nombre);
                    }   
                }
            }
            if(tipo.equals("DDR")){
                for (int i = 0; i < DDR.size(); i++) {
                    if (DDR.get(i) != null){
                        Programa programa_actual = DDR.get(i);
                        String nombre = programa_actual.getNombre();
                        ejecucion.add(nombre);
                    }   
                }
            }
            //Proceso para verificar que no se muestren programas repetidos 
            Collections.sort(ejecucion);
            String[] ejecucionString = new String[ejecucion.size()];
            for (int i = 0; i < ejecucion.size(); i++){
                if (ejecucion.get(i) != null)
                    ejecucionString[i] = ejecucion.get(i);
            }
            programasEjecucion = recorrerArreglo(ejecucionString);

            //Programas en cola
            for(Programa programa: cola){
                String nombre = programa.getNombre();
                programasCola += " " + nombre + ","; 
            }
        } catch (ArrayIndexOutOfBoundsException e){
            String s = "ERROR: recorrido de String: " + e.toString() + " Tiene que ingresar minimo un programa para poder ejecutar esta opcion o estar seguro de que hay al menos uno en ejecucion"; 
            throw new ArrayIndexOutOfBoundsException(s);
        } catch (Exception e){
            String s = "ERROR: RAM.estadosProgramas: " + e.toString(); 
            throw new Exception(s);
        }

        estadosProgramas[0] = programasEjecucion; estadosProgramas[1] = programasCola;
        return estadosProgramas;
    }
    //***************************************************************

    /****************************************************************
     * recorrerArreglo: recorre un arreglo en busca de elementos repetidos y los concatena solo una vez
     * @param arreglo
     * @return cadena
     */
    private String recorrerArreglo(String[] arreglo){
        String cadena = "";
        if (arreglo.length > 0){
            cadena += " " + arreglo[0]  + ","; 
            for (int i = 1; i < arreglo.length; i++){
                if (arreglo[i].equals(arreglo[i-1])){}
                else 
                    cadena += " " + arreglo[i]  + ",";
            }
        }
        return cadena;
    }
    //***************************************************************

    /****************************************************************
     * espaciosPrograma: Busca en el arreglo todas las posiciones que correspondan al nombre del programa y suma en una variable entera cada vez que encuentra el valor. Retorna dicha variable.
     * @param programa_buscar
     * @return espacios
     */
    public int espaciosPrograma(String programa_buscar){
        int espacios = 0;

        if (tipo.equals("SDR")){
            for (int i = 0; i < SDR.length; i++) {
                if (SDR[i] != null){
                    Programa programa_actual = SDR[i];
                    String nombre = programa_actual.getNombre();
                    if (programa_buscar.equals(nombre))
                        espacios++;
                }
            }   
        }

        if (tipo.equals("DDR")){
            for (int i = 0; i < DDR.size(); i++){
                if (DDR.get(i) != null){
                    Programa programa_actual = DDR.get(i);
                    String nombre = programa_actual.getNombre();
                    if (programa_buscar.equals(nombre))
                        espacios++;
                }
            }
        }

        return espacios;
    }
    //***************************************************************

    /****************************************************************
     * estadoMemoria: Concatenación de los bloques ocupados en la memoria (bloque diferente a null) y memoria libre (bloques iguales a null)
     * @return estadoMemoria
     */
    public String estadoMemoria(){
        String estadoMemoria = "";

        if(tipo.equals("SDR")){
            for (int i = 0; i < SDR.length; i++) {
                if (SDR[i] != null){
                    Programa programa_actual = SDR[i];
                    String nombre = programa_actual.getNombre();
                    estadoMemoria += "|" + "\t" + nombre + "\t" + "|"; //Espacios con programas
                }
                else
                    estadoMemoria += "|" + "\t" + "vacio" + "\t" + "|"; //Espacios vacios 
            }
        }
        if(tipo.equals("DDR")){
            for (int i = 0; i < DDR.size(); i++) {
                if (DDR.get(i) != null){
                    Programa programa_actual = DDR.get(i);
                    String nombre = programa_actual.getNombre();
                    estadoMemoria += "|" + "\t" + nombre + "\t" + "|"; //Espacios con programas
                }
                else
                    estadoMemoria += "|" + "\t" + "vacio" + "\t" + "|"; //Espacios vacios 
            }
        }
        return estadoMemoria;
    }
    //***************************************************************

    /****************************************************************
     * cicloReloj: Reduce en 1 la propiedad de tiempo de cada programa y verifica si se puede retirar el programa e ingresar uno nuevo.
     * @throws Exception
     */
    public String cicloReloj() throws Exception{
        String programasFinalizados = "";
        ArrayList <String> programas = new ArrayList <String>();

        if(tipo.equals("SDR")){
            for (int i = 0; i < SDR.length; i++) {
                if (SDR[i] != null){
                    Programa programa_actual = SDR[i];
                    programa_actual.setTiempo(); //Reducir el tiempo de cada programa
                }
            }
            for (int i = 0; i < SDR.length; i++) {
                if (SDR[i] != null){
                    Programa programa_actual = SDR[i];
                    double tiempoPrograma = programa_actual.getTiempo();
                    if (Math.round(tiempoPrograma) < 1){ //Si ya no tiene tiempo
                        programas.add(SDR[i].getNombre());
                        SDR[i] = null; //Eliminar el espacio que ocupaba el programa en el arreglo
                        boolean recorridoCola = true;
                        if (cola.isEmpty())
                            recorridoCola = false;
                        else{
                            for (Programa programa_cola: cola){ //Ingresar programas de la cola
                                double espacioPrograma = programa_cola.getEspacio();
                                if ((espacioPrograma/64) <= this.tamano){
                                    recorridoCola = ingresarPrograma(programa_cola);
                                    cola.poll();
                                    if (!recorridoCola) break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (tipo.equals("DDR")){
            for (int i = 0; i < DDR.size(); i++) {
                if (DDR.get(i) != null){
                    Programa programa_actual = DDR.get(i);
                    programa_actual.setTiempo(); //Reducir el tiempo de cada programa
                }
            }
            for (int i = 0; i < DDR.size(); i++) {
                if (DDR.get(i) != null){
                    Programa programa_actual = DDR.get(i);
                    double tiempoPrograma = programa_actual.getTiempo();
                    if (Math.round(tiempoPrograma) < 1){ //Si ya no tiene tiempo
                        programas.add(DDR.get(i).getNombre());
                        DDR.set(i, null); //Eliminar el espacio que ocupaba el programa en el arreglo
                    }
                }
            }
            boolean recorridoCola = true;
            if (cola.isEmpty())
                recorridoCola = false;
            else{ //Proceso para agregar programas de la cola
                for (Programa programa_cola: cola){
                    double espacioPrograma = programa_cola.getEspacio();
                    if ((espacioPrograma/64) <= this.tamano){
                        recorridoCola = ingresarPrograma(programa_cola);
                        cola.poll();
                        if (!recorridoCola) break;
                    }
                }
            }
            //Proceso para reducir el tamano de la memoria que no está siendo usada
            boolean tamano_correcto = false;
            int espacio_vacio = 0;
            while(!tamano_correcto){
                int espacio_ocupado = 0;
                for (int j = 0; j < DDR.size(); j++){
                    if (DDR.get(j) != null)
                        espacio_ocupado++;
                }
                if(this.tamano/2 > espacio_ocupado && this.tamano > bloquesMemoria(4)){
                    for (int j = (this.tamano/2); j < this.tamano; j++){
                        if (DDR.get(j) == null)
                            espacio_vacio++;
                    }
                    this.tamano/=2;
                }
                else
                    tamano_correcto = true;
            }
            int tamano_actual = DDR.size();
            for (int j = tamano_actual-1; j >=(tamano_actual-espacio_vacio); j--){
                DDR.remove(j);
            }
        }

        Collections.sort(programas);
        String[] ejecucionString = new String[programas.size()];
        for (int i = 0; i < programas.size(); i++){
            if (programas.get(i) != null)
                ejecucionString[i] = programas.get(i);
        }
        programasFinalizados = recorrerArreglo(ejecucionString);

        return programasFinalizados;
    }
    //***************************************************************

    /****************************************************************
     * leerArchivo: Lee un archivo .txt donde se encuentran todos los programas previos a la ejecución del programa
     * @throws Exception
     * @throws IOException
     */
    public void leerArchivo() throws Exception, IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String datos; String[] datos_separados = new String[7]; 
            while ((datos = br.readLine()) != null){ //Lectura de archivo
                datos_separados = datos.split(";");
                String nombre = datos_separados[0]; 
                double espacio = Double.parseDouble(datos_separados[1]); 
                double tiempo = Double.parseDouble(datos_separados[2]);
                Programa programa_archivo = new Programa(nombre, espacio, tiempo);
                ingresarPrograma(programa_archivo);
            }
        }
        catch (IOException e){
            String s = "RAM.leerArchivo:" + e.toString() + " Error de lectura";
			throw new Exception(s);
        }
        catch (Exception e){
            String s = "ERROR: RAM.leerArchivo: " + e.toString(); 
            throw new Exception(s);
        }
    }
    //***************************************************************
}
