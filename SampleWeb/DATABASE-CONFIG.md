# Database Configuration

This application requires a PostgreSQL database connection. **Database credentials should NOT be committed to source control.**

## Configuration Options

### Option 1: TomEE Global Configuration (Recommended)

Configure the DataSource in your TomEE server's `conf/tomee.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<tomee>
    <Resource id="jdbc/SampleDS" type="javax.sql.DataSource">
        JdbcDriver org.postgresql.Driver
        JdbcUrl jdbc:postgresql://localhost:5434/sampledb
        UserName your_username
        Password your_password
        JtaManaged true
        InitialSize 5
        MaxTotal 20
        MaxIdle 10
        MinIdle 5
        MaxWaitMillis 10000
        TestOnBorrow true
        ValidationQuery SELECT 1
    </Resource>
</tomee>
```

### Option 2: Application-Level Configuration

If you prefer application-level configuration:

1. Copy `WEB-INF/resources.xml.template` to `WEB-INF/resources.xml`
2. Update the database credentials in the new file
3. The `resources.xml` file is in `.gitignore` and will not be committed

## Eclipse Development Setup

For Eclipse/WTP development:

1. Ensure the PostgreSQL JDBC driver is available (already included in WAR dependencies)
2. Configure your TomEE server in Eclipse to use the `conf/tomee.xml` from your TomEE installation
3. The JNDI resource `java:comp/env/jdbc/SampleDS` will be available to your application

## Required Database

- **Type**: PostgreSQL 12+
- **JNDI Name**: `java:comp/env/jdbc/SampleDS`
- **Database Name**: sampledb (or configure your own)
- **Default Port**: 5434 (adjust as needed)

## Environment Variables (Alternative)

You can also use environment variables in your `resources.xml`:

```xml
UserName ${DB_USERNAME}
Password ${DB_PASSWORD}
```

Then set `DB_USERNAME` and `DB_PASSWORD` environment variables before starting TomEE.
