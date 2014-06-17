package org.opentalking.menutree;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TreeViewAdapter extends ArrayAdapter<TreeElement> {
	
	private int img_leaf = R.drawable.icon_user;// 没有子节点的节点图标
	private int img_expand = R.drawable.outline_list_expand;// 展开的图标
	private int img_collapse = R.drawable.outline_list_collapse;// 收缩的图标
	private int img_tree_space_1 = R.drawable.tree_space_1;// 连接线
	private int img_tree_space_2 = R.drawable.tree_space_2;

	private Context context;
	private LayoutInflater mInflater;
	private ArrayList<TreeElement> treeElementList;
	private int viewResourceId;

	public TreeViewAdapter(Context context, int viewResourceId,
			ArrayList<TreeElement> objects) {
		super(context, viewResourceId, objects);
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.treeElementList = objects;
		this.viewResourceId = viewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		convertView = mInflater.inflate(viewResourceId, null);
		holder = new ViewHolder();
		holder.caption = (TextView) convertView.findViewById(R.id.caption);
		holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		holder.space = (LinearLayout) convertView.findViewById(R.id.space);
		convertView.setTag(holder);
		TreeElement treeElement = treeElementList.get(position);

		int level = treeElement.getLevel();
		if (level == 0) {// 根节点

		} else {
			ArrayList<Integer> spaceList = treeElement.getSpaceList();

			// 绘制前面的组织架构线条
			for (int i = 0; i < spaceList.size(); i++) {
				ImageView img = new ImageView(context);
				img.setImageResource(spaceList.get(i));
				holder.space.addView(img);
			}
			ImageView img = new ImageView(context);
			// 节点图标
			if (treeElement.isLastSibling()) {
				img.setImageResource(img_tree_space_2);
			} else {
				img.setImageResource(img_tree_space_1);
			}

			holder.space.addView(img);
		}
		if (treeElement.isHasChild()) {
			if (treeElement.isExpanded()) {
				holder.icon.setImageResource(img_expand);
			} else {
				holder.icon.setImageResource(img_collapse);
			}
			holder.icon.setOnClickListener(new TreeElementIconClickListener(
					context, treeElementList, this, treeElement.getPosition()));
		} else {
			holder.icon.setImageResource(img_leaf);
		}
		holder.caption.setText(treeElement.getCaption());// 设置标题
		if (treeElement.getCaptionOnClickListener() != null) {// 设置文字的点击事件
			holder.caption.setTag(treeElement.getValue());
			holder.caption.setOnClickListener(treeElement
					.getCaptionOnClickListener());

		}
		return convertView;
	}

	class ViewHolder {
		LinearLayout space;
		TextView caption;
		ImageView icon;
	}

	public static class TreeElementIconClickListener implements OnClickListener {
		private Context context;
		private ArrayList<TreeElement> treeElementList;
		private TreeViewAdapter treeViewAdapter;
		private int position;

		public TreeElementIconClickListener(Context mContext,
				ArrayList<TreeElement> mTreeElementList,
				TreeViewAdapter mTreeViewAdapter, int position) {
			this.context = mContext;
			this.treeElementList = mTreeElementList;
			this.treeViewAdapter = mTreeViewAdapter;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			
			Log.d("onClick","点击的position:" + position);
			
			if (!treeElementList.get(position).isHasChild()) {
				Toast.makeText(context,
						treeElementList.get(position).getCaption(),
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (treeElementList.get(position).isExpanded()) {
				treeElementList.get(position).setExpanded(false);
				TreeElement element = treeElementList.get(position);
				ArrayList<TreeElement> temp = new ArrayList<TreeElement>();

				for (int i = position + 1; i < treeElementList.size(); i++) {
					if (element.getLevel() >= treeElementList.get(i).getLevel()) {
						break;
					}
					temp.add(treeElementList.get(i));
				}

				treeElementList.removeAll(temp);
				for (int i = position + 1; i < treeElementList.size(); i++) {
					treeElementList.get(i).setPosition(i);
				}

				treeViewAdapter.notifyDataSetChanged();
			} else {
				TreeElement obj = treeElementList.get(position);
				obj.setExpanded(true);
				int level = obj.getLevel();
				int nextLevel = level + 1;

				ArrayList<TreeElement> tempList = obj.getChildList();

				for (int i = 0; i < tempList.size(); i++) {
					TreeElement element = tempList.get(i);
					element.setLevel(nextLevel);
					element.setExpanded(false);
					treeElementList.add(position + i + 1, element);
				}
				for (int i = position + 1; i < treeElementList.size(); i++) {
					treeElementList.get(i).setPosition(i);
				}
				treeViewAdapter.notifyDataSetChanged();
			}

		}
	}
}