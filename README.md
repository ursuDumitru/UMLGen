# Examples

- command:
```bash
.\gradlew run --console=plain --args='D:\University\Master\An2\Sem2\PASS\Proiect\UMLGen\build\libs\UMLGen-1.0-SNAPSHOT.jar --ignore java.lang.*,,org.foo.*,org.lessons.* --show-package no --show-attributes yes --show-methods yes'
```
- output:
```text
[RelationExtractor|- relations: Map of String,UmlRelation;- PRIORITY: Map of RelationType,Integer|+ extractRelations();- extractExtend();- extractAssociation();- isValidRelation();- extractImplement();- extractDependency();- insertRelationByPriority()]
[TypeFilter||+ isClassOk();+ isFieldOk();+ isMethodOk()]
[Main||+ main()]
[MethodInfo$ParamInfo|~ name: String;~ type: String|+ getParamName();+ getParamType()]
[YumlWriter||+ outputInConsole()]
[TypeConverter||+ convertTypeToFqcn();~ addAllReferences();+ convertTypeToString();+ getReferencedFqcnsFromType()]
[ClassLoaderFactory||+ create()]
[MethodInfo|- name: String;- returnType: String;- params: List of MethodInfo$ParamInfo;- vis: Visibility;- isStatic: boolean|+ getVisibility();+ getParams();+ getName();+ toString();+ getReturnType()]
[UmlClass|- fqcn: String;- kind: Kind;- fields: List of FieldInfo;- methods: List of MethodInfo;- extendsFqcn: String;- implementsFqcns: Set of String;- associationTargets: Set of String;- dependencyTargets: Set of String|+ addToFields();+ addToMethods();+ addToImplementsFqcn();+ addToDependencyTargets();+ addToAssociationTargets();+ getFqcn();+ setFqcn();+ setKind();+ setExtendsFqcn();+ getExtendsFqcn();+ toString();+ getFields();+ getMethods()]
[Visibility|+ PUBLIC: Visibility;+ PROTECTED: Visibility;+ PRIVATE: Visibility;+ PACKAGE: Visibility;- $VALUES: Visibility array|+ convertModifierToVisibility();+ values();+ valueOf()]
[NameFormatter||+ formatClassName();- cutPackageFromName();- isIdentifierChar();- simplifyToken();+ formatType()]
[ArgParser|- args: String array;- seenOptions: Set of String|- checkValueDoesNotStartWithDash();- checkBoolOptionHasOnlyOneValue();- getArtifactPath();- printRawArgs();- getIgnoreOption();- getBooleanOption();- checkSeenOption();- checkForHelp();- checkTokenStartsWithDDash();- checkTokenHasValues();+ parse()]
[UmlModel|- classesByName: Map of String,UmlClass;- classesRelations: Set of UmlRelation|+ addToClassesByName();+ setClassesRelations();+ getClassesRelations();+ printClassesRelations();+ getClassesByName();+ printClassesByName()]
[ReflectionAnalyzer|- loaderFactory: ClassLoaderFactory;- typeFilter: TypeFilter;- typeConverter: TypeConverter;- relationExtractor: RelationExtractor|- getUmlModel();- getLoadedClasses();- getIncludedClasses();- toUmlClassNode();+ analyze()]
[Kind|+ CLASS: Kind;+ INTERFACE: Kind;+ ENUM: Kind;- $VALUES: Kind array|+ getKindOfClass();+ values();+ valueOf()]
[RelationType|+ EXTENDS: RelationType;+ IMPLEMENTS: RelationType;+ ASSOCIATION: RelationType;+ DEPENDENCY: RelationType;- $VALUES: RelationType array|+ equals();+ values();+ valueOf()]
[UmlRelation|- fromFqcn: String;- toFqcn: String;- type: RelationType;- label: String|+ getFromFqcn();+ setFromFqcn();+ setType();+ getLabel();+ setLabel();+ getToFqcn();+ setToFqcn();+ equals();+ toString();+ hashCode();+ getType()]
[FileScanner||- getClassNamesSet();- printClassNames();+ scan()]
[FieldInfo|- name: String;- type: String;- vis: Visibility;- isStatic: boolean|+ getVisibility();+ getName();+ toString();+ getType()]
[Config|- artifactPath: Path;- ignorePatterns: List of String;- showPacket: boolean;- showMethods: boolean;- showAttributes: boolean|+ getArtifactPath();+ printConfig();+ isShowMethods();+ isShowPacket();+ isShowAttributes();+ isFqcnIgnored()]
[YumlRenderer|- nameFormatter: NameFormatter|+ render();- renderField();- renderVisibility();- renderMethod();- renderClass();- renderRelation()]
[Visibility]->[Visibility]

[ReflectionAnalyzer]->[TypeConverter]
[ReflectionAnalyzer]->[TypeFilter]
[UmlClass]->[FieldInfo]
[ReflectionAnalyzer]->[ClassLoaderFactory]
[Kind]->[Kind]
[RelationExtractor]->[RelationType]
[FieldInfo]->[Visibility]
[MethodInfo]->[MethodInfo$ParamInfo]
[UmlClass]->[Kind]
[RelationExtractor]->[UmlRelation]
[UmlModel]->[UmlClass]
[MethodInfo]->[Visibility]
[YumlRenderer]->[NameFormatter]
[UmlClass]->[MethodInfo]
[RelationType]->[RelationType]
[UmlModel]->[UmlRelation]
[ReflectionAnalyzer]->[RelationExtractor]
[UmlRelation]->[RelationType]
```

- command:
```bash
.\gradlew run --console=plain --args='D:\University\Master\An2\Sem2\PASS\Proiect\UMLGen\build\libs\UMLGen-1.0-SNAPSHOT.jar --ignore java.lang.*'
```

- output:
```text
[RelationExtractor]
[TypeFilter]
[Main]
[MethodInfo$ParamInfo]
[YumlWriter]
[TypeConverter]
[ClassLoaderFactory]
[MethodInfo]
[UmlClass]
[Visibility]
[NameFormatter]
[ArgParser]
[UmlModel]
[ReflectionAnalyzer]
[Kind]
[RelationType]
[UmlRelation]
[FileScanner]
[FieldInfo]
[Config]
[YumlRenderer]
[Visibility]->[Visibility]

[ReflectionAnalyzer]->[TypeConverter]
[ReflectionAnalyzer]->[TypeFilter]
[UmlClass]->[FieldInfo]
[ReflectionAnalyzer]->[ClassLoaderFactory]
[Kind]->[Kind]
[RelationExtractor]->[RelationType]
[FieldInfo]->[Visibility]
[MethodInfo]->[MethodInfo$ParamInfo]
[UmlClass]->[Kind]
[RelationExtractor]->[UmlRelation]
[UmlModel]->[UmlClass]
[MethodInfo]->[Visibility]
[YumlRenderer]->[NameFormatter]
[UmlClass]->[MethodInfo]
[RelationType]->[RelationType]
[UmlModel]->[UmlRelation]
[ReflectionAnalyzer]->[RelationExtractor]
[UmlRelation]->[RelationType]
```

- command:
```bash
.\gradlew run --console=plain --args='D:\University\Master\An2\Sem2\PASS\Proiect\UMLGen\build\libs\UMLGen-1.0-SNAPSHOT.jar --ignore java.lang.*, --show-methods yes'
```

- output:
```text
[RelationExtractor||- extractDependency();- extractAssociation();- isValidRelation();+ extractRelations();- extractExtend();- extractImplement();- insertRelationByPriority()]
[TypeFilter||+ isMethodOk();+ isClassOk();+ isFieldOk()]
[Main||+ main()]
[MethodInfo$ParamInfo||+ getParamType();+ getParamName()]
[YumlWriter||+ outputInConsole()]
[TypeConverter||~ addAllReferences();+ convertTypeToFqcn();+ convertTypeToString();+ getReferencedFqcnsFromType()]
[ClassLoaderFactory||+ create()]
[MethodInfo||+ getVisibility();+ getParams();+ getName();+ toString();+ getReturnType()]
[UmlClass||+ addToMethods();+ addToFields();+ addToAssociationTargets();+ addToDependencyTargets();+ addToImplementsFqcn();+ getFqcn();+ setKind();+ setFqcn();+ getExtendsFqcn();+ setExtendsFqcn();+ toString();+ getFields();+ getMethods()]
[Visibility||+ convertModifierToVisibility();+ values();+ valueOf()]
[NameFormatter||- simplifyToken();+ formatClassName();- cutPackageFromName();- isIdentifierChar();+ formatType()]
[ArgParser||- checkValueDoesNotStartWithDash();- checkBoolOptionHasOnlyOneValue();- getBooleanOption();- getIgnoreOption();- getArtifactPath();- printRawArgs();- checkForHelp();- checkSeenOption();- checkTokenStartsWithDDash();- checkTokenHasValues();+ parse()]
[UmlModel||+ addToClassesByName();+ setClassesRelations();+ getClassesRelations();+ printClassesRelations();+ printClassesByName();+ getClassesByName()]
[ReflectionAnalyzer||- getLoadedClasses();- getUmlModel();- getIncludedClasses();- toUmlClassNode();+ analyze()]
[Kind||+ getKindOfClass();+ values();+ valueOf()]
[RelationType||+ equals();+ values();+ valueOf()]
[UmlRelation||+ getFromFqcn();+ getToFqcn();+ setFromFqcn();+ setType();+ getLabel();+ setToFqcn();+ setLabel();+ equals();+ toString();+ hashCode();+ getType()]
[FileScanner||- printClassNames();- getClassNamesSet();+ scan()]
[FieldInfo||+ getVisibility();+ getName();+ toString();+ getType()]
[Config||+ getArtifactPath();+ printConfig();+ isShowAttributes();+ isFqcnIgnored();+ isShowMethods();+ isShowPacket()]
[YumlRenderer||+ render();- renderRelation();- renderClass();- renderVisibility();- renderMethod();- renderField()]
[UmlClass]->[Kind]
[MethodInfo]->[Visibility]
[UmlModel]->[UmlRelation]
[UmlClass]->[MethodInfo]
[RelationType]->[RelationType]
[MethodInfo]->[MethodInfo$ParamInfo]
[UmlRelation]->[RelationType]

[ReflectionAnalyzer]->[ClassLoaderFactory]
[FieldInfo]->[Visibility]
[RelationExtractor]->[UmlRelation]
[YumlRenderer]->[NameFormatter]
[Kind]->[Kind]
[ReflectionAnalyzer]->[TypeConverter]
[ReflectionAnalyzer]->[TypeFilter]
[RelationExtractor]->[RelationType]
[Visibility]->[Visibility]
[UmlClass]->[FieldInfo]
[ReflectionAnalyzer]->[RelationExtractor]
[UmlModel]->[UmlClass]
```

- command:
```bash
.\gradlew run --console=plain --args='D:\University\Master\An2\Sem2\PASS\Proiect\UMLGen\build\libs\UMLGen-1.0-SNAPSHOT.jar --ignore java.lang.*, --show-methods yes --show-attributes yes'
```

- output:
```text
[RelationExtractor|- relations: Map of String,UmlRelation;- PRIORITY: Map of RelationType,Integer|- insertRelationByPriority();- extractDependency();+ extractRelations();- isValidRelation();- extractImplement();- extractExtend();- extractAssociation()]
[TypeFilter||+ isClassOk();+ isMethodOk();+ isFieldOk()]
[Main||+ main()]
[MethodInfo$ParamInfo|~ name: String;~ type: String|+ getParamName();+ getParamType()]
[YumlWriter||+ outputInConsole()]
[TypeConverter||+ convertTypeToString();+ getReferencedFqcnsFromType();+ convertTypeToFqcn();~ addAllReferences()]
[ClassLoaderFactory||+ create()]
[MethodInfo|- name: String;- returnType: String;- params: List of MethodInfo$ParamInfo;- vis: Visibility;- isStatic: boolean|+ getName();+ toString();+ getReturnType();+ getParams();+ getVisibility()]
[UmlClass|- fqcn: String;- kind: Kind;- fields: List of FieldInfo;- methods: List of MethodInfo;- extendsFqcn: String;- implementsFqcns: Set of String;- associationTargets: Set of String;- dependencyTargets: Set of String|+ toString();+ getFields();+ getMethods();+ addToImplementsFqcn();+ addToAssociationTargets();+ addToDependencyTargets();+ setKind();+ getFqcn();+ setFqcn();+ addToMethods();+ addToFields();+ getExtendsFqcn();+ setExtendsFqcn()]
[Visibility|+ PUBLIC: Visibility;+ PROTECTED: Visibility;+ PRIVATE: Visibility;+ PACKAGE: Visibility;- $VALUES: Visibility array|+ values();+ valueOf();+ convertModifierToVisibility()]
[NameFormatter||+ formatType();- cutPackageFromName();- isIdentifierChar();- simplifyToken();+ formatClassName()]
[ArgParser|- args: String array;- seenOptions: Set of String|+ parse();- getBooleanOption();- checkForHelp();- printRawArgs();- checkSeenOption();- getIgnoreOption();- checkBoolOptionHasOnlyOneValue();- checkValueDoesNotStartWithDash();- checkTokenStartsWithDDash();- checkTokenHasValues();- getArtifactPath()]
[UmlModel|- classesByName: Map of String,UmlClass;- classesRelations: Set of UmlRelation|+ setClassesRelations();+ printClassesRelations();+ getClassesRelations();+ addToClassesByName();+ printClassesByName();+ getClassesByName()]
[ReflectionAnalyzer|- loaderFactory: ClassLoaderFactory;- typeFilter: TypeFilter;- typeConverter: TypeConverter;- relationExtractor: RelationExtractor|- getLoadedClasses();- getIncludedClasses();- getUmlModel();+ analyze();- toUmlClassNode()]
[Kind|+ CLASS: Kind;+ INTERFACE: Kind;+ ENUM: Kind;- $VALUES: Kind array|+ values();+ valueOf();+ getKindOfClass()]
[RelationType|+ EXTENDS: RelationType;+ IMPLEMENTS: RelationType;+ ASSOCIATION: RelationType;+ DEPENDENCY: RelationType;- $VALUES: RelationType array|+ equals();+ values();+ valueOf()]
[UmlRelation|- fromFqcn: String;- toFqcn: String;- type: RelationType;- label: String|+ equals();+ toString();+ hashCode();+ getType();+ getToFqcn();+ getFromFqcn();+ setLabel();+ setType();+ setToFqcn();+ getLabel();+ setFromFqcn()]
[FileScanner||+ scan();- printClassNames();- getClassNamesSet()]
[FieldInfo|- name: String;- type: String;- vis: Visibility;- isStatic: boolean|+ getName();+ toString();+ getType();+ getVisibility()]
[Config|- artifactPath: Path;- ignorePatterns: List of String;- showPacket: boolean;- showMethods: boolean;- showAttributes: boolean|+ isShowMethods();+ printConfig();+ isShowPacket();+ isShowAttributes();+ isFqcnIgnored();+ getArtifactPath()]
[YumlRenderer|- nameFormatter: NameFormatter|+ render();- renderRelation();- renderField();- renderVisibility();- renderMethod();- renderClass()]
[UmlClass]->[Kind]
[MethodInfo]->[Visibility]
[UmlModel]->[UmlRelation]
[UmlClass]->[MethodInfo]
[RelationType]->[RelationType]
[MethodInfo]->[MethodInfo$ParamInfo]
[UmlRelation]->[RelationType]

[ReflectionAnalyzer]->[ClassLoaderFactory]
[FieldInfo]->[Visibility]
[RelationExtractor]->[UmlRelation]
[YumlRenderer]->[NameFormatter]
[Kind]->[Kind]
[ReflectionAnalyzer]->[TypeConverter]
[ReflectionAnalyzer]->[TypeFilter]
[RelationExtractor]->[RelationType]
[Visibility]->[Visibility]
[UmlClass]->[FieldInfo]
[ReflectionAnalyzer]->[RelationExtractor]
[UmlModel]->[UmlClass]
```

- with this code enabled:
```Java
// file: YumlRender.java
return switch (relation.getType()) {
    case EXTENDS -> fromClass + "^- " + toClass;
    case IMPLEMENTS -> fromClass + "^-.-" + toClass;
    case ASSOCIATION -> fromClass + "->" + toClass;
    case DEPENDENCY -> fromClass + "-.->" + toClass; // this is not commented
    default -> "";
};
```
- command:
```bash
\gradlew run --console=plain --args='D:\University\Master\An2\Sem2\PASS\Proiect\UMLGen\build\libs\UMLGen-1.0-SNAPSHOT.jar --ignore java.lang.*,,org.foo.*,org.lessons.* --show-package no --show-attributes yes --show-methods yes'
```

- output:
```text
[RelationExtractor|- relations: Map of String,UmlRelation;- PRIORITY: Map of RelationType,Integer|+ extractRelations();- extractExtend();- extractAssociation();- extractImplement();- extractDependency();- isValidRelation();- insertRelationByPriority()]
[TypeFilter||+ isClassOk();+ isFieldOk();+ isMethodOk()]
[Main||+ main()]
[MethodInfo$ParamInfo|~ name: String;~ type: String|+ getParamName();+ getParamType()]
[YumlWriter||+ outputInConsole()]
[TypeConverter||+ convertTypeToFqcn();~ addAllReferences();+ convertTypeToString();+ getReferencedFqcnsFromType()]
[ClassLoaderFactory||+ create()]
[MethodInfo|- name: String;- returnType: String;- params: List of MethodInfo$ParamInfo;- vis: Visibility;- isStatic: boolean|+ getParams();+ getVisibility();+ getName();+ toString();+ getReturnType()]
[UmlClass|- fqcn: String;- kind: Kind;- fields: List of FieldInfo;- methods: List of MethodInfo;- extendsFqcn: String;- implementsFqcns: Set of String;- associationTargets: Set of String;- dependencyTargets: Set of String|+ addToFields();+ addToMethods();+ getFqcn();+ setFqcn();+ setKind();+ addToDependencyTargets();+ addToAssociationTargets();+ addToImplementsFqcn();+ getExtendsFqcn();+ setExtendsFqcn();+ toString();+ getFields();+ getMethods()]
[Visibility|+ PUBLIC: Visibility;+ PROTECTED: Visibility;+ PRIVATE: Visibility;+ PACKAGE: Visibility;- $VALUES: Visibility array|+ convertModifierToVisibility();+ values();+ valueOf()]
[NameFormatter||+ formatType();- isIdentifierChar();+ formatClassName();- cutPackageFromName();- simplifyToken()]
[ArgParser|- args: String array;- seenOptions: Set of String|- checkTokenHasValues();- checkTokenStartsWithDDash();- checkBoolOptionHasOnlyOneValue();- checkValueDoesNotStartWithDash();- printRawArgs();- getBooleanOption();- checkSeenOption();- getArtifactPath();- checkForHelp();- getIgnoreOption();+ parse()]
[UmlModel|- classesByName: Map of String,UmlClass;- classesRelations: Set of UmlRelation|+ addToClassesByName();+ setClassesRelations();+ printClassesRelations();+ getClassesRelations();+ getClassesByName();+ printClassesByName()]
[ReflectionAnalyzer|- loaderFactory: ClassLoaderFactory;- typeFilter: TypeFilter;- typeConverter: TypeConverter;- relationExtractor: RelationExtractor|+ analyze();- toUmlClassNode();- getIncludedClasses();- getUmlModel();- getLoadedClasses()]
[Kind|+ CLASS: Kind;+ INTERFACE: Kind;+ ENUM: Kind;- $VALUES: Kind array|+ getKindOfClass();+ values();+ valueOf()]
[RelationType|+ EXTENDS: RelationType;+ IMPLEMENTS: RelationType;+ ASSOCIATION: RelationType;+ DEPENDENCY: RelationType;- $VALUES: RelationType array|+ equals();+ values();+ valueOf()]
[UmlRelation|- fromFqcn: String;- toFqcn: String;- type: RelationType;- label: String|+ getFromFqcn();+ getToFqcn();+ setType();+ setToFqcn();+ getLabel();+ setLabel();+ setFromFqcn();+ equals();+ toString();+ hashCode();+ getType()]
[FileScanner||- printClassNames();- getClassNamesSet();+ scan()]
[FieldInfo|- name: String;- type: String;- vis: Visibility;- isStatic: boolean|+ getVisibility();+ getName();+ toString();+ getType()]
[Config|- artifactPath: Path;- ignorePatterns: List of String;- showPacket: boolean;- showMethods: boolean;- showAttributes: boolean|+ isFqcnIgnored();+ isShowMethods();+ printConfig();+ isShowAttributes();+ isShowPacket();+ getArtifactPath()]
[YumlRenderer|- nameFormatter: NameFormatter|+ render();- renderVisibility();- renderMethod();- renderClass();- renderRelation();- renderField()]
[Visibility]->[Visibility]
[ReflectionAnalyzer]-.->[UmlClass]
[ReflectionAnalyzer]->[TypeConverter]
[ReflectionAnalyzer]->[TypeFilter]
[UmlClass]->[FieldInfo]
[RelationExtractor]-.->[Config]
[ReflectionAnalyzer]->[ClassLoaderFactory]
[YumlRenderer]-.->[UmlModel]
[Kind]->[Kind]
[RelationExtractor]-.->[TypeFilter]
[RelationExtractor]->[RelationType]
[NameFormatter]-.->[Config]
[FieldInfo]->[Visibility]
[MethodInfo]->[MethodInfo$ParamInfo]
[UmlClass]->[Kind]
[ArgParser]-.->[Config]
[YumlRenderer]-.->[UmlClass]
[YumlRenderer]-.->[MethodInfo]
[RelationExtractor]->[UmlRelation]
[RelationExtractor]-.->[TypeConverter]
[ReflectionAnalyzer]-.->[Config]
[UmlModel]->[UmlClass]
[ClassLoaderFactory]-.->[Config]
[YumlRenderer]-.->[Visibility]
[MethodInfo]->[Visibility]
[YumlRenderer]->[NameFormatter]
[ReflectionAnalyzer]-.->[UmlModel]
[UmlClass]->[MethodInfo]
[YumlRenderer]-.->[UmlRelation]
[TypeFilter]-.->[Config]
[RelationType]->[RelationType]
[UmlModel]->[UmlRelation]
[ReflectionAnalyzer]->[RelationExtractor]
[UmlRelation]->[RelationType]
[YumlRenderer]-.->[Config]
[YumlRenderer]-.->[FieldInfo]
```

- command(example from course)
```bash
.\gradlew run --console=plain --args='"C:\Users\dumit\Desktop\EventNotifier.jar" --ignore java.lang.*,,org.foo.*,org.lessons.* --show-package no --show-attributes yes --show-methods yes'
```

- output:
```text
[Hub||+ triggerPublication()]
[PagingSystem||+ inform()]
[InvalidEventTypeException]
[CriticalFaultFilter||+ apply()]
[Filter||+ apply()]
[FaultEvent|+ CRITICAL: int;+ MODERATE: int;+ LOW: int;+ severity: int;+ source: String|]
[Subscriber||+ inform()]
[StatusEvent|+ status: String;+ source: String|]
[Subscription|+ eventType: Class of ?;+ filter: Filter;+ subscriber: Subscriber|]
[Event]
[Main||+ main()]
[ManagementEvent]
[Router||+ triggerPublication()]
[ErrorLogger||+ inform()]
[StatusConsole||+ inform()]
[EventService|- eventClass: Class of ?;# subscriptions: List of Subscription;- singleton: EventService|+ instance();+ publish();+ subscribe();+ unsubscribe()]

[Subscriber]^-.-[StatusConsole]
[Subscriber]^-.-[ErrorLogger]
[Filter]^-.-[CriticalFaultFilter]
[ManagementEvent]^-.-[FaultEvent]
[Subscriber]^-.-[PagingSystem]
[ManagementEvent]^-.-[StatusEvent]
[Subscription]->[Filter]
[EventService]->[EventService]
[Subscription]->[Subscriber]
[EventService]->[Subscription]
[Event]^-.-[ManagementEvent]
```