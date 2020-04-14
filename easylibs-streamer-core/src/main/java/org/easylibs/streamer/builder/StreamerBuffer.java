/*
 * MIT License
 * 
 * Copyright (c) 2020 Sly Technologies Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.easylibs.streamer.builder;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

/**
 * The Class StreamerBuffer.
 *
 * @author Mark Bednarczyk [repo@slytechs.com]
 */
public class StreamerBuffer {

	/**
	 * The Class Context.
	 */
	private static class Context {

		/** The out. */
		protected final Appendable out;

		/** The formatting enabled. */
		private boolean formatted = true;

		/** The lineno. */
		private int lineno;

		/** The charno. */
		private int charno;

		/** The linewrap. */
		private int linewrap = 80;

		/** The delimiter flag. */
		private boolean delimiterFlag;

		/**
		 * Instantiates a new context.
		 *
		 * @param out the out
		 */
		public Context(StringBuilder out) {
			super();
			this.out = out;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "[lineno=" + lineno + ", charno=" + charno + ", out=" + out + "]";
		}

	}

	/** The context. */
	private final Context context;

	/** The indent. */
	private int indent = 4;

	/** The padno. */
	private int padno = 0;

	/** The delimiter. */
	private String delimiter = "";

	/** The key mapper. */
	private Function<Enum<?>, String> keyMapper;

	/**
	 * Instantiates a new streamer buffer.
	 */
	public StreamerBuffer() {
		this.context = new Context(new StringBuilder());
		this.keyMapper = Enum::name;
	}

	/**
	 * Instantiates a new streamer buffer.
	 *
	 * @param keyMapper the key mapper
	 */
	public StreamerBuffer(Function<Enum<?>, String> keyMapper) {
		this.context = new Context(new StringBuilder());
		this.keyMapper = keyMapper;
	}

	/**
	 * Instantiates a new streamer buffer.
	 *
	 * @param link the link
	 */
	public StreamerBuffer(StreamerBuffer link) {

		this.context = link.context;
		this.padno = link.padno;
		this.indent = link.indent;
		this.keyMapper = link.keyMapper;
		this.delimiter = link.delimiter;

	}

	/**
	 * Append.
	 *
	 * @param ch the ch
	 * @return the streamer buffer
	 */
	private StreamerBuffer append(char ch) {

		if (context.formatted) {
			wrapIf();
			padIf();
		}

		append0(ch);

		return this;
	}

	/**
	 * Append.
	 *
	 * @param str the str
	 * @return the streamer buffer
	 */
	private StreamerBuffer append(String str) {

		if (context.formatted) {
			wrapIf();
			padIf();
		}

		append0(str);

		return this;
	}

	/**
	 * Append.
	 *
	 * @param str   the str
	 * @param start the start
	 * @param end   the end
	 * @return the streamer buffer
	 */
	@SuppressWarnings("unused")
	private StreamerBuffer append(String str, int start, int end) {

		if (context.formatted) {
			wrapIf();
			padIf();
		}

		append0(str, start, end);

		return this;
	}

	/**
	 * Append 0.
	 *
	 * @param ch the ch
	 * @return the streamer buffer
	 */
	private StreamerBuffer append0(char ch) {
		try {
			context.out.append(ch);
			context.charno++;
		} catch (IOException e) {
			throw new StreamerException(e);
		}

		return this;
	}

	/**
	 * Append 0.
	 *
	 * @param str the str
	 * @return the streamer buffer
	 */
	private StreamerBuffer append0(String str) {
		try {
			context.out.append(str);
			context.charno += str.length();
		} catch (IOException e) {
			throw new StreamerException(e);
		}

		return this;
	}

	/**
	 * Append 0.
	 *
	 * @param str   the str
	 * @param start the start
	 * @param end   the end
	 * @return the streamer buffer
	 */
	private StreamerBuffer append0(String str, int start, int end) {
		try {
			context.out.append(str, start, end);
			context.charno += end - start;
		} catch (IOException e) {
			throw new StreamerException(e);
		}

		return this;
	}

	/**
	 * As string.
	 *
	 * @return the string
	 */
	public String asString() {
		return context.out.toString();
	}

	/**
	 * Charno.
	 *
	 * @return the int
	 */
	public int charno() {
		return context.charno;
	}

	/**
	 * Delimiter.
	 *
	 * @param delimiter the delimiter
	 * @return the streamer buffer
	 */
	public StreamerBuffer delimiter(String delimiter) {
		try {
			context.out.append(delimiter);
		} catch (IOException e) {
			throw new StreamerException(e);
		}

		context.charno += delimiter.length();

		return this;
	}

	/**
	 * Delimiter if.
	 *
	 * @param delimiter the delimiter
	 * @return the streamer buffer
	 */
	public StreamerBuffer delimiterIf(String delimiter) {
		return context.delimiterFlag ? delimiter(delimiter) : this;
	}

	/**
	 * Gets the delimiter.
	 *
	 * @return the delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * Checks if is formatting enabled.
	 *
	 * @return true, if is formatting enabled
	 */
	public boolean isFormattingEnabled() {
		return context.formatted;
	}

	/**
	 * Lineno.
	 *
	 * @return the int
	 */
	public int lineno() {
		return context.lineno;
	}

	/**
	 * Ln.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer ln() {
		if (!context.formatted) {
			space();
			return this;
		}

		try {
			context.out.append("\n");
		} catch (IOException e) {
			throw new StreamerException(e);
		}

		context.lineno++;
		context.charno = 0;

		return this;
	}

	/**
	 * Ln if.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer lnIf() {
		return (context.charno > 0) ? ln() : this;
	}

	/**
	 * New indented buffer.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer newIndentedBuffer() {
		final StreamerBuffer body = new StreamerBuffer(this);
		return body.lnIf().padno(indent);
	}

	/**
	 * New section.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer newSection() {
		final StreamerBuffer section = new StreamerBuffer(this);
		return section.lnIf();
	}

	/**
	 * Create a new section and println str.
	 *
	 * @param str the str
	 * @return the streamer buffer
	 */
	public StreamerBuffer newSection(String str) {
		return newSection().println(str);
	}

	/**
	 * Pad.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer pad() {
		return context.formatted ? repeat(padno, ' ') : this;
	}

	/**
	 * Pad if.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer padIf() {
		return (context.charno == 0) ? pad() : this;
	}

	/**
	 * Padno.
	 *
	 * @param count the count
	 * @return the streamer buffer
	 */
	public StreamerBuffer padno(int count) {
		this.padno += count;

		return this;
	}

	/**
	 * Print with no-delimiter and no-newline.
	 *
	 * @param <E>     the element type
	 * @param section the str
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer print(E section) {
		return print(keyMapper.apply(section));
	}

	/**
	 * Print with no-delimiter and no-newline.
	 *
	 * @param str the str
	 * @return the streamer buffer
	 */
	public StreamerBuffer print(String str) {
		context.delimiterFlag = true;

		return append(str);
	}

	/**
	 * Print with a delimiter and no-newline.
	 *
	 * @param str the str
	 * @return the streamer buffer
	 */
	public StreamerBuffer printd(String str) {
		return delimiterIf(delimiter)
				.print(str);
	}

	/**
	 * Print with the supplied delimiter and no-newline.
	 *
	 * @param str       the str
	 * @param delimiter the delimiter
	 * @return the streamer buffer
	 */
	public StreamerBuffer printd(String str, String delimiter) {
		return delimiterIf(delimiter).print(str);
	}

	/**
	 * Print with a delimiter and a newline at the end.
	 *
	 * @param str the str
	 * @return the streamer buffer
	 */
	public StreamerBuffer printdln(String str) {
		return delimiterIf(delimiter).println(str);
	}

	/**
	 * Print with new line and no-delimiter.
	 *
	 * @param <E>     the element type
	 * @param section the section
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer println(E section) {
		return println(keyMapper.apply(section));
	}

	/**
	 * Print with new line and no-delimiter.
	 *
	 * @param str the str
	 * @return the streamer buffer
	 */
	public StreamerBuffer println(String str) {
		return append(str).lnIf();
	}

	/**
	 * Repeat character {@code count} times.
	 *
	 * @param count the count
	 * @param ch    the ch
	 * @return the streamer buffer
	 */
	private StreamerBuffer repeat(int count, char ch) {
		for (int i = 0; i < count; i++) {
			append0(ch);
		}

		return this;
	}

	/**
	 * Reset delimiter to default.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer resetDelimiter() {
		setDelimiter("");

		return this;
	}

	/**
	 * Print (no-newline) a new section.
	 *
	 * @param <E>     the element type
	 * @param section the section
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer section(E section) {
		return resetDelimiter()
				.lnIf()
				.print(keyMapper.apply(section))
				.space();
	}

	/**
	 * Print (no-newline) a new section and append space separated args.
	 *
	 * @param <E>     the element type
	 * @param section the section
	 * @param args    the args
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer section(E section, Collection<E> args) {
		resetDelimiter().lnIf().print(keyMapper.apply(section));

		args.stream()
				.map(keyMapper)
				.peek(v -> space())
				.forEach(this::print);

		return space();
	}

	/**
	 * Print (no-newline) a new section and set a delimiter.
	 *
	 * @param <E>       the element type
	 * @param section   the section
	 * @param delimiter the delimiter
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer section(E section, String delimiter) {
		return lnIf()
				.print(keyMapper.apply(section))
				.setDelimiter(delimiter)
				.space();
	}

	/**
	 * Println (newline) a new section
	 *
	 * @param <E>     the element type
	 * @param section the section
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer sectionln(E section) {
		return resetDelimiter()
				.lnIf()
				.print(keyMapper.apply(section))
				.ln();
	}

	/**
	 * Println (newline) a new section and append space separated args before
	 * newline.
	 *
	 * @param <E>     the element type
	 * @param section the section
	 * @param args    the args
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer sectionln(E section, Collection<E> args) {
		resetDelimiter()
				.lnIf()
				.print(keyMapper.apply(section));

		args.stream()
				.map(keyMapper)
				.peek(v -> space())
				.forEach(this::print);

		return ln().resetDelimiter();
	}

	/**
	 * Println (newline) a new section and set a delimiter.
	 *
	 * @param <E>       the element type
	 * @param section   the section
	 * @param delimiter the delimiter
	 * @return the streamer buffer
	 */
	public <E extends Enum<?>> StreamerBuffer sectionln(E section, String delimiter) {
		return setDelimiter(delimiter)
				.lnIf()
				.println(keyMapper.apply(section));
	}

	/**
	 * Sets the delimiter.
	 *
	 * @param delimiter the delimiter
	 * @return the streamer buffer
	 */
	public StreamerBuffer setDelimiter(String delimiter) {
		this.delimiter = delimiter;
		context.delimiterFlag = false;

		return this;
	}

	/**
	 * Sets the formatting enabled.
	 *
	 * @param formattingEnabled the new formatting enabled
	 */
	public void enableFormatter(boolean formattingEnabled) {
		context.formatted = formattingEnabled;
	}

	/**
	 * Space.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer space() {
		return append(' ');
	}

	/**
	 * Space if.
	 *
	 * @return the streamer buffer
	 */
	public StreamerBuffer spaceIf() {
		return (context.charno > padno) ? space() : this;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StreamerBuffer [padno=" + padno + ", " + context + "]";
	}

	/**
	 * Wrap.
	 *
	 * @return the int
	 */
	public int wrap() {
		return context.linewrap;
	}

	/**
	 * Wrap.
	 *
	 * @param linewrap the linewrap
	 */
	public void wrap(int linewrap) {
		context.linewrap = linewrap;
	}

	/**
	 * Wrap if.
	 *
	 * @return the streamer buffer
	 */
	private StreamerBuffer wrapIf() {
		return ((context.linewrap != 0) && (context.charno >= context.linewrap))
				? ln()
				: this;
	}

	/**
	 * Gets the key mapper.
	 *
	 * @return the key mapper
	 */
	public Function<Enum<?>, String> getKeyMapper() {
		return keyMapper;
	}

	/**
	 * Sets the key mapper.
	 *
	 * @param keywordToName the keyword to name
	 */
	public void setKeywordParser(Function<Enum<?>, String> keywordToName) {
		this.keyMapper = keywordToName;
	}

}
