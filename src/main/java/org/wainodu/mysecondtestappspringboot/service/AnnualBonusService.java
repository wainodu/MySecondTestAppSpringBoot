package org.wainodu.mysecondtestappspringboot.service;

import org.springframework.stereotype.Service;
import org.wainodu.mysecondtestappspringboot.model.Positions;

@Service
public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
}
