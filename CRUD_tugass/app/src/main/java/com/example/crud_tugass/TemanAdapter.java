package com.example.crud_tugass;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(long id);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public TemanAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgPhoto;
        public TextView nameText, birthdayText, telephoneText;

        public TemanViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            nameText = itemView.findViewById(R.id.tv_item_name);
            birthdayText = itemView.findViewById(R.id.tv_item_tgl);
            telephoneText = itemView.findViewById(R.id.tv_item_tlp);
        }
    }

    @NonNull
    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view  = layoutInflater.inflate(R.layout.content_main, parent, false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TemanViewHolder holder, final int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_NAME));
        String telephone = mCursor.getString(mCursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_TELEPHONE));
        String birthday = mCursor.getString(mCursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_BIRTHDAY));
        String photo = mCursor.getString(mCursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_PHOTO));
        final long id = mCursor.getLong(mCursor.getColumnIndex(DatabaseSetting.DatabaseEntry._ID));

        Glide.with(holder.itemView.getContext())
                .load(photo)
                .apply(new RequestOptions().override(100, 100))
                .into(holder.imgPhoto);

        holder.nameText.setText(name);
        holder.telephoneText.setText(telephone);
        holder.birthdayText.setText(birthday);
        holder.itemView.setTag(id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}
