package ${packageName}.service;

import ${packageName}.common.PageBean;
import ${packageName}.entity.${modelName};
import org.springframework.data.domain.Pageable;

public interface ${modelName}Service {
    void insert(${modelName} ${fieldName});

    void update(${modelName} ${fieldName});

    void delete(${primaryKey.columnType}[] ${primaryKey.changeColumnName}s);

    ${modelName} findOne(${primaryKey.columnType} ${primaryKey.changeColumnName});

    PageBean<${modelName}> pageQuery(Pageable pageable, ${modelName} ${fieldName});
}

