# Introduction
## What is quarkus.io

Quarkus is a full-stack, Kubernetes-native Java framework that aims to optimise Java for containers, providing a good 
platform for serverless, cloud, function-as-a-service and Kubernetes environments.

Quarkus also offers the possibility of compiling a native image using GraalVM, which enables shorter start-up times 
and much more efficient memory use. It is said that Quarkus builds applications to consume 1/10th the memory when 
compared to traditional Java, and has a faster startup time (as much as 300 times faster).

The Quarkus feature set includes:
* Live coding so that developers can immediately check the effect of code changes and quickly troubleshoot them
* Unified imperative and reactive programming
* Unified configuration
* Easy native executable generation

>___a few links to further reading___
> 
> [Quarkus.io - What is Quarkus](https://quarkus.io/about/) \
> [Redhat - What is Quarkus](https://www.redhat.com/en/topics/cloud-native-apps/what-is-quarkus) \
> [Wikipedia - Quarkus](https://en.wikipedia.org/wiki/Quarkus)


# Bootstrap the project

## Prerequisites
* a good IDE, like IntelliJ IDEA
* JDK 11+
* Apache Maven 3.8.1

Maven must use the right Java: check this with
```
mvn --version
```

## let's create a new quarkus project using maven

1. create an empty folder

2. run mvn command in terminal, this will create the maven project
```
mvn io.quarkus.platform:quarkus-maven-plugin:2.7.4.Final:create \
     -DprojectGroupId=at.fhj.msd20 \
     -DprojectArtifactId=showcase \
     -Dextensions="resteasy"
```
note that the inclusion of the resteasy dependency provides an implementation of JAX-RS.
>___JAX-RS___  
>_to learn some basics about JAX-RS maybe start here:_  
>_[Baeldung - JAX-RS is just an API](https://www.baeldung.com/jax-rs-spec-and-implementations)_


>**_NOTE FOR MS WINDOWS USERS:_**
>
>&nbsp;&nbsp;
>                   if using cmd:
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
>                   don't use backslash and put everything on the same line!
> 
>&nbsp;&nbsp; if using powershell:
> &nbsp;      wrap  -D parameters in double quotes!
>
> so this will be
>
>```
>mvn io.quarkus.platform:quarkus-maven-plugin:2.7.4.Final:create "-DprojectGroupId=at.fhj.msd20" "-DprojectArtifactId=showcase" "-Dextensions=resteasy"
>```

3. switch into created showcase folder
4. run
```
mvn compile quarkus:dev
```
5. open browser on http://localhost:8080/, this will show the default welcome page
---
# let's deliver some data

## Create your first "hello-world" REST endpoint
1. create a new Java Class named FHJRestApi under /src/main/java/at.fhj.msd20/rest
2. define URI-Path by annotating the REST-Class with @Path("/fhj")
4. create a GET method to get your first REST endpoint 
```
    @GET
    public String sayHello(){
        return "Hello World! :)";
    }
```
4. got to http://localhost:8080/fhj to see the data.

## Create an REST endpoint with a path parameter
1. create this GET method 
```
    @GET
    @Path("/{name}")
    public String sayHello(@PathParam("name") String name){
        return "Hello "+name+"! :)";
    }
```
_The @Path-Annotation with the brackets defines a variable in the path. The @PathParam("variable") links to the 
variable of the path, so you can use it later on in the function._ 

## Other possible HTTP-Annotations
```
@PUT
@POST
@DELETE
@PATCH
@OPTIONS
@HEAD
```
>___JAX-RS___  
>_for more documentation about JAX-RS HTTP Bindings you can have a look at this website:_  
>_[RESTful Java with JAX-RS 2.0 - Binding HTTP Methods](https://dennis-xlc.gitbooks.io/restful-java-with-jax-rs-2-0-2rd-edition/content/en/part1/chapter4/binding_http_methods.html)_
---
# let's keep  the rest endpoint class clean, create a service class
1. Inject a new unwritten ServiceClass into your FhjRestApi-class with this code snippet:
````
    @Inject
    FhjService service;
````
>___JAX-RS___  
>_more about dependency injection:_  
>_[openliberty.io - JAX-RS integration with CDI](https://openliberty.io/docs/22.0.0.1/jaxrs-integration-cdi.html)_
2. create this FhjService class in a new package at.fhj.msd20.service
3. now replace the return value from the sayHello() function with a new method of the FhjService. Like
````
    @GET
    public String sayHello(){
        return service.sayHello();
    }
````
4. lets the IDE create this method for you. in the end it should look like these:
````
    public String sayHello() {
        return "hello world! :)"; 
    }
````
_with this structure you can keep the rest api classes clean and have the possibility to put all your business logic 
into the service classes._ 

5. repeat this for the other class.
6. annotate @RequestScoped over the whole FhjService Class, so it will be only spawned when necessary
>___JAX-RS___  
>_if you want to learn more about scopes maybe start here:_  
>_[Oracle: The Java EE 6 Tutorial - Using Scopes](https://docs.oracle.com/javaee/6/tutorial/doc/gjbbk.html)_
---
# lets quickly integrate swaggerUi
1. add new dependency to pom.xml
````
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-openapi</artifactId>
</dependency>
````
2. press d (opens Dev UI) in terminal running quarkus:dev
3. click on swaggerUi in the smallrye open api field
---
# let's have some last words about configuration

The whole configuration for the quarkus is controlled by a file called application.properties.
Because a .properties-file is terrible to read and write, I recommend renaming it to application.yaml.
As little extra you get autocompletion.  To use the yaml pleas insert this dependency in your pom.xml.

````
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-config-yaml</artifactId>
    <version>2.8.0.Final</version>
</dependency>
````

Please note that convention over configuration always applies here. There for, only entries that really configure the 
behaviour of the application away from its default behaviour should be made.

A small example of this is for example:
````
quarkus:
  http:
    port: 18080
````
This reconfigures the port under which quarkus runs and thus enables several quarkus to be started on the same computer.
This is extremely practical, especially for the development of microservices.
---
# let's have a look on possible helpful extensions

## Lombok
````
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.22</version>
    </dependency>
````
This will reduce boilerplate code in your project. It does it by replacing the Getter and Setter methods by annotations. 
And it will give you the possibility to create a NoArgsConstructor and an AllArgsConstructor just in time by annotating. 
Sounds great? Give it a try. :)
## JDBC-Hibernate-Panache
````
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm-panache</artifactId>
    </dependency>
````
This can really simplify your database access. Simply annotate your DB-Entity like you used from hibernate. 
But also just stream your DB query output into a reactive pipeline to work with it asynchronously. 

## PostgreSQL
````
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
````
If Docker is present on your computer, it will automatically launch a new container with your database. Configure the 
database properties in the application.yaml

## JSON-B
````
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jsonb</artifactId>
    <version>2.8.0.Final</version>
</dependency>
````
JSON-B helps you to convert Java objects to/from JSON messages. It defines a  mapping algorithm for converting existing 
Java classes to JSON, while enabling developers to customize the mapping process through the use of Java annotations.

__...AND MANY, MANY MORE__
---
