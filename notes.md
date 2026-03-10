# Notes taken during the app's development

## Cerinte

- Utilizand capacitatile reflective din java (java.lang.reflection) implementati un tool simplu care realizeaza reverse
  engineering de diagrame de clase din cod compilat.
- Criterii de notare:
  - Start: 3 pct
  - Cerinte minime: Implementarea corecta a extragerii de informatii prin reflection - introspection din cod compilat:
    4 puncte
  - Cerinte complete: Implementarea corecta a formatarii informatiilor extrase pentru a servi ca input intr-un tool de
    UML drawing: 2 puncte
  - Calitatea codului: respectarea principiilor de proiectare si programare OO: 1 pct

- **(1)Cerinte minime**: Tool-ul accepta ca argumente in linia de comanda numele unui fisier compilat (ar putea fi un fisier
  *.jar sau un fisier *.dll / *.exe) si extrage toate informatiile necesare pentru a descrie diagrama de clase a
  claselor continute. Nu este necesar sa se deseneze grafic diagrama de clase, este sufucient pentru cazul minim sa se
  afiseze intr-o forma textuala oarecare toate informatiile de acest tip: clase, interfete, metode, campuri, relatii
  dintre clase/interfete.

- **Cerinte complete:** Tool-ul trebuiesa formateze informatiile extrase intr-un format acceptat de un tool UML precum,
  de exemplu, yuml. De asemenea, tool-ul trebuie sa poata primi optiuni de configurare suplimentare:
    - se poate specifica un set de clase de ignorat la desenarea diagramei (de exemplu, toate clasele java.lang.*)
    - se doreste ca numele claselor sa apara ca nume complete cu pachete/subpachete: da/nu
    - arata numele metodelor pe diagrama: da/nu
    - arata numele atributelor: da/nu

- Cele 4 tipuri de relatii intre clase si/sau interfete analizate vor fi mostenire, implementare interfata, asociere,
  dependenta. Reamintim ca informatiile extrase prin reflexie nu sunt suficiente pentru a distinge subtipurile de
  asocieri precum agregarea sau compunerea. Astfel relatile de agregare si de compozitie vor fi reprezentate ca simple
  asocieri.
- Cardinalitatea relatiilor de asociere este ignorata.
- Cand sunt gasite tipuri parametrizate, includeti si tipul actual al argumentului in diagrama (de exemplu, daca
  detectati o dependenta de List, si clasa Student sa apara ca dependenta).

### What my App will do:

- extract info with Reflection API from compiled code
- from CLI: the app needs a file_path(.jar, .dll, .exe)
- (1) extract and print: classes, interfaces, methods, attributes, umlRelations between classes/interfaces
  - umlRelations between classes/interfaces: inheritance, interface impl., association, dependency
  - association cardinality is ignored
- for parameterized types include and the actual type(ex: List<Student> -> Student shows as a dependency)
- (2) format the extracted information into yuml format.
- from CLI: specify a set of classes to be ignored (ex: java.lang.*)
- from CLI: specify if class names should include packet path (yes/no)
- from CLI: show method names on the diagram (yes/no)
- from CLI: show attribute names on the diagram (yes/no)

## Dev

### Arch

```scss
java.org
main
 ├─ Main
 ├─ ArgParser
 ├─ Config
 └─ FileScanner

reflection
 ├─ ReflectionAnalyzer
 ├─ ClassLoaderFactory
 ├─ TypeFilter
 ├─ RelationExtractor
 └─ GenericTypeResolver

uml
 ├─ YumlRenderer
 ├─ NameFormatter
 └─ YumlWriter
```

## Trail: The Reflection API

- [Link](https://docs.oracle.com/javase/tutorial/reflect/index.html)

## Lesson: Classes

- Every type is either a reference or a primitive. Classes, enums, and arrays(which all inherit from `java.lang.Object`) 
  as well as interfaces are all reference types. Examples of reference types include `java.lang.String`, all the 
  wrapper classes for primitive types such as `java.lang.Double`, the interface `java.io.Serializable`, and the enum
  `javax.swing.SortOrder`. There is a fixed set of primitive types: _boolean_, _byte_, _short_, _int_, _long_, _char_,
  _float_, and _double_.
- For every type of object, the Java virtual machine instantiates an immutable instance of `java.lang.Class` which
  provides methods to examine the runtime properties of the object including its members and type information. Class
  also provides the ability to create new classes and objects. Most importantly, it is the entry point for all the
  Reflection APIs. This lesson covers the most commonly used reflection operations involving classes:
  - `Retrieving Class Objects` describes the ways to get a `Class`
  - `Examining Class Modifiers` and Types shows how to access the class declaration information
  - `Discovering Class Members` illustrates how to list the constructors, fields, methods, and nested classes in a class
  - `Troubleshooting describes` common errors encountered when using `Class`

### Retrieving Class Objects

#### Object.getClass()

- If an instance of an object is available, then the simplest way to get its `Class` is to invoke `Object.getClass()`.
  Of course, this only works for reference types which all inherit from `Object`. Some examples follow.
- Example:
```java
Class c = "foo".getClass();
```
- returns the `Class` for `String`

#### .class Syntax
- If the type is available but there is no instance then it is possible to obtain a `Class` by appending "_.class_" to
  the name of the type. This is also the easiest way to obtain the `Class` for a primitive type.
```java
boolean b;
Class c = b.getClass();   // compile-time error

Class c = boolean.class;  // correct
```

#### Class.forName()

- If the fully-qualified name of a class is available, it is possible to get the corresponding `Class` using the static
  method `Class.forName()`. This cannot be used for primitive types. The syntax for names of array classes is described
  by `Class.getName()`. This syntax is applicable to references and primitive types.
```java
Class c = Class.forName("com.duke.MyLocaleServiceProvider");
```

#### TYPE Field for Primitive Type Wrappers

- The `.class` syntax is a more convenient and the preferred way to obtain the `Class` for a primitive type; however
  there is another way to acquire the `Class`. Each of the primitive types and void has a wrapper class in `java.lang`
  that is used for boxing of primitive types to reference types. Each wrapper class contains a field named `TYPE` which
  is equal to the `Class` for the primitive type being wrapped.
```java
Class c = Double.TYPE;
```

#### Methods that Return Classes

- There are several Reflection APIs which return classes but these may only be accessed if a `Class` has already been
  obtained either directly or indirectly.

- `Class.getSuperclass()`
  - Returns the super class for the given class.
```java  
Class c = javax.swing.JButton.class.getSuperclass();
```

- `Class.getClasses()`
  - Returns all the public classes, interfaces, and enums that are members of the class including inherited members.
```java
Class<?>[] c = Character.class.getClasses();
```

- `Class.getDeclaredClasses()`
  - Returns all the classes interfaces, and enums that are explicitly declared in this class.
```java
Class<?>[] c = Character.class.getDeclaredClasses();
```  

- `Class.getDeclaringClass()`
  - `java.lang.reflect.Field.getDeclaringClass()`
  - `java.lang.reflect.Method.getDeclaringClass()`
  - `java.lang.reflect.Constructor.getDeclaringClass()`
  - Returns the `Class` in which these members were declared
```java
import java.lang.reflect.Field;

Field f = System.class.getField("out");
Class c = f.getDeclaringClass();
```

- `Class.getEnclosingClass()`
  - Returns the immediately enclosing class of the class.
```java
Class c = Thread.State.class().getEnclosingClass();
```

### Examining Class Modifiers and Types

[//]: # (Tip for intellJ: double CTRL + hold the second CTRL +  UP or DOWN to edit multiple lines)
- A class may be declared with one or more modifiers which affect its runtime behavior:
  - Access modifiers: _public_, _protected_, and _private_
  - Modifier requiring override: _abstract_
  - Modifier restricting to one instance: _static_
  - Modifier prohibiting value modification: _final_
  - Modifier forcing strict floating point behavior: _strictfp_
  - Annotations

[//]: # (NOTE: A reference to a Class object is a variable that points to the JVM’s runtime representation of a Java
         class, allowing inspection of its structure and metadata.)

- Not all modifiers are allowed on all classes, for example an interface cannot be _final_ and an enum cannot be
  _abstract_. `java.lang.reflect.Modifier` contains declarations for all possible modifiers. It also contains methods
  which may be used to decode the set of modifiers returned by `Class.getModifiers()`.
  - see example in [ClassDeclarationSpy.java](src/main/java/org/example/lessons/ClassDeclarationSpy.java)

### Discovering Class Members

- There are two categories of methods provided in `Class` for accessing fields, methods, and constructors: methods
  which enumerate these members and methods which search for particular members. Also, there are distinct methods for
  accessing members declared directly on the class versus methods which search the superinterfaces and superclasses for
  inherited members. The following tables provide a summary of all the member-locating methods and their characteristics.

- Class Methods for Locating Fields

| Class API             | Lists multiple members? | Includes inherited members? | Includes private members? |
|-----------------------|-------------------------|-----------------------------|---------------------------|
| `getDeclaredField()`  | No                      | No                          | Yes                       |
| `getField()`          | No                      | Yes                         | No                        |
| `getDeclaredFields()` | Yes                     | No                          | Yes                       |
| `getFields()`         | Yes                     | Yes                         | No                        |


- Class Methods for Locating Methods

| Class API              | Lists multiple members? | Includes inherited members? | Includes private members? |
|------------------------|-------------------------|-----------------------------|---------------------------|
| `getDeclaredMethod()`  | No                      | No                          | Yes                       |
| `getMethod()`          | No                      | Yes                         | No                        |
| `getDeclaredMethods()` | Yes                     | No                          | Yes                       |
| `getMethods()`         | Yes                     | Yes                         | No                        |

- Class Methods for Locating Constructors

| Class API                   | Lists multiple members? | Includes inherited members? | Includes private members? |
|-----------------------------|-------------------------|-----------------------------|---------------------------|
| `getDeclaredConstructor()`  | No                      | N/A¹                        | Yes                       |
| `getConstructor()`          | No                      | N/A¹                        | No                        |
| `getDeclaredConstructors()` | Yes                     | N/A¹                        | Yes                       |
| `getConstructors()`         | Yes                     | N/A¹                        | No                        |

- Given a class name and an indication of which members are of interest, the `ClassSpy` example uses the `get*s()`
  methods to determine the list of all public elements, including any which are inherited.
  - see example in [ClassSpy.java](src/main/java/org/lessons/ClassSpy.java)
    - args ex_1: `ClassSpy java.lang.ClassCastException CONSTRUCTOR`
    - args ex_2: `org.lessons.ClassMember FIELD METHOD`
  - In the fields portion of these results, enum constants are listed. While these are technically fields, it might be
    useful to distinguish them from other fields. This example could be modified to use
    `java.lang.reflect.Field.isEnumConstant()` for this purpose.

### Troubleshooting

- The following examples show typical errors which may be encountered when reflecting on classes.

#### Compiler Warning: "Note: ... uses unchecked or unsafe operations"

- When a method is invoked, the types of the argument values are checked and possibly converted. `ClassWarning` invokes
  `getMethod()` to cause a typical unchecked conversion warning.
```java
import java.lang.reflect.Method;

public class ClassWarning {
    void m() {
	try {
	    Class c = ClassWarning.class;
	    Method m = c.getMethod("m");  // warning

        // production code should handle this exception more gracefully
	} catch (NoSuchMethodException x) {
    	    x.printStackTrace();
    	}
    }
}
```

- The more preferable solution is to modify the declaration of c to include an appropriate generic type. In this case,
  the declaration should be:
```java
Class<?> c = warn.getClass();
```

#### InstantiationException when the Constructor is Not Accessible

- `Class.newInstance()` will throw an _InstantiationException_ if an attempt is made to create a new instance of the
  class and the zero-argument constructor is not visible. The ClassTrouble example illustrates the resulting stack
  trace.
```java
class Cls {
    private Cls() {}
}

public class ClassTrouble {
    public static void main(String... args) {
	try {
	    Class<?> c = Class.forName("Cls");
	    c.newInstance();  // InstantiationException

        // production code should handle these exceptions more gracefully
	} catch (InstantiationException x) {
	    x.printStackTrace();
	} catch (IllegalAccessException x) {
	    x.printStackTrace();
	} catch (ClassNotFoundException x) {
	    x.printStackTrace();
	}
    }
}
```

## Lesson: Members

- Reflection defines an interface `java.lang.reflect.Member` which is implemented by `java.lang.reflect.Field`,
  `java.lang.reflect.Method`, and `java.lang.reflect.Constructor`.
  - NOTE: Since **constructors are not inherited**, they are not members. This differs from the implementing classes of
    `java.lang.reflect.Member`.

- **Fields** have a type and a value. The `java.lang.reflect.Field` class provides methods for accessing type 
  information and setting and getting values of a field on a given object.
- **Methods** have return values, parameters, and may throw exceptions. The `java.lang.reflect.Method` class provides
  methods for obtaining the type information for the parameters and return value. It may also be used to invoke methods
  on a given object.
- The Reflection APIs for **constructors** are defined in `java.lang.reflect.Constructor` and are similar to those for
  methods, with two major exceptions: first, constructors have no return values; second, the invocation of a constructor
  creates a new instance of an object for a given class.

### Fields

- A field is a class, interface, or enum with an associated value.
- A class's fields are identified by invoking `Class.getFields()`. The `getFields()` method returns an array of `Field`
  objects containing one object per accessible public field.
- A public field is accessible if it is a member of either:
  - this class
  - a superclass of this class
  - an interface implemented by this class
  - an interface extended from an interface implemented by this class

#### Obtaining Field Types

- A field may be either of primitive or reference type. There are eight primitive types: _boolean_, _byte_, _short_,
  _int_, _long_, _char_, _float_, and _double_. A **reference type is anything that is a direct or indirect subclass of
  `java.lang.Object`** including interfaces, arrays, and enumerated types.
- The [FieldSpy.java](src/main/java/org/lessons/FieldSpy.java) example prints the field's type and generic type given a
  fully-qualified binary class name and field name.

[//]: # (To open Run/Debug Configuration: ALT + SHIFT + F10 then {number})

#### Retrieving and Parsing Field Modifiers

- There are several modifiers that may be part of a field declaration:
 - Access modifiers: _public_, _protected_, and _private_
 - Field-specific modifiers governing runtime behavior: _transient_ and _volatile_
 - Modifier restricting to one instance: _static_
 - Modifier prohibiting value modification: _final_
 - Annotations

- The method `Field.getModifiers()` can be used to return the integer representing the set of declared modifiers for the
  field. The bits representing the modifiers in this integer are defined in `java.lang.reflect.Modifier`.
- The [FieldModifierSpy.java](src/main/java/org/lessons/FieldModifierSpy.java) example illustrates how to search for
  fields with a given modifier.

#### Getting and Setting Field Values

- Given an instance of a class, it is possible to use reflection to set the values of fields in that class. This is
  typically done only in special circumstances when setting the values in the usual way is not possible. Because such
  access usually violates the design intentions of the class, it should be used with the utmost discretion.

#### Troubleshooting

- IllegalArgumentException due to Inconvertible Types
- NoSuchFieldException for Non-Public Fields
- IllegalAccessException when Modifying Final Fields

### Methods

- A method contains executable code which may be invoked. Methods are inherited and in non-reflective code behaviors
  such as overloading, overriding, and hiding are enforced by the compiler. In contrast, reflective code makes it
  possible for method selection to be restricted to a specific class without considering its superclasses. Superclass
  methods may be accessed but it is possible to determine their declaring class; this is impossible to discover
  programmatically without reflection and is the source of many subtle bugs.

#### Obtaining Method Type Information

- A method declaration includes the name, modifiers, parameters, return type, and list of throwable exceptions.
  The `java.lang.reflect.Method` class provides a way to obtain this information.
- The [MethodSpy.java](src/main/java/org/lessons/MethodSpy.java) example illustrates how to enumerate all the declared
  methods in a given class and retrieve the return, parameter, and exception types for all the methods of the given
  name.

#### Obtaining Names Of Method Parameters

- You can obtain the names of the formal parameters of any method or constructor with the method
  `java.lang.reflect.Executable.getParameters`. (The classes `Method` and `Constructor` extend the class `Executable`
  and therefore inherit the `method Executable.getParameters`.) However, _.class_ files do not store formal parameter
  names by default. This is because many tools that produce and consume class files may not expect the larger static
  and dynamic footprint of `.class` files that contain parameter names. In particular, these tools would have to handle
  larger `.class` files, and the Java Virtual Machine (JVM) would use more memory. In addition, some parameter names,
  such as _secret_ or _password_, may expose information about security-sensitive methods.
- To store formal parameter names in a particular `.class` file, and thus enable the Reflection API to retrieve formal
  parameter names, compile the source file with the _-parameters_ option to the _javac_ compiler.
- The [MethodParameterSpy.java](src/main/java/org/lessons/MethodParameterSpy.java) example illustrates how to retrieve
  the names of the formal parameters of all constructors and methods of a given class. The example also prints other
  information about each parameter.

#### Retrieving and Parsing Method Modifiers

- There a several modifiers that may be part of a method declaration:
  - Access modifiers: _public_, _protected_, and _private_
  - Modifier restricting to one instance: _static_
  - Modifier prohibiting value modification: _final_
  - Modifier requiring override: _abstract_
  - Modifier preventing reentrancy: _synchronized_
  - Modifier indicating implementation in another programming language: _native_
  - Modifier forcing strict floating point behavior: _strictfp_
  - Annotations
- The [MethodModifierSpy.java](src/main/java/org/lessons/MethodModifierSpy.java) example lists the modifiers of a
  method with a given name. It also displays whether the method is synthetic (compiler-generated), of variable arity,
  or a bridge method (compiler-generated to support generic interfaces).

#### Invoking Methods

- Reflection provides a means for invoking methods on a class. Typically, this would only be necessary if it is not
  possible to cast an instance of the class to the desired type in non-reflective code. Methods are invoked with
  `java.lang.reflect.Method.invoke()`. The first argument is the object instance on which this particular method is to
  be invoked. (If the method is static, the first argument should be null.) Subsequent arguments are the method's 
  parameters. If the underlying method throws an exception, it will be wrapped by an
  `java.lang.reflect.InvocationTargetException`. The method's original exception may be retrieved using the exception
  chaining mechanism's `InvocationTargetException.getCause()` method.

#### Troubleshooting 

- NoSuchMethodException Due to Type Erasure
- IllegalAccessException when Invoking a Method
- IllegalArgumentException from Method.invoke()
- InvocationTargetException when Invoked Method Fails

### Constructors

- A constructor is used in the creation of an object that is an instance of a class. Typically it performs operations
  required to initialize the class before methods are invoked or fields are accessed. Constructors are never inherited.

#### Finding Constructors

- A constructor declaration includes the name, modifiers, parameters, and list of throwable exceptions. The
  `java.lang.reflect.Constructor` class provides a way to obtain this information.
- The [ConstructorSift.java](src/main/java/org/lessons/ConstructorSift.java) example illustrates how to search a class's
  declared constructors for one which has a parameter of a given type.

#### Retrieving and Parsing Constructor Modifiers

- Because of the role of constructors in the language, fewer modifiers are meaningful than for methods:
  - Access modifiers: _public_, _protected_, and _private_
  - Annotations
- The [ConstructorAccess.java](src/main/java/org/lessons/ConstructorAccess.java) example searches for constructors in a
  given class with the specified access modifier. It also displays whether the constructor is synthetic
  (compiler-generated) or of variable arity.

#### Creating New Class Instances

- There are two reflective methods for creating instances of classes: `java.lang.reflect.Constructor.newInstance()` and
  `Class.newInstance()`.

#### Troubleshooting

- InstantiationException Due to Missing Zero-Argument Constructor
- Class.newInstance() Throws Unexpected Exception
- Problems Locating or Invoking the Correct Constructor
- IllegalAccessException When Attempting to Invoke an Inaccessible Constructor

## Lesson: Arrays and Enumerated Types

- From the Java virtual machine's perspective, arrays and enumerated types (or enums) are just classes. Many of the
  methods in `Class` may be used on them. Reflection provides a few specific APIs for arrays and enums.

### Arrays

- An array is an object of reference type which contains a fixed number of components of the same type; the length of
  an array is immutable. Creating an instance of an array requires knowledge of the length and component type. Each
  component may be a primitive type (such as _byte_, _int_, or _double_), a reference type (such as `String`, `Object`,
  or `java.nio.CharBuffer`), or an array. Multi-dimensional arrays are really just arrays which contain components of
  array type.

#### Identifying Array

- Array types may be identified by invoking `Class.isArray()`. To obtain a `Class` use one of the methods described in
  [Retrieving Class Objects](#retrieving-class-objects) section of this trail.
- The [ArrayFind.java](src/main/java/org/lessons/ArrayFind.java) example identifies the fields in the named class that
  are of array type and reports the component type for each of them.

#### Creating New Arrays

- Just as in non-reflective code, reflection supports the ability to dynamically create arrays of arbitrary type and
  dimensions via `java.lang.reflect.Array.newInstance()`. Consider ArrayCreator, a basic interpreter capable of
  dynamically creating arrays.


#### Getting and Setting Arrays and Their Components

- Setting a Field of Type Array
- Accessing Elements of a Multidimensional Array

#### Troubleshooting

- IllegalArgumentException due to Inconvertible Types
- ArrayIndexOutOfBoundsException for Empty Arrays
- IllegalArgumentException if Narrowing is Attempted

### Enumerated Types

- An enum is a language construct that is used to define type-safe enumerations which can be used when a fixed set of
  named values is desired. All enums implicitly extend `java.lang.Enum`. Enums may contain one or more enum constants,
  which define unique instances of the enum type. An enum declaration defines an enum type which is very similar to a
  class in that it may have members such as fields, methods, and constructors (with some restrictions).
- Since enums are classes, reflection has no need to define an explicit `java.lang.reflect.Enum` class. The only
  Reflection APIs that are specific to enums are `Class.isEnum()`, `Class.getEnumConstants()`, and
  `java.lang.reflect.Field.isEnumConstant()`. Most reflective operations involving enums are the same as any other
  class or member. For example, enum constants are implemented as `public static final` fields on the enum.

#### Examining Enums

- Reflection provides three enum-specific APIs:
  - `Class.isEnum()`
    - Indicates whether this class represents an enum type
  - `Class.getEnumConstants()`
    - Retrieves the list of enum constants defined by the enum in the order they're declared
  - `java.lang.reflect.Field.isEnumConstant()`
    - Indicates whether this field represents an element of an enumerated type
- Sometimes it is necessary to dynamically retrieve the list of enum constants; in non-reflective code this is
  accomplished by invoking the implicitly declared static method `values()` on the enum. If an instance of an enum type
  is not available the only way to get a list of the possible values is to invoke `Class.getEnumConstants()` since it
  is impossible to instantiate an enum type.
- Given a fully qualified name, the [EnumConstants.java](src/main/java/org/lessons/EnumConstants.java) example shows
  how to retrieve an ordered list of constants in an enum using `Class.getEnumConstants()`.
- The [EnumSpy.java](src/main/java/org/lessons/EnumSpy.java) code illustrates how to use these APIs to get additional
  information about the enum's declaration. The example uses `Class.isEnum()` to restrict the set of classes examined.
  It also uses `Field.isEnumConstant()` to distinguish enum constants from other fields in the enum declaration
  (not all fields are enum constants).

#### Getting and Setting Fields with Enum Types

- Fields which store enums are set and retrieved as any other reference type, using `Field.set()` and `Field.get()`.
  For more information on accessing fields, see the [Fields](#Fields) section of this trail.

#### Troubleshooting

- IllegalArgumentException When Attempting to Instantiate an Enum Type
- IllegalArgumentException when Setting a Field with an Incompatible Enum Type
- 

[//]: # (NOTE: ALT + F12 to quick open terminal)