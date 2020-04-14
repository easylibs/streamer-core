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
package org.easylibs.streamer.tuple;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractTuple implements Serializable, Tuple {

	private static final long serialVersionUID = -3947048635987966734L;
	private final int degree;
	private String[] labels; // lazy allocated when needed

	protected AbstractTuple(int degree) {
		this.degree = degree;
	}

	protected AbstractTuple(int degree, String[] labels) {
		this.degree = degree;
		this.labels = labels;
	}

	protected int checkBounds(int index) {
		if (index < 0 || index >= degree()) {
			throw new IndexOutOfBoundsException("Tuple index out of bounds " + index + " with degree " + degree());
		}

		return index;
	}

	@Override
	public int degree() {
		return degree;
	}

	@Override
	public abstract Object get(int index);

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(int index, Class<T> type) {
		return (T) get(index);
	}

	protected Object getIndexed(int index, Object u) {
		if (index < 0 || index >= degree()) {
			throw new IndexOutOfBoundsException("Tuple index out of bounds " + index + " with degree " + degree());
		}

		return u;
	}

	/**
	 * @see org.easylibs.streamer.tuple.Tuple#getLabel(int)
	 */
	public Optional<String> getLabel(int index) {
		checkBounds(index);

		return (labels == null)
				? Optional.empty()
				: Optional.ofNullable(labels[index]);
	}

	@Override
	public Optional<Object> getOptional(int index) {
		return Optional.ofNullable(get(index));
	}

	@Override
	public <T> Optional<T> getOptional(int index, Class<T> type) {
		return Optional.ofNullable(get(index, type));
	}

	@Override
	public abstract <T> void set(int index, T v);

	@Override
	public Tuple setLabel(int index, String label) {
		checkBounds(index);

		/* Lazy allocate labels storage */
		if (labels == null) {
			labels = new String[degree()];
		}

		labels[index] = label;

		return this;
	}

	@Override
	public Stream<?> stream() {
		return IntStream.range(0, degree())
				.mapToObj(this::get);
	}

	@Override
	public <T> Stream<T> streamOf(Class<T> type) {
		return stream()
				.filter(type::isInstance)
				.map(type::cast);
	}

	private String formatValueLabelCombo(int index) {
		final String label = (labels == null) ? null : labels[index];
		final Object value = get(index);
		final String quotes = (value instanceof String) ? "\"" : "";

		return (label == null)
				? quotes + String.valueOf(value) + quotes
				: label + "=" + quotes + String.valueOf(value) + quotes;
	}

	@Override
	public String toString() {
		final String name = getClass().getSimpleName();

		return IntStream.range(0, degree())
				.mapToObj(this::formatValueLabelCombo)
				.collect(Collectors.joining(", ", name + " [", "]"));
	}

}
