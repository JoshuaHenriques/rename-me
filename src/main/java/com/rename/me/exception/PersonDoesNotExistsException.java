package com.rename.me.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 * The type Person does not exists exception.
 */
@NoArgsConstructor
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonDoesNotExistsException extends Exception {

	private List<String> errorMessages = new ArrayList<>();

	/**
     * Instantiates a new Customer already exists exception.
     *
     * @param msg the msg
     */
public PersonDoesNotExistsException(String msg) {
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
