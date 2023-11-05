package org.wainodu.mysecondtestappspringboot.service;

import org.springframework.stereotype.Service;
import org.wainodu.mysecondtestappspringboot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
