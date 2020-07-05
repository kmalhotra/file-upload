package com.demo.fileupload.common.validator;

public class ValidationResult {
    private boolean valid;
    private String messsage;

    public static ValidationResult ok(){
        return new ValidationResult(true, null);
    }

    public static ValidationResult fail(String message){
        return new ValidationResult(false, message);
    }

    private ValidationResult(boolean valid, String messsage) {
        this.valid = valid;
        this.messsage = messsage;
    }

    public boolean isValid() {
        return valid;
    }

    public void throwIfInvalid() {
        if(!isValid()) throw new ValidationException(getMesssage());
    }

    public void throwIfInvalid(String fieldName) {
        if(!isValid()) throw new ValidationException(fieldName + " : " + getMesssage());
    }

    public String getMesssage() {
        return messsage;
    }
}
