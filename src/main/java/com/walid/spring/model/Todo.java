package com.walid.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private int id;

    @NotNull
    private String user;

    @Size(min = 10, message = "Enter at least 10 characters.")
    private String desc;
    private Date targetDate;
    private boolean isDone;
}