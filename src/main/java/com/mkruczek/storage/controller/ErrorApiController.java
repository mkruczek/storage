package com.mkruczek.storage.controller;

import com.mkruczek.storage.exception.exceptions.AddressNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorApiController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        throw new AddressNotFoundException("Page not found.");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}