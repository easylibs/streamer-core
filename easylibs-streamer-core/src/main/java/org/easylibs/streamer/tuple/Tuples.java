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

import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class Tuples {

	public static TupleX copyFrom(final Object obj) {
		final Class<?> cl = obj.getClass();
		final int MODIFIERS = (Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT);

		final Object[] values = Stream.of(cl.getDeclaredFields())
				.filter(f -> (f.getModifiers() & MODIFIERS) == 0)
				.map(f -> {
					try {
						final boolean b = f.isAccessible();
						if (!b) {
							f.setAccessible(true);
						}

						final Object value = f.get(obj);
						f.setAccessible(b);

						return value;
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				})
				.toArray();

		return new TupleX(values);
	}

	public static TupleX fromClass(final Class<?> cl) {
		final int MODIFIERS = (Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT);

		final int degree = (int) Stream.of(cl.getDeclaredFields())
				.filter(f -> (f.getModifiers() & MODIFIERS) == 0)
				.count();

		return new TupleX(degree);
	}

	public static <TUPLE extends Tuple, T> TupleGetter<T> getter(final TUPLE tuple, int index, Class<T> type) {
		Objects.requireNonNull(tuple, "tuple");
		Objects.requireNonNull(type, "type");

		if (index < 0 || index >= tuple.degree()) {
			throw new IndexOutOfBoundsException(
					"Tuple index out of bounds " + index + " with degree " + tuple.degree());
		}

		return new TupleGetter<T>() {

			@Override
			public int degree() {
				return tuple.degree();
			}

			@Override
			public T get() {
				return tuple.get(index, type);
			}

			@Override
			public int index() {
				return index;
			}

			@Override
			public Class<T> type() {
				return type;
			}
		};
	}

	public static <T0> TupleX of(T0 v0, Object... args) {
		if (args.length == 0) {
			return new TupleX(v0);
		}

		final Object[] array = new Object[args.length + 1];
		array[0] = v0;

		for (int i = 1; i < array.length; i++) {
			array[i] = args[i - 1];
		}

		return new TupleX(array);
	}

	public static <T0> Tuple1<T0> of(T0 v0) {
		return new Tuple1<>(v0);
	}

	public static <T0, T1> Tuple2<T0, T1> of(T0 v0, T1 v1) {
		return new Tuple2<>(v0, v1);
	}

	public static <T0, T1, T2> Tuple3<T0, T1, T2> of(T0 v0, T1 v1, T2 v2) {
		return new Tuple3<>(v0, v1, v2);
	}

	public static <T0, T1, T2, T3> Tuple4<T0, T1, T2, T3> of(T0 v0, T1 v1, T2 v2, T3 v3) {
		return new Tuple4<>(v0, v1, v2, v3);
	}

	public static <T0, T1, T2, T3, T4> Tuple5<T0, T1, T2, T3, T4> of(T0 v0, T1 v1, T2 v2, T3 v3, T4 v4) {
		return new Tuple5<>(v0, v1, v2, v3, v4);
	}

	public static <T0, T1, T2, T3, T4, T5> Tuple6<T0, T1, T2, T3, T4, T5> of(T0 v0, T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
		return new Tuple6<>(v0, v1, v2, v3, v4, v5);
	}

	public static <T0, T1, T2, T3, T4, T5, T6> Tuple7<T0, T1, T2, T3, T4, T5, T6> of(
			T0 v0,
			T1 v1,
			T2 v2,
			T3 v3,
			T4 v4,
			T5 v5,
			T6 v6) {
		return new Tuple7<>(v0, v1, v2, v3, v4, v5, v6);
	}

	public static <T0, T1, T2, T3, T4, T5, T6, T7> Tuple8<T0, T1, T2, T3, T4, T5, T6, T7> of(
			T0 v0,
			T1 v1,
			T2 v2,
			T3 v3,
			T4 v4,
			T5 v5,
			T6 v6,
			T7 v7) {
		return new Tuple8<>(v0, v1, v2, v3, v4, v5, v6, v7);
	}

	public static <T0, T1, T2, T3, T4, T5, T6, T7, T8> Tuple9<T0, T1, T2, T3, T4, T5, T6, T7, T8> of(
			T0 v0,
			T1 v1,
			T2 v2,
			T3 v3,
			T4 v4,
			T5 v5,
			T6 v6,
			T7 v7,
			T8 v8) {
		return new Tuple9<>(v0, v1, v2, v3, v4, v5, v6, v7, v8);
	}

	public static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> of(
			T0 v0,
			T1 v1,
			T2 v2,
			T3 v3,
			T4 v4,
			T5 v5,
			T6 v6,
			T7 v7,
			T8 v8,
			T9 v9) {
		return new Tuple10<>(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9);
	}

	public static <TUPLE extends Tuple, T> TupleSetter<T> setter(final TUPLE tuple, int index, Class<T> type) {
		Objects.requireNonNull(tuple, "tuple");
		Objects.requireNonNull(type, "type");

		if (index < 0 || index >= tuple.degree()) {
			throw new IndexOutOfBoundsException(
					"Tuple index out of bounds " + index + " with degree " + tuple.degree());
		}

		return new TupleSetter<T>() {

			@Override
			public int degree() {
				return tuple.degree();
			}

			@Override
			public int index() {
				return index;
			}

			@Override
			public void set(T value) {
				tuple.set(index, value);
			}

			@Override
			public Class<T> type() {
				return type;
			}
		};
	}

	private Tuples() {
	}

}
