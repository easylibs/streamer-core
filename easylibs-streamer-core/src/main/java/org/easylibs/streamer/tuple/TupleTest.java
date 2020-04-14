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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TupleTest {

	@Test
	void tuple_assertThrows() {
		TupleVarargs tuple = new TupleVarargs();

		System.out.println(tuple);

		assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(1));
	}

	@Test
	void tuple2_assertEquals() {
		Tuple2<?, ?> tuple = new Tuple2<>("Hello", 10);

		System.out.println(tuple);

		assertEquals("Hello", tuple.get(0, String.class));
		assertEquals(10, tuple.get(1, int.class));
	}

	@Test
	void tuple_assertEquals() {
		TupleVarargs tuple = new TupleVarargs("Hello", 10);

		System.out.println(tuple);

		assertEquals("Hello", tuple.get(0, String.class));
		assertEquals(10, tuple.get(1, int.class));
	}

}
