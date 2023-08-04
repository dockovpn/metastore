# metastore
Abstraction layer for Map and DB table

## Usage

### Create slick configuration

In `application.conf` create entry for slick

```
slick {
  connectionPool = "HikariCP"
  numThreads = 25
  url = "jdbc:mariadb://host:3306/database"
  properties = {
    databaseName = "database"
    databaseName = ${?DB_NAME}
    user = "user"
    user = ${?DB_USER}
    password = "password"
    password = ${?DB_PASSWORD}
  }
  keepAliveConnection = true
}
```

### Define case classes

Define case classes that you want to use with metastore. All primitive types and Option are allowed. Filed names must be camel cased.

```scala
final case class User(
  firstName    : String,
  lastName     : String,
  email        : String,
  userName     : String,
  userPassword : String,
  emailVerified: Boolean = false,
  authMethod: String,
  lastSession: Option[String]
)
```

**⚠️ Note:** If you intend to use `DB_STORE` your table column names must be snake cased.

### Define or import implicits

Define or import implicits in the scope of `StoreProvider`. In this example we will define implicits, that are necessary for metastore

```scala
// Define ExecutionContext
implicit val executionContext: ExecutionContext = ExecutionContext.global

// Define lazy database reference.
// As of now you have to create DBref even though you want to use only MAP_STORE (in this case config can be dummy)
private implicit val dbRef: DBRef = lazily { Database.forConfig("slick") }

// Define custom table metadata provider which must extend io.dockovpn.AbstractTableMetadataProvider
private implicit val metadataProvider: AbstractTableMetadataProvider = new AbstractTableMetadataProvider {
  val UserClass: String = getType[User].toString
  val SomeOtherStoreClass: String = getType[SomeOtherStoreClass].toString
  // ...
  // get FQCN for all classes you want to use with store

  override def getTableMetadata[V](implicit tag: ClassTag[V]): TableMetadata = tag.runtimeClass.getName match {
    case UserClass =>
        new TableMetadata(
          tableName = "users_table",
          fieldName = "users_unique_field",
          rconv = GetResult { r =>
            r.skip
            User(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<)
          }
        )
      case SomeOtherStoreClass =>
        new TableMetadata(
          tableName = "some_other_table",
          fieldName = "unique_field",
          rconv = SomeOtherStoreClass { r =>
            r.skip
            UserSession(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<)
          }
        )
      // ...
      // match all FQCN names
      case unrecognizedType =>
        throw new IllegalArgumentException(s"Unrecognized type [$unrecognizedType] to get TableMetadata for")
  }
}
```

### Get store for your case class

```scala
private val userStore: AbstractStore[User] = StoreProvider.getStoreByType(/* MAP_STORE or DB_STORE */)
```

### Use store

Use store to retrive, filter, put or update data 

See all methods available:

```scala
trait AbstractStore[V] {
  
  def contains(k: String): Future[Boolean]
  
  def filter(predicate: Predicate): Future[Seq[V]]
  
  def get(k: String): Future[Option[V]]
  
  def put(k: String, v: V): Future[Unit]
  
  def update(k: String, v: V): Future[Unit]
}
```
