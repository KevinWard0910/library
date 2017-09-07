package com.chinaredstar.core.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;
import com.chinaredstar.core.ucrop.Crop;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

/**
 * Created by hairui.xiang on 2017/9/6.
 */

public class PhotoHelper {
    private static final String KEY_TAKE_PHOTOS_RESULT = "take_photos_result";
    /**
     * 打开相机
     */
    public final static int RC_ACTION_IMAGE_CAPTURE = 2001;
    /**
     * 打开相册
     */
    public final static int RC_ACTION_PICK = 2002;

    public interface OnPhotoGetListener {
        void onGetPhotoPath(Uri uri);
    }

    public interface IUCrop {
        boolean onStartUCrop(@NonNull Uri source, @NonNull Uri destination);
    }

    public static Crop.Options getDefaultUCropOptions() {
        Crop.Options options = new Crop.Options();
        //设置裁剪图片的保存格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);//  JPEG,PNG,WEBP;
        //设置裁剪图片的图片质量
        options.setCompressionQuality(80);//[0-100]
        //设置你想要指定的可操作的手势
//        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
//        options.setMaxScaleMultiplier(5);
//        options.setImageToCropBoundsAnimDuration(666);
//        options.setCircleDimmedLayer(true);
//        options.setDimmedLayerColor(Color.DKGRAY);
//        options.setShowCropFrame(false);
//        options.setCropGridStrokeWidth(1);
//        options.setCropGridColor(Color.WHITE);
//        options.setCropGridColumnCount(3);
//        options.setCropGridRowCount(3);
//        options.setHideBottomControls(true);
        return options;
    }

    /**
     * @param ucrop 如果为null返回原图
     * @param l     图片地址回传
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data, IUCrop ucrop, OnPhotoGetListener l) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_ACTION_PICK://data.getData().toString()
                   /* if ("content".equals(data.getScheme())) {
                    } else if ("file".equals(data.getScheme())) {
                    }*/
                    if (null != ucrop) {
                        if (!ucrop.onStartUCrop(data.getData(), Uri.fromFile(getOutputPhoto(true)))) {
                            if (null != l) {// 异常返回原图
                                l.onGetPhotoPath(data.getData());
                            }
                        }
                    } else {
                        if (null != l) {
                            l.onGetPhotoPath(data.getData());
                        }
                    }
                    break;
                case RC_ACTION_IMAGE_CAPTURE://SharedPreferencesHelper.getObj("take_photos_result", String.class)
                    if (null != ucrop) {
                        if (!ucrop.onStartUCrop(Uri.fromFile(new File(getPhotosPath())), Uri.fromFile(getOutputPhoto(true)))) {
                            if (null != l) {// 异常返回原图
                                l.onGetPhotoPath(Uri.fromFile(new File(getPhotosPath())));
                            }
                        }
                    } else {
                        if (null != l) {
                            l.onGetPhotoPath(Uri.fromFile(new File(getPhotosPath())));
                        }
                    }
                    break;
                case Crop.REQUEST_CROP:
                    if (null != l) {
                        l.onGetPhotoPath(Crop.getOutput(data));
                    }
                    break;
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            final Throwable cropError = Crop.getError(data);
            cropError.printStackTrace();
        }
    }

    /**
     * 打开相机
     */
    public static void onTakePhotos(Activity activity) {
        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        File file = getOutputPhoto(false);
        if (null == file) {
            //File can not be created!
        }
        Uri imgUri = Uri.fromFile(file);
        if (null != imgUri) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            activity.startActivityForResult(intent, RC_ACTION_IMAGE_CAPTURE);
        } else {

        }
    }

    /**
     * 打开相册
     */
    public static void onOpenAlbum(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), RC_ACTION_PICK);

       /* Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
        activity.startActivityForResult(intent, RC_ACTION_PICK);*/
    }


    private static File getOutputPhoto(boolean isCrop) {
        File photosDir = new File(PathUtil.getAppFilesDir(), "photos/");
        if (!photosDir.exists()) {
            photosDir.mkdirs();
        }
        String timestamp = TimeUtil.format("yyyy_MM_dd_HH_mm_ss");
        File photo;
        String photoPath;
        if (isCrop) {
            photoPath = photosDir.getPath() + File.separator + "IMG_" + timestamp + "_CROP" + ".jpg";
            photo = new File(photoPath);
        } else {
            photoPath = photosDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg";
            photo = new File(photoPath);
        }
        recordPhotosPath(photo);
        return photo;
    }

    private static void recordPhotosPath(File photo) {
        SharedPreferencesHelper.putObj(KEY_TAKE_PHOTOS_RESULT, photo.toString());
    }

    public static String getPhotosPath() {
        return SharedPreferencesHelper.getObj(KEY_TAKE_PHOTOS_RESULT, String.class);
    }
}