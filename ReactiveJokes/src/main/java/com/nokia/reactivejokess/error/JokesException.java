package com.nokia.reactivejokess.error;

public class JokesException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JokesException(String message) {
		
		super(message);
	}
	
	public class DatabaseDownException extends JokesException {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DatabaseDownException(String message) {
	        super(message);
	    }
	    
	}

}
