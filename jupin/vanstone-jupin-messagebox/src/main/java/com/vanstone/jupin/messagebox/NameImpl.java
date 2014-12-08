/**
 * 
 */
package com.vanstone.jupin.messagebox;

/**
 * 信箱名称实现
 * @author shipeng
 */
public class NameImpl implements Name {

	private String name;
	private String group;
	
	public NameImpl() {
		this(Constants.DEFAULT_MESSGEBOX_GROUP, Constants.DEFAULT_MESSGEBOX_NAME);
	}
	
	public NameImpl(String name, String group) {
		if (name == null || name.equals("") || group == null || group.equals("")) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.group = group;
	}
	
	@Override
	public String getKey() {
		StringBuffer sb = new StringBuffer();
		sb.append(Constants.MESSAGE_BOX_PREFIX);
		sb.append(Constants.GROUP_NAME_PREFIX);
		sb.append(this.getMessageBoxGroup()).append(":");
		sb.append(Constants.NAME_PREFIX);
		sb.append(this.getMessageBoxName());
		return sb.toString();
	}
	
	@Override
	public String getMessageBoxName() {
		return this.name;
	}
	
	@Override
	public String getMessageBoxGroup() {
		return this.group;
	}
	
}
