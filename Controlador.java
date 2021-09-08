/*
#      #    #######    ########   #######   #          #######   ##      #    #########
#     #        #       #          #         #             #      # #     #    #
#    #         #       #          #         #             #      #  #    #    #
####           #       #####      #######   #             #      #   #   #    #    ####
#    #         #       #                #   #             #      #    #  #    #       #
#     #        #       #                #   #             #      #     # #    #       #
#      #    ########   ########   #######   ########   #######   #      ##    #########
*/

public class Controlador {
    /** 
    @author: José Pablo Kiesling Lange
    Nombre del programa: Controlador.java
    @version: 
        - Creación: 08/09/2021
        - Última modificación: 08/09/2021

    Clase que comunica el modelo con la vista y controla sus acciones
    */

    //---------------------------MÉTODOS-----------------------------

    public static void main(String[] args) {
        //Instancia de objetos
        RAM ram;
        Programa programa;
        Vista vista = new Vista();

        try{
            //Saludar al usuario
            vista.bienvenida();

            //Verificar si se quiere abrir el archivo o no
            boolean abrir = vista.abrirArchivo();
                
            //Leer Archivo
            if (abrir){
                ram.leerArchivo();
            } 

            int opcion = -1;
            while (opcion != 7){
                //Despliegue de las opciones del menú y su previa lectura de dicha opción
                opcion = vista.menuOpciones();
                
                //Inicializar (crear una nueva RAM)
                if (opcion == 1){
                    String tipo = vista.pedirTipo(); //
                    int tamano = vista.pedirTamano();
                    ram = new RAM(tipo, tamano);
                }

                //Ingresar Programas
                if (opcion == 2){
                    String nombre = vista.pedirNombre();
                    int espacio = vista.pedirEspacio();
                    int tiempo = vista.pedirTiempo();
                    ram.ingresarPrograma(nombre,espacio, tiempo);
                }
                
                //Cantidad de memoria RAM
                if (opcion == 3){
                    int[] memoria = ram.cantidadMemoria();
                    vista.cantidadMemoria(memoria);
                }

                //Ver programas y sus propiedades
                if (opcion == 4){
                    String[] programas = ram.estadosProgramas();
                    vista.estadosProgramas(programas);
                }

                //Espacios que ocupa un programa en particular
                if (opcion == 5){
                    String programa_busqueda = vista.pedirPrograma();
                    int espacios = ram.espaciosPrograma();
                    vista.espaciosPrograma(espacios);
                }

                //Estado de la memoria RAM
                if (opcion == 6){
                    String estado = ram.estadoMemoria();
                    vista.estadoMemoria(estado);
                }

                //Salir
                if(opcion == 7){
                    vista.despedida();
                }
            }
        } catch (Exception e){
            String s = e.getMessage();
            vista.error(s);
        }
    }
}
