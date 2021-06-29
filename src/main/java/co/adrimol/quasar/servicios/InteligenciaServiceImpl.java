package co.adrimol.quasar.servicios;

import co.adrimol.quasar.controladores.InteligenciaService;
import co.adrimol.quasar.entidades.Nave;
import co.adrimol.quasar.entidades.Portacarga;
import co.adrimol.quasar.entidades.Posicion;
import co.adrimol.quasar.entidades.SateliteWrapper;
import co.adrimol.quasar.excepciones.MensajeException;
import co.adrimol.quasar.excepciones.UbicacionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class InteligenciaServiceImpl implements InteligenciaService {

    @Autowired
    UbicacionService ubicacionService;

    @Autowired
    MensajeService mensajeService;

    @Autowired
    private Environment environment;

    @Override
    public Nave getNave(String data) throws MensajeException, UbicacionException {

        SateliteWrapper sateliteWrapper = null;
        try {
            sateliteWrapper = new ObjectMapper().readValue(data, SateliteWrapper.class);
        } catch (JsonProcessingException e) {
            throw new MensajeException("Problema al procesar la informaciòn.");
        }
        if(sateliteWrapper.getMensajes().size() < 2)
            throw new MensajeException("Nùmero de mensajes insuficientes");
        String mensaje = mensajeService.getMensaje(sateliteWrapper.getMensajes());

        uploadPositions(sateliteWrapper);
        if( (sateliteWrapper.getPosiciones().length < 2) || (sateliteWrapper.getDistancias().length < 2) )
            throw new UbicacionException("Nùmero posicion o distancias insuficientes");
        double [] points = ubicacionService.getUbicacion(sateliteWrapper.getPosiciones(), sateliteWrapper.getDistancias());
        Posicion posicion = new Posicion(points);

        return new Portacarga(posicion, mensaje);
    }

    public void uploadPositions(SateliteWrapper sateliteWrapper){

        if(sateliteWrapper.getPosiciones()[0] == null) {
            int numberSat = Integer.parseInt(environment.getProperty("satellites.numbers"));
            double[][] pointsList = new double[numberSat][];
            String[] satellitePos;
            for (int i = 0; i < sateliteWrapper.getSatelites().size(); i++) {
                satellitePos = environment.getProperty("satellites." + i + ".position").split(",");
                pointsList[i] = Arrays.stream(satellitePos)
                        .map(Double::valueOf)
                        .mapToDouble(Double::doubleValue)
                        .toArray();
            }
            sateliteWrapper.setPosiciones(pointsList);
        }
    }
}
