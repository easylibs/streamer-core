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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.easylibs.streamer.builder.StreamerProperty.StringProperty;

/**
 * 
 * @author Mark Bednarczyk [repo@slytechs.com]
 */
public abstract class StreamerBuilder<KEY extends Enum<KEY>, T> {

	public StringProperty createProperty(KEY key) {
		final StringProperty property = new StringProperty(key.name());

		map.put(key, property);

		return property;
	}

	private final Map<Enum<?>, StringProperty> map;

	public StreamerBuilder() {
		this.map = Collections.synchronizedMap(new HashMap<>());
	}

	public StreamerBuilder(StreamerBuilder<KEY, T> link) {
		this.map = link.map;
	}

	public Optional<StringProperty> get(KEY key) {
		return Optional.ofNullable(map.get(key));
	}

	public StringProperty orElseCreate(KEY key) {
		final StringProperty property = map.get(key);

		return property == null ? createProperty(key) : property;
	}

	public void add(KEY key, String value) {
		orElseCreate(key).add(value);
	}

	public void add(KEY key, String value, String... values) {
		orElseCreate(key).add(value, values);
	}

	public abstract T build();

	public int size() {
		return map.size();
	}

	public int size(KEY key) {
		return get(key)
				.map(StreamerProperty::size)
				.orElse(0);
	}

	@SuppressWarnings("unchecked")
	public Collection<String> collectAny(KEY... args) {

		if (args.length == 0) {
			return Collections.emptyList();
		}

		return Stream.of(args)
				.filter(map::containsKey)
				.map(map::get)
				.map(StringProperty::get)
				.collect(Collectors.toList());
	}

	public boolean contains(KEY key) {
		return map.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public StreamerBuilderSegment<KEY> required(KEY key, KEY... other) {
		final StreamerBuilderSegment<KEY> bp = get(key)
				.map(p -> new StreamerBuilderSegment<>(key, p, this))
				.orElseThrow(() -> new MissingRequiredException("SQL keyword " + key.name()));

		bp.other(other);

		return bp;
	}

	@SuppressWarnings("unchecked")
	public StreamerBuilderSegment<KEY> optional(KEY key, KEY... other) {
		StreamerBuilderSegment<KEY> bp = get(key)
				.map(p -> new StreamerBuilderSegment<>(key, p, this))
				.orElseGet(StreamerBuilderSegment::empty);

		if (!bp.isEmpty()) {
			bp.other(other);
		}

		return bp;
	}

	/**
	 * @param from
	 * @param tables
	 */
	public void set(KEY key, String[] tables) {
		orElseCreate(key).set(tables);
	}

	public void set(KEY key, String value) {
		orElseCreate(key).set(value);
	}

	public void set(KEY key, String arg, String... args) {
		orElseCreate(key).set(arg, args);
	}

}
