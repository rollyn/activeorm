# ActiveORM

**ActiveORM** is a lightweight ActiveRecord-style ODM built on top of Spring Data MongoDB, inspired by the simplicity of ActiveRecord (Rails) and the fluency of modern Java (21+).

---

## ✨ Features

- `@Domain` annotation to mark domain entities.
- `@Reference` annotation to simulate `@DBRef` behavior with cascading:
  - Automatically handles cascade save, update, delete.
- Code-generated DSL for concise MongoDB queries:
  ```java
  List<User> users = User.findAll();
  User admin = User.where(u -> u.getName().equals("admin"));
  List<Role> roles = Role.where(r -> r.getStatus() == Status.ACTIVE).orderBy("createdAt").limit(10);
  ```
- Custom `ActiveRecord<T>` superclass handles persistence logic.
- Built-in annotation processor to auto-generate fluent static accessors.
- No need to declare a `UserRepository` for simple CRUD.
- Fully Spring Boot compatible with Spring Data MongoDB.

---

## 🛠️ Setup

### 1. Maven Project Structure

```bash
activeorm/
├── activeorm-core       # Contains annotations and runtime logic
├── activeorm-processor  # Annotation processor (compile-time codegen)
└── sample-app           # Example usage
```

### 2. Install to local Maven

```bash
cd activeorm
mvn clean install
```

### 3. Sample `application.yml`

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/test?replicaSet=rs0
```

You may also configure it with:
```yaml
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      database: test
      replica-set-name: rs0
```

Make sure no environment variable (e.g., `SPRING_DATA_MONGODB_HOST=mongodb`) is overriding your config.

---

## 🧠 How @Reference Works

The custom `@Reference` annotation is equivalent to Spring’s `@DBRef`, but decouples your code from `spring-data` internals. Under the hood:

- Fields annotated with `@Reference` are treated as DBRefs.
- You can cascade operations using `cascade = CascadeMode.ALL` or fine-tuned with `SAVE`, `UPDATE`, `DELETE`.

```java
@Reference(cascade = CascadeMode.ALL)
private List<Role> roles;
```

---

## 💡 Usage Example

### Model

```java
@Domain
public class User extends ActiveRecord<User> {
    private String name;
}
```

### Query

```java
User user = User.where(u -> u.getName().equals("John")).firstOrNull();
user.setName("Johnny");
user.save();
```

---

## 📦 Building & Contributing

- Java 21+
- Spring Boot 3.3+
- Run tests with `mvn test`
- Contributions welcome! Fork the repo and create a PR.

---

© 2025 Ethanium · MIT License
