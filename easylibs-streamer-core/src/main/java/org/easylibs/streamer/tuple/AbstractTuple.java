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
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class AbstractTuple implements Serializable, Tuple {

	private static final long serialVersionUID = -3947048635987966734L;
	private final int degree;

	protected AbstractTuple(int degree) {
		this.degree = degree;
	}

	@Override
	public int degree() {
		return degree;
	}

	protected int checkBounds(int index) {
		if (index < 0 || index >= degree()) {
			throw new IndexOutOfBoundsException("Tuple index out of bounds " + index + " with degree " + degree());
		}

		return index;
	}

	protected Object getIndexed(int index, Object u) {
		if (index < 0 || index >= degree()) {
			throw new IndexOutOfBoundsException("Tuple index out of bounds " + index + " with degree " + degree());
		}

		return u;
	}

	@Override
	public abstract Object get(int index);

	@Override
	public Optional<Object> getOptional(int index) {
		return Optional.ofNullable(get(index));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(int index, Class<T> type) {
		return (T) get(index);
	}

	@Override
	public <T> Optional<T> getOptional(int index, Class<T> type) {
		return Optional.ofNullable(get(index, type));
	}

	@Override
	public abstract <T> void set(int index, T v);

	@Override
	public Stream<?> stream() {
		return StreamSupport.stream(new Spliterator<Object>() {

			@Override
			public int characteristics() {
				return 0;
			}

			@Override
			public long estimateSize() {
				return degree();
			}

			int i = 0;

			@Override
			public boolean tryAdvance(Consumer<? super Object> action) {
				
				if (i < degree()) {
					action.accept(get(i++));
				}

				return i < degree();
			}

			@Override
			public Spliterator<Object> trySplit() {
				return null;
			}

		}, false);
	}

	@Override
	public <T> Stream<T> streamOf(Class<T> type) {
		return stream()
				.filter(type::isInstance)
				.map(type::cast);
	}

	@Override
	public String toString() {
		final String name = getClass().getSimpleName();
		return name + " " + stream()
				.map(String::valueOf)
				.collect(Collectors.joining(", ", "[", "]"));
	}

}
