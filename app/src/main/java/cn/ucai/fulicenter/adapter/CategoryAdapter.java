package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageLoader;


/**
 * Created by mac-yk on 2016/10/19.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<Integer> groupList;
    ArrayList<ArrayList<Integer>> childList;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<CategoryChildBean> mChildList;


    public CategoryAdapter(Context context, ArrayList<CategoryChildBean> mChileList, ArrayList<CategoryGroupBean> mGroupList) {
        this.context = context;
        this.mChildList = mChileList;
        this.mGroupList = mGroupList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.item_categoryGroup, null);
            GroupViewHolder holder = new GroupViewHolder(convertView);
            CategoryGroupBean categoryGroupBean=mGroupList.get(groupPosition);
            ImageLoader.downloadImg(context,holder.ivGroup,categoryGroupBean.getImageUrl());
            holder.textView.setText(categoryGroupBean.getName());

        if (isExpanded) {
            holder.ivExpand.setImageResource(R.mipmap.expand_off);
        } else {
            holder.ivExpand.setImageResource(R.mipmap.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.item_categoryChild, null);
            ChildViewHolder holder = new ChildViewHolder(convertView);
            CategoryChildBean childBean= mChildList.get(childPosition);
            ImageLoader.downloadImg(context,holder.ivCategoryChild,childBean.getImageUrl());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        @BindView(R.id.ivGroup)
        ImageView ivGroup;
        @BindView(R.id.ivExpand)
        ImageView ivExpand;
        @BindView(R.id.tv_category_title)
        TextView textView;

        GroupViewHolder(View v) {
            super();
            ButterKnife.bind(v);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_category_child)
        ImageView ivCategoryChild;
        ChildViewHolder(View v){
            super();
            ButterKnife.bind(v);
        }
    }
}
