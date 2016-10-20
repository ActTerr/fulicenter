package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;


/**
 * Created by mac-yk on 2016/10/19.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;

    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    int index;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> mGroupList, ArrayList<ArrayList<CategoryChildBean>> mChildList) {
        this.context = context;
        this.mChildList = new ArrayList<>();
        this.mGroupList = new ArrayList<>();
        this.mGroupList.addAll(mGroupList);
        this.mChildList.addAll(mChildList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList == null ? 0 : mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).get(childPosition) : null;
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
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
            CategoryGroupBean categoryGroupBean = (CategoryGroupBean) getGroup(groupPosition);
            if (categoryGroupBean != null) {
                ImageLoader.downloadImg(context, holder.ivGroup, categoryGroupBean.getImageUrl());
                holder.tvCategoryTitle.setText(categoryGroupBean.getName());
                if (isExpanded) {
                    holder.ivExpand.setImageResource(R.mipmap.expand_off);
                } else {
                    holder.ivExpand.setImageResource(R.mipmap.expand_on);
                }
            }

        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
            final CategoryChildBean childBean = (CategoryChildBean) getChild(groupPosition, childPosition);
            if (childBean != null) {
                ImageLoader.downloadImg(context, holder.ivCategoryChild, childBean.getImageUrl());
                holder.tvCategoryChild.setText(childBean.getName());
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<CategoryChildBean>list =mChildList.get(groupPosition);
                        String groupName=mGroupList.get(groupPosition).getName();
                        MFGT.gotoCategoryChildActivity(context,childBean.getId(),list,groupName);
                    }
                });
            }
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void initDataGroup(ArrayList<CategoryGroupBean> list) {
        if (mGroupList != null) {
            mGroupList.clear();
        }
        mGroupList.addAll(list);
        notifyDataSetChanged();

    }

    public void initDataChild(ArrayList<ArrayList<CategoryChildBean>> list) {
        if (mChildList != null) {
            mChildList.clear();
        }
        mChildList.addAll(list);
        Log.e("adapter", list.toString());
        notifyDataSetChanged();
        Log.e("init", "success");
    }




     class ChildViewHolder {
        @BindView(R.id.iv_category_child)
        ImageView ivCategoryChild;
        @BindView(R.id.tv_category_child)
        TextView tvCategoryChild;
        @BindView(R.id.btn_category_child)
        RelativeLayout btn;

        ChildViewHolder(View v) {
            super();
            ButterKnife.bind(this, v);
        }

    }

    static class GroupViewHolder {
        @BindView(R.id.ivGroup)
        ImageView ivGroup;
        @BindView(R.id.tv_category_title)
        TextView tvCategoryTitle;
        @BindView(R.id.ivExpand)
        ImageView ivExpand;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
