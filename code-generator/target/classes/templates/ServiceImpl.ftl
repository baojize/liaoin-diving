package ${packageName}.service.impl;

import ${packageName}.common.PageBean;
import ${packageName}.dao.${modelName}Repository;
import ${packageName}.entity.${modelName};
import ${packageName}.service.${modelName}Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ${modelName}ServiceImpl implements ${modelName}Service {

    @Resource
    private ${modelName}Repository ${fieldName}Repository;

    @Override
    public void insert(${modelName} ${fieldName}) {
        ${fieldName}.setIsDelete("0");
        ${fieldName}Repository.save(${fieldName});
    }

    @Override
    public void update(${modelName} ${fieldName}) {
        ${fieldName}.setIsDelete(null);
        ${fieldName}Repository.save(${fieldName});
    }

    @Override
    public void delete(${primaryKey.columnType}[] ${primaryKey.changeColumnName}s) {
        List<${modelName}> ${fieldName}List = new ArrayList<>();
        for (${primaryKey.columnType} ${primaryKey.changeColumnName} : ${primaryKey.changeColumnName}s) {
            ${modelName} ${fieldName} = ${fieldName}Repository.findOne(${primaryKey.changeColumnName});
            if (${fieldName} == null) {
                continue;
            }
            ${fieldName}.setIsDelete("1");
            ${fieldName}List.add(${fieldName});
        }
        ${fieldName}Repository.save(${fieldName}List);
    }

    @Override
    public ${modelName} findOne(${primaryKey.columnType} ${primaryKey.changeColumnName}) {
        return ${fieldName}Repository.findOne(${primaryKey.changeColumnName});
    }

    @Override
    public PageBean<${modelName}> pageQuery(Pageable pageable, ${modelName} ${fieldName}) {
        // 1.查询条件
        Specification<${modelName}> specification = new Specification<${modelName}>() {
            @Override
            public Predicate toPredicate(Root<${modelName}> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (${fieldName} != null) {
<#list columnClassList as columnClass>
<#if columnClass.columnType=="String">
                    if ((${fieldName}.get${columnClass.changeColumnName?cap_first}() != null) && (!${fieldName}.get${columnClass.changeColumnName?cap_first}().trim().equals(""))) {
                        list.add(cb.like(root.get("${columnClass.changeColumnName}").as(${columnClass.columnType}.class), "%" + ${fieldName}.get${columnClass.changeColumnName?cap_first}() + "%"));
                    }
<#else>
                    if (${fieldName}.get${columnClass.changeColumnName?cap_first}() != null) {
                        list.add(cb.equal(root.get("${columnClass.changeColumnName}").as(${columnClass.columnType}.class), ${fieldName}.get${columnClass.changeColumnName?cap_first}()));
                    }
</#if>
</#list>
                }
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class),"1"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<${modelName}> page = ${fieldName}Repository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<${modelName}> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }
}

