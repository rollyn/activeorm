package com.ethanium.activeorm.mapping;

import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.mongodb.core.mapping.*;

public class ActiveOrmMongoMappingContext extends MongoMappingContext {

  @Override
  public MongoPersistentProperty createPersistentProperty(
      Property property, MongoPersistentEntity<?> owner, SimpleTypeHolder simpleTypeHolder) {

    return new ActiveOrmMongoPersistentProperty(
        property, owner, simpleTypeHolder, PropertyNameFieldNamingStrategy.INSTANCE);
  }
}
