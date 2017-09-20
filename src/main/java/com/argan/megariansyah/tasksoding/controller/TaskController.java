package com.argan.megariansyah.tasksoding.controller;

import com.argan.megariansyah.tasksoding.config.SodingBaseController;
import com.argan.megariansyah.tasksoding.domain.Task;
import com.argan.megariansyah.tasksoding.service.task.TaskService;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by Argan on 9/11/2017.
 */
@Controller
@Slf4j
@RequestMapping(value = "/")
public class TaskController extends SodingBaseController {

    @Autowired
    private TaskService taskService;

    @Value("${actor.dummy}")
    private String actor;

    @RequestMapping(method = RequestMethod.GET)
    public String task(Model model) {
        return "task";
    }

    private int SIZE_PER_PAGE = 5;


    @Override
    @RequestMapping(value = "/getId")
    public ResponseEntity getId(HttpServletRequest request) {
        try {
            long id = Long.valueOf(request.getParameter("id"));
            Task task = taskService.findOne(id);
            return new ResponseEntity(task, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(errorMapping(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity getAll(HttpServletRequest request) {
        try {
            String pageNumber = request.getParameter("pageNumber");
            Page<Task> page = taskService.findAll(pageInit(Integer.parseInt(pageNumber)));
            return new ResponseEntity(page, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(errorMapping(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Pageable pageInit(int page) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated"));
        return new PageRequest(page, SIZE_PER_PAGE, sort);
    }

    @Override
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Task task = new Task(name, description);
            taskService.create(task);
            return new ResponseEntity(task, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(errorMapping(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity update(HttpServletRequest request) {
        try {
            long id = Long.valueOf(request.getParameter("id"));
            Task taskFind = new Task();
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            taskFind.setId(id);
            taskFind.setName(name);
            taskFind.setDescription(description);
            taskService.update(taskFind);
            return new ResponseEntity(taskFind, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(errorMapping(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity delete(HttpServletRequest request) {
        try {
            long id = Long.valueOf(request.getParameter("id"));
            Task taskDelete = taskService.delete(id);
            return new ResponseEntity(taskDelete, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(errorMapping(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
