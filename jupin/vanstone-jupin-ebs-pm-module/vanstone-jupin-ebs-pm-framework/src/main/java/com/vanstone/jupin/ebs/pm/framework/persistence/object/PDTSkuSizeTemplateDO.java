package com.vanstone.jupin.ebs.pm.framework.persistence.object;

public class PDTSkuSizeTemplateDO {
    private Integer id;

    private String templateName;

    private Integer systemable;

    private Integer waistlineable;

    private Integer weightable;

    private Integer hipable;

    private Integer chestable;

    private Integer heightable;

    private Integer shoulderable;

    private String templateContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getSystemable() {
        return systemable;
    }

    public void setSystemable(Integer systemable) {
        this.systemable = systemable;
    }

    public Integer getWaistlineable() {
        return waistlineable;
    }

    public void setWaistlineable(Integer waistlineable) {
        this.waistlineable = waistlineable;
    }

    public Integer getWeightable() {
        return weightable;
    }

    public void setWeightable(Integer weightable) {
        this.weightable = weightable;
    }

    public Integer getHipable() {
        return hipable;
    }

    public void setHipable(Integer hipable) {
        this.hipable = hipable;
    }

    public Integer getChestable() {
        return chestable;
    }

    public void setChestable(Integer chestable) {
        this.chestable = chestable;
    }

    public Integer getHeightable() {
        return heightable;
    }

    public void setHeightable(Integer heightable) {
        this.heightable = heightable;
    }

    public Integer getShoulderable() {
        return shoulderable;
    }

    public void setShoulderable(Integer shoulderable) {
        this.shoulderable = shoulderable;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }
}