package org.wainodu.mysecondtestappspringboot.service;

import org.junit.jupiter.api.Test;
import org.wainodu.mysecondtestappspringboot.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class QuarterBonusServiceImplTest {
    @Test
    void calculate() {
        Positions position = Positions.TL;
        double bonus = 2.0;
        int workDays = 73;
        double salary = 100000.00;
        int quarterNumber = 1;
        double result = new QuarterBonusServiceImpl().calculate(position, salary, bonus, workDays, quarterNumber);
        double expected = 213698.6301369863;
        System.out.println(result);
        assertThat(result).isEqualTo(expected);
    }
}