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

public class Tuple10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> extends AbstractTuple {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2722350547379096933L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> Class<Tuple10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>> cast(
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
		return (Class) Tuple10.class;
	}

	private T0 v0;
	private T1 v1;
	private T2 v2;
	private T3 v3;
	private T4 v4;
	private T5 v5;
	private T6 v6;
	private T7 v7;
	private T8 v8;
	private T9 v9;

	public Tuple10() {
		super(10);
	}

	public Tuple10(T0 v0, T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8, T9 v9) {
		super(10);
		this.v0 = v0;
		this.v1 = v1;
		this.v3 = v3;
		this.v4 = v4;
		this.v5 = v5;
		this.v6 = v6;
		this.v7 = v7;
		this.v8 = v8;
		this.v9 = v9;
	}

	@Override
	public Object get(int index) {
		checkBounds(index);

		switch (index) {
		case 0:
			return v0;
		case 1:
			return v1;
		case 2:
			return v2;
		case 3:
			return v3;
		case 4:
			return v4;
		case 5:
			return v5;
		case 6:
			return v6;
		case 7:
			return v7;
		case 8:
			return v8;
		case 9:
			return v9;
		}

		throw new IllegalStateException("invalid switch statement encountered while getting a tuple");
	}

	public T0 get0() {
		return v0;
	}

	public T1 get1() {
		return v1;
	}

	public T2 get2() {
		return v2;
	}

	public T3 get3() {
		return v3;
	}

	public T4 get4() {
		return v4;
	}

	public T5 get5() {
		return v5;
	}

	public T6 get6() {
		return v6;
	}

	public T7 get7() {
		return v7;
	}

	public T8 get8() {
		return v8;
	}

	public T9 get9() {
		return v9;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void set(int index, T v) {
		switch (checkBounds(index)) {
		case 0:
			v0 = (T0) v;
			break;
		case 1:
			v1 = (T1) v;
			break;
		case 2:
			v2 = (T2) v;
			break;
		case 3:
			v3 = (T3) v;
			break;
		case 4:
			v4 = (T4) v;
			break;
		case 5:
			v5 = (T5) v;
			break;
		case 6:
			v6 = (T6) v;
			break;
		case 7:
			v7 = (T7) v;
			break;
		case 8:
			v8 = (T8) v;
			break;
		case 9:
			v9 = (T9) v;
			break;
		}

		throw new IllegalStateException("invalid switch statement encountered while setting a tuple");
	}

	public void set0(T0 v0) {
		this.v0 = v0;
	}

	public void set1(T1 v1) {
		this.v1 = v1;
	}

	public void set2(T2 v2) {
		this.v2 = v2;
	}

	public void set3(T3 v3) {
		this.v3 = v3;
	}

	public void set4(T4 v4) {
		this.v4 = v4;
	}

	public void set5(T5 v5) {
		this.v5 = v5;
	}

	public void set6(T6 v6) {
		this.v6 = v6;
	}

	public void set7(T7 v7) {
		this.v7 = v7;
	}

	public void set8(T8 v8) {
		this.v8 = v8;
	}

	public void set9(T9 v9) {
		this.v9 = v9;
	}

}
