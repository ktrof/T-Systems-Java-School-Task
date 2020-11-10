package org.tsystems.javaschool.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.tsystems.javaschool.model.dto.stand.StandUpdateDto;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Trofim Kremen
 */
@Component
public class JsonParser {

    public String writeToJSON(StandUpdateDto standUpdateDto) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, standUpdateDto);
        return stringWriter.toString();
    }

}
