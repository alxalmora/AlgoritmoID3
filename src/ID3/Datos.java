/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ID3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author AlexAlmora
 */
public class Datos {
    public static final int DATOBASE = 5;
    public static final String[] ATRIBUTOS= {"BI-RADS","Edad","Forma","Margen","Densidad","Tipo"};
    public static final String[] FORMA = {"","Redonda","Ovalada","Lobular","Irregular","Error F"};
    public static final String[] MARGEN={"","Circunscrito","Microlobular","Oscuro","Mal Formado","Espiculada","Error M"};
    public static final String[] DENSIDAD ={"","Alto","Iso","Bajo","Contenido Graso","Error D"};
    public static final String[] TIPO ={"Benigno","Maligno"};
    public static final String[] EDADES={"","19-42","43-65","66-88","Error Ed"};
    public static final int[] NUMTIPOS ={5,3,4,5,4,2};
    private Set<Integer> restricciones;
    private ArrayList<Tupla> datos;
    private Set<Integer>[] diferentes;
    private int datosBenignos;
    private int datosMalignos;
    public Datos(Set<Integer> restricciones, int nuevaRestriccion){
        this.restricciones = restricciones;
        diferentes = new HashSet[6];
        datos = new ArrayList<>();
        for(int i=0;i<6;i++){
            diferentes[i] = new HashSet<>();
        }
        datosBenignos=0;
        datosMalignos=0;
        if(nuevaRestriccion>=0){
            this.restricciones.add(nuevaRestriccion);
        }
    }
    public void addTupla(int[] dato){
        if(dato[5]==0){
            datosBenignos++;
        }else{
            datosMalignos++;
        }
        for(int i=0;i<6;i++){
            diferentes[i].add(dato[i]);
        }
        datos.add(new Tupla(dato));
    }
    public int encuentraEntropiaMaxima(){
        double max = Double.NEGATIVE_INFINITY;
        int index = -1;
        double aux;
        //System.out.println(Arrays.toString(restricciones.toArray()));
        for(int i=0;i<5;i++){
            if(restricciones.isEmpty() || !restricciones.contains(i)){
              aux =calculaEntropiaBase()-calculaEntropias(i);
              if(max<aux){
                  max = aux;
                  //System.out.println(i);
                  index = i;
            }
          }
        }
        if(datosMalignos+datosBenignos==0){
            index=-1;
        }
        return index;
    }
    public Set<Integer> regresaTipos(){
        return diferentes[encuentraEntropiaMaxima()];
    }
    public Iterator getRestricciones(){
        return restricciones.iterator();
    }
    public double calculaEntropiaBase(){
        double resp = 0;
        if(datosBenignos>0){
            resp = Funciones.Funciones.getLog2(datosBenignos, (datosMalignos+datosBenignos));
        }
        if(datosMalignos>0){
            resp += Funciones.Funciones.getLog2(datosMalignos, (datosMalignos+datosBenignos));
        }
        return resp;
    }
    private double calculaEntropias(int dato){
        double[] entropiasIndividuales= new double[diferentes[dato].size()];
        int[] numTipo = new int[diferentes[dato].size()];
        int benignos,malignos;
        double resp=0;
        int total = datosBenignos+datosMalignos;
        Iterator<Integer> it = diferentes[dato].iterator();
        for(int j=0;j<diferentes[dato].size();j++){
            benignos = 0;
            malignos =0;
            int comparador = it.next();
            for(int i=0;i<total;i++){
                if(datos.get(i).getDato(dato)==comparador){
                    if(datos.get(i).getDato(DATOBASE)==0){
                        benignos++;
                    }else{
                        malignos++;
                    }
                }
            }
            //System.out.println(benignos+" #Benignos "+malignos+" #Malignos "+(benignos+malignos)+" #Total");
            double aux = 0;
            if(benignos>0){
                aux=Funciones.Funciones.getLog2(benignos,(benignos+malignos));
            }
            if(malignos>0){
                aux+=Funciones.Funciones.getLog2(malignos,(benignos+malignos));
            }
            entropiasIndividuales[j]= aux;
            numTipo[j] = benignos+malignos;
        }
        for(int i=0;i<entropiasIndividuales.length;i++){
            resp +=((double)numTipo[i]/total)*entropiasIndividuales[i];
        }
        return resp;
    }
    public Datos getDatos(int columna, int tipo, Set<Integer> restricciones){
        Datos aux = new Datos(restricciones,-1);
        Tupla auxTuplas;
        //System.out.println(this.debTotal());
        for(int i=0;i<this.datos.size();i++){
            auxTuplas = this.datos.get(i);
            //System.out.println("Tipo: "+tipo+" Dato: "+auxTuplas.getDato(columna));
            if(auxTuplas.getDato(columna)==tipo){
                aux.addTupla(auxTuplas.getDatos().clone());
           //     System.out.println(Arrays.toString(auxTuplas.getDatos()));
            }
        }
        //aux.restricciones.add(aux.encuentraEntropiaMaxima());
        return aux;
    }
    public String esFinal(){
        if(this.encuentraEntropiaMaxima()==-1 || (datosMalignos+datosBenignos) == 0){
            return "No Hay Informacion";
        }
        if(restricciones.size() == 5){
            return "Probabilidad de que sea Benigno: "+(double)(datosBenignos/(double)(datosMalignos+datosBenignos));
        }
        else if(calculaEntropiaBase()!=0){
            return "Probabilidad de que sea Benigno: "+(double)(datosBenignos/(double)(datosMalignos+datosBenignos));
        }else if(datosBenignos>0){
            return "Benigno";
        }else{
            return "Maligno";
        }
    }
    public boolean puedeExpanderse(){
        if(datosBenignos+datosMalignos == 0){
            return false;
        }
        if(this.encuentraEntropiaMaxima()==-1){
            return false;
        }
        if(datos.isEmpty()){
            return false;
        }
        if(calculaEntropiaBase()==0.0){
            return false;
        }
        return restricciones.size() < 5;
    }
    
    public int debTotal(){
        return (datosBenignos+datosMalignos);
    }
}
