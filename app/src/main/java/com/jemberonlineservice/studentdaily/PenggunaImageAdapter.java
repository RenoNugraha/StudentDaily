package com.jemberonlineservice.studentdaily;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by RenoNugraha on 9/8/2016.
 */
public class PenggunaImageAdapter extends ArrayAdapter<Pengguna> {
    Context context;
    int layoutResourceId;
    ArrayList<Pengguna> data = new ArrayList<Pengguna>();
    public PenggunaImageAdapter(Context context, int layoutResourceId, ArrayList<Pengguna> data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ImageHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.profile_image);
            row.setTag(holder);
        }else {
            holder = (ImageHolder) row.getTag();
        }

        Pengguna foto = data.get(position);
        byte[] outImage = foto.foto_pengguna;
        ByteArrayInputStream imagestream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imagestream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;
    }

    static class ImageHolder
    {
        ImageView imgIcon;
    }
}
