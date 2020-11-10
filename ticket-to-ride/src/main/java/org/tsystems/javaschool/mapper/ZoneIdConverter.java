package org.tsystems.javaschool.mapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;

/**
 * @author Trofim Kremen
 */
@Converter
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {


    @Override
    public String convertToDatabaseColumn(ZoneId zoneId) {
        if (zoneId == null ) {
            return null;
        }
        return zoneId.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return ZoneId.of(dbData);
    }
}
