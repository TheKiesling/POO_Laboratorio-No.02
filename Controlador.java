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
        - Última modificación: 15/09/2021

    Clase que comunica el modelo con la vista y controla sus acciones
    */

    //---------------------------MÉTODOS-----------------------------

    public static void main(String[] args) throws Exception{
        //Instancia de objetos
        RAM ram = null;
        Programa programa;
        Vista vista = new Vista();

        try{
            //Saludar al usuario
            vista.bienvenida();

            int opcion = -1;
            while (opcion != 8){
                //Despliegue de las opciones del menú y su previa lectura de dicha opción
                opcion = vista.menuOpciones();
                
                //Inicializar (crear una nueva RAM)
                if (opcion == 1){
                    String tipo = vista.pedirTipo(); 

                    if (tipo.equals("SDR")){
                        int tamano = vista.pedirTamano();
                        ram = new RAM(tipo, tamano);
                    }

                    else 
                        ram = new RAM(tipo);
                    
                    //Verificar si se quiere abrir el archivo o no
                    boolean abrir = vista.abrirArchivo();
                
                    //Leer Archivo
                    if (abrir){
                        ram.leerArchivo();
                    } 
                }

                //Ingresar Programas
                if (opcion == 2){
                    String nombre = vista.pedirNombre();
                    double espacio = vista.pedirEspacio();
                    double tiempo = vista.pedirTiempo();
                    programa = new Programa(nombre, espacio, tiempo);
                    boolean ingreso = ram.ingresarPrograma(programa);
                    vista.ingresarPrograma(ingreso);
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
                    int espacios = ram.espaciosPrograma(programa_busqueda);
                    vista.espaciosPrograma(espacios, programa_busqueda);
                }

                //Estado de la memoria RAM
                if (opcion == 6){
                    String estado = ram.estadoMemoria();
                    vista.estadoMemoria(estado);
                }

                //Ciclo de Reloj
                if (opcion == 7){
                    String programasFinalizados = ram.cicloReloj();
                    vista.cicloReloj(programasFinalizados);
                }

                //Salir
                if(opcion == 8){
                    vista.despedida();
                }
            }
        }catch (NullPointerException e){
            String s = "ERROR: NullPointerException. No puede ejecutar esta acción si antes no ha creado una memoria RAM";
            vista.error(s);
        }
        catch (Exception e){
            String s = "ERROR: " + e.getMessage();
            vista.error(s);
        }
    }
}
