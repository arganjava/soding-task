package com.argan.megariansyah.tasksoding.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Argan on 9/12/2017.
 */
public abstract class SodingBaseCrud {
    public abstract @ResponseBody ResponseEntity getId(HttpServletRequest request);

    public abstract @ResponseBody ResponseEntity getAll(HttpServletRequest request);

    public abstract @ResponseBody ResponseEntity create(HttpServletRequest request);

    public abstract @ResponseBody ResponseEntity update(HttpServletRequest request);

    public abstract @ResponseBody ResponseEntity delete(HttpServletRequest request);

    public Map errorMapping(String errorMessage){
        Map map = new HashMap();
        map.put("error", errorMessage);
        return map;
    }

}
