package org.opentalking.menutree;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


public class MenuTreeFragment extends Fragment{

	public static MenuTreeFragment getInstance(Bundle args) {
		MenuTreeFragment newFragment = new MenuTreeFragment();
		if (args != null) {
			newFragment.setArguments(args);
		}
		return newFragment;
	}

	private ListView tree_listview;
	private TreeViewAdapter treeViewAdapter;
	private ArrayList<TreeElement> mRootList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_transrecheck_pre, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		tree_listview=(ListView) view.findViewById(R.id.tree_listview);
		mRootList = new ArrayList<TreeElement>();
		treeViewAdapter = new TreeViewAdapter(this.getActivity(), R.layout.atom_tree,
				mRootList);
		tree_listview.setAdapter(treeViewAdapter);
		
		
		OnClickListener myOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MenuTreeFragment.this.getActivity(), "" + v.getTag(), Toast.LENGTH_SHORT)
						.show();
			}
		};
		TreeElement treeElement1 = new TreeElement("node1", "资金资产信息", "1", true,
				false, myOnClickListener);
		TreeElement treeElement2 = new TreeElement("node2", "台帐", "2", true,
				false, myOnClickListener);
		TreeElement treeElement1_1 = new TreeElement("node11", "理财需求录入", "1_1",
				false, true, myOnClickListener);
		TreeElement treeElement1_2 = new TreeElement("node12", "资金找资产", "1_2",
				false, true, myOnClickListener);
		TreeElement treeElement1_3 = new TreeElement("node13", "资产找资金", "1_3",
				false, true, myOnClickListener);
		
		TreeElement treeElement2_1 = new TreeElement("node21", "台账补录",
				"2_1", true, true, myOnClickListener);
		TreeElement treeElement2_1_1 = new TreeElement("node211", "定存业务补录",
				"2_1_1", false, true, myOnClickListener);

		mRootList.add(treeElement1);
		mRootList.add(treeElement2);
		
		treeElement1.addChild(treeElement1_1);
		treeElement1.addChild(treeElement1_2);
		treeElement1.addChild(treeElement1_3);

		treeElement2.addChild(treeElement2_1);
		treeElement2_1.addChild(treeElement2_1_1);
		


		treeViewAdapter.notifyDataSetChanged();
//
//		// 展开根目录
//		if (rootElement.isExpanded()) {
//			TreeElementIconClickListener mtest = new TreeElementIconClickListener(
//					TransReCheckPreFragment.this.getActivity(), mRootList, treeViewAdapter, 0);
//			mtest.onClick(null);
//		}
//		tree_listview.performClick();
//		tree_listview.getAdapter().getView(0, null, tree_listview).performClick();


	}

}
