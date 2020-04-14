/*
 * Copyright (c) 2020 Sly Technologies Inc.
 */
package org.easylibs.streamer.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Mark Bednarczyk [repo@slytechs.com]
 */
public class StreamerProperty<T> {

	public static class StringProperty extends StreamerProperty<String> {

		public StringProperty(String name) {
			super(name);
		}

		public String join(String delimiter) {
			return list.stream().collect(Collectors.joining(delimiter));
		}

		public String join(String delimiter, String prefix, String suffix) {
			return list.stream().collect(Collectors.joining(delimiter, prefix, suffix));
		}

	}

	protected final String name;
	protected final List<T> list;

	public StreamerProperty(String name) {
		Objects.requireNonNull(name, "name");

		this.name = name;
		this.list = Collections.synchronizedList(new ArrayList<>());
	}

	public void add(T value) {
		this.list.add(value);
	}

	@SuppressWarnings("unchecked")
	public void add(T value, T... values) {
		this.list.add(value);
		this.list.addAll(Arrays.asList(values));
	}

	public void addAll(Collection<T> values) {
		this.list.addAll(values);
	}

	/**
	 * @param values2
	 */
	@SuppressWarnings("unchecked")
	public void addAll(T... values) {
		this.list.addAll(Arrays.asList(values));

	}

	public void forEach(Consumer<T> action) {
		stream().forEach(action);
	}

	public T get() {
		return get(0);
	}

	public T get(int index) {
		return list.get(index);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public String name() {
		return name;
	}

	public void set(T value) {
		this.list.clear();
		this.list.add(value);
	}

	@SuppressWarnings("unchecked")
	public void set(T value, T... values) {
		this.list.clear();
		this.list.add(value);
		this.list.addAll(Arrays.asList(values));
	}

	/**
	 * @param tables
	 */
	public void set(T[] values) {
		this.list.clear();
		this.list.addAll(Arrays.asList(values));
	}

	public int size() {
		return list.size();
	}

	public Stream<T> stream() {
		return list.stream();
	}

	public String toString() {
		if (size() == 1) {
			return String.valueOf(get());
		}

		return stream()
				.map(String::valueOf)
				.collect(Collectors.joining(", "));
	}
}
