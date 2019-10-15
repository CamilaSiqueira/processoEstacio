package prova.prova.utils.constraint.validators;

import org.springframework.util.ObjectUtils;
import prova.prova.enums.RoleEnum;
import prova.prova.utils.constraint.RoleCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Camila Siqueira
 */
public class RoleCheckValidator implements ConstraintValidator<RoleCheck, String> {

    @Override
    public void initialize(RoleCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null) {
            if (ObjectUtils.containsConstant(RoleEnum.values(), value)) {
                return true;
            }
        }

        return false;
    }
}
