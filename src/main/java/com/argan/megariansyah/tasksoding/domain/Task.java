package com.argan.megariansyah.tasksoding.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Argan on 9/11/2017.
 */
@Entity(name = "task_soding")
@Data
public class Task {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", length = 30, nullable = false)
    private String name;
    @Column(name = "description", length = 150, nullable = true)
    private String description;
    @Column(name = "date_created" )
    private Timestamp dateCreated;
    @Column(name = "date_updated" )
    private Timestamp dateUpdated;

    public Task() {
    }

    public Task validate() throws Exception {
        if(this.name == null || this.name.isEmpty()){
            throw new Exception("Name Mandatory");
        }else if(name.length() > 30){
            throw new Exception("Name Maximum 30 Character");
        }else if(description.length() > 150){
            throw new Exception("Description Maximum 150 Character");
        }
        return this;
    }


    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Task createTask(){
        this.setDateCreated(this.dateToTimeStamp(new Date()));
        return this;
    }

    public Task updateTask() {
        this.setDateUpdated(this.dateToTimeStamp(new Date()));
        return this;
    }

    private Timestamp dateToTimeStamp(Date date){
        long dateTime = date.getTime();
        return new Timestamp(dateTime);
    }

}
