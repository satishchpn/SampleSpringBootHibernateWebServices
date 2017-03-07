package com.demo.dao.converter;

public interface ToEntityConverter<T, E> {
	T getAsTo(E entity);

	E getAsEntity(T to);
}
