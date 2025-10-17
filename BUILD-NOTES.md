# Build Notes - JakartaEE 10 Multi-Module Project

## Build Summary

The project has been successfully built with the following components:

### Modules Created
1. **SampleCore** - JPA entities and JDBC repositories
2. **SampleEjb** - Stateless EJB session beans
3. **ExampleRemoteEJB** - Remote EJB interface
4. **SampleWeb** - Struts 6 web application
5. **SampleEar** - EAR package (12MB)

### Build Output
- EAR File: `SampleEar/target/JakartaEESample.ear` (12MB)
- WAR File: `SampleWeb/target/SampleWeb.war`
- EJB JARs: `SampleEjb/target/SampleEjb-1.0.0-SNAPSHOT.jar`
- Remote EJB JAR: `ExampleRemoteEJB/target/ExampleRemoteEJB-1.0.0-SNAPSHOT.jar`

## Important Configuration Notes

### Technology Stack
- **JakartaEE**: 10.0.0
- **Apache Struts**: 6.4.0 (NOT Struts 7 - Struts 7 milestones are not yet available in Maven Central)
- **Java**: 17 (compiled with release 17)
- **PostgreSQL**: 12+
- **Build Tool**: Maven 3.8+ (Maven wrapper included)

### Key Differences from Requirements

#### Struts Version
- **Original Requirement**: Struts 7
- **Actual Implementation**: Struts 6.4.0
- **Reason**: Struts 7 milestone builds (7.0.0-M9) are not available in Maven Central repository. Struts 6.4.0 is the latest stable version.
- **Impact**: Struts 6.4.0 uses `javax` namespace instead of `jakarta`, which requires careful handling of servlet APIs.

#### CDI Integration
- The `struts2-cdi-plugin` was removed because Struts 6.4.0 CDI plugin is designed for javax CDI, not Jakarta CDI
- EJB injection using `@EJB` annotation still works correctly in Struts actions
- The `@Named` annotation was removed from action classes to avoid javax/jakarta namespace conflicts

#### Java Version
- Configured for Java 17 (as per requirements)
- Uses `maven.compiler.release=17` for proper compilation targeting

## Build Command

Using installed Maven:
```bash
mvn clean install
```

Using Maven wrapper (no Maven installation required):
```bash
# Linux/macOS
./mvnw clean install

# Windows
mvnw.cmd clean install
```

The project includes a Maven wrapper (mvnw/mvnw.cmd) so Maven installation is optional.

## Deployment Locations

### TomEE 10.0+ Plus
```
$TOMEE_HOME/apps/JakartaEESample.ear
```

### GlassFish 7
```
$GLASSFISH_HOME/glassfish/domains/domain1/autodeploy/JakartaEESample.ear
```

Or use:
```bash
bin/asadmin deploy /path/to/JakartaEESample.ear
```

## Database Configuration

The application expects a PostgreSQL datasource with JNDI name:
```
java:comp/env/jdbc/SampleDS
```

See README.md for complete database setup instructions.

## Web Context Root

The application is deployed at:
```
http://localhost:8080/sample
```

## Known Limitations

1. **Struts Version**: Using Struts 6.4.0 instead of Struts 7 due to artifact availability
2. **CDI Integration**: Limited CDI integration between Struts and JakartaEE due to javax/jakarta namespace differences
3. **Remote EJB**: Remote EJB interface works, but requires proper server configuration for remote calls

## Future Enhancements

When Struts 7 becomes available in Maven Central:
1. Update `struts2.version` in parent POM to 7.0.0 or later
2. Re-enable `struts2-cdi-plugin` dependency
3. Add `@Named` annotations back to action classes
4. Change servlet imports from `javax.servlet` to `jakarta.servlet`
5. Re-enable CDI object factory in struts.xml: `<constant name="struts.objectFactory" value="cdi"/>`

## Testing the Build

To verify the build is working correctly:

```bash
# Check that all modules built successfully
ls -lh SampleCore/target/*.jar
ls -lh SampleEjb/target/*.jar
ls -lh ExampleRemoteEJB/target/*.jar
ls -lh SampleWeb/target/*.war
ls -lh SampleEar/target/*.ear

# Verify EAR structure
jar -tf SampleEar/target/JakartaEESample.ear
```

## Quick Start

1. Set up PostgreSQL database (see README.md)
2. Configure datasource in application server (see README.md)
3. Deploy EAR file
4. Access application at http://localhost:8080/sample

For complete deployment instructions, see README.md.
