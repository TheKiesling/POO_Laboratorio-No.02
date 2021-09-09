/*
#      #    #######    ########   #######   #          #######   ##      #    #########
#     #        #       #          #         #             #      # #     #    #
#    #         #       #          #         #             #      #  #    #    #
####           #       #####      #######   #             #      #   #   #    #    ####
#    #         #       #                #   #             #      #    #  #    #       #
#     #        #       #                #   #             #      #     # #    #       #
#      #    ########   ########   #######   ########   #######   #      ##    #########
*/

import java.util.ArrayList;
import java.util.Queue;
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
    private Queue <Programa> cola;

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

    public boolean ingresarPrograma(Programa programa){
        int espacio = programa.getEspacio();
        int bloques_programa = bloquesMemoria(espacio);
        boolean ingreso = false;
        boolean bandera = false;

        if (tipo.equals("SDR")){ //Memoria SDR
            int espacio_disponible = 0;
            for (int i = 0; i < SDR.length; i++) //Ciclo para ver el espacio disponible
                if (SDR[i] != null)
                    espacio_disponible++;
            
            if (bloques_programa <= espacio_disponible){ //Si hay espacio
                for (int i = 0; i < SDR.length; i++){
                    if (SDR[i] == null) //Ver en que partes de la memoria hay espacio
                        while (bloques_programa != 0){
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
                    if (DDR.get(i) != null)
                        espacio_disponible++;

                if (bloques_programa <= espacio_disponible){ //Si hay espacio
                    for (int i = 0; i < DDR.size(); i++){
                        if (DDR.get(i) == null) //Ver en que partes de la memoria hay espacio
                            while (bloques_programa != 0){
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
                        bandera = true; //Si se sabe que no se pudo ingresar
                    }

                }
            }
        }

        return ingreso;
    }

}
