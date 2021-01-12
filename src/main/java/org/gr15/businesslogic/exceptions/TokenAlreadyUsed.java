/**
 * @author Wassim
 */

package org.gr15.businesslogic.exceptions;

public class TokenAlreadyUsed extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TokenAlreadyUsed(String message) {
        super(message);
    }
}
