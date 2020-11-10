package org.tsystems.javaschool.service;

import org.tsystems.javaschool.dto.StandDto;
import org.tsystems.javaschool.dto.StationDto;
import org.tsystems.javaschool.util.JsonParser;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Trofim Kremen
 */
@Singleton
public class RestClientService implements Serializable {

    private Client client;

    public StandDto getStandByStationIdAndRideDate(int stationId, LocalDate rideDate) {
        Client client = getClient();
        WebTarget uriTarget = getUriTarget(client);
        WebTarget endpointTarget = uriTarget
                .path("stations")
                .path(String.valueOf(stationId))
                .path("stand")
                .queryParam("rideDate", rideDate);
        System.out.println(endpointTarget.getUri().toString());
        return endpointTarget.request(MediaType.APPLICATION_JSON_TYPE).get(StandDto.class);
    }

    public List<StationDto> getStationStandList() {
        Client client = getClient();
        WebTarget uriTarget = getUriTarget(client);
        WebTarget endpointTarget = uriTarget
                .path("stations");
        System.out.println(endpointTarget.getUri().toString());
        return Arrays.asList(endpointTarget.request(MediaType.APPLICATION_JSON_TYPE).get(StationDto[].class));
    }

    private Client getClient() {
        if (Objects.isNull(client)) {
            client = ClientBuilder.newClient();
        }
        return client;
    }

    private WebTarget getUriTarget(Client client) {
        return client.target(URI.create("http://172.17.0.1:8080/api/"));
    }
}
