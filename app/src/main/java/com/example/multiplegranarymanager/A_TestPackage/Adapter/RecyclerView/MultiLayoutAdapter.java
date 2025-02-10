package com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView;
import java.util.List;

/**
 * @author Created by Even on 2019/2/21
 * Emial: emailtopan@163.com
 * 多布局Adapter
 */
public abstract class MultiLayoutAdapter<T> extends BaseRecyclerAdapter<T> {
    /**
     * layoutId的顺序必须和ViewType的顺序相同，而且从零开始
     *
     * @param mDataList
     * @param layoutIds
     */
    public MultiLayoutAdapter(List mDataList, int[] layoutIds) {
        super(mDataList, layoutIds);
    }

    @Override
    protected void covert(BaseViewHolder holder, T item, int position) {
        coverts(holder, item, position, getItemViewType(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }

    /**
     * 获取当前View的type，根据type值得不同，加载不同得布局
     *
     * @param position
     * @return itemType
     */
    protected abstract int getItemType(int position);

    protected abstract void coverts(BaseViewHolder holder, T item, int position, int itemType);

}
