package com.buaa.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.buaa.adapter.MyAdapter;
import com.buaa.util.Globals;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private TextView nowPath;
    private List<Map<String, Object>> values;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Globals.init(this);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        nowPath = (TextView) findViewById(R.id.now_path);

        values = new ArrayList<>();
        File sd = Environment.getExternalStorageDirectory();
        loadFile(sd);
        adapter = new MyAdapter(this, values);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = values.get(position);
                boolean isDir = (boolean) map.get("isDir");
                File file = new File(map.get("fullPath").toString());
                if (isDir) {
                    //文件夹
                    values.clear();
                    //不在sdcard根目录才能有返回上一级
                    if (!Environment.getExternalStorageDirectory().getAbsolutePath().equals(file.getAbsolutePath())) {
                        Map<String, Object> parentData = new HashMap<String, Object>();
                        parentData.put("extName", "open_dir");
                        parentData.put("fileName", "返回一级");
                        parentData.put("isDir", true);
                        parentData.put("fullPath", file.getParent());
                        parentData.put("real", "TRUE");
                        values.add(parentData);
                    }
                    loadFile(file);
                    //告诉adpater数据改变了
                    adapter.notifyDataSetChanged();
                } else {
                    //文件
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("文件信息");
                    builder.setMessage("文件大小为：" + file.length() + "\r\n" + "文件名：" + file.getName());
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Map<String, Object> map = values.get(position);
                boolean isDir = (boolean)map.get("isDir");
                if(!isDir) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("提示信息");
                    builder.setMessage("您确定要删除吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File file = new File(map.get("fullPath").toString());
                            file.delete();
                            values.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                return false;
            }
        });
    }

    //遍历选中的文件夹并展示在页面上
    private void loadFile(File path) {
        File[] fs = path.listFiles();
        nowPath.setText(path.getAbsolutePath());
        for(int i=0;i<fs.length;i++) {
            File f = fs[i];
            String fileName = f.getName();
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("fullPath", f.getAbsolutePath());
            if(f.isDirectory()) {
                map.put("extName", "close_dir");
                map.put("isDir", true);
            }else {
                map.put("extName", fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase());
                map.put("isDir", false);
            }
            values.add(map);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            Map<String, Object> map = values.get(0);
            if("TRUE".equals(map.get("real"))) {
                //返回上一级目录
                list.performItemClick(list.getChildAt(0),0,list.getChildAt(0).getId());
            }else {
                //提示是否退出应用程序
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示信息");
                builder.setMessage("亲，真的要残忍的离开吗？");
                builder.setPositiveButton("真的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //终止应用程序执行
                        finish();
                    }
                });
                builder.setNegativeButton("骗你的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
            //将返回功能取消
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
