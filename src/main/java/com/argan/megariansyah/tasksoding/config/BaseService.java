package com.argan.megariansyah.tasksoding.config;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Argan on 9/20/2017.
 */
public interface BaseService<T, Serialize> {
    Page<T> findAll(Pageable pageable);

    T findOne(Serialize serialize);

    T delete(Serialize serialize) throws Exception;

    T create(T t) throws Exception;

    T update(T t) throws Exception;

}
