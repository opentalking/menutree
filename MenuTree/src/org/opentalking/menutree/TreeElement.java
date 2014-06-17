package org.opentalking.menutree;

import java.util.ArrayList;
import java.util.List;


import android.view.View.OnClickListener;

public class TreeElement {
	private int img_tree_space_n = R.drawable.tree_space_n;
	private int img_tree_space_y = R.drawable.tree_space_y;

	private String id;// 节点id
	private String caption;// 节点标题
	private String value;// 节点的值
	private int level;// 层级
	private TreeElement parent;// 该节点的父节点
	private boolean isHasChild;// 是否有子节点
	private boolean isExpanded;// 是否处于展开
	private ArrayList<TreeElement> childList;// 子节点数组
	private boolean isLastSibling;// 是否是同级节点的最后一个
	private ArrayList<Integer> spaceList;// 组织结构线条数组
	private int position;// 在listview中所处的位置
	private OnClickListener captionOnClickListener;

	public TreeElement() {
		super();
	}

	public TreeElement(String id, String caption, String value,
			Boolean isHasChild, Boolean isExpanded,
			OnClickListener captionOnClickListener) {
		this.id = id;
		this.caption = caption;
		this.value = value;
		this.parent = null;
		this.level = 0;
		this.isHasChild = isHasChild;
		this.isExpanded = isExpanded;
		if (isHasChild) {
			this.childList = new ArrayList<TreeElement>();
		}
		this.isLastSibling = false;
		this.setSpaceList(new ArrayList<Integer>());
		this.position = 0;
		this.setCaptionOnClickListener(captionOnClickListener);
	}

	// 添加子节点
	public void addChild(TreeElement treeElement) {
		treeElement.parent = this;
		if (treeElement.getParent() != null
				&& treeElement.getParent().getChildList().size() > 0) {// 将之前的同级节点的置为非最后一个节点
			List<TreeElement> siblingList = treeElement.getParent()
					.getChildList();
			treeElement.getParent().getChildList().get(siblingList.size() - 1)
					.setLastSibling(false);
		}
		this.childList.add(treeElement);
		this.isHasChild = true;
		treeElement.level = this.level + 1;
		treeElement.isLastSibling = true;
		if (this.level > 0) {
			treeElement.getSpaceList().addAll(this.getSpaceList());
			if (this.isLastSibling()) {
				treeElement.getSpaceList().add(img_tree_space_n);
			} else {
				treeElement.getSpaceList().add(img_tree_space_y);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TreeElement getParent() {
		return parent;
	}

	public void setParent(TreeElement parent) {
		this.parent = parent;
	}

	public boolean isHasChild() {
		return isHasChild;
	}

	public void setHasChild(boolean isHasChild) {
		this.isHasChild = isHasChild;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public ArrayList<TreeElement> getChildList() {
		return childList;
	}

	public void setChildList(ArrayList<TreeElement> childList) {
		this.childList = childList;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLastSibling() {
		return isLastSibling;
	}

	public void setLastSibling(boolean isLastSibling) {
		this.isLastSibling = isLastSibling;
	}

	public ArrayList<Integer> getSpaceList() {
		return spaceList;
	}

	public void setSpaceList(ArrayList<Integer> spaceList) {
		this.spaceList = spaceList;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public OnClickListener getCaptionOnClickListener() {
		return captionOnClickListener;
	}

	public void setCaptionOnClickListener(OnClickListener captionOnClickListener) {
		this.captionOnClickListener = captionOnClickListener;
	}

}