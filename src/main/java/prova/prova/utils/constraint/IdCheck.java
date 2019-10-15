package prova.prova.utils.constraint;

import prova.prova.utils.constraint.validators.IdCheckValidator;
import prova.prova.utils.constraint.validators.RoleCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Camila Siqueira
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCheckValidator.class)
@Documented
public @interface IdCheck {
    String message() default "Invalid user ID.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
