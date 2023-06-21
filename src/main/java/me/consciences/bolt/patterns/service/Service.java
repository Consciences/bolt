package me.consciences.bolt.patterns.service;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface Service<E> {

    List<E> getService();

    default void register(final E entity) {
        this.getService().add(entity);
    }

    @NotNull
    default Optional<E> get(final int index) {
        return Optional.ofNullable(this.getService().get(index));
    }

    default void unregister(final E entity) {
        this.getService().remove(entity);
    }

    default void evict(final E entity) {
        this.getService().remove(this.getService().size());
        this.getService().add(entity);
    }

    default int getSize() {
        return this.getService().size();
    }

    default boolean contains(final E entity) {
        return this.getService().contains(entity);
    }
}
