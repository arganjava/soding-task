package com.argan.megariansyah.tasksoding.controller;

import com.argan.megariansyah.tasksoding.config.SodingBaseCrud;
import com.argan.megariansyah.tasksoding.domain.Task;
import com.argan.megariansyah.tasksoding.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Argan on 9/11/2017.
 */
@Controller
@Slf4j
@RequestMapping(value = "/")
public class SodingController extends SodingBaseCrud {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String task(Model model) {
        model.addAttribute("name", "sss");
        return "task";
    }

    private int SIZE_PER_PAGE = 5;
    private int CURRENT_PAGE = 1;


    @Override
    @RequestMapping(value = "/getId")
    public ResponseEntity getId(HttpServletRequest request) {
        try {
            long id = Long.valueOf(request.getParameter("id"));
            Task task = taskRepository.findOne(id);
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
            String pageCondition = request.getParameter("pageCondition");
            String pageNumber = request.getParameter("pageNumber");

            Page<Task> page = null;

            if ("isPaging".equals(pageCondition)) {
                page = taskRepository.findAll(new PageRequest(Integer.parseInt(pageNumber), SIZE_PER_PAGE));
            } else if ("isPaging".equals(pageCondition)) {
                page = taskRepository.findAll(pageInit(Integer.parseInt(pageNumber)).previousOrFirst());
            } else {
                page = taskRepository.findAll(pageInit(CURRENT_PAGE).first());
            }
            return new ResponseEntity(page, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(errorMapping(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Pageable pageInit(int page) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "dateCreated"));
        return new PageRequest(page, SIZE_PER_PAGE, sort);
    }

    @Override
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Task task = new Task(name, description).createTask().validate();
            taskRepository.save(task);
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
            Task taskFind = taskRepository.findOne(id);
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            if (taskFind != null) {
                taskFind.setName(name);
                taskFind.setDescription(description);
                taskFind.updateTask().validate();
                taskRepository.save(taskFind);
            }
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
            Task taskFind = taskRepository.findOne(id);
            taskRepository.delete(taskFind);
            return new ResponseEntity(taskFind, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(errorMapping(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
