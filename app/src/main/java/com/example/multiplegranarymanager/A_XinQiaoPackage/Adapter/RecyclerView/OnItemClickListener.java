package com.example.multiplegranarymanager.A_XinQiaoPackage.Adapter.RecyclerView;

/**
 * @author Created by Even on 2019/2/21
 * Emial: emailtopan@163.com
 * recyclerView单击事件监听
 */
public interface OnItemClickListener<T> {
    void onItemClick(BaseViewHolder holder, T item, int position);
}

