package com.argan.megariansyah.tasksoding.service.task;

import com.argan.megariansyah.tasksoding.domain.Task;
import com.argan.megariansyah.tasksoding.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Argan on 9/20/2017.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Task findOne(Long aLong) {
        return taskRepository.findOne(aLong);
    }

    @Override
    public Task delete(Long aLong) throws Exception {
        Task task =taskRepository.findOne(aLong);
        if(task != null){
            taskRepository.delete(task);
            return task;
        }else {
           throw new Exception(" Task "+task.getName()+" not found");
        }
    }

    @Override
    public Task create(Task task) throws Exception {
        return taskRepository.save(task.createTask().validate());
    }

    @Override
    public Task update(Task task) throws Exception {
        Task taskFind = taskRepository.findOne(task.getId());
        if(taskFind != null){
            task.setDateCreated(taskFind.getDateCreated());
            taskRepository.save(task.updateTask().validate());
            return task;
        }else {
            throw new Exception(" Task "+task.getName()+" not found");
        }
    }
}
