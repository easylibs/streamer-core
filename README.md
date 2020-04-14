# Streamer Core Package
Streamer is an adapter library between persistence storage and Java Streams. Multiple implementations provide adapters for various persistance technologies. All libraries are very lightweight compared to similar counter parts yet do very similar job and provide lots flexibility.

1. `streamer-core` is the required core package by all adapters
1. `streamer-sql` provides SQL functionality to Java Streams
1. `streamer-orm` provides ORM (Object-Relational Mapping) to persistance and Java Streams

## Streamer SQL - SQL with Java Streams
The `SQL Streamer` provides easy to use, yet very flexible. The `SqlStreamer` is the main manager for SQL connections and object lifecycles. You can easily create one with a factory method `SqlStreamer.of(java.sql.Connection connection)` where you supply the backend connectivity.

Here is a simple example that creates and executes the following SQL query and presents the result as a Java `Stream`.

SQL:
```
SELECT 1 + 1;
```
Java:
```java
final SqlStreamer streamer = SqlStreamer.of(ds.getConnection());
streamer.query()
  .select(int.class, "1 + 1")
  .stream()
  .forEach(System.out::println);
```
Output:
```
2
```
## Streamer ORM - Object Relational Mapper
The `ORM Streamer` maps user objects to persistance entities and provides easy to use Java `Streams` that can be be configured to efficiently query the persistance layer. For SQL, queries are built based on `Stream` filters, mapping, etc to generate most efficient query or statement so least amount of data is actually sent. The results are presented in a standard `Stream` object where the data can be further filtered, remapped and collected.

Here is a basic example that query a database table *Person* using a POJO class `Person` with just 2 fields `int id` and `String name`. The result is further filtered to include only those database entries who's name starts with the letter 'A':
SQL:
```
SELECT * FROM Person WHERE name LIKE "A%";
```
Java:
```java
final OrmStreamer streamer = OrmStreamer.of(ds.getConnection());

Table<Person> table = streamer.meta().table(Person.class);
StringAttribute<Person> name = table.meta().stringAttribute("name");

table.stream()
  .filter(name.startsWith("A"))
  .forEach(System.out::println);
```
Output:
```
Person [id=0, name=Alex]
Person [id=1, name=Amelia]
```
