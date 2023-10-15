package org.wainodu.mysecondtestappspringboot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.wainodu.mysecondtestappspringboot.exception.ValidationFailedException;

@Service
public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}
