package com.clinicavet.clinicavet.exeption;


import org.springframework.ui.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseError(DataAccessException e, Model model) {
        model.addAttribute("error", "Error en la base de datos");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}