package com.vanstone.jupin.productdefine.persistence.object;

public class PDTAttributeEnumvalueDO {
    private Integer id;

    private Integer attributeDefId;

    private Integer sort;

    private String objectvalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttributeDefId() {
        return attributeDefId;
    }

    public void setAttributeDefId(Integer attributeDefId) {
        this.attributeDefId = attributeDefId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getObjectvalue() {
        return objectvalue;
    }

    public void setObjectvalue(String objectvalue) {
        this.objectvalue = objectvalue;
    }
}