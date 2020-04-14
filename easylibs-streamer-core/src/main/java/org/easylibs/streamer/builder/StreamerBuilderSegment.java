/*
 * Copyright (c) 2020 Sly Technologies Inc.
 */
package org.easylibs.streamer.builder;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.easylibs.streamer.builder.StreamerProperty.StringProperty;

public class StreamerBuilderSegment<E extends Enum<E>> {

	public static <E extends Enum<E>> StreamerBuilderSegment<E> empty() {
		return new StreamerBuilderSegment<>();
	}

	private final StringProperty property;
	private Collection<E> other = Collections.emptyList();
	private final E key;
	private final StreamerBuilder<E, ?> builder;

	private StreamerBuilderSegment() {
		this.property = null;
		this.key = null;
		this.builder = null;
	}

	public boolean isEmpty() {
		return (property == null);
	}

	public StreamerBuilderSegment(E key, StringProperty property, StreamerBuilder<E, ?> builder) {

		this.key = Objects.requireNonNull(key, "key");
		this.builder = Objects.requireNonNull(builder, "builder");
		this.property = Objects.requireNonNull(property, "property");

	}

	public Stream<String> stream() {
		return isEmpty() ? Stream.empty() : property.stream();
	}

	public void forEach(Consumer<String> action) {
		if (!isEmpty()) {
			stream().forEach(action);
		}
	}

	public StreamerBuilderSegment<E> before(Runnable action) {
		if (!isEmpty()) {
			action.run();
		}

		return this;
	}

	public StreamerBuilderSegment<E> before(Consumer<String> action, String arg) {
		if (!isEmpty()) {
			action.accept(arg);
		}

		return this;
	}

	public StreamerBuilderSegment<E> before(BiConsumer<String, String> action, String arg1, String arg2) {
		if (!isEmpty()) {
			action.accept(arg1, arg2);
		}

		return this;
	}

	@SuppressWarnings("unchecked")
	public StreamerBuilderSegment<E> other(E... other) {
		this.other = Stream.of(other)
				.filter(builder::contains)
				.collect(Collectors.toList());

		return this;
	}

	public StreamerBuilderSegment<E> header(BiConsumer<E, Collection<E>> action) {
		if (!isEmpty()) {
			action.accept(key, other);
		}

		return this;
	}
}