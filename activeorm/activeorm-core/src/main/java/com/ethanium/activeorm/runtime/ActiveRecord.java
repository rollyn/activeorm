package com.ethanium.activeorm.runtime;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.List;
public abstract class ActiveRecord<T extends ActiveRecord<T>> {
    private static MongoTemplate template;
    public static void init(MongoTemplate mongo) { template = mongo; }
    public static <D> D find(Class<D> c, Object id){ return template.findById(id,c);}
    public static <D> List<D> findAll(Class<D> c){ return template.findAll(c);}
    public static <D> QueryBuilder<D> where(Class<D> c){ return new QueryBuilder<>(c,template);}
    @SuppressWarnings("unchecked") public T save(){ template.save(this); return (T)this;}
    public void delete(){ template.remove(this); }
}
