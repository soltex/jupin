/**
 * 
 */
package com.vanstone.jupin.productdefine;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;
import com.vanstone.jupin.productdefine.attr.Attr4Enum;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplate;

/**
 * 商品品类
 * @author shipeng
 */
public class ProductCategory extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = -310782740763204047L;
	
	/** 父产品类型 */
	private ProductCategory parentProductCategory;
	/** 自然id */
	private Integer id;
	/** 品类名称 */
	private String categoryName;
	/** 封面图片 */
	private ImageBean converImage;
	/** 品类描述 */
	private String description;
	/** 栏目绑定的URL */
	private String categoryBindPage;
	/** 排序 */
	private int sort = Constants.SYS_DEFAULT_SORT;
	/** 是否为叶子节点 */
	private boolean leafable = false;
	/** 栏目状态*/
	private CategoryState categoryState = CategoryState.Active;
	/**是否存在商品*/
	private boolean existProduct = false;
	/**是否存在颜色*/
	private boolean existColor=false;
	/**是否存在尺码*/
	private boolean existSize = false;
	/**选用的尺码模板*/
	private SizeTemplate sizeTemplate;
	/** 品类下的叶子节点 */
	private Collection<ProductCategory> leafProductCategories = new ArrayList<ProductCategory>();
	/** 品类下的一级孩子节点 */
	private Collection<ProductCategory> childProductCategories = new ArrayList<ProductCategory>();
	/** 所有孩子节点，包含叶子一级非叶子节点 */
	private Collection<ProductCategory> allChildProductCategories = new ArrayList<ProductCategory>();
	/** 品类路径 不包含ROOT节点 */
	private Collection<ProductCategory> productCategoryNodePath = new ArrayList<ProductCategory>();
	/** 用来检索用的属性 */
	private Collection<Attr4Enum> searchAttributes = new ArrayList<Attr4Enum>();
	/** 全部商品属性 */
	private Collection<AbstractAttribute> allAttributes = new ArrayList<AbstractAttribute>();
	/** 全部商品属性 */
	private Collection<AbstractAttribute> allProductAttributes = new ArrayList<AbstractAttribute>();
	/** 旗下全部属性 */
	private Collection<AbstractAttribute> allCurrentAttributes = new ArrayList<AbstractAttribute>();
	/** 旗下商品属性 */
	private Collection<AbstractAttribute> currentProductAttributes = new ArrayList<AbstractAttribute>();
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	public boolean isLeafable() {
		return leafable;
	}

	public void setLeafable(boolean leafable) {
		this.leafable = leafable;
	}
	
}
