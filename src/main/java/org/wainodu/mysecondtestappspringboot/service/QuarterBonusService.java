package org.wainodu.mysecondtestappspringboot.service;

import org.springframework.stereotype.Service;
import org.wainodu.mysecondtestappspringboot.model.Positions;

@Service
public interface QuarterBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays, int quarterNumber);
}