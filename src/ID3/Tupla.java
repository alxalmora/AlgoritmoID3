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
public class Tupla {

    private int[] datos;

    public Tupla(int[] data) {
        datos = data;
    }
    public int[] getDatos(){
        return datos;
    }
    public int getDato(int index){
        return datos[index];
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("BI-RADS: ").append(datos[0]);
        sb.append("| Edad: ").append(datos[1]);
        sb.append("| Forma: ").append(Datos.FORMA[datos[2]]);
        sb.append("| Margen: ").append(Datos.MARGEN[datos[3]]);
        sb.append("| Densidad: ").append(Datos.DENSIDAD[datos[4]]);
        sb.append("| Tipo: ").append(Datos.TIPO[datos[5]]);
        return sb.toString();
    }
    
}
