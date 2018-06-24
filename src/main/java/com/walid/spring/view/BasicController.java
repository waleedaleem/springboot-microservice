package com.walid.spring.view;

import com.walid.spring.model.WelcomeBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BasicController {

    @GetMapping({"/welcome/name/{name}", "/welcome"})
    public String welcome(@PathVariable(value = "name", required = false) Optional<String> name) {
        return String.format("Welcome %s!", name.orElse("World"));
    }

    @GetMapping(value = "/welcome/object/name/{name}")
    public WelcomeBean welcomeWithBean(@PathVariable("name") String name) {
        return new WelcomeBean(String.format("Welcome %s!", name));
    }
}
