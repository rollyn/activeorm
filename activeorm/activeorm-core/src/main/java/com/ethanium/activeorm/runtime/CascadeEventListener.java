package com.ethanium.activeorm.runtime;
import com.ethanium.activeorm.annotations.*;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
@Component
public class CascadeEventListener extends AbstractMongoEventListener<ActiveRecord<?>>{
 private final MongoTemplate mongo;
 public CascadeEventListener(MongoTemplate m){mongo=m;}
 @Override public void onBeforeConvert(BeforeConvertEvent<ActiveRecord<?>> e){
  traverse(e.getSource(),f->mode(f,CascadeMode.SAVE)||mode(f,CascadeMode.UPDATE),this::save);
 }
 @Override public void onBeforeDelete(BeforeDeleteEvent<ActiveRecord<?>> e){
  traverse(e.getSource(),f->mode(f,CascadeMode.DELETE),this::del);
 }
 private boolean mode(Field f,CascadeMode m){
  Reference r=AnnotatedElementUtils.findMergedAnnotation(f,Reference.class);
  return r!=null&&(r.cascade()==m||r.cascade()==CascadeMode.ALL);}
 private void traverse(Object root,java.util.function.Predicate<Field> p,BiConsumer<Object,Field> h){
  Set<Object> seen=Collections.newSetFromMap(new IdentityHashMap<>());
  Deque<Object> dq=new ArrayDeque<>(); dq.push(root);
  while(!dq.isEmpty()){Object cur=dq.pop(); if(!seen.add(cur))continue;
   for(Field f:cur.getClass().getDeclaredFields()) if(p.test(f)){
     h.accept(cur,f);
     f.setAccessible(true);
     try{
      Object ch=f.get(cur);
      if(ch instanceof Collection<?> col)col.forEach(dq::push);
      else if(ch!=null)dq.push(ch);
     }catch(Exception ignore){}
   }
  }
 }
 @Transactional void save(Object p,Field f){act(p,f,true);}
 @Transactional void del(Object p,Field f){act(p,f,false);}
 private void act(Object p,Field f,boolean sv){
  f.setAccessible(true);
  try{
    Object v=f.get(p);
    if(v instanceof Collection<?> col) col.forEach(x-> {if(sv)mongo.save(x);else mongo.remove(x);} );
    else if(v!=null){if(sv)mongo.save(v);else mongo.remove(v);}
  }catch(Exception ignore){}
 }
}
