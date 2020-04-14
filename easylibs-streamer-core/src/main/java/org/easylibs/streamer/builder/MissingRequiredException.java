/*
 * Copyright (c) 2020 Sly Technologies Inc.
 */
package org.easylibs.streamer.builder;

/**
 * 
 * @author Mark Bednarczyk [repo@slytechs.com]
 */
public class MissingRequiredException extends StreamerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2241523062591582730L;

	/**
	 * 
	 */
	public MissingRequiredException() {
	}

	/**
	 * @param arg0
	 */
	public MissingRequiredException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public MissingRequiredException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MissingRequiredException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public MissingRequiredException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
