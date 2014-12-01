package com.vanstone.jupin.ecs.product.framework.persistence.object;

public class PDTBrandDO {
    private Integer id;

    private String brandName;

    private String brandNameEn;

    private String brandNameFirstLetter;

    private String brandNamePinyin;
    
    private String logoFileId;

    private Integer logoWidth;

    private Integer logoHeight;

    private String logoFileExt;

    private Integer systemable;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandNameEn() {
        return brandNameEn;
    }

    public void setBrandNameEn(String brandNameEn) {
        this.brandNameEn = brandNameEn;
    }

    public String getBrandNameFirstLetter() {
        return brandNameFirstLetter;
    }

    public void setBrandNameFirstLetter(String brandNameFirstLetter) {
        this.brandNameFirstLetter = brandNameFirstLetter;
    }

    public String getLogoFileId() {
        return logoFileId;
    }

    public void setLogoFileId(String logoFileId) {
        this.logoFileId = logoFileId;
    }

    public Integer getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(Integer logoWidth) {
        this.logoWidth = logoWidth;
    }

    public Integer getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(Integer logoHeight) {
        this.logoHeight = logoHeight;
    }

    public String getLogoFileExt() {
        return logoFileExt;
    }

    public void setLogoFileExt(String logoFileExt) {
        this.logoFileExt = logoFileExt;
    }

    public Integer getSystemable() {
        return systemable;
    }

    public void setSystemable(Integer systemable) {
        this.systemable = systemable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getBrandNamePinyin() {
		return brandNamePinyin;
	}

	public void setBrandNamePinyin(String brandNamePinyin) {
		this.brandNamePinyin = brandNamePinyin;
	}
}