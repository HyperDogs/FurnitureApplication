package com.example.ton.furnitureapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import resource.BitmapManager;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<AbstractModel> modelList;

    private OnItemClickListener mItemClickListener;


    public RecyclerViewAdapter(Context context, ArrayList<AbstractModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<AbstractModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        try {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {

                final AbstractModel model = getItem(position);
                ViewHolder genericViewHolder = (ViewHolder) holder;
                File file = new File(Environment.getExternalStorageDirectory()+File.separator + "DCIM" + File.separator + "Camera" + File.separator + model.getImage());
                Bitmap bitmap = BitmapManager.decode(file.getPath(),300,350);
                //Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+File.separator + "DCIM" + File.separator + "Camera" + File.separator + model.getImage());
                if (bitmap != null) {
                    Picasso.with(mContext).load(Uri.fromFile(file)).fit().centerCrop().into(genericViewHolder.imgUser);
                }else {
                    Picasso.with(mContext).load(R.drawable.camera2).fit().centerCrop().into(genericViewHolder.imgUser);
                }
                genericViewHolder.dateV.setText(model.getDate());
                genericViewHolder.cusNoV.setText(model.getCusNo());
                genericViewHolder.itemV.setText(model.getItemNo());
                genericViewHolder.colorV.setText(model.getColorNo());
                genericViewHolder.coV.setText(model.getCoNo());
                genericViewHolder.inspV.setText(model.getInspector());
                genericViewHolder.mailV.setText(model.getMail());

                if(model.getMail().equals("Sent")){
                    genericViewHolder.mailV.setTextColor(Color.parseColor("#52b071"));
                } else {
                    genericViewHolder.mailV.setTextColor(Color.parseColor("#C3333A"));
                }
        }
        }catch (Exception e){
            throw e;
        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private AbstractModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, AbstractModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView dateV,cusNoV,itemV,colorV,coV,inspV,mailV;


        // @BindView(R.id.img_user)
        // ImageView imgUser;
        // @BindView(R.id.item_txt_title)
        // TextView itemTxtTitle;
        // @BindView(R.id.item_txt_message)
        // TextView itemTxtMessage;
        // @BindView(R.id.radio_list)
        // RadioButton itemTxtMessage;
        // @BindView(R.id.check_list)
        // CheckBox itemCheckList;
        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.imgUser = (ImageView) itemView.findViewById(R.id.img_user);
            this.dateV = (TextView)itemView.findViewById(R.id.dateV);
            this.cusNoV = (TextView)itemView.findViewById(R.id.cusNoV);
            this.itemV = (TextView)itemView.findViewById(R.id.itemV);
            this.colorV = (TextView)itemView.findViewById(R.id.colorV);
            this.coV = (TextView)itemView.findViewById(R.id.coV);
            this.inspV = (TextView)itemView.findViewById(R.id.inspV);
            this.mailV = (TextView)itemView.findViewById(R.id.mailV);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

