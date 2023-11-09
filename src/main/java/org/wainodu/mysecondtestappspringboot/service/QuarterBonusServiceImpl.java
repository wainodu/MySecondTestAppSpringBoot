package org.wainodu.mysecondtestappspringboot.service;

import org.springframework.stereotype.Service;
import org.wainodu.mysecondtestappspringboot.model.Positions;

import java.time.Year;
import java.util.Calendar;

@Service
public class QuarterBonusServiceImpl implements QuarterBonusService {
    private static Integer currentYear = Year.now().getValue();
    private double formula;

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays, int quarterNumber) {
        this.formula = salary * bonus * positions.getPositionCoefficient() / workDays;
        if (!positions.isManager()) {
            return 0;
        }
        if (quarterNumber == 1) {
            if (isLeapYear(currentYear)) {
                return 91 * formula / 3;
            } else {
                return 90 * formula / 3;
            }
        }
        if (quarterNumber == 2) {
            return 91 * formula / 3;
        }
        return 92 * formula / 3;
    }
}
