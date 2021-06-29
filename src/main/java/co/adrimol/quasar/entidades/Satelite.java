package co.adrimol.quasar.entidades;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Satelite extends Nave {

    @JsonAlias(value = "distance")
    private double distancia;
    @JsonAlias(value = "name")
    private String nombre;
    @JsonAlias(value = "message")
    private List<String> mensaje;
}
