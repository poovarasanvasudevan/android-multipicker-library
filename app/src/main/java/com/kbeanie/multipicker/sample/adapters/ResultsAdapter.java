package com.kbeanie.multipicker.sample.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kbeanie.multipicker.api.entity.ChosenFile;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.kbeanie.multipicker.sample.R;

import java.io.File;
import java.util.List;

/**
 * Created by kbibek on 2/24/16.
 */
public class ResultsAdapter extends BaseAdapter {
    private final static String TAG = ResultsAdapter.class.getSimpleName();

    private final static int TYPE_IMAGE = 0;
    private final static int TYPE_VIDEO = 1;
    private final static int TYPE_FILE = 2;
    private final static int TYPE_CONTACT = 3;

    private final static String FORMAT_IMAGE_VIDEO_DIMENSIONS = "%sw x %sh";
    private final static String FORMAT_ORIENTATION = "Ortn: %s";
    private final static String FORMAT_DURATION = "%s secs";

    private final Context context;
    private List<? extends ChosenFile> files;

    public ResultsAdapter(List<? extends ChosenFile> files, Context context) {
        this.files = files;
        this.context = context;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView: " + files.size());
        ChosenFile file = (ChosenFile) getItem(position);
        int itemViewType = getItemViewType(position);
        if (convertView == null) {
            switch (itemViewType) {
                case TYPE_IMAGE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.adapter_images, null);
                    break;
                case TYPE_FILE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.adapter_files, null);
                    break;
                case TYPE_VIDEO:
                    convertView = LayoutInflater.from(context).inflate(R.layout.adapter_videos, null);
                    break;
            }
        }

        switch (itemViewType) {
            case TYPE_IMAGE:
                showImage(file, convertView);
                break;
            case TYPE_FILE:
                showFile(file, convertView);
                break;
            case TYPE_VIDEO:
                showVideo(file, convertView);
                break;

        }
        return convertView;
    }

    private void showVideo(ChosenFile file, View view) {
        ChosenVideo video = (ChosenVideo) file;

        SimpleDraweeView ivImage = (SimpleDraweeView) view.findViewById(R.id.ivImage);
        ivImage.setImageURI(Uri.fromFile(new File(video.getPreviewThumbnail())));

        TextView tvDimension = (TextView) view.findViewById(R.id.tvDimension);
        tvDimension.setText(String.format(FORMAT_IMAGE_VIDEO_DIMENSIONS, video.getWidth(), video.getHeight()));

        TextView tvMimeType = (TextView) view.findViewById(R.id.tvMimeType);
        tvMimeType.setText(file.getFileExtensionFromMimeTypeWithoutDot());

        TextView tvSize = (TextView) view.findViewById(R.id.tvSize);
        tvSize.setText(file.getHumanReadableSize(false));

        TextView tvDuration = (TextView) view.findViewById(R.id.tvDuration);
        tvDuration.setText(String.format(FORMAT_DURATION, video.getDuration()));

        TextView tvOrientation = (TextView) view.findViewById(R.id.tvOrientation);
        tvOrientation.setText(String.format(FORMAT_ORIENTATION, video.getOrientationName()));
    }

    private void showImage(ChosenFile file, View view) {
        ChosenImage image = (ChosenImage) file;

        SimpleDraweeView ivImage = (SimpleDraweeView) view.findViewById(R.id.ivImage);
        ivImage.setImageURI(Uri.fromFile(new File(image.getThumbnailSmallPath())));

        TextView tvDimension = (TextView) view.findViewById(R.id.tvDimension);
        tvDimension.setText(String.format(FORMAT_IMAGE_VIDEO_DIMENSIONS, image.getWidth(), image.getHeight()));

        TextView tvMimeType = (TextView) view.findViewById(R.id.tvMimeType);
        tvMimeType.setText(file.getFileExtensionFromMimeTypeWithoutDot());

        TextView tvSize = (TextView) view.findViewById(R.id.tvSize);
        tvSize.setText(file.getHumanReadableSize(false));

        TextView tvOrientation = (TextView) view.findViewById(R.id.tvOrientation);
        tvOrientation.setText(String.format(FORMAT_ORIENTATION, image.getOrientationName()));
    }

    private void showFile(ChosenFile file, View view) {
        TextView tvImage = (TextView) view.findViewById(R.id.tvImage);
        tvImage.setText(file.getFileExtensionFromMimeTypeWithoutDot());

        TextView tvMimeType = (TextView) view.findViewById(R.id.tvMimeType);
        tvMimeType.setText(file.getFileExtensionFromMimeTypeWithoutDot());

        TextView tvSize = (TextView) view.findViewById(R.id.tvSize);
        tvSize.setText(file.getHumanReadableSize(false));

    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        String type = ((ChosenFile) getItem(position)).getType();
        switch (type) {
            case "image":
                return TYPE_IMAGE;
            case "file":
                return TYPE_FILE;
            case "video":
                return TYPE_VIDEO;
        }
        return TYPE_FILE;
    }
}