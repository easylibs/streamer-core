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

import java.util.Optional;
import java.util.stream.Stream;

public interface Tuple {

	int degree();

	Object get(int index);

	<T> T get(int index, Class<T> type);

	public Optional<String> getLabel(int index);

	Optional<Object> getOptional(int index);

	<T> Optional<T> getOptional(int index, Class<T> type);

	default <T> TupleGetter<T> getter(int index, Class<T> type) {
		return Tuples.getter(this, index, type);
	}

	<T> void set(int index, T v);

	public Tuple setLabel(int index, String label);

	default <T> TupleSetter<T> setter(int index, Class<T> type) {
		return Tuples.setter(this, index, type);
	}

	Stream<?> stream();

	<T> Stream<T> streamOf(Class<T> type);
}