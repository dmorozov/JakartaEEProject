# JakartaEE 10 Multi-Module Sample Application

A comprehensive JakartaEE 10 multi-module Maven project demonstrating modern enterprise Java development with Struts 7, EJB, JPA, and PostgreSQL.

## Project Overview

This project showcases a complete enterprise application architecture with:
- **JakartaEE 10** platform
- **Apache Struts 7** MVC framework with Tiles layout management
- **Stateless EJB** session beans for business logic
- **Remote EJB** interface for distributed calls
- **JPA 3.1** with Hibernate and PostgreSQL
- **JDBC alternative** implementation with repository pattern
- **Security integration** between web and EJB layers
- **Maven multi-module** project structure
- **EAR packaging** for enterprise deployment

## Project Structure

```
JakartaEEBootstrap/
├── pom.xml                    # Parent POM with dependency management
├── SampleCore/                # JPA entities and domain model
│   ├── src/main/java/
│   │   └── com/example/jakartaee/
│   │       ├── entity/        # JPA entities (Account, Contact, Address)
│   │       └── repository/    # JDBC repositories (alternative to JPA)
│   └── src/main/resources/
│       └── META-INF/
│           └── persistence.xml
├── SampleEjb/                 # Business logic with stateless EJBs
│   ├── src/main/java/
│   │   └── com/example/jakartaee/
│   │       └── service/       # EJB services (Account, Contact, Address)
│   └── src/main/resources/
│       └── META-INF/
│           └── ejb-jar.xml    # EJB security configuration
├── ExampleRemoteEJB/          # Remote EJB module
│   └── src/main/java/
│       └── com/example/jakartaee/
│           └── remote/        # HelloWorldRemote interface and implementation
├── SampleWeb/                 # Struts 7 web presentation layer
│   ├── src/main/java/
│   │   └── com/example/jakartaee/
│   │       └── action/        # Struts actions with EJB injection
│   ├── src/main/resources/
│   │   └── struts.xml         # Struts configuration
│   └── src/main/webapp/
│       ├── WEB-INF/
│       │   ├── web.xml        # Web application configuration
│       │   ├── tiles.xml      # Tiles layout configuration
│       │   ├── layouts/       # Tiles layout templates
│       │   └── pages/         # JSP pages for CRUD operations
│       └── index.jsp
└── SampleEar/                 # EAR packaging module
    └── src/main/application/
        └── META-INF/
            └── application.xml
```

## Prerequisites

- **Java 17** or higher
- **Maven 3.8+** (or use the included Maven wrapper)
- **PostgreSQL 12+**
- **TomEE 10.0+ Plus** or **GlassFish 7**

### Maven Wrapper

This project includes a Maven wrapper, so you don't need to have Maven installed. You can use:

- **Linux/macOS**: `./mvnw` instead of `mvn`
- **Windows**: `mvnw.cmd` instead of `mvn`

All Maven commands in this README can be executed with either `mvn` or `./mvnw`.

## Database Setup

### 1. Install PostgreSQL

```bash
# Ubuntu/Debian
sudo apt-get install postgresql postgresql-contrib

# macOS (using Homebrew)
brew install postgresql

# Windows - Download from https://www.postgresql.org/download/windows/
```

### 2. Create Database and User

```bash
# Connect to PostgreSQL
sudo -u postgres psql

# Create database and user
CREATE DATABASE sampledb;
CREATE USER sampleuser WITH PASSWORD 'samplepass';
GRANT ALL PRIVILEGES ON DATABASE sampledb TO sampleuser;

# For PostgreSQL 15+, also grant schema privileges
\c sampledb
GRANT ALL ON SCHEMA public TO sampleuser;
```

### 3. Create Database Schema

Run the SQL script located at `sql/schema.sql`:

```bash
psql -U sampleuser -d sampledb -f sql/schema.sql
```

Or manually execute the schema creation:

```sql
-- See sql/schema.sql for complete schema
```

## Build Instructions

### Build the entire project:

Using installed Maven:

```bash
mvn clean install
```

Or using Maven wrapper (no Maven installation required):

```bash
# Linux/macOS
./mvnw clean install

# Windows
mvnw.cmd clean install
```

This will:
1. Compile all modules
2. Run tests (if any)
3. Package modules (JAR, EJB, WAR)
4. Create the EAR file at `SampleEar/target/JakartaEESample.ear`

### Build individual modules:

```bash
# Core module only (using Maven wrapper)
cd SampleCore && ../mvnw clean install

# Or with installed Maven
cd SampleCore && mvn clean install

# EJB module only
cd SampleEjb && mvn clean install

# Web module only
cd SampleWeb && mvn clean package
```

## Deployment Instructions

### Option 1: Apache TomEE 10.0+ Plus

#### 1. Download and Install TomEE

```bash
# Download TomEE 10.0+ Plus
wget https://dlcdn.apache.org/tomee/tomee-10.0.0-M3/apache-tomee-10.0.0-M3-plus.tar.gz
tar -xzf apache-tomee-10.0.0-M3-plus.tar.gz
cd apache-tomee-plus-10.0.0-M3
```

#### 2. Configure DataSource

Edit `conf/tomee.xml` and add:

```xml
<tomee>
  <Resource id="SampleDS" type="DataSource">
    JdbcDriver org.postgresql.Driver
    JdbcUrl jdbc:postgresql://localhost:5432/sampledb
    UserName sampleuser
    Password samplepass
    JtaManaged true
    InitialSize 5
    MaxActive 20
    MaxIdle 10
    MinIdle 2
    MaxWait 10000
    TestOnBorrow true
    TestWhileIdle true
    TimeBetweenEvictionRunsMillis 30000
    ValidationQuery SELECT 1
  </Resource>
</tomee>
```

#### 3. Copy PostgreSQL JDBC Driver

```bash
# Download PostgreSQL JDBC driver
wget https://jdbc.postgresql.org/download/postgresql-42.7.3.jar -P lib/

# Or copy manually
cp ~/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar lib/
```

#### 4. Deploy the EAR

```bash
# Copy EAR to apps directory
cp /path/to/JakartaEEBootstrap/SampleEar/target/JakartaEESample.ear apps/
```

#### 5. Configure Security Realm (Optional)

Edit `conf/tomee.xml` to add users:

```xml
<tomee>
  <Resource id="MySecurityRealm" type="PropertiesLoginModule">
    UsersFile conf/users.properties
    GroupsFile conf/groups.properties
  </Resource>
</tomee>
```

Create `conf/users.properties`:
```
admin=adminpass
user=userpass
```

Create `conf/groups.properties`:
```
admin=admin,user
user=user
```

#### 6. Start TomEE

```bash
bin/startup.sh  # Linux/macOS
bin/startup.bat # Windows

# View logs
tail -f logs/catalina.out
```

#### 7. Access the Application

Open browser: `http://localhost:8080/SampleWeb/`

---

### Option 2: GlassFish 7

#### 1. Download and Install GlassFish

```bash
# Download GlassFish 7
wget https://download.eclipse.org/ee4j/glassfish/glassfish-7.0.14.zip
unzip glassfish-7.0.14.zip
cd glassfish7
```

#### 2. Start GlassFish

```bash
bin/asadmin start-domain domain1
```

#### 3. Configure PostgreSQL JDBC Driver

```bash
# Copy PostgreSQL driver to GlassFish lib
cp ~/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar glassfish/domains/domain1/lib/
```

#### 4. Create JDBC Connection Pool

```bash
bin/asadmin create-jdbc-connection-pool \
  --datasourceclassname org.postgresql.ds.PGConnectionPoolDataSource \
  --restype javax.sql.ConnectionPoolDataSource \
  --property "user=sampleuser:password=samplepass:serverName=localhost:portNumber=5432:databaseName=sampledb" \
  SamplePool
```

#### 5. Create JDBC Resource

```bash
bin/asadmin create-jdbc-resource \
  --connectionpoolid SamplePool \
  jdbc/SampleDS
```

#### 6. Configure Security Realm (Optional)

```bash
# Create file realm
bin/asadmin create-auth-realm \
  --classname com.sun.enterprise.security.auth.realm.file.FileRealm \
  --property file=${com.sun.aas.instanceRoot}/config/keyfile:jaas-context=fileRealm \
  sample-realm

# Add users
bin/asadmin create-file-user \
  --groups admin,user \
  --authrealmname sample-realm \
  admin

bin/asadmin create-file-user \
  --groups user \
  --authrealmname sample-realm \
  user
```

#### 7. Deploy the EAR

```bash
# Via command line
bin/asadmin deploy /path/to/JakartaEEBootstrap/SampleEar/target/JakartaEESample.ear

# Or via Admin Console
# Open http://localhost:4848
# Applications -> Deploy -> Upload JakartaEESample.ear
```

#### 8. Access the Application

Open browser: `http://localhost:8080/sample`

---

## Application Features

### 1. Account Management
- Create, Read, Update, Delete (CRUD) operations
- List all accounts
- View account details
- One-to-many relationship with contacts

### 2. Contact Management
- CRUD operations for contacts
- Associate contacts with accounts
- Filter contacts by account
- One-to-many relationship with addresses

### 3. Address Management
- CRUD operations for addresses
- Associate addresses with contacts
- Filter addresses by contact

### 4. Remote EJB Test
- Demonstrates remote EJB calls
- HelloWorld remote interface example
- Shows server time
- Message processing

## Technology Stack

### Backend
- **JakartaEE 10** - Enterprise Java platform
- **EJB 4.0** - Enterprise JavaBeans for business logic
- **JPA 3.1** - Java Persistence API with Hibernate
- **CDI 4.0** - Contexts and Dependency Injection
- **Bean Validation 3.0** - Data validation
- **JTA 2.0** - Java Transaction API

### Frontend
- **Apache Struts 7.0** - MVC framework
- **Struts Tiles** - Layout composition framework
- **JSP 3.1** - JavaServer Pages
- **JSTL 3.0** - JSP Standard Tag Library
- **HTML5/CSS3** - Modern web standards

### Database
- **PostgreSQL 12+** - Relational database
- **JDBC 4.3** - Database connectivity
- **HikariCP** - Connection pooling (via JPA provider)

### Build & Packaging
- **Maven 3.8+** - Project management and build
- **EAR packaging** - Enterprise Archive deployment

## Development Notes

### JPA vs JDBC Implementation

This project includes both JPA and JDBC implementations:

1. **JPA Implementation** (Default):
   - Located in `SampleCore/src/main/java/com/example/jakartaee/entity/`
   - Used by EJB services in `SampleEjb`
   - Provides object-relational mapping
   - Automatic transaction management
   - Caching support

2. **JDBC Implementation** (Alternative):
   - Located in `SampleCore/src/main/java/com/example/jakartaee/repository/`
   - Direct SQL queries similar to Spring's JdbcTemplate
   - More control over SQL execution
   - Useful for complex queries or performance tuning
   - To use: inject repositories instead of EntityManager in EJBs

### Security Configuration

The application implements declarative security:

1. **EJB Layer**:
   - Role-based access control with `@RolesAllowed`, `@PermitAll`
   - Security context propagation from web to EJB layer
   - Configured in `ejb-jar.xml`

2. **Web Layer**:
   - Form-based authentication
   - Protected resources under `/secure/*`
   - Configured in `web.xml`

### Struts 7 Plugins

The following Struts 7 plugins are configured:

1. **Convention Plugin** - Zero-configuration using naming conventions
2. **Tiles Plugin** - Layout management and templating
3. **JSON Plugin** - REST API support (optional)
4. **CDI Plugin** - Integration with JakartaEE CDI for dependency injection

## Troubleshooting

### Common Issues

#### 1. DataSource Not Found

**Error**: `java.sql.SQLException: Cannot create JDBC driver of class '' for connect URL 'null'`

**Solution**:
- Verify DataSource is configured in server (tomee.xml or GlassFish)
- Check JNDI name matches `java:comp/env/jdbc/SampleDS`
- Ensure PostgreSQL JDBC driver is in server's lib directory

#### 2. ClassNotFoundException for PostgreSQL Driver

**Solution**:
```bash
# Copy driver to server lib
cp ~/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar $SERVER_HOME/lib/
```

#### 3. EJB Injection Fails in Struts Actions

**Error**: `NullPointerException` when accessing EJB

**Solution**:
- Ensure `struts2-cdi-plugin` is in classpath
- Add `@Named` annotation to Struts actions
- Configure `struts.objectFactory=cdi` in struts.xml
- For TomEE, ensure beans.xml exists in WEB-INF/

#### 4. Tiles Definition Not Found

**Error**: `Cannot find tiles definition 'account.list'`

**Solution**:
- Verify tiles.xml is in WEB-INF/
- Check definition names match action results in struts.xml
- Ensure tiles-core and struts2-tiles-plugin are in classpath

#### 5. Port Conflicts

**Solution**:
```bash
# Change TomEE ports in conf/server.xml
<Connector port="8080" ... />  # Change to 8081, etc.

# Change GlassFish ports
bin/asadmin set configs.config.server-config.http-service.http-listener.http-listener-1.port=8081
```

### Debugging

Enable detailed logging:

**TomEE**: Edit `conf/logging.properties`
```properties
.level = INFO
com.example.jakartaee.level = FINE
org.apache.struts2.level = FINE
```

**GlassFish**: Edit `glassfish/domains/domain1/config/logging.properties`
```properties
com.example.jakartaee.level=FINE
org.apache.struts2.level=FINE
```

## Testing the Application

### 1. Verify Deployment

```bash
# TomEE
curl http://localhost:8080/sample/

# GlassFish
curl http://localhost:8080/sample/
```

### 2. Test CRUD Operations

1. Navigate to `http://localhost:8080/sample/`
2. Click "Manage Accounts"
3. Create a new account
4. Create contacts for the account
5. Create addresses for contacts

### 3. Test Remote EJB

1. Navigate to `http://localhost:8080/sample/`
2. Click "Test Remote EJB"
3. Verify remote EJB calls execute successfully

## Project Dependencies

Key Maven dependencies:

```xml
<!-- JakartaEE 10 -->
<dependency>
    <groupId>jakarta.platform</groupId>
    <artifactId>jakarta.jakartaee-api</artifactId>
    <version>10.0.0</version>
</dependency>

<!-- Struts 7 -->
<dependency>
    <groupId>org.apache.struts</groupId>
    <artifactId>struts2-core</artifactId>
    <version>7.0.0-M9</version>
</dependency>

<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
```

## Contributing

This is a sample/template project. Feel free to use it as a starting point for your own JakartaEE applications.

## License

This project is provided as-is for educational and development purposes.

## Additional Resources

- [JakartaEE Documentation](https://jakarta.ee/)
- [Apache Struts Documentation](https://struts.apache.org/)
- [TomEE Documentation](https://tomee.apache.org/)
- [GlassFish Documentation](https://glassfish.org/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Awesome Jakarta EE Resources for Developers](https://awesome-jakartaee.github.io/)

## Author

Created as a comprehensive JakartaEE 10 sample application demonstrating modern enterprise Java development practices.
