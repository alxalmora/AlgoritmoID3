/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ID3;

/**
 *
 * @author AlexAlmora
 */
public class ID3 {
    private Nodo mainNodo;
   public ID3(){
       Datos aux = Funciones.Funciones.leerDatos();
       mainNodo = new Nodo(aux.encuentraEntropiaMaxima(),-1, aux,-1);
       Funciones.Funciones.imprimeDatos(mainNodo, 0);
   } 
   public String buscaDiagnostico(int[] cad){
       Nodo aux = mainNodo;
       while(!aux.hojaFinal()){
           aux=aux.getNodo(cad[aux.getColumna()]);
       }
       return aux.getDiagnostico();
   }
}
