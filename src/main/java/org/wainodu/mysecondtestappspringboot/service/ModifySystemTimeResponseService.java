package org.wainodu.mysecondtestappspringboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wainodu.mysecondtestappspringboot.model.Response;
import org.wainodu.mysecondtestappspringboot.util.DateTimeUtil;

import java.util.Date;
@Slf4j
@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        log.info("response: {}", response);
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        return response;
    }
}
