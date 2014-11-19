package com.vanstone.jupin.framework.productdef.persistence.object;

public class PDTCategoryDO {
    private Integer id;

    private String categoryName;

    private String description;

    private String categoryBindPage;

    private String coverFileId;

    private Integer coverFileWidth;

    private Integer coverFileHeight;

    private String coverFileExt;

    private Integer parentId;

    private Integer sort;

    private Integer leafable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryBindPage() {
        return categoryBindPage;
    }

    public void setCategoryBindPage(String categoryBindPage) {
        this.categoryBindPage = categoryBindPage;
    }

    public String getCoverFileId() {
        return coverFileId;
    }

    public void setCoverFileId(String coverFileId) {
        this.coverFileId = coverFileId;
    }

    public Integer getCoverFileWidth() {
		return coverFileWidth;
	}

	public void setCoverFileWidth(Integer coverFileWidth) {
		this.coverFileWidth = coverFileWidth;
	}

	public Integer getCoverFileHeight() {
        return coverFileHeight;
    }

    public void setCoverFileHeight(Integer coverFileHeight) {
        this.coverFileHeight = coverFileHeight;
    }

    public String getCoverFileExt() {
        return coverFileExt;
    }

    public void setCoverFileExt(String coverFileExt) {
        this.coverFileExt = coverFileExt;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLeafable() {
        return leafable;
    }

    public void setLeafable(Integer leafable) {
        this.leafable = leafable;
    }
}