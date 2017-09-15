package com.argan.megariansyah.tasksoding.repository;

import com.argan.megariansyah.tasksoding.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Argan on 9/11/2017.
 */
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Page<Task> findAll(Pageable pageable);
}
