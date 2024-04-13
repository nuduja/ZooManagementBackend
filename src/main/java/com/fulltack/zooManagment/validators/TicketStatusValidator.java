package com.fulltack.zooManagment.validators;

import com.fulltack.zooManagment.enums.TicketStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TicketStatusValidator implements ConstraintValidator<ValidTicketStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        for (TicketStatus status : TicketStatus.values()) {
            if (status.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}