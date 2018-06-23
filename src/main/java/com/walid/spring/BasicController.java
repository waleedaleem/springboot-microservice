package com.walid.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BasicController {

    @GetMapping({"/welcome/{name}", "/welcome"})
    public String welcome(@PathVariable(value = "name", required = false) Optional<String> name) {
        return String.format("Hello %s!", name.orElse("World"));
    }
}
