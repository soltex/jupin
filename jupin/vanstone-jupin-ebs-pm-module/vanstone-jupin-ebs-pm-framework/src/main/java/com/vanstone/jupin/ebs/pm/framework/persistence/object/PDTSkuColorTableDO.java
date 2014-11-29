package com.vanstone.jupin.ebs.pm.framework.persistence.object;

public class PDTSkuColorTableDO {
    private Integer id;

    private String colorName;

    private String colorRgb;

    private String colorCss;

    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorRgb() {
        return colorRgb;
    }

    public void setColorRgb(String colorRgb) {
        this.colorRgb = colorRgb;
    }

    public String getColorCss() {
        return colorCss;
    }

    public void setColorCss(String colorCss) {
        this.colorCss = colorCss;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}