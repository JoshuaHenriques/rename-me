package com.bootstrap.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Person already exists exception.
 */
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonAlreadyExistsException extends Exception {

    private List<String> errorMessages = new ArrayList<>();

    /**
     * Instantiates a new Person already exists exception.
     *
     * @param msg the msg
     */
    public PersonAlreadyExistsException(String msg) {
        super(msg);
    }

    /**
     * Gets error messages.
     *
     * @return the error messages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Sets error messages.
     *
     * @param errorMessages the error messages
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * Add error message.
     *
     * @param msg the msg
     */
    public void addErrorMessage(String msg) {
        this.errorMessages.add(msg);
    }
}
