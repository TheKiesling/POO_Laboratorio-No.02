/*
#      #    #######    ########   #######   #          #######   ##      #    #########
#     #        #       #          #         #             #      # #     #    #
#    #         #       #          #         #             #      #  #    #    #
####           #       #####      #######   #             #      #   #   #    #    ####
#    #         #       #                #   #             #      #    #  #    #       #
#     #        #       #                #   #             #      #     # #    #       #
#      #    ########   ########   #######   ########   #######   #      ##    #########
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class Vista {
    /**
     @author: José Pablo Kiesling Lange
    Nombre del programa: Vista.java
    @version: 
        - Creación: 07/09/2021
        - Última modificación: 07/09/2021

    Clase que tiene como fin ser un sistema I/O para la manipulación del programa
     */ 

    //---------------------------PROPIEDADES--------------------------
    private Scanner scan;

    //---------------------------MÉTODOS------------------------------

    /*****************************************************************
     * constructor: instancia el scanner
     */
    public Vista(){
        scan = new Scanner(System.in);
    }
    //****************************************************************

    /*****************************************************************
     * error: captura errores y muestra el mensaje que se obtuvo
     * @param e
     */
    public void error(String s){
        System.out.println("Ha ocurrido un error : -- " + s); 
    }
    //****************************************************************

    //---------------------------MENÚ---------------------------------
    /*****************************************************************
     * bienvenida: imprime un mensaje de bienvenida
     */
    public void bienvenida(){
        System.out.println("\n01001000 01100101 01101100 01101100 01101111 00100000 01110111 01101111 01110010 01101100 01100100 00100000 01101111 01100110 00100000 01001111 01001111 01010000");
        System.out.println("\n Bienvenido a tu memoria RAM");
    }
    //****************************************************************
    
    /*****************************************************************
     * abrirArchivo: pregunta al usuario si desea abrir el archivo con los datos
     * @return abrir_archivo
     * @throws Exception
     */
    public boolean abrirArchivo() throws Exception{
        boolean abrir_archivo = false;
        boolean bandera = false;

        try{
            System.out.println("Desea abrir el archivo 'programas.txt' que contiene los programas que se inician al encender el sistema? (Si/No)");

            while (!bandera){ //Ciclo para evaluar si se ingresó una respuesta correcta
                String abrir = scan.nextLine().toLowerCase();
                if (abrir.equals("si")) {
                    System.out.println("Abriendo archivo...\n\n");
                    bandera = true;
                    abrir_archivo = true;
                }
                else if (abrir.equals("no"))
                    bandera = true;
                else
                    System.out.println("Ingrese Si o No");
            }
        } catch (Exception e){ //Captura cualquier error que no sea de input
            String s = "Ocurrió un error con scan.nextLine() " +  abrir_archivo + ": " + e.toString();
            throw new Exception(s);
        }
        return abrir_archivo;
    }
    //****************************************************************

    /*****************************************************************
     * menuOpciones: despliega el menú y recibe la opción del usuario
     * @return opcion
     * @throws Exception 
     * @throws InputMismatchException
     */
    public int menuOpciones() throws Exception, InputMismatchException{
        int opcion = -1;
        boolean bandera = true;

        try{
            //Despliegue de menú de opciones  
            System.out.println("\n\nLas operaciones que puedo hacer se listan a continuacion, ¿Que hara?");
            System.out.println("\n\n1. Inicializar (crear una nueva RAM)");
            System.out.println("2. Ingresar Programas");
            System.out.println("3. Cantidad de memoria RAM");
            System.out.println("4. Ver programas y sus propiedades");
            System.out.println("5. Espacios que ocupa un programa en particular");
            System.out.println("6. Estado de la memoria RAM");
            System.out.println("7. Guardar y Salir\n\n");

            while (!bandera){ //Ciclo para evaluar si se ingresó una opcion válida
                opcion = Integer.parseInt(scan.nextLine());
                System.out.println();
                if (opcion > 0 && opcion <= 7) //Opciones válidas
                    bandera = true;
                else{ 
                    System.out.println("ERROR: Ingrese una de las opciones indicadas anteriormente"); 
                }
            }
        } catch (InputMismatchException e){ //Error de ingreso por input
            String s = "Error de conversión con scan.nextInt() " + opcion + ": " + e.toString(); 
            throw new InputMismatchException(s);
        } catch (Exception e){ //Captura cualquier error que no sea de input
            String s = "Ocurrió un error con scan.nextInt() " +  opcion + ": " + e.toString();
            throw new Exception(s);
        }
        return opcion;
    }
    //****************************************************************

    /*****************************************************************
     * despedida: Imprime un mensaje de despedida
     */
    public void despedida(){
        System.out.println("\n01000111 01101111 01101111 01100100 00100000 01100010 01111001 01100101 00100001 00001010");
        System.out.println("\nHasta la próxima usuario, se despide tu memoria RAM");
        scan.close();
    }
    //****************************************************************

    /*****************************************************************
     * pedirTipo: pide la el tipo de memoria RAM
     * @return tipo
     * @throws Exception
     */
    public String pedirTipo() throws Exception{
        String tipo = "";
        boolean bandera = false;
        
        try{
            System.out.println("Ingrese el tipo de la memoria RAM que desea crear (SDR/DDR)");

            while (!bandera){ //Ciclo para evaluar si se ingresó un tipo de memoria correcto
                tipo = scan.nextLine().toUpperCase();
                System.out.println();
                if (tipo.equals("SDR") || tipo.equals("DDR")) //Tipos admitidos
                    bandera = true;
                else 
                    System.out.println("ERROR: Ingrese un tipo de memoria valida (SDR/DDR)"); 
            }
        } catch (Exception e){ //Captura cualquier error que no sea de input
            String s = "Ocurrió un error con scan.nextLine() " +  tipo + ": " + e.toString();
            throw new Exception(s);
        }
        return tipo;
    }
    //****************************************************************

    /*****************************************************************
     * pedirTamano: pide el tamano de la memoria RAM
     * @return
     * @throws Exception
     * @throws InputMismatchException
     */
    public int pedirTamano() throws Exception, InputMismatchException{
        int tamano = 0;
        boolean bandera = false;

        try{
            System.out.println("Ingrese solo el numero del tamano de la memoria RAM que desea crear en GB");
        
            while (!bandera){ //Ciclo para evaluar si se ingresó un tamano de memoria correcto
                tamano = Integer.parseInt(scan.nextLine());
                System.out.println();
                if (tamano > 0) //Verificar si el espacio de memoria es válido
                    bandera = true;
                else 
                    System.out.println("ERROR: Ingrese un tamano de memoria valida"); 
            }
            
        } catch (InputMismatchException e){ //Error de ingreso por input
            String s = "Error de conversión con scan.nextInt() " + tamano + ": " + e.toString(); 
            throw new InputMismatchException(s);

        } catch (Exception e){ //Captura cualquier error que no sea de input
            String s = "Ocurrió un error con scan.nextInt() " +  tamano + ": " + e.toString();
            throw new Exception(s);
        }
        return tamano;
    }
    //****************************************************************

    /*****************************************************************
     * pedirNombre: pide el nombre de un programa a ingresar
     * @return nombre
     * @throws Exception
     */
    public String pedirNombre() throws Exception{
        String nombre = "";
        boolean bandera = false;
        
        try{     
            System.out.println("Ingrese el nombre del programa que quiere ejecutar");
            
            while (!bandera){ //Ciclo para evaluar si se ingresó un nombre de programa valido
                nombre = scan.nextLine();
                System.out.println();
                if (nombre.equals("") || nombre.equals(";")) //Excepciones
                    System.out.println("ERROR: Ingrese un programa valido"); 
                else 
                    bandera = true;
            }
        } catch (Exception e){ //Captura cualquier error que no sea de input
            String s = "Ocurrió un error con scan.nextInt() " +  nombre + ": " + e.toString();
            throw new Exception(s);
        }
        return nombre;
    }
    //****************************************************************

    /*****************************************************************
     * pedirEspacio: pide el espacio que ocupa el programa
     * @return espacio
     * @throws Exception
     * @throws InputMismatchException
     */
    public int pedirEspacio() throws Exception, InputMismatchException{
        int espacio = 0;
        boolean bandera = false;
        
        try{
            System.out.println("Ingrese el espacio que ocupa el programa que quiere ejecutar");

            while (!bandera){ //Ciclo para evaluar si se ingresó un espacio de programa valido
                espacio = Integer.parseInt(scan.nextLine());
                System.out.println();
                if (espacio > 0) //Verificar si el espacio del programa es válido
                    bandera = true;
                else 
                    System.out.println("ERROR: Ingrese un espacio válido"); 
            }
        } catch (InputMismatchException e){ //Error de ingreso por input
            String s = "Error de conversión con scan.nextInt() " + espacio + ": " + e.toString(); 
            throw new InputMismatchException(s);

        } catch (Exception e){ //Captura cualquier error que no sea de input
            String s = "Ocurrió un error con scan.nextInt() " +  espacio + ": " + e.toString();
            throw new Exception(s);
        }
        return espacio;
    }
    //****************************************************************

    /*****************************************************************
     * pedirTiempo: pide el tiempo de ejecución del programa
     * @return tiempo
     * @throws Exception
     * @throws InputMismatchException
     */
    public int pedirTiempo() throws Exception, InputMismatchException{
        int tiempo = 0;
        boolean bandera = false;
        
        try{
            System.out.println("Ingrese la ubicacion del parqueo (Techado/Aereo)");
            
            while (!bandera){ //Ciclo para evaluar si se ingresó un tiempo de programa valido
                tiempo = Integer.parseInt(scan.nextLine());
                System.out.println();
                if (tiempo > 0) //Verificar si el espacio del programa es válido
                    bandera = true;
                else 
                    System.out.println("ERROR: Ingrese un espacio válido"); 
            }
        } catch (InputMismatchException e){ //Error de ingreso por input
            String s = "Error de conversión con scan.nextInt() " + tiempo + ": " + e.toString(); 
            throw new InputMismatchException(s);

        } catch (Exception e){ //Captura cualquier error que no sea de input
            String s = "Ocurrió un error con scan.nextInt() " +  tiempo + ": " + e.toString();
            throw new Exception(s);
        }
        return tiempo;
    }
    //****************************************************************
}