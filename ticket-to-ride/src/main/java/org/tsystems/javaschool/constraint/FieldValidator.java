package org.tsystems.javaschool.constraint;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class FieldValidator implements ConstraintValidator<ValidateFields, Object> {

   private String firstFieldName;
   private String secondFieldName;
   private boolean equality;
   private String message;

   @Override
   public void initialize(final ValidateFields constraintAnnotation) {

      firstFieldName = constraintAnnotation.first();
      secondFieldName = constraintAnnotation.second();
      equality = constraintAnnotation.equality();
      message = constraintAnnotation.message();

   }

   @Override
   public boolean isValid(final Object obj, final ConstraintValidatorContext constraintValidatorContext) {
      boolean valid = true;
      try {
         final Object firstObj = BeanUtils.getProperty(obj, firstFieldName);
         final Object secondObj = BeanUtils.getProperty(obj, secondFieldName);
         if (equality) {
            valid = ((firstObj == null) && (secondObj == null)) || ((firstObj != null) && firstObj.equals(secondObj));
         }
         else {
            valid = ((firstObj == null) && (secondObj == null)) || ((firstObj != null) && !firstObj.equals(secondObj));
         }
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
         e.printStackTrace();
      }
      if (!valid) {
         constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                 .addPropertyNode(firstFieldName)
                 .addConstraintViolation()
                 .disableDefaultConstraintViolation();
      }
      return valid;
   }

}
