package com.example.ton.furnitureapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import resource.CreateFile;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;
    private Album album;
    private File file;
    private Uri uri;
    private int detailPickImage = 1003;
    private boolean onSwap = false, onRemove= false;
    private int firstSelected=-1;
    private ProgressDialog progressDialog;
    private Handler mHandler = new Handler();

    public void setOnSwap(boolean swap){
        onSwap = swap;
    }

    public void setOnRemove(boolean remove){
        onRemove= remove;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail, overflow;
        public ImageButton pickImg;
        public CheckBox removeChk;
        public LinearLayout holdingIcon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            pickImg = (ImageButton) view.findViewById(R.id.pickImg);
            removeChk = (CheckBox) view.findViewById(R.id.removeCheckbox);
            holdingIcon = (LinearLayout) view.findViewById(R.id.holdingIcon);
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        album = albumList.get(position);
        holder.title.setText(album.getName());


        //if(Album.DETAIL_BITMAP[position] != null) {
        if((Album.DETAIL_FILENAME[position] != null) && !Album.DETAIL_FILENAME[position].equals("")){
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "DCIM" + File.separator + "Camera" + File.separator + Album.DETAIL_FILENAME[position]);

            if (file.exists()) {
                Picasso.with(mContext).load(Uri.fromFile(file)).fit().centerCrop().into(holder.thumbnail);
            }
            //Picasso.with(mContext).load(Utility.getImageUri(mContext, Album.DETAIL_BITMAP[position])).noFade()
            //.fit().centerCrop().into(holder.thumbnail);
            holder.overflow.setVisibility(View.VISIBLE);

            if(onRemove){
                holder.removeChk.setVisibility(View.VISIBLE);
            } else {
                holder.removeChk.setVisibility(View.GONE);
            }
        } else {


            Picasso.with(mContext).load(album.getThumbnail()).noFade()
                    .fit().centerCrop().into(holder.thumbnail);
            holder.overflow.setVisibility(View.GONE);

            //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
        }

        if(Album.DETAIL_MEMO[position] != null){
            holder.title.setText(Album.DETAIL_MEMO[position]);
        } else {
            holder.title.setText("");
        }




        //กดที่Text
        if(onSwap){
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(firstSelected == -1) {
                        v.setAlpha((float) 0.3);
                        holder.thumbnail.setAlpha((float) 0.3);
                        holder.overflow.setAlpha((float) 0.3);
                        holder.holdingIcon.setVisibility(View.VISIBLE);
                        holder.pickImg.setVisibility(View.GONE);
                        firstSelected = position;
                    } else {
                        /*progressDialog = ProgressDialog.show(mContext, "",
                                "Moving...", true);

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {*/
                                if(firstSelected != position) {
                                    //Bitmap firstBMP = Album.DETAIL_BITMAP[firstSelected];
                                    //Bitmap lastBMP = Album.DETAIL_BITMAP[position];

                                    String firstMemo = Album.DETAIL_MEMO[firstSelected];
                                    String lastMemo = Album.DETAIL_MEMO[position];

                                    String firstFileName = Album.DETAIL_FILENAME[firstSelected];
                                    String lastFileName = Album.DETAIL_FILENAME[position];

                                    //Album.DETAIL_BITMAP[firstSelected] = lastBMP;
                                    //Album.DETAIL_BITMAP[position] = firstBMP;
                                    Album.DETAIL_MEMO[firstSelected] = lastMemo;
                                    Album.DETAIL_MEMO[position] = firstMemo;
                                    Album.DETAIL_FILENAME[firstSelected] = lastFileName;
                                    Album.DETAIL_FILENAME[position] = firstFileName;
                                }

                                firstSelected = -1;
                                v.setAlpha(1);
                                holder.thumbnail.setAlpha(1);
                                holder.overflow.setAlpha(1);
                                holder.holdingIcon.setVisibility(View.GONE);
                                holder.pickImg.setVisibility(View.VISIBLE);
                    //            progressDialog.dismiss();
                                notifyDataSetChanged();
                    //        }
                   //     }, 1000);
                    }
                }
            });

            //กดที่รูป
            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(firstSelected == -1) {
                        v.setAlpha((float) 0.3);
                        holder.thumbnail.setAlpha((float) 0.3);
                        holder.overflow.setAlpha((float) 0.3);
                        holder.holdingIcon.setVisibility(View.VISIBLE);
                        holder.pickImg.setVisibility(View.GONE);
                        firstSelected = position;
                    } else {

                        //progressDialog = ProgressDialog.show(mContext, "",
                         //       "Moving...", true);

                        //mHandler.postDelayed(new Runnable() {
                        //    @Override
                         //   public void run() {
                                if(firstSelected != position) {
                                    //Bitmap firstBMP = Album.DETAIL_BITMAP[firstSelected];
                                    //Bitmap lastBMP = Album.DETAIL_BITMAP[position];

                                    String firstMemo = Album.DETAIL_MEMO[firstSelected];
                                    String lastMemo = Album.DETAIL_MEMO[position];

                                    String firstFileName = Album.DETAIL_FILENAME[firstSelected];
                                    String lastFileName = Album.DETAIL_FILENAME[position];

                                    //Album.DETAIL_BITMAP[firstSelected] = lastBMP;
                                    //Album.DETAIL_BITMAP[position] = firstBMP;
                                    Album.DETAIL_MEMO[firstSelected] = lastMemo;
                                    Album.DETAIL_MEMO[position] = firstMemo;
                                    Album.DETAIL_FILENAME[firstSelected] = lastFileName;
                                    Album.DETAIL_FILENAME[position] = firstFileName;
                                }

                                firstSelected = -1;
                                v.setAlpha(1);
                                holder.thumbnail.setAlpha(1);
                                holder.overflow.setAlpha(1);
                                holder.holdingIcon.setVisibility(View.GONE);
                                holder.pickImg.setVisibility(View.VISIBLE);
                        //        progressDialog.dismiss();
                                notifyDataSetChanged();
                        //    }
                        //}, 1000);

                    }
                }
            });
        } else {
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!onRemove && !onSwap) {
                        final Album album1 = albumList.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Memo");

                        // Set up the input
                        final EditText input = new EditText(mContext);
                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        input.setText(Album.DETAIL_MEMO[position]);
                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //album1.setName(input.getText().toString());
                                Album.DETAIL_MEMO[position] = input.getText().toString();
                                albumList.set(position, album1);
                                Home.updateView();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();
                    }
                }
            });

            //กดที่รูป
            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!onRemove && !onSwap) {
                        //if (Album.DETAIL_BITMAP[position] != null) {
                        if((Album.DETAIL_FILENAME[position] != null) && !Album.DETAIL_FILENAME[position].equals("")){
                            Intent fullSizeIMG = new Intent(mContext, Preview.class);
                            fullSizeIMG.putExtra("IMG_INDEX", position);
                            mContext.startActivity(fullSizeIMG);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Album.DETAIL_FILE = CreateFile.createUnique();
                            album.setFileName(CreateFile.getFileName());
                            uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", Album.DETAIL_FILE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            ((Activity) mContext).startActivityForResult(intent, position);
                        }
                    }
                }
            });
        }


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onRemove && !onSwap) {
                    showPopupMenu(holder.overflow, position, holder.thumbnail, holder.overflow);
                }
            }
        });

        holder.removeChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                //Toast.makeText(mContext,position+" : "+String.valueOf(isChecked),Toast.LENGTH_LONG).show();

                if(isChecked == true){
                    Album.DETAIL_REMOVED_INDEX.add(position);///memo filename
                } else {
                    int detailIndex = Album.DETAIL_REMOVED_INDEX.indexOf(position);
                    Album.DETAIL_REMOVED_INDEX.remove(detailIndex);
                }

                String allPos = "";
                for(int i=0; i<Album.DETAIL_REMOVED_INDEX.size(); i++){
                    allPos += String.valueOf(i);
                }

                //Toast.makeText(mContext,allPos, Toast.LENGTH_LONG).show();
            }
        });

        holder.pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onRemove && !onSwap) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.putExtra("position", "xxxx");
                    Album.CURRENT_PICK_IMG_POSITION = position;
                    ((Activity) mContext).startActivityForResult(pickPhoto, detailPickImage);
                }
            }
        });




    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,int position, ImageView detailThumbnail, ImageView detailOverflow) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position, detailThumbnail, detailOverflow));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private int positionCard;
        private ImageView mDetailThumnail, mDetailOverflow;

        public MyMenuItemClickListener(int position, ImageView detailThumbnail, ImageView detailOverflow) {
            positionCard = position;
            this.mDetailThumnail = detailThumbnail;
            this.mDetailOverflow = detailOverflow;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Album.DETAIL_FILE= CreateFile.createUnique();
                    uri = FileProvider.getUriForFile(mContext,BuildConfig.APPLICATION_ID + ".provider",Album.DETAIL_FILE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    ((Activity) mContext).startActivityForResult(intent, positionCard);
                    return true;
                case R.id.action_play_next:
                    //Album.DETAIL_BITMAP[positionCard] = null;
                    Album.DETAIL_FILENAME[positionCard] = null;
                    Album.DETAIL_MEMO[positionCard] = null;
                    Glide.with(mContext).load(album.getThumbnail()).into(mDetailThumnail);
                    mDetailOverflow.setVisibility(View.GONE);
                    return true;
                default:
            }
            return false;
        }


    }



   /* public void startActivityForResult(Intent intent, int positionCard) {

    }*/



    @Override
    public int getItemCount() {
        return albumList.size();
    }

}
