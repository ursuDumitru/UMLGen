package org.reflection.model;

import org.reflection.TypeConverter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MethodInfo {
    public class ParamInfo {
        String name;
        String type;

        public String getParamName() {
            return this.name;
        }

        public String getParamType() {
            return this.type;
        }
    }

    private final String name;
    private final String returnType;
    private final List<ParamInfo> params;
    private final Visibility vis;
    private final boolean isStatic;

    public MethodInfo(Method m, TypeConverter typeConverter) {
        this.name = m.getName();
        this.returnType = typeConverter.convertTypeToString(m.getGenericReturnType());
        this.vis = Visibility.convertModifierToVisibility(m.getModifiers());
        this.isStatic = Modifier.isStatic(m.getModifiers());

        List<ParamInfo> tempParams = new ArrayList<>();
        int index = 0;
        for (Type type : m.getGenericParameterTypes()) {
            ParamInfo paramInfo = new ParamInfo();
            paramInfo.name = "arg" + index;
            paramInfo.type = typeConverter.convertTypeToString(type);
            tempParams.add(paramInfo);
            index++;
        }
        this.params = Collections.unmodifiableList(tempParams);
    }

    public Visibility getVisibility() {
        return this.vis;
    }

    public String getName() {
        return this.name;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<ParamInfo> getParams() {
        return this.params;
    }

    @Override
    public String toString() {
        String paramsString = "";
        for (ParamInfo param : this.params) {
            paramsString +=
                    "\n\t\t\t\tParamInfo: "
                            + "[\n\t\t\t\t\tname: "
                            + param.name
                            + ",\n\t\t\t\t\ttype: "
                            + param.type
                            + "\n\t\t\t\t],";
        }
        return "\n\t\tMethodInfo: "
                + "[\n\t\t\tname: "
                + this.name
                + ",\n\t\t\treturnType: "
                + this.returnType
                + ",\n\t\t\tparams: ["
                + paramsString
                + "\n\t\t\t]"
                + ",\n\t\t\tvis: "
                + this.vis
                + ",\n\t\t\tisStatic: "
                + this.isStatic
                + "\n\t\t]";
    }
}
