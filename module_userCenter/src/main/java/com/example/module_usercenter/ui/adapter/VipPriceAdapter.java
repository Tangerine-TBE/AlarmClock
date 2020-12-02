package com.example.module_usercenter.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_usercenter.R;
import com.example.module_usercenter.bean.PriceBean;

import java.util.List;

public class VipPriceAdapter extends RecyclerView.Adapter<VipPriceAdapter.MyHolder> {

    private List<PriceBean> mPriceBeanList;
    private OnItemClickListener mOnItemClickListener=null;
    private int mPosition;

    public VipPriceAdapter(List<PriceBean> list) {
        this.mPriceBeanList=list;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_buy_container, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.setItemData(mPriceBeanList.get(position),position);
        if (mPosition == position) {
            holder.scb_checkbox.setImageResource(R.mipmap.icon_ck_select);
        } else {
            holder.scb_checkbox.setImageResource(R.mipmap.icon_ck_normal);
        }

    }

    @Override
    public int getItemCount() {
        return mPriceBeanList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_price_title,tv_price;
        private ImageView scb_checkbox;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_price_title=itemView.findViewById(R.id.tv_price_title);
            tv_price=itemView.findViewById(R.id.tv_price);
            scb_checkbox=itemView.findViewById(R.id.scb_checkbox);
        }

        public void setItemData(PriceBean bean, int position) {
            tv_price_title.setText(bean.getTitle());
            tv_price.setText(bean.getPrice()+"元");

            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(bean);
                    mPosition=position;
                    notifyDataSetChanged();
                }
            });

        }
    }




    public void  setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener {
        void onItemClick(PriceBean bean);
    }

}
