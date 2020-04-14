package org.easylibs.streamer.builder;

public class StreamerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8043894601035890107L;

	public StreamerException() {
	}

	public StreamerException(String arg0) {
		super(arg0);
	}

	public StreamerException(Throwable arg0) {
		super(arg0);
	}

	public StreamerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public StreamerException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
