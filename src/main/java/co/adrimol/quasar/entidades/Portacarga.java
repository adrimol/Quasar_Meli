package co.adrimol.quasar.entidades;

import lombok.Getter;
import lombok.Setter;


public class Portacarga extends Nave {

    @Getter
    @Setter
    private String mensaje;

    public Portacarga(Posicion posicion, String mensaje){
        this.setPosicion(posicion);
        this.mensaje = mensaje;
    }
}
