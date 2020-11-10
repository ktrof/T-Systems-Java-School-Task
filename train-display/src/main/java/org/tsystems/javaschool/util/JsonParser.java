package org.tsystems.javaschool.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.tsystems.javaschool.dto.StandUpdateDto;
import org.tsystems.javaschool.dto.StationDto;

import javax.ejb.Singleton;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@Singleton
@Slf4j
public class JsonParser {

    ObjectMapper objectMapper = new ObjectMapper();

    public StandUpdateDto readStandDtoJSON(String text) {
        StandUpdateDto standUpdateDto = new StandUpdateDto();
        try {
            standUpdateDto = objectMapper.readValue(text, StandUpdateDto.class);
        } catch (JsonProcessingException e) {
            log.error("Error reading JSON", e);
        }
        return standUpdateDto;
    }

}
