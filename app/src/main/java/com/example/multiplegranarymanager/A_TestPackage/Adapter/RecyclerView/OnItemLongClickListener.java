package com.example.multiplegranarymanager.A_TestPackage.Adapter.RecyclerView;
/**
 * @author Created by Even on 2019/2/21
 * Emial: emailtopan@163.com
 * recyclerView 长按事件
 */
public interface OnItemLongClickListener<T> {
    void onItemLongClick(BaseViewHolder holder, T item, int position);
}

