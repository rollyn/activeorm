package com.ethanium.activeorm.config;
import com.ethanium.activeorm.mapping.ActiveOrmMongoMappingContext;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.*;
@Configuration
public class MongoConfig {
    @Bean
    public MongoMappingContext mongoMappingContext(MongoCustomConversions conversions) {
        ActiveOrmMongoMappingContext ctx = new ActiveOrmMongoMappingContext();
        ctx.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        ctx.afterPropertiesSet();
        return ctx;
    }
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory,
                                                       MongoMappingContext ctx,
                                                       MongoCustomConversions conversions) {
        DbRefResolver resolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter conv = new MappingMongoConverter(resolver, ctx);
        conv.setCustomConversions(conversions);
        conv.afterPropertiesSet();
        return conv;
    }
}
