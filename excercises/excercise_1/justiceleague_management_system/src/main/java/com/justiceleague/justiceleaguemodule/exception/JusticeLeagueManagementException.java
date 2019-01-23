package com.justiceleague.justiceleaguemodule.exception;

/**
 * This class define the common exception class that will be used within the
 * system.
 * 
 * @author dinuka
 *
 */
public class JusticeLeagueManagementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4269316494280751012L;

	public JusticeLeagueManagementException(final String message) {
		super(message);
	}

	public JusticeLeagueManagementException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
