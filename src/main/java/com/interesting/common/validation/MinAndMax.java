package com.interesting.common.validation;




import com.interesting.common.validation.validator.MinAndMaxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以为空 但是必须在值范围内
 *
 *  最大值最小值都是包括
 */
@Target({ElementType.METHOD,ElementType.FIELD}) // 表示这个注解可以作用在什么地方，例如作用在方法上，或作用在某个字段上。
@Retention(RetentionPolicy.RUNTIME) //被它所注解的注解保留多久，runtime表示不仅被保存到class文件中，jvm加载class文件之后，仍然存在
@Constraint(validatedBy = MinAndMaxValidator.class) //表示我们判断逻辑的具体实现类是什么。
public @interface MinAndMax {
    String message() default "Illegal value";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int max();

    int min();
}
