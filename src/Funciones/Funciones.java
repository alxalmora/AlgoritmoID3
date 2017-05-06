/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import ID3.Datos;
import ID3.Nodo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * @author AlexAlmora
 */
public class Funciones {

    public static final double LOG2 = Math.log(2);

    public static double getLog2(int x, int total) {
        double ratio = ((double) x / (double) total);
        double log = Math.log(ratio) / LOG2;
        double resp = -1 * log * (ratio);
        return resp;
    }

    public static Datos leerDatos() {
        Datos datos = new Datos(new HashSet<Integer>(), -1);
        try (BufferedReader br = new BufferedReader(new FileReader("Resources/data.txt"))) {
            //System.out.println("entro");
            String sCurrentLine;
            StringTokenizer st;
            while ((sCurrentLine = br.readLine()) != null) {
                if (!sCurrentLine.contains("?")) {
                    st = new StringTokenizer(sCurrentLine, ",");
                    int[] arrAux = new int[6];
                    for (int i = 0; i < 6; i++) {
                        arrAux[i] = Integer.parseInt(st.nextToken());
                    }
                    if(arrAux[1]<43){
                        arrAux[1]=1;
                    }else if(arrAux[1]<66){
                        arrAux[1]=2;
                    }else{
                        arrAux[1]=3;
                    }
                    //edad+=arrAux[1];
                    //total++;
                    //minEdad=Math.min(minEdad, arrAux[1]);
                    //maxEdad=Math.max(maxEdad, arrAux[1]);
                    datos.addTupla(arrAux);
                }
            }
            //System.out.println("Edad: "+edad+" Total "+total+" Min edad: "+minEdad+" Max Edad: "+maxEdad);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return datos;
    }
    
    public static void imprimeIdent(Nodo print, int idn){
        if(print.toString() == null){
            System.out.println("Por alguna razon es nulo");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<idn;i++){
            sb.append("\t");
        }
        sb.append(print.toString());
        System.out.println(sb.toString());
    }
    public static void imprimeDatos(Nodo padre,int idn){
        if(!padre.hasNode()){
            imprimeIdent(padre,idn);
        }else{
            imprimeIdent(padre,idn);
            Iterator it = padre.getHijos();
            while(it.hasNext()){
                
                imprimeDatos((Nodo)it.next(),idn+1);
            }
        }
    }
}
