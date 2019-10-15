package prova.prova.utils.constraint;

import org.springframework.util.ObjectUtils;
import prova.prova.enums.RoleEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Camila Siqueira
 */
public class RoleCheckValidator implements ConstraintValidator<RoleCheck, String> {
    private String value;

    @Override
    public void initialize(RoleCheck constraintAnnotation) {
        this.value = constraintAnnotation.value();
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
