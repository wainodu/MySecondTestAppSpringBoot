package org.wainodu.mysecondtestappspringboot.service;

import org.springframework.stereotype.Service;
import org.wainodu.mysecondtestappspringboot.exception.UnsupportedCodeException;
@Service
public class UnsupportedCodeValidationService {
    public void isValid(int uid) throws UnsupportedCodeException {
        if (uid == 123) {
            throw new UnsupportedCodeException();
        }
    }
}
