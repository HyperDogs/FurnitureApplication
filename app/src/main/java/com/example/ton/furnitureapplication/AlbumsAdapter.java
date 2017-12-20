package com.example.ton.furnitureapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import resource.CreateFile;

import static android.app.Activity.RESULT_OK;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;
    private Album album;
    private File file;
    private Uri uri;
    private int detailPickImage = 1003;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public ImageButton pickImg;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            pickImg = (ImageButton) view.findViewById(R.id.pickImg);

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


        if(Album.DETAIL_BITMAP[position] != null) {
            Picasso.with(mContext).load(Utility.getImageUri(mContext, Album.DETAIL_BITMAP[position]))
                    .fit().centerCrop().into(holder.thumbnail);
            holder.overflow.setVisibility(View.VISIBLE);

        } else {
            holder.overflow.setVisibility(View.GONE);
        }

        if(Album.DETAIL_MEMO[position] !=null){
            holder.title.setText(Album.DETAIL_MEMO[position]);
        }

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        //กดที่รูป
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Album.DETAIL_BITMAP[position] != null){
                    Intent fullSizeIMG = new Intent(mContext,PreveiwImage.class);
                    fullSizeIMG.putExtra("IMG_INDEX",position);
                    mContext.startActivity(fullSizeIMG);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Album.DETAIL_FILE= CreateFile.createUnique();
                    album.setFileName(CreateFile.getFileName());
                    uri = FileProvider.getUriForFile(mContext,BuildConfig.APPLICATION_ID + ".provider",Album.DETAIL_FILE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    ((Activity) mContext).startActivityForResult(intent, position);
                }
            }
        });
        //กดที่Text
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Album album1 = albumList.get(position);
                Toast.makeText(mContext,"Position alert"+position,Toast.LENGTH_SHORT).show();
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
        });


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, position, holder.thumbnail, holder.overflow);
            }
        });

        holder.pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhoto.putExtra("position",String.valueOf(position));
                ((Activity) mContext).startActivityForResult(pickPhoto , position);
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
                    Album.DETAIL_BITMAP[positionCard] = null;
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
