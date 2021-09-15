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
        - Última modificación: 15/09/2021

    Clase que tendrá todos los datos del programa en cuestión.
    */

    //---------------------------PROPIEDADES-------------------------
    private String nombre;
    private double espacio;
    private double tiempo;

    //---------------------------MÉTODOS-----------------------------
    /****************************************************************
     * instancia de los atributos del programa con sus valores iniciales obtenidos como parámetros del método
     * @param nombre
     * @param espacio
     * @param tiempo
     */
    public Programa(String nombre, double espacio, double tiempo){
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
    public double getTiempo(){
        return tiempo;
    }
    //***************************************************************

    /****************************************************************
     * setTiempo: Reduce el tiempo del programa en fracción al espacio usado
     */
    public void setTiempo(){
        double bloques = this.espacio/64;
        double bloques_programa = Math.ceil(bloques);
        this.tiempo -= 1/bloques_programa;
    }
    //***************************************************************
}
