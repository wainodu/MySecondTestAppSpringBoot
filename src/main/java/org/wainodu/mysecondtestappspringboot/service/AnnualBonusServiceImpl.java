package org.wainodu.mysecondtestappspringboot.service;

import org.springframework.stereotype.Service;
import org.wainodu.mysecondtestappspringboot.model.Positions;

import java.time.Year;
import java.util.Calendar;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {
    private Integer currentYear = Year.now().getValue();

    public static int countYearDays(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        if (positions.isManager()){
            return 0;
        }
        return salary * bonus * countYearDays(this.currentYear) * positions.getPositionCoefficient() / workDays;
    }
}
