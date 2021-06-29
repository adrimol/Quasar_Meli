package co.adrimol.quasar.controladores;

import co.adrimol.quasar.entidades.Satelite;
import co.adrimol.quasar.entidades.SateliteWrapper;
import co.adrimol.quasar.excepciones.MensajeException;
import co.adrimol.quasar.excepciones.UbicacionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ComunicacionController {

    @Autowired
    private InteligenciaService inteligenciaService;
    @Autowired
    private Environment environment;
    private final List<Satelite> lstSatelites = new ArrayList<>();

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE},
            path = "/topsecret")
    public ResponseEntity topSecret(RequestEntity<String> requestEntity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(inteligenciaService.getNave(requestEntity.getBody()));
        } catch (MensajeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (UbicacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,},
            path = "/topsecret_split/{satellite_name}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity topSecret_splitPost(RequestEntity<String> requestEntity,
                                              @PathVariable("satellite_name") String satelliteName) {
        try {
            //Validar si satelite ya existe
            List<Satelite> lstSat = lstSatelites.stream()
                    .filter(s -> s.getNombre().equals(satelliteName))
                    .collect(Collectors.toList());
            //Si el satelite es nuevo y el trinomio de satelites ya existe iniciar nuevo trinomio
            if (lstSat.size() == 0) {
                if (lstSatelites.size() == 3) {
                    lstSatelites.clear();
                }
                lstSatelites.add(procesarSatelite(lstSat, requestEntity, satelliteName));
            }else{
                procesarSatelite(lstSat, requestEntity, satelliteName);
            }
            if (lstSatelites.size() < 3) {
                StringBuilder mensaje = new StringBuilder();
                if(lstSatelites.size()==1){
                    mensaje.append("Iniciando trilateracion... ");
                }
                mensaje.append("Se requieren ").append(environment.getProperty("satellites.numbers"));
                mensaje.append(" sátelites para completar cálculo.");
                return ResponseEntity.status(HttpStatus.OK).body(mensaje.toString());
            } else {
                StringBuilder data = new StringBuilder("{\"satellites\":");
                data.append(new ObjectMapper().writeValueAsString(lstSatelites));
                data.append("}");
                return ResponseEntity.status(HttpStatus.OK).body(inteligenciaService.getNave(data.toString()));
            }
        } catch (MensajeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (UbicacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private Satelite procesarSatelite(List<Satelite> lstSat, RequestEntity requestEntity, String satelliteName) {
        try {
            Satelite dataSat = new ObjectMapper().readValue(requestEntity.getBody().toString(), Satelite.class);
            Satelite sat = lstSat.size() > 0 ? lstSat.get(0) : new Satelite();
            sat.setNombre(satelliteName);
            sat.setDistancia(dataSat.getDistancia());
            sat.setMensaje(dataSat.getMensaje());
            return sat;
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
