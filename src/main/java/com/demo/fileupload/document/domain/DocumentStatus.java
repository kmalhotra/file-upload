package com.demo.fileupload.document.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum DocumentStatus {
    // Denotes a state where the document upload is requested but not completed yet.
    PENDING,
    // Denotes a state where the document upload is complete.
    UPLOADED,
    // Denotes a state where the document has been processed and sanitized.
    PROCESSED;

    static {
        PENDING.states = Collections.unmodifiableSet(new HashSet<DocumentStatus>());
        UPLOADED.states = Collections.unmodifiableSet(new HashSet<DocumentStatus>() {{
            add(PENDING);
        }});
        PROCESSED.states = Collections.unmodifiableSet(new HashSet<DocumentStatus>() {{
            add(UPLOADED);
        }});
    }

    // states is a list of previous statuses from which 'this' status can be achieved
    Set<DocumentStatus> states;

    public boolean validateTransition(DocumentStatus from) {
        return this.states.contains(from);
    }
}
