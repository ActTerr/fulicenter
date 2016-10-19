package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;


/**
 * Created by mac-yk on 2016/10/19.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<Integer> groupList;
    ArrayList<ArrayList<Integer>> childList;



    public CategoryAdapter(Context context, ArrayList<Integer> groupList, ArrayList<ArrayList<Integer>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
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
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_category, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            convertView.getTag();
        }
        holder.ivGroup.setImageResource(groupList.get(groupPosition));
        if (isExpanded) {
            holder.ivExpand.setImageResource(R.mipmap.expand_off);
        } else {
            holder.ivExpand.setImageResource(R.mipmap.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.item_categorychild, null);
            ChildViewHolder holder = new ChildViewHolder(convertView);
            holder.ivCategoryChild.setImageResource(childList.get(groupPosition).get(childPosition));
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
