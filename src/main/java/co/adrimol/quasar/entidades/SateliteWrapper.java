package co.adrimol.quasar.entidades;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class SateliteWrapper {

    @Getter
    @Setter
    @JsonAlias(value = "satellites")
    private List<Satelite> satelites;

    public double[] getDistancias(){
        return satelites.stream()
                .mapToDouble(s -> s.getDistancia())
                .toArray();
    }

    public double[][] getPosiciones(){
        double [][] posiciones = new double[satelites.size()][];
        String[] puntos;
        for(int i = 0; i < satelites.size(); i ++){
            if(satelites.get(i).getPosicion() != null) {
                puntos = satelites.get(i).getPosicion().toString().split(",");
                posiciones[i] = Arrays.stream(puntos)
                        .map(Double::valueOf)
                        .mapToDouble(Double::doubleValue)
                        .toArray();
            }
        }
        return posiciones;
    }

    public void setPosiciones(double[][] puntosList){
        Posicion posicion;
        for(int i = 0; i < puntosList.length; i++){
            posicion = new Posicion(puntosList[i]);
            satelites.get(i).setPosicion(posicion);
        }
    }

    public List<List<String>> getMensajes(){
        return satelites.stream()
                .map(s -> s.getMensaje())
                .collect(Collectors.toList());
    }
}
