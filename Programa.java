/*
#      #    #######    ########   #######   #          #######   ##      #    #########
#     #        #       #          #         #             #      # #     #    #
#    #         #       #          #         #             #      #  #    #    #
####           #       #####      #######   #             #      #   #   #    #    ####
#    #         #       #                #   #             #      #    #  #    #       #
#     #        #       #                #   #             #      #     # #    #       #
#      #    ########   ########   #######   ########   #######   #      ##    #########
*/

public class Programa {
    /** 
    @author: José Pablo Kiesling Lange
    Nombre del programa: Vehiculo.java
    @version: 
        - Creación: 08/09/2021
        - Última modificación: 08/09/2021

    Clase que tendrá todos los datos del programa en cuestión.
    */

    //---------------------------PROPIEDADES-------------------------
    private String nombre;
    private double espacio;
    private int tiempo;

    //---------------------------MÉTODOS-----------------------------
    /****************************************************************
     * instancia de los atributos del programa con sus valores iniciales obtenidos como parámetros del método
     * @param nombre
     * @param espacio
     * @param tiempo
     */
    public Programa(String nombre, double espacio, int tiempo){
        this.nombre = nombre;
        this.espacio = espacio;
        this.tiempo = tiempo;
    }
    //***************************************************************

    /****************************************************************
     * devuelve el nombre del programa
     * @return nombre
     */
    public String getNombre(){
        return nombre;
    }
    //***************************************************************

    /****************************************************************
     * devuelve el espacio del programa
     * @return espacio
     */
    public double getEspacio(){
        return espacio;
    }
    //***************************************************************

    /****************************************************************
     * devuelve el tiempo de ejecución del programa
     * @return tiempo
     */
    public int getTiempo(){
        return tiempo;
    }
    //***************************************************************
}
