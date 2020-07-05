package com.demo.fileupload.document.validattor;

import com.demo.fileupload.common.validator.ValidationResult;
import com.demo.fileupload.document.domain.Document;
import com.demo.fileupload.document.domain.DocumentStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StateChangeValidator extends DocumentValidator {

    @Override
    public ValidationResult validate(Document instance, Map params) {
        DocumentStatus persistentStatus = (DocumentStatus) params.get("persistentStatus");
        return instance.getStatus().validateTransition(persistentStatus) ? ValidationResult.ok()
                : ValidationResult.fail("Transition Not allowed from " + persistentStatus.name()
                + " to " + instance.getStatus().name());
    }
}
