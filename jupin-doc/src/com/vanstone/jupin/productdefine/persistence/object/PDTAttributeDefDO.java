package com.vanstone.jupin.productdefine.persistence.object;

public class PDTAttributeDefDO {
    private Integer id;

    private String attributeName;

    private Integer attributeType;

    private Integer searchable;

    private Integer attributeScope;

    private Integer listshowable;

    private String attributeDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(Integer attributeType) {
        this.attributeType = attributeType;
    }

    public Integer getSearchable() {
        return searchable;
    }

    public void setSearchable(Integer searchable) {
        this.searchable = searchable;
    }

    public Integer getAttributeScope() {
        return attributeScope;
    }

    public void setAttributeScope(Integer attributeScope) {
        this.attributeScope = attributeScope;
    }

    public Integer getListshowable() {
        return listshowable;
    }

    public void setListshowable(Integer listshowable) {
        this.listshowable = listshowable;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }
}