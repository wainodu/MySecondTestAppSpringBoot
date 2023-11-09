package org.wainodu.mysecondtestappspringboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    /**
     * Уникальный идентификатор сообщения
     */
    private String uid;
    /**
     * Уникальный идентификатор операции
     */
    private String operationUid;
    /**
     * Время создания сообщения
     */
    private String systemTime;
    /**
     * Код результата
     */
    private Codes code;
    /**
     * Бонус
     */
    private Double bonus;
    /**
     * Код ошибки
     */
    private ErrorCodes errorCode;
    /**
     * Сообщение ошибки
     */
    private ErrorMessages errorMessage;

}
