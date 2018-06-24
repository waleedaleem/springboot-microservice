package com.walid.spring;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class WelcomeBean {

    @NotNull
    private String message;
}
