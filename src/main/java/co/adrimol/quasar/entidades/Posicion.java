package co.adrimol.quasar.entidades;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Posicion {

    private double x;
    private double y;

    public Posicion(double[] puntos){
        this.x = puntos[0];
        this.y = puntos[1];
    }

    @Override
    public String toString(){
        return x+","+y;
    }
}
