package uk.gov.hmcts.reform.fpl.validation.interfaces.time;

import uk.gov.hmcts.reform.fpl.validation.validators.time.HasEndDateAfterStartDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HasEndDateAfterStartDateValidator.class)
public @interface HasEndDateAfterStartDate {
    String message() default "The start date cannot be after the end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
