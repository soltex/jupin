package com.vanstone.jupin.ebs.pm.framework.persistence.object;

public class PDTAttributeDefDO {
    private Integer id;

    private String attributeName;

    private Integer attributeType;

    private Integer searchable;

    private Integer listshowable;

    private Integer multiselectable;

    private Integer existProduct;

    private Integer requiredable;

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

    public Integer getListshowable() {
        return listshowable;
    }

    public void setListshowable(Integer listshowable) {
        this.listshowable = listshowable;
    }

    public Integer getMultiselectable() {
        return multiselectable;
    }

    public void setMultiselectable(Integer multiselectable) {
        this.multiselectable = multiselectable;
    }

    public Integer getExistProduct() {
        return existProduct;
    }

    public void setExistProduct(Integer existProduct) {
        this.existProduct = existProduct;
    }

    public Integer getRequiredable() {
        return requiredable;
    }

    public void setRequiredable(Integer requiredable) {
        this.requiredable = requiredable;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }
}