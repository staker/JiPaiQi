package com.staker.main.util;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 辅助工具类
 * 2019/09/04
 */
public class AccessibilityUtil {

    private static AccessibilityUtil instance;
    private AccessibilityService service;

    private AccessibilityUtil(AccessibilityService service) {
        this.service = service;
    }

    public static AccessibilityUtil getInstance(AccessibilityService service) {
        if (instance == null) {
            synchronized (AccessibilityUtil.class) {
                if (instance == null) {
                    instance = new AccessibilityUtil(service);
                }
            }
        }
        return instance;
    }


    public void logListDesc(String nodeId){
        List<AccessibilityNodeInfo> list =findViewListById(nodeId);
        if (list == null) {
            Log.e("staker","需要打印的数组为 空");
            return;
        }
        int size=list.size();
        for (int i = 0; i <size ; i++) {
            AccessibilityNodeInfo temp=list.get(i);
            Log.e("staker","数组 第"+i+"个 desc="+temp.getContentDescription());
            Log.e("staker","数组 第"+i+"个 text="+temp.getText());
        }
    }
    public void logNodeInfo(AccessibilityNodeInfo nodeInfo){
        if(nodeInfo==null){
            Log.e("staker","需要打印的Nodeinfo为 空");
        }else {
            Log.e("staker","nodeInfo.text="+nodeInfo.getText());
            Log.e("staker","node desc="+nodeInfo.getContentDescription());
        }
    }


    /**
     * 根据getRootInActiveWindow查找和当前text相等的控件
     *
     * @param text 需要找的字符串
     */
    public  ArrayList<AccessibilityNodeInfo> findViewByEqualsText(String text) {
        List<AccessibilityNodeInfo> listContains = findViewByContainText( text);
        if (listContains == null || listContains.size() == 0) {
            return null;
        }
        ArrayList<AccessibilityNodeInfo> listNew = new ArrayList<>();
        for (AccessibilityNodeInfo tempNode : listContains) {
            if (tempNode.getText() != null && text.equals(tempNode.getText().toString())) {
                listNew.add(tempNode);
            } else {
                tempNode.recycle();
            }
        }
        return listNew;
    }

    /**
     * 点击该控件,
     * 该方法会递归调用它的父控件，直到无法找到可点击的父控件
     *
     * @return true表示点击成功
     */
    public boolean clickViewByNodeInfo(AccessibilityNodeInfo nodeInfo) {
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            } else {
                nodeInfo = nodeInfo.getParent();
            }
        }
        return false;
    }

    /**
     * 根据getRootInActiveWindow查找包含当前text的控件
     *
     * @param containText 只要内容包含就会找到（应该是根据drawText找的）
     */
    public List<AccessibilityNodeInfo> findViewByContainText(String containText) {
        AccessibilityNodeInfo info = service.getRootInActiveWindow();
        if (info == null) return null;
        List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByText(containText);
        info.recycle();
        return list;
    }


    /**
     * 根据getRootInActiveWindow查找当前id的控件 (条件是确定当前id只有一个的时候可以直接获取数组的第一个)
     *
     * @param id 控件的id
     */
    public AccessibilityNodeInfo findViewById(String id) {
        List<AccessibilityNodeInfo> listNode = findViewListById(id);
        if (listNode != null && listNode.size() > 0) {
            for (AccessibilityNodeInfo nodeInfo : listNode) {
                if (nodeInfo != null) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    /**
     * 根据提供的AccessibilityNodeInfo查找它的子控件id的控件(条件是确定当前id只有一个的时候可以直接获取数组的第一个)
     *
     * @param id 控件的id
     */
    public AccessibilityNodeInfo findViewById(AccessibilityNodeInfo rootNode,String id) {
        List<AccessibilityNodeInfo> listNode = findViewListById(rootNode,id);
        if (listNode != null && listNode.size() > 0) {
            for (AccessibilityNodeInfo nodeInfo : listNode) {
                if (nodeInfo != null) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }



    /**
     * 根据getRootInActiveWindow查找当前id的控件集合(类似listview这种一个页面重复的id很多)
     *
     * @param id 控件的id
     */
    public List<AccessibilityNodeInfo> findViewListById(String id) {
        try {
            AccessibilityNodeInfo rootInfo = service.getRootInActiveWindow();
            if (rootInfo == null) {
                return null;
            }
            List<AccessibilityNodeInfo> list = rootInfo.findAccessibilityNodeInfosByViewId(id);
            rootInfo.recycle();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据提供的AccessibilityNodeInfo查找它的子控件id的控件集合(类似listview这种一个页面重复的id很多)
     *
     * @param id 控件的id
     */
    public List<AccessibilityNodeInfo> findViewListById(AccessibilityNodeInfo nodeInfo,String id) {
        try {
            if (nodeInfo == null) {
                return null;
            }
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 通过找到text字符串对应的 NodeInfo 并进行点击操作
     *
     * @param text
     */
    public void clickViewByText(String text) {
        AccessibilityNodeInfo rootNodeInfo = service.getRootInActiveWindow();
        if (rootNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = findViewByEqualsText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                   if (clickViewByNodeInfo(nodeInfo)){
                       //说明成功点击了，否则循环搜索下一个进行点击
                       break;
                   }
                }
            }
        }
    }


    /**
     * 通过id找到对应的 NodeInfo 并进行点击操作
     */
    public void clickViewByID(String id) {
        AccessibilityNodeInfo rootNodeInfo = service.getRootInActiveWindow();
        if (rootNodeInfo == null) {
            return;
        }
        List<AccessibilityNodeInfo> nodeInfoList = rootNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null) {
                    if (clickViewByNodeInfo(nodeInfo)){
                        //说明成功点击了，否则循环搜索下一个进行点击
                        break;
                    }
                }
            }
        }
    }


    /**
     * 模拟输入,只能针对 nodeinfo 确定是 EditText控件才有效
     *
     * @param nodeInfo nodeInfo
     * @param text     text
     */
    public void inputText(AccessibilityNodeInfo nodeInfo, String text) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Bundle arguments = new Bundle();
                arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                ClipboardManager clipboard = (ClipboardManager) service.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", text);
                clipboard.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            }
        } catch (Exception e) {
            Log.e("staker", "模拟输入 文字  异常=" + e.toString() + e.getMessage());
        }
    }

    private OutputStream os=null;
    /**
     * 如果控件重写过click方法，改用的是touch方法，那么对于root过的手机，可以用这个方法
     * @param nodeInfo
     */
    public  void forceClick(AccessibilityNodeInfo nodeInfo) {

        Rect rect = new Rect();
        nodeInfo.getBoundsInScreen(rect);
        int x=(rect.left+rect.right)/2;
        int y = (rect.top + rect.bottom) / 2;
        autoClickPos(x,y);
//        String cmd = "input tap " + String.valueOf(x) + " " + String.valueOf(y);
//        try {
//            if (os == null) {
//                os = Runtime.getRuntime().exec("su").getOutputStream();
//            }
//            os.write(cmd.getBytes());
//            os.flush();//清空缓存
//            os.close();//停止流
//            os = null;
//        } catch (Exception e) {
//        }
    }

    public void autoClickPos( final double x, final double y) {
        String cmd="input tap "+x+" "+y;
        adbShell(cmd);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 线程睡眠0.3s
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // 利用ProcessBuilder执行shell命令
//                String[] order = { "input", "tap", "" + x, "" + y };
//                try {
//                    new ProcessBuilder(order).start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }


    public void adbShell(String cmd){
        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            Logg.e("adbShell: 输出命令:"+cmd+"成功");
        } catch (Throwable t) {
            t.printStackTrace();
            Logg.e("adbShell: 输出命令:"+cmd+"失败");
        }
    }

}
