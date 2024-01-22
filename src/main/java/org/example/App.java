package org.example;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException {
        JsonReader jsonReader = new JsonReader();
        File json = new File("src/main/java/org/example/tickets.json");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Tickets tickets = jsonReader.getTickets(json);
        List<Ticket> tickets1 = tickets.getTickets().stream().filter(ticket -> ticket.getOrigin_name().equals("Владивосток"))
                .filter(ticket -> ticket.getDestination_name().equals("Тель-Авив")).collect(Collectors.toList());
        Map<String,Long> min_fly = new HashMap<>();
        List<Long> price = new ArrayList<>();
        long sum = 0;
        for (Ticket ticket : tickets1) {
            Date departure_date = dateFormat.parse(ticket.getDeparture_date());
            Date departure_time = timeFormat.parse(ticket.getDeparture_time());
            departure_date.setHours(departure_time.getHours());
            departure_date.setMinutes(departure_time.getMinutes());
            Date arrival_date = dateFormat.parse(ticket.getArrival_date());
            Date arrival_time = timeFormat.parse(ticket.getArrival_time());
            arrival_date.setHours(arrival_time.getHours());
            arrival_date.setMinutes(arrival_time.getMinutes());
            price.add(ticket.getPrice());
            sum += ticket.getPrice();
            long time_between = arrival_time.getTime() - departure_time.getTime();
            if(min_fly.getOrDefault(ticket.getCarrier(),Long.MAX_VALUE) > time_between )
                min_fly.put(ticket.getCarrier(),time_between);
        }
        min_fly.forEach((k,v) -> System.out.println(k + " " + String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(v),
                TimeUnit.MILLISECONDS.toMinutes(v)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(v)))));
        price.sort((x,y) -> Math.toIntExact(x - y));
        long ans = (sum/price.size())-price.get(price.size()/2);
        System.out.println(ans);
    }
}
