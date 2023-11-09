package org.wainodu.mysecondtestappspringboot.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    /**
     * Уникальный идентификатор сообщения
     */
    @NotBlank
    @Size(max = 32)
    private String uid;
    /**
     * Уникальный идентификатор операции
     */
    @NotBlank
    @Size(max = 32)
    private String operationUid;
    /**
     * Имя системы отправителя
     */
    private Systems systemName;
    /**
     * Время создания сообщения
     */
    @NotBlank
    private String systemTime;
    /**
     * Наименование ресурса
     */
    private String source;
    /**
     * Должность
     */
    private Positions position;
    /**
     * Зарплата
     */
    private Double salary;
    /**
     * Бонус
     */
    private Double bonus;
    /**
     * Количество рабочих дней
     */
    private Integer workDays;
    /**
     * Уникальный идентификатор коммуникации
     */
    @Min(1)
    @Max(100000)
    private int communicationId;
    /**
     * Уникальный идентификатор шаблона
     */
    private int templateId;
    /**
     * Код продукта
     */
    private int productCode;
    /**
     * СМС код
     */
    private int smsCode;
    /**
     * Номер квартала
     */
    private int quarterNumber;

    @Override
    public String toString() {
        return "{" +
                "uid" + uid + '\'' +
                ", operationUid ='" + operationUid + '\'' +
                ", systemName ='" + systemName + '\'' +
                ", systemTime ='" + systemTime + '\'' +
                ", source ='" + source + '\'' +
                ", communicationId ='" + communicationId +
                ", templateId ='" + templateId +
                ", productCode ='" + productCode +
                ", smsCode ='" + smsCode +
                '}';
    }
}
