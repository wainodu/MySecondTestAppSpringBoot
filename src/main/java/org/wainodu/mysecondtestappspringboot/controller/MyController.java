package org.wainodu.mysecondtestappspringboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wainodu.mysecondtestappspringboot.exception.UnsupportedCodeException;
import org.wainodu.mysecondtestappspringboot.exception.ValidationFailedException;
import org.wainodu.mysecondtestappspringboot.model.Request;
import org.wainodu.mysecondtestappspringboot.model.Response;
import org.wainodu.mysecondtestappspringboot.service.UnsupportedCodeValidationService;
import org.wainodu.mysecondtestappspringboot.service.ValidationService;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MyController {
    private final ValidationService validationService;
    private final UnsupportedCodeValidationService unsupportedCodeValidationService;

    public MyController(ValidationService validationService, UnsupportedCodeValidationService unsupportedCodeValidationService) {
        this.validationService = validationService;
        this.unsupportedCodeValidationService = unsupportedCodeValidationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();
        try {
            unsupportedCodeValidationService.isValid(Integer.parseInt(request.getUid()));
        } catch (UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage("uid = 123");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
