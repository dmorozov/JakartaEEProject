# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

### Standard Build
```bash
# Using Maven wrapper (recommended - no Maven installation required)
./mvnw clean install

# Or using installed Maven
mvn clean install
```

### Build Individual Modules
```bash
# Core module (JPA entities)
cd SampleCore && ../mvnw clean install

# EJB module (business logic)
cd SampleEjb && ../mvnw clean install

# Web module (Struts frontend)
cd SampleWeb && ../mvnw clean package

# Build from parent directory
cd /workspaces && ./mvnw clean install
```

### Verify Build Artifacts
```bash
# Check EAR file (main deployable)
ls -lh SampleEar/target/JakartaEESample.ear

# Inspect EAR contents
jar -tf SampleEar/target/JakartaEESample.ear
```

## Testing

This project currently has no automated test suite. Manual testing requires:
1. PostgreSQL database setup (see Database Setup section)
2. Application server deployment (TomEE or GlassFish)
3. Accessing the web interface at `http://localhost:8080/sample/`

## Code Quality

### Code Formatting
The project uses Prettier for JSP files:
```bash
# Format JSP files
npx prettier --write "**/*.jsp"

# Check formatting without making changes
npx prettier --check "**/*.jsp"
```

Prettier configuration is in `.prettierrc.mjs` with `prettier-plugin-jsp` for JSP support.

### Static Analysis
The project uses Checkstyle and PMD for code quality:

```bash
# Run Checkstyle validation
./mvnw checkstyle:check

# Run PMD analysis
./mvnw pmd:check

# Run both during the validate phase
./mvnw validate
```

Configuration files:
- Checkstyle: `.vscode/checkstyle.xml` with suppressions in `.vscode/checkstyle-suppressions.xml`
- PMD: `.vscode/pmd-ruleset.xml`

Note: Both tools run automatically during `mvn clean install` and will fail the build on violations.

## High-Level Architecture

### Multi-Module Maven Structure

This is a **JakartaEE 10 multi-module enterprise application** packaged as an EAR. The modules follow a layered architecture:

1. **SampleCore** (JAR) - Domain layer
   - JPA 3.1 entities: `Account`, `Contact`, `Address` with bidirectional relationships
   - JDBC repository alternative (unused by default) in `com.example.jakartaee.repository`
   - Persistence unit: `SamplePU` configured in `META-INF/persistence.xml`

2. **SampleEjb** (EJB-JAR) - Business logic layer
   - Stateless session beans: `AccountServiceBean`, `ContactServiceBean`, `AddressServiceBean`
   - Each service has a local interface (e.g., `AccountService`)
   - Uses `@PersistenceContext` to inject EntityManager from `SamplePU`
   - Security roles defined in `ejb-jar.xml` (currently commented out in code)
   - Transactional operations with `@TransactionAttribute`

3. **ExampleRemoteEJB** (EJB-JAR) - Remote EJB interface
   - Demonstrates remote EJB capability
   - `HelloWorldRemote` interface and `HelloWorldBean` implementation
   - Used for remote EJB testing

4. **SampleWeb** (WAR) - Presentation layer
   - **Apache Struts 7.0.3** MVC framework (note: configured as Struts 6.0 DTD for compatibility)
   - Struts Actions use `@EJB` injection to access session beans
   - Apache Tiles for layout composition (`tiles.xml` in WEB-INF)
   - CDI integration enabled via `struts2-cdi-plugin`
   - JSP views in `WEB-INF/pages/` and layouts in `WEB-INF/layouts/`

5. **SampleEar** (EAR) - Enterprise archive
   - Packages all modules together
   - Deployed to JakartaEE 10 application servers (TomEE 10+ or GlassFish 7)

### Key Architectural Patterns

- **Service Layer Pattern**: EJBs provide service interfaces for business operations
- **DAO/Repository Pattern**: JPA entities accessed via EntityManager (JDBC repositories available as alternative)
- **MVC Pattern**: Struts Actions (controllers) → JSP views with Tiles layouts
- **Dependency Injection**: EJB injection in Struts actions, CDI integration throughout
- **Declarative Security**: Role-based access control defined in `ejb-jar.xml`
- **Declarative Transactions**: Container-managed transactions via `@TransactionAttribute`

### Data Flow

1. HTTP Request → Struts Action (web layer)
2. Action calls EJB Service via `@EJB` injection
3. Service uses EntityManager to access JPA entities
4. EntityManager talks to PostgreSQL via JTA DataSource (`java:comp/env/jdbc/SampleDS`)
5. Response flows back through Action to JSP/Tiles view

### Technology Integration Points

- **Struts + EJB**: Actions use `@EJB` to inject stateless session beans
- **Struts + CDI**: `struts.objectFactory=cdi` in struts.xml enables CDI integration
- **EJB + JPA**: Services inject `EntityManager` via `@PersistenceContext(unitName = "SamplePU")`
- **JPA + PostgreSQL**: Persistence unit configured with `java:comp/env/jdbc/SampleDS` datasource
- **Security Propagation**: Web security context propagates to EJB layer via `<use-caller-identity/>`

### Entity Relationships

- **Account** (1) → (many) **Contact**: `@OneToMany` with cascade and orphan removal
- **Contact** (1) → (many) **Address**: `@OneToMany` with cascade and orphan removal
- All relationships use `@ManyToOne` on the owning side (Contact owns Account, Address owns Contact)

## Database Setup

### Create PostgreSQL Database
```bash
# Connect as postgres user
sudo -u postgres psql

# Create database and user
CREATE DATABASE sampledb;
CREATE USER sampleuser WITH PASSWORD 'samplepass';
GRANT ALL PRIVILEGES ON DATABASE sampledb TO sampleuser;

# For PostgreSQL 15+
\c sampledb
GRANT ALL ON SCHEMA public TO sampleuser;
```

### Initialize Schema
```bash
psql -U sampleuser -d sampledb -f sql/schema.sql
```

### Optional: Load Sample Data
```bash
psql -U sampleuser -d sampledb -f sql/sample-data.sql
```

### DataSource Configuration

The application expects a JNDI datasource: `java:comp/env/jdbc/SampleDS`

**For TomEE**: Edit `$TOMEE_HOME/conf/tomee.xml`:
```xml
<Resource id="jdbc/SampleDS" type="javax.sql.DataSource">
    JdbcDriver org.postgresql.Driver
    JdbcUrl jdbc:postgresql://localhost:5432/sampledb
    UserName sampleuser
    Password samplepass
    JtaManaged true
</Resource>
```

**PostgreSQL JDBC Driver**: Must be in `$TOMEE_HOME/lib/` or `$GLASSFISH_HOME/glassfish/domains/domain1/lib/`
```bash
cp ~/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar $SERVER_HOME/lib/
```

## Deployment

### Deploy to TomEE 10+
```bash
# Copy EAR to TomEE apps directory
cp SampleEar/target/JakartaEESample.ear $TOMEE_HOME/apps/

# Start TomEE
$TOMEE_HOME/bin/startup.sh

# View logs
tail -f $TOMEE_HOME/logs/catalina.out
```

### Deploy to GlassFish 7
```bash
# Using asadmin
$GLASSFISH_HOME/bin/asadmin deploy SampleEar/target/JakartaEESample.ear

# Or copy to autodeploy
cp SampleEar/target/JakartaEESample.ear $GLASSFISH_HOME/glassfish/domains/domain1/autodeploy/
```

### Access Application
```
http://localhost:8080/sample/
```

## Important Configuration Files

### Struts Configuration
- **struts.xml**: Action mappings, result types, Tiles integration
- **tiles.xml**: Layout definitions for all pages (list, form, view templates)
- Actions are in `com.example.jakartaee.action` package
- Convention: `/account/list` → `AccountAction.list()` → `account.list` tiles definition

### JPA Configuration
- **persistence.xml**: Defines `SamplePU` persistence unit
- Uses JTA transactions with datasource `java:comp/env/jdbc/SampleDS`
- Hibernate dialect: `PostgreSQLDialect`
- Schema generation disabled (managed via SQL scripts)

### EJB Configuration
- **ejb-jar.xml**: Security roles (admin, user) and identity propagation
- Services use `@TransactionAttribute(REQUIRED)` for write operations
- Read operations use `@TransactionAttribute(SUPPORTS)` for performance

## Known Limitations and Quirks

1. **Struts Version Mismatch**: Parent POM declares Struts 7.0.3, but struts.xml uses Struts 6.0 DTD. This is intentional for compatibility.

2. **Security Annotations Commented**: Role-based annotations (`@RolesAllowed`, etc.) are commented out in EJB code. Security roles are defined in `ejb-jar.xml` but not enforced at method level.

3. **JDBC Repositories Unused**: `SampleCore` includes JDBC repository implementations (JdbcTemplate-style) but EJBs use JPA EntityManager by default. JDBC repos can be used as alternative.

4. **Java Version**: Project configured for Java 21 (see parent pom.xml `maven.compiler.release=21`).

5. **CDI Integration**: While `struts2-cdi-plugin` is configured, EJB injection via `@EJB` is the primary integration pattern between web and business layers.

## Common Development Tasks

### Adding a New Entity
1. Create entity class in `SampleCore/src/main/java/com/example/jakartaee/entity/`
2. Add corresponding table to `sql/schema.sql`
3. Add `<class>` entry in `persistence.xml` (or rely on auto-discovery)
4. Create EJB service interface and implementation in `SampleEjb`
5. Create Struts action in `SampleWeb` with `@EJB` injection
6. Add action mappings to `struts.xml`
7. Create JSP views and Tiles definitions

### Troubleshooting EJB Injection in Struts
If `@EJB` injections return null:
1. Verify `struts2-cdi-plugin` is in classpath
2. Check `beans.xml` exists in `WEB-INF/` (enables CDI)
3. Ensure `struts.objectFactory=cdi` in struts.xml
4. Verify EJB module is packaged in EAR and listed in `application.xml`

### Viewing JPA SQL
Enable SQL logging in `persistence.xml`:
```xml
<property name="hibernate.show_sql" value="true"/>
<property name="hibernate.format_sql" value="true"/>
```

## Project Versions

- **JakartaEE**: 10.0.0
- **Apache Struts**: 7.0.3
- **PostgreSQL**: 42.7.3 (JDBC driver)
- **Java**: 21 (compiler target)
- **Maven**: 3.8+ (wrapper included)
