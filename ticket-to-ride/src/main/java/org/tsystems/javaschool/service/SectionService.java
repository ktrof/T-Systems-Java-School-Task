package org.tsystems.javaschool.service;

import org.tsystems.javaschool.model.dto.SectionDto;

import java.util.List;

/**
 * The interface Section service.
 *
 * @author Trofim Kremen
 */
public interface SectionService {

    /**
     * Gets all.
     *
     * @return the all
     */
    List<SectionDto> getAll();

}
