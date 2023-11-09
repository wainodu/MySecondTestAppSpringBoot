package org.wainodu.mysecondtestappspringboot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wainodu.mysecondtestappspringboot.exception.UnsupportedCodeException;
import org.wainodu.mysecondtestappspringboot.exception.ValidationFailedException;
import org.wainodu.mysecondtestappspringboot.model.*;
import org.wainodu.mysecondtestappspringboot.service.*;
import org.wainodu.mysecondtestappspringboot.util.DateTimeUtil;


import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final UnsupportedCodeValidationService unsupportedCodeValidationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifySourceRequestService modifySourceRequestService;
    private final ModifySystemNameRequestService modifySystemNameRequestService;
    private final AnnualBonusServiceImpl annualBonusService;
    private final QuarterBonusServiceImpl quarterBonusService;


    @Autowired
    public MyController(ValidationService validationService, UnsupportedCodeValidationService unsupportedCodeValidationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        ModifySourceRequestService modifySourceRequestService,
                        ModifySystemNameRequestService modifySystemNameRequestService,
                        AnnualBonusServiceImpl annualBonusService,
                        QuarterBonusServiceImpl quarterBonusService) {
        this.validationService = validationService;
        this.unsupportedCodeValidationService = unsupportedCodeValidationService;
        this.modifyResponseService = modifyResponseService;
        this.modifySourceRequestService = modifySourceRequestService;
        this.modifySystemNameRequestService = modifySystemNameRequestService;
        this.annualBonusService = annualBonusService;
        this.quarterBonusService = quarterBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("response: {}", response);

        try {
            unsupportedCodeValidationService.isValid(Integer.parseInt(request.getUid()));
        } catch (UnsupportedCodeException e) {
            log.error("request: {}", request);
            log.error("response: {}", response);
            log.error("bindingResultError: {}", bindingResult.getFieldError().getDefaultMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            log.error("request: {}", request);
            log.error("response: {}", response);
            log.error("bindingResultError: {}", bindingResult.getFieldError().getDefaultMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("request: {}", request);
            log.error("response: {}", response);
            log.error("bindingResultError: {}", bindingResult.getFieldError().getDefaultMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        modifyResponseService.modify(response);
//        это для прошлой лабы
//        modifySourceRequestService.modify(request);
//        modifySystemNameRequestService.modify(request);

        Double annualBonus = annualBonusService.calculate(request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays());

        Double quarterBonus = quarterBonusService.calculate(request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays(),
                request.getQuarterNumber());

        if (annualBonus > quarterBonus) {
            response.setBonus(annualBonus);
        } else {
            response.setBonus(quarterBonus);
        }

        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }
}
