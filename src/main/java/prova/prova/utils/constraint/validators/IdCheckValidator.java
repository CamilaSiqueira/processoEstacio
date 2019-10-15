package prova.prova.utils.constraint.validators;

import prova.prova.security.dto.JwtAuthenticationDto;
import prova.prova.utils.constraint.IdCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdCheckValidator implements ConstraintValidator<IdCheck, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        JwtAuthenticationDto authenticationDto = (JwtAuthenticationDto) object;

        if (authenticationDto.getEmail() != null) {
            return true;
        } else if (authenticationDto.getId() != null) {
            return true;
        }

        return false;
    }
}
