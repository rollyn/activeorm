package com.ethanium.activeorm.mapping;

import com.ethanium.activeorm.annotations.Reference;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.mongodb.core.mapping.*;

/** Persistent property that treats @Reference as a DBRef. */
public class ActiveOrmMongoPersistentProperty extends BasicMongoPersistentProperty {

  public ActiveOrmMongoPersistentProperty(
      Property property,
      MongoPersistentEntity<?> owner,
      SimpleTypeHolder simpleTypeHolder,
      FieldNamingStrategy namingStrategy) {
    super(property, owner, simpleTypeHolder, namingStrategy);
  }

  @Override
  public boolean isDbReference() {
    return isAnnotationPresent(Reference.class) || super.isDbReference();
  }

  @Override
  public boolean isAssociation() {
    return isAnnotationPresent(Reference.class) || super.isAssociation();
  }
}
