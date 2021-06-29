package co.adrimol.quasar.servicios;

import co.adrimol.quasar.excepciones.MensajeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MensajeService {

    public List<String> getMsgPhrases(List<List<String>> msgList){

        List<String> listWords = new ArrayList<>();
        for( List<String> msg : msgList){
            listWords = Stream.concat(listWords.stream(), msg.stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        listWords.remove("");
        return listWords;
    }

    public void removerBrecha(List<List<String>> msgList, int gapSize){

        int s = 0;
        for(int i = 0; i < msgList.size(); i++){
            s = msgList.get(i).size();
            msgList.set(i, msgList.get(i).subList(s-gapSize, s));
        }

        //msgList.stream().forEach(s -> s.subList(s.size()-lagSize, s.size()));
    }

    public String completeMessage(List<List<String>> msgList){

        String phrase = "";
        for(List<String> m : msgList){
            if(m.size()>0 && !m.get(0).equals("")){
                phrase = (m.size() == 1) ? m.get(0) : m.get(0) + " ";
                msgList.stream().forEach( s -> s.remove(0));
                return  phrase + completeMessage(msgList);
            }
        }
        return "";
    }

    public String getMensaje(List<List<String>> msgList) throws MensajeException {

        List<String> msgPhrases = getMsgPhrases(msgList);
        if(!validarTamanioMensajes(msgList, msgPhrases.size()))
            throw new MensajeException("Tamaño del mensaje incorrecto");

        removerBrecha(msgList,msgPhrases.size());
        String message = completeMessage(msgList);
        if(!validateMessagePhrases(msgPhrases,message))
            throw new MensajeException("No se puede conocer el mensaje");

        return message;
    }

    public boolean validarTamanioMensajes(List<List<String>> mensajes, int tamanio){
        for(List<String> m : mensajes){
            if(m.size() < tamanio){
                return false;
            }
        }
        return true;
    }

    public boolean validateMessagePhrases(List<String> phrases, String message){
        List<String> msg = Arrays.stream(message.split(" ")).collect(Collectors.toList());
        Collections.sort(phrases);
        Collections.sort(msg);
        return Arrays.equals(phrases.toArray(), msg.toArray());
    }
}
