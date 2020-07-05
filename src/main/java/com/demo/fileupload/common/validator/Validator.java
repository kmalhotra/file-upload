package com.demo.fileupload.common.validator;

import java.util.Map;

@FunctionalInterface
public interface Validator<T> {

    ValidationResult validate(T instance, Map<String, Object> params);
}
