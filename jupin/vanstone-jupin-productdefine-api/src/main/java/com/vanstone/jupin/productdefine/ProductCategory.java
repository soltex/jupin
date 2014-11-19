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
	/** 全部商品实例属性 */
	private Collection<Attr4Enum> allSkuAttributes = new ArrayList<Attr4Enum>();
	/** 旗下全部属性 */
	private Collection<AbstractAttribute> allCurrentAttributes = new ArrayList<AbstractAttribute>();
	/** 旗下商品属性 */
	private Collection<AbstractAttribute> currentProductAttributes = new ArrayList<AbstractAttribute>();
	/** 旗下商品实例属性 */
	private Collection<Attr4Enum> currentSkuAttributes = new ArrayList<Attr4Enum>();
	
	@Override
	public String getId() {
		return null;
	}
	
}
