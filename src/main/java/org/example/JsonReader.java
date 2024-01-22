package org.example;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ext.JodaDeserializers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


public class JsonReader {
    public Tickets getTickets(File json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd.MM.yy"));

        Tickets tickets = null;
        try {
            tickets = mapper.readValue(json,Tickets.class);
        }
        catch (IOException e){
            System.out.println(e);
        }
        return tickets;
    }
}
