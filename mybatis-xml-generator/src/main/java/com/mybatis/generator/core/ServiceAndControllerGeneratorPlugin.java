package com.mybatis.generator.core;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.getProperties;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * service controller构建插件
 *
 * @author leell
 */
public class ServiceAndControllerGeneratorPlugin extends PluginAdapter {

    /**
     * 项目目录，一般为 src/main/java
     */
    private String targetProject;

    /**
     * service包名
     */
    private String servicePackage;

    /**
     * service实现类包名
     */
    private String serviceImplPackage;
    /**
     * Controlle类包名
     */
    private String controllerPackage;
    /**
     * service接口名前缀
     */
    private String servicePreffix;

    /**
     * service接口名后缀
     */
    private String serviceSuffix;

    /**
     * service接口的父接口
     */
    private String superServiceInterface;

    /**
     * service实现类的父类
     */
    private String superServiceImpl;
    /**
     * controller类的父类
     */
    private String superController;

    /**
     * dao接口基类
     */
    private String superDaoInterface;

    /**
     * Example类的包名
     */
    private String examplePacket;
    /**
     * 作者
     */
    private String author;

    private String recordType;
    /**
     * modal名称
     */
    private String modelName;

    private FullyQualifiedJavaType model;

    private String mapperName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    /**
     * 是否使用lombok
     */
    private boolean hasLombok;

    @Override
    public boolean validate(List<String> warnings) {
        boolean valid = true;
        targetProject = properties.getProperty("targetProject");
        servicePackage = properties.getProperty("servicePackage");
        serviceImplPackage = properties.getProperty("serviceImplPackage");
        servicePreffix = properties.getProperty("servicePreffix");
        servicePreffix = stringHasValue(servicePreffix) ? servicePreffix : "";
        serviceSuffix = properties.getProperty("serviceSuffix");
        serviceSuffix = stringHasValue(serviceSuffix) ? serviceSuffix : "";
        superServiceInterface = properties.getProperty("superServiceInterface");
        superServiceImpl = properties.getProperty("superServiceImpl");
        superDaoInterface = properties.getProperty("superDaoInterface");
        controllerPackage = properties.getProperty("controllerPackage");
        superController = properties.getProperty("superController");
        author = properties.getProperty("author");
        String hasLombokStr = properties.getProperty("hasLombok");
        if (StringUtils.hasText(hasLombokStr)) {
            hasLombok = Boolean.parseBoolean(hasLombokStr);
        }

        return valid;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        recordType = introspectedTable.getBaseRecordType();
        modelName = recordType.substring(recordType.lastIndexOf(".") + 1);

        model = new FullyQualifiedJavaType(recordType);
        serviceName = servicePackage + "." + servicePreffix + modelName + serviceSuffix;
        serviceImplName = serviceImplPackage + "." + modelName + serviceSuffix + "Impl";
        examplePacket = recordType.substring(0, recordType.lastIndexOf("."));
        controllerName = controllerPackage.concat(".").concat(modelName).concat("Controller");
        List<GeneratedJavaFile> answer = new ArrayList<>();
        GeneratedJavaFile gjf = generateServiceInterface(introspectedTable);
        GeneratedJavaFile gjf2 = generateServiceImpl(introspectedTable);
        GeneratedJavaFile gjf3 = generateController(introspectedTable);
        answer.add(gjf);
        answer.add(gjf2);
        answer.add(gjf3);
        return answer;
    }

    /**
     * 生成service接口
     *
     * @param introspectedTable
     * @return
     */
    private GeneratedJavaFile generateServiceInterface(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(serviceName);
        Interface serviceInterface = new Interface(service);
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);
        //添加类注释
        serviceInterface.addJavaDocLine("/**");
        serviceInterface.addJavaDocLine(" * " + introspectedTable.getRemarks() + " 服务类");
        if (StringUtils.hasText(author)) {
            serviceInterface.addJavaDocLine(" * @author " + author);
        }
        serviceInterface.addJavaDocLine(" * @since " + getDateString());
        serviceInterface.addJavaDocLine(" */");

        // 添加父接口
        if (stringHasValue(superServiceInterface)) {
            String superServiceInterfaceName = superServiceInterface.substring(superServiceInterface.lastIndexOf(".") + 1);
            serviceInterface.addImportedType(new FullyQualifiedJavaType(superServiceInterface));
            serviceInterface.addImportedType(new FullyQualifiedJavaType(recordType));
            serviceInterface.addSuperInterface(new FullyQualifiedJavaType(superServiceInterfaceName + "<" + modelName + ">"));
        }
        GeneratedJavaFile gjf = new GeneratedJavaFile(serviceInterface, targetProject, context.getJavaFormatter());
        return gjf;
    }

    /**
     * 生成serviceImpl实现类
     *
     * @param introspectedTable
     * @return
     */
    private GeneratedJavaFile generateServiceImpl(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(serviceName);
        FullyQualifiedJavaType serviceImpl = new FullyQualifiedJavaType(serviceImplName);
        TopLevelClass clazz = new TopLevelClass(serviceImpl);
        //描述类的作用域修饰符
        clazz.setVisibility(JavaVisibility.PUBLIC);
        //添加类注释
        clazz.addJavaDocLine("/**");
        clazz.addJavaDocLine(" * " + introspectedTable.getRemarks() + " 服务实现类");
        if (StringUtils.hasText(author)) {
            clazz.addJavaDocLine(" * @author " + author);
        }
        clazz.addJavaDocLine(" * @since " + getDateString());
        clazz.addJavaDocLine(" */");
        //描述类 引入的类
        clazz.addImportedType(service);
        //描述类 的实现接口类
        clazz.addSuperInterface(service);
        if (stringHasValue(superServiceImpl)) {
            String superServiceImplName = superServiceImpl.substring(superServiceImpl.lastIndexOf(".") + 1);
            clazz.addImportedType(new FullyQualifiedJavaType(mapperName));
            String simpleMapperName = mapperName.substring(mapperName.lastIndexOf(".") + 1);
            clazz.addImportedType(superServiceImpl);
            clazz.addImportedType(recordType);
            clazz.setSuperClass(superServiceImplName + "<" + simpleMapperName + "," + modelName + ">");
        } else {
            String daoFieldType = introspectedTable.getMyBatis3JavaMapperType();
            String daoFieldName = firstCharToLowCase(daoFieldType.substring(daoFieldType.lastIndexOf(".") + 1));
            //描述类的成员属性
            Field daoField = new Field(daoFieldName, new FullyQualifiedJavaType(daoFieldType));
            clazz.addImportedType(new FullyQualifiedJavaType(daoFieldType));
            clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
            //描述成员属性 的注解
            daoField.addAnnotation("@Autowired");
            //描述成员属性修饰符
            daoField.setVisibility(JavaVisibility.PRIVATE);
            clazz.addField(daoField);
        }
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        clazz.addAnnotation("@Service");

        GeneratedJavaFile gjf2 = new GeneratedJavaFile(clazz, targetProject, context.getJavaFormatter());
        return gjf2;
    }


    /**
     * 生成controller类
     *
     * @param introspectedTable
     * @return
     */
    private GeneratedJavaFile generateController(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType controller = new FullyQualifiedJavaType(controllerName);
        TopLevelClass clazz = new TopLevelClass(controller);
        //描述类的作用域修饰符
        clazz.setVisibility(JavaVisibility.PUBLIC);

        clazz.addJavaDocLine("/**");
        clazz.addJavaDocLine(" * " + introspectedTable.getRemarks() + " 控制器");
        if (StringUtils.hasText(author)) {
            clazz.addJavaDocLine(" * @author " + author);
        }
        clazz.addJavaDocLine(" * @since " + getDateString());
        clazz.addJavaDocLine(" */");

        //添加@Controller注解，并引入相应的类
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        clazz.addAnnotation("@RestController");
        //添加@RequestMapping注解，并引入相应的类
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        clazz.addAnnotation("@RequestMapping(\"/" + firstCharToLowCase(modelName) + "\")");
        //添加@Api注解，并引入相应的类
        clazz.addImportedType(new FullyQualifiedJavaType("io.swagger.v3.oas.annotations.tags.Tag"));
        clazz.addAnnotation("@Tag(name = \"" + introspectedTable.getRemarks() + "\", description = \"" + introspectedTable.getRemarks() + "\")");

        //引入controller的父类和model，并添加泛型
        if (stringHasValue(superController)) {
            clazz.addImportedType(superController);
            clazz.addImportedType(recordType);
            FullyQualifiedJavaType superInterfac = new FullyQualifiedJavaType(superController + "<" + modelName + ">");
            clazz.addSuperInterface(superInterfac);
        }

        //引入Service
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(serviceName);
        clazz.addImportedType(service);

        //添加Service成员变量
        String serviceFieldName = firstCharToLowCase(serviceName.substring(serviceName.lastIndexOf(".") + 1));
        Field daoField = new Field(serviceFieldName, new FullyQualifiedJavaType(serviceName));
        clazz.addImportedType(new FullyQualifiedJavaType(serviceName));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        //描述成员属性 的注解
        daoField.addAnnotation("@Autowired");
        //描述成员属性修饰符
        daoField.setVisibility(JavaVisibility.PRIVATE);
        clazz.addField(daoField);

        GeneratedJavaFile gjf2 = new GeneratedJavaFile(clazz, targetProject, context.getJavaFormatter());
        return gjf2;
    }

    private String firstCharToLowCase(String str) {
        char[] chars = new char[1];
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            return str.replaceFirst(temp, temp.toLowerCase());
        }
        return str;
    }

    protected String getDateString() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    protected String getSimpleClassName(String className) {
        return className.substring(className.lastIndexOf(".") + 1);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        recordType = introspectedTable.getBaseRecordType();
        mapperName = interfaze.getType().getFullyQualifiedName();
        String modelName = recordType.substring(recordType.lastIndexOf(".") + 1);

        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * " + introspectedTable.getRemarks() + " Mapper 接口");
        if (StringUtils.hasText(author)) {
            interfaze.addJavaDocLine(" * @author " + author);
        }
        interfaze.addJavaDocLine(" * @since " + getDateString());
        interfaze.addJavaDocLine(" */");
        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        interfaze.addAnnotation("@Repository");
        interfaze.addImportedType(new FullyQualifiedJavaType(superDaoInterface));
        interfaze.addImportedType(new FullyQualifiedJavaType(recordType));
        interfaze.addSuperInterface(new FullyQualifiedJavaType(getSimpleClassName(superDaoInterface) + "<" + modelName + ">"));
        return super.clientGenerated(interfaze, introspectedTable);
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return !StringUtils.hasText(superDaoInterface);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (hasLombok) {
            // 添加domain的import
            topLevelClass.addImportedType("lombok.Data");

            // 添加domain的注解
            topLevelClass.addAnnotation("@Data");
        }
        topLevelClass.addJavaDocLine("/**");

        String remarks = introspectedTable.getRemarks();
        topLevelClass.addImportedType(new FullyQualifiedJavaType("io.swagger.v3.oas.annotations.media.Schema"));
        if (StringUtility.stringHasValue(remarks)) {
            topLevelClass.addAnnotation("@Schema(name =\"" + remarks + "\")");
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" * ").append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @author ").append(getProperties().getProperty("user.name"));
        topLevelClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @date ");
        sb.append(getDateString());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        field.addJavaDocLine("/**");
        String remarks = introspectedColumn.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            field.addAnnotation("@Schema(name =\"" + remarks + "\")");
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);
            }
        }
        field.addJavaDocLine(" */");
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 不生成getter
        return !hasLombok;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 不生成setter
        return !hasLombok;
    }
}