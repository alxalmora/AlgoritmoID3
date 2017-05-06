/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ID3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author AlexAlmora
 */
public class Nodo {

    private int columna;
    private int tipoRelacionPadre;
    private int columnaRelacionPadre;
    private ArrayList<Nodo> hijos;
    private Datos datos;
    private String esFinal;
    private int id;
    private static int idF = 0;

    public Nodo(int columna, int tipo, Datos datos, int columnaRelacionPadre) {
        this.columna = columna;
        this.columnaRelacionPadre = columnaRelacionPadre;
        this.tipoRelacionPadre = tipo;
        idF++;
        this.id = idF;
        this.datos = datos;
        hijos = new ArrayList<>();
        //System.out.println("ID: "+id+" Nodo: "+Datos.ATRIBUTOS[this.columna]+" Tipo: "+this.tipoRelacionPadre+" Elementos Tabla"+this.datos.debTotal());
        //System.out.println(this.toString());
            crearNodos();
    }

    public void addNodo(Nodo node) {
        hijos.add(node);
    }

    public boolean hasNode() {
        return !hijos.isEmpty();
    }

    public boolean hojaFinal() {
        esFinal = datos.esFinal();
        return (!datos.puedeExpanderse());
    }
    public String getDiagnostico(){
        return esFinal;
    }
    private void crearNodos() {
       if (!hojaFinal()) {
            int auxTipo;
            for (int i = 1; i <= Datos.NUMTIPOS[columna]; i++) {
                Iterator it = datos.getRestricciones();
                Set<Integer> nuevasRestricciones = new HashSet<>();
                while (it.hasNext()) {
                    nuevasRestricciones.add((int) (it.next()));
                }
                nuevasRestricciones.add(columna);
                auxTipo = i;
                Datos filtro = datos.getDatos(columna, auxTipo, nuevasRestricciones);
                //System.out.println(Datos.ATRIBUTOS[columna]+" --> tamaño:"+filtro.debTotal()+" --> Tipo"+ auxTipo+"--> siguiente E:"+filtro.encuentraEntropiaMaxima());     
                this.addNodo(new Nodo(filtro.encuentraEntropiaMaxima(), auxTipo, filtro,columna));
            }
        }
    }

    public String toString() {
        String aux;
        switch (columnaRelacionPadre) {
            case -1:
                return "";
            case 0:
                aux = Integer.toString(tipoRelacionPadre);
                break;
            case 1:
                aux = Datos.EDADES[tipoRelacionPadre];
                break;
            case 2:
                aux = Datos.FORMA[tipoRelacionPadre];
                break;
            case 3:
                aux = Datos.MARGEN[tipoRelacionPadre];
                break;
            case 4:
                aux = Datos.DENSIDAD[tipoRelacionPadre];
                break;
            case 5:
                aux = Datos.TIPO[tipoRelacionPadre];
                break;
            default:
                aux = "Error en el Tipo";
        }
        if (!hasNode()) {
            return Datos.ATRIBUTOS[columnaRelacionPadre] + " --" + aux + "->" + esFinal;
        } else {
            return Datos.ATRIBUTOS[columnaRelacionPadre] + " --" + aux + "->";
        }
    }
    public int getColumna(){
        return columna;
    }
    public int getTipo(){
        return tipoRelacionPadre;
    }
    public Nodo getNodo(int tipo){
        Iterator it = this.getHijos();
        Nodo aux = null;
        while(it.hasNext()){
        aux = (Nodo) it.next();
            if(aux.getTipo()==tipo){
                return aux;
            }
        }
        return aux;
    }
    public Iterator<Nodo> getHijos() {
        return hijos.iterator();
    }
    public int numeroHijos(){
        return hijos.size();
    }
}
