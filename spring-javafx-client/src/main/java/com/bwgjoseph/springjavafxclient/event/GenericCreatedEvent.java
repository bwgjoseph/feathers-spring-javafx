package com.bwgjoseph.springjavafxclient.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import lombok.Value;

// Use GenericType event where we group all the CreatedEvent (e.g User, Post, etc)
// This way we drastically reduce the number of (Created) events that has to be created
@Value
public class GenericCreatedEvent<T> implements ResolvableTypeProvider {
  private T entity;
  private Object source;
  
  public GenericCreatedEvent(Object source, T entity) {
    this.source = source;
    this.entity = entity;
  }

  // Spring lets you include type information (that is otherwise lost in runtime) by implementing ResolvableTypeProvider
  // Otherwise, due to Generic Type Erasure, otherwise, it wouldn't know the Entity type
  @Override
  public ResolvableType getResolvableType() {
    return ResolvableType.forClassWithGenerics(
      getClass(),
      ResolvableType.forClass(entity.getClass())
    );
  }
}