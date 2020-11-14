package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.model.entity.SectionEntity;

import java.util.Collection;
import java.util.List;

/**
 * The interface Section repository.
 *
 * @author Trofim Kremen
 */
public interface SectionRepository {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<SectionEntity> findAll();

    /**
     * Find by id section entity.
     *
     * @param id the id
     * @return the section entity
     */
    SectionEntity findById(int id);

    /**
     * Find by schedule section section entity id.
     *
     * @param id the id
     * @return the section entity
     */
    SectionEntity findByScheduleSectionId(int id);

    /**
     * Add section entity.
     *
     * @param sectionEntity the section entity
     */
    void add(SectionEntity sectionEntity);

    /**
     * Add iterable.
     *
     * @param sectionEntityCollection the section entity collection
     */
    void add(Collection<SectionEntity> sectionEntityCollection);

}
