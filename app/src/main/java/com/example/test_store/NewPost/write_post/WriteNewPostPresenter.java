package com.example.test_store.NewPost.write_post;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.core.app.ActivityCompat;

import com.example.test_store.R;
import com.example.test_store.NewPost.add_details.AddPostDetailsView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import xute.markdeditor.EditorControlBar;
import xute.markdeditor.utilities.FilePathUtils;

import static android.content.Context.MODE_PRIVATE;
import static com.example.test_store.Constants.IMG_INTEND_REQUEST_CODE;
import static com.example.test_store.Constants.USER_DATA_FILE;

public class WriteNewPostPresenter implements WriteNewPostContract.Presenter{
    private WriteNewPostView view;

    public WriteNewPostPresenter(WriteNewPostView writeNewPostView) {
        view = writeNewPostView;
    }

    protected void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(view, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(view, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, IMG_INTEND_REQUEST_CODE);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                view.startActivityForResult(intent, IMG_INTEND_REQUEST_CODE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMG_INTEND_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK && data.getData() != null){
                Uri uri = data.getData();
                String filePath = FilePathUtils.getPath(view, uri);
                Log.d("MyTAG", "lol: "+filePath);
                //view.markDEditor.insertImage( filePath, true, "");
            }
        }
    }

    @Override
    public void goBack() {
        view.finish();
    }

    @Override
    public void goNext() {
        String content = view.postContent.getText().toString();
        Intent intent = new Intent(view.getApplicationContext(), AddPostDetailsView.class);
        intent.putExtra("content", content);
        view.startActivity(intent);
    }

    @Override
    public void showMoreOptions(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.new_post_menu_save_draft:
                        //save as draft to phone memory
                        saveDraft();
                        return true;
                    case R.id.new_post_menu_clear:
                        //maybe ask user before doing that
                        view.postContent.setText("");
                    default:
                        return false;
                }
            }
        });
        popupMenu.inflate(R.menu.new_post_menu);
        popupMenu.show();
    }

    private void saveDraft(){
        FileOutputStream draft = null;
        try{
            draft = view.openFileOutput("post.txt", MODE_PRIVATE);
            draft.write(view.postContent.getText().toString().getBytes());
            draft.close();
            view.showToast("Draft saved");
        } catch (IOException e){
            Log.d("MyTAG", "error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setTextEditor() {
//        view.markDEditor.configureEditor(
//                getProfileDirectory(new Database().getUserID() + "/user_posts_photo/"),//server url for image upload
//                "",              //serverToken
//                true,           // isDraft: set true when you are loading draft
//                "Type here...", //default hint of input box
//                NORMAL
//        );
//
//        view.markDEditor.loadDraft(new DraftModel(null));
//        view.editorControlBar.setEditorControlListener(this);
//        view.editorControlBar.setEditor(view.markDEditor);
    }
    //    @Override
//    public void onInsertImageClicked() {
//        openGallery();
//    }
//
//    @Override
//    public void onInserLinkClicked() {
//        view.showToast("Link");
//    }
}
