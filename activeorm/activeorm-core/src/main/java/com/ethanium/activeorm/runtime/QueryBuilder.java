package com.ethanium.activeorm.runtime;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
public class QueryBuilder<T>{
  private final Class<T> cls; private final MongoTemplate tpl; private final Query q=new Query();
  QueryBuilder(Class<T> c,MongoTemplate t){cls=c;tpl=t;}
  public QueryBuilder<T> filter(Criteria c){q.addCriteria(c);return this;}
  public QueryBuilder<T> orderBy(String f){q.with(Sort.by(f));return this;}
  public QueryBuilder<T> limit(int n){q.limit(n);return this;}
  public List<T> fetch(){return tpl.find(q,cls);}
  public T one(){return tpl.findOne(q,cls);}
}
