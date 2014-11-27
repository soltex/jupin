package com.vanstone.jupin.productdefine.persistence.object;

public class PDTAttributeEnumvalueDO {
    private Integer id;

    private Integer attributeDefId;

    private Integer sort;

    private Integer valueState;

    private String objecttext;

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

    public Integer getValueState() {
        return valueState;
    }

    public void setValueState(Integer valueState) {
        this.valueState = valueState;
    }

    public String getObjecttext() {
        return objecttext;
    }

    public void setObjecttext(String objecttext) {
        this.objecttext = objecttext;
    }
}