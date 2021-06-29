package co.adrimol.quasar.controladores;

import co.adrimol.quasar.entidades.Nave;
import co.adrimol.quasar.excepciones.MensajeException;
import co.adrimol.quasar.excepciones.UbicacionException;
import org.springframework.http.RequestEntity;

public interface InteligenciaService {

    public Nave getNave(String data) throws MensajeException, UbicacionException;
}
