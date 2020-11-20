package weixin.actions;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.staker.main.application.MyApplication;
import com.staker.main.interfaces.Action;
import com.staker.main.util.AccessibilityUtil;
import com.staker.main.util.GlobalActionUtil;
import com.staker.main.util.SleepUtil;

import java.util.ArrayList;
import java.util.List;

import weixin.WeiXinConstant;

import static weixin.WeiXinConstant.WeiXinId.ID_Img_Comment;
import static weixin.WeiXinConstant.WeiXinId.ID_Item;
import static weixin.WeiXinConstant.WeiXinId.ID_Listview;
import static weixin.WeiXinConstant.WeiXinId.ID_Txt_Name;
import static weixin.WeiXinConstant.WeiXinId.ID_Txt_Zan;

/**
 * 这是朋友圈循环点赞的一系列动作
 */
public class TimelineZanAction implements Action {
    private final  String TAG = getClass().getSimpleName();
    private AccessibilityService service;
    private AccessibilityUtil accessibilityUtil;
    private AccessibilityNodeInfo nodeListview;
    private AccessibilityNodeInfo nodeLayoutZan;
    private AccessibilityNodeInfo nodeTxtZan;


    List<AccessibilityNodeInfo> listTimeLine;

    public TimelineZanAction(){
        service= MyApplication.getContext().getAccessibilityService();
        accessibilityUtil=AccessibilityUtil.getInstance(service);
    }
    @Override
    public void run(AccessibilityEvent event) {
        String className = event.getClassName().toString();
        if(WeiXinConstant.WeiXinClass.Class_TimeLine.equals(className)){
            Log.w(TAG,"--------进来朋友圈界面");
            startTimeLine(event);
        }

    }




    private void startTimeLine(AccessibilityEvent event){




        try {
            //微信UI界面的根节点，开始遍历节点
            AccessibilityNodeInfo rootNodeInfo = service.getRootInActiveWindow();
            if (rootNodeInfo == null) {
                return;
            }
            nodeListview=accessibilityUtil.findViewById(ID_Listview);
            if(nodeListview==null){
                return;
            }
            checkIsZan();
        } catch (Exception e) {
            if (e != null && e.getMessage() != null) {
                Log.d("staker","报错:" + e.getMessage().toString());
            }
        }



    }

    private synchronized void checkIsZan() {
        listTimeLine = accessibilityUtil.findViewListById(nodeListview, ID_Item);
        boolean canScroll = true;
        while (canScroll && listTimeLine != null && listTimeLine.size() > 0) {
            for (AccessibilityNodeInfo nodeItem : listTimeLine) {
                  AccessibilityNodeInfo nodeImgComment=accessibilityUtil.findViewById(nodeItem,ID_Img_Comment);
                  accessibilityUtil.clickViewByNodeInfo(nodeImgComment);//点击 右下角评论的功能按钮
                SleepUtil.sleepTime(200);
                nodeLayoutZan = accessibilityUtil.findViewById(WeiXinConstant.WeiXinId.ID_Layout_Zan);
                nodeTxtZan = accessibilityUtil.findViewById(nodeLayoutZan, ID_Txt_Zan);
                if(nodeTxtZan==null){
                    Log.e(TAG,"突然就没有拿到赞，是不是点击评论的按钮 没打开");
                    continue;
                }
                if ("赞".equals(nodeTxtZan.getText())) {
                    Log.w(TAG, "还没有点过赞");
                    accessibilityUtil.clickViewByNodeInfo(nodeLayoutZan);
                } else {
                    Log.w(TAG, "点过赞  不进行操作");
                }
            }
            canScroll = GlobalActionUtil.performScrollForward(nodeListview);
            Log.w(TAG, "向下滑动=" + canScroll);
            SleepUtil.sleepTime(1000);
            listTimeLine = accessibilityUtil.findViewListById(nodeListview, ID_Item);
        }
    }



}


//        topList.clear();
//
//        if (!mUserName.equals("")) {
//
//            //测试获得评论按钮的父节点，再反推出点赞按钮
//            List<AccessibilityNodeInfo> fuBtns =
//                    rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/co0");
//
//            Log.d("staker","fuBtns数量：" + fuBtns.size());
//
//            if (fuBtns.size() != 0) {
//
//                //删掉超出屏幕的fuBtn
//                AccessibilityNodeInfo lastFuBtn = fuBtns.get(fuBtns.size() - 1);
//                Rect lastFuBtnOutBound = new Rect();
//                lastFuBtn.getBoundsInScreen(lastFuBtnOutBound);
//                if (lastFuBtnOutBound.top >50) {
//                    fuBtns.remove(lastFuBtn);
//                }
//
//                for (int i = 0; i < fuBtns.size(); i++) {
//                    AccessibilityNodeInfo fuBtn = fuBtns.get(i);
//                    Log.d("staker","fuBtn的子节点数量:" + fuBtn.getChildCount());//3-4个
//                    List<AccessibilityNodeInfo> plBtns = fuBtn.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cj9");
//                    Log.d("staker","从这里发现评论按钮:" + plBtns.size());
//
//                    if (plBtns.size() == 0) {
//                        if (listTimeLine.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
//                            test3(service.getRootInActiveWindow());
//                        }
//                        return;
//                    }
//
//                    AccessibilityNodeInfo plbtn = plBtns.get(0);    //评论按钮
//                    List<AccessibilityNodeInfo> zanBtns = fuBtn.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cnn");
//                    Log.d("staker","从这里发现点赞文字显示区域:" + zanBtns.size());
//                    if (zanBtns.size() != 0) {
//                        //2.如果不为空，则查找有没有自己点过赞，有则不点，没有则点
//                        AccessibilityNodeInfo zanbtn = zanBtns.get(0);
//                        Log.d("staker","点赞的人是:" + zanbtn.getText().toString());
//                        if (zanbtn != null && zanbtn.getText() != null &&
//                                zanbtn.getText().toString().contains(mUserName)) {
//                            Log.d("staker","*********************这一条已经被赞过辣");
//                            //判断是否需要翻页，如果当前所有页面的父节点都没点过了，就需要翻页
//                            boolean ifxuyaofanye = false;
//                            Log.d("staker","Ｏ(≧口≦)Ｏ: i=" + i + "  fuBtns.size():" + fuBtns.size());
//                            if (i == fuBtns.size() - 1) {
//                                ifxuyaofanye = true;
//                            }
//                            if (ifxuyaofanye) {
//                                //滑动前检测一下是否还有没有点过的点
//                                if (jianceIfLou()) {
//                                    Log.d("staker","还有遗漏的点！！！！再检查一遍!!!!!!!!!!");
//                                    test3(service.getRootInActiveWindow());
//                                } else {
//                                    if (listTimeLine.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
//                                        test3(service.getRootInActiveWindow());
//                                        return;
//                                    }
//                                }
//                            }
//
//                        } else {
//                            Log.d("staker","**************************:自己没有赞过!");
//                            //开始执行点赞流程
//                            if (plBtns.size() != 0) {
//                                Rect outBounds = new Rect();
//                                plbtn.getBoundsInScreen(outBounds);
//                                int top = outBounds.top;
//
//                                //根据top判断如果已经点开了就不重复点开了
//                                if (topList.contains(top)) {
//                                    return;
//                                }
//                                //com.tencent.mm:id/cj5 赞
//                                if (plbtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
//                                    List<AccessibilityNodeInfo> zanlBtns = rootNodeInfo.
//                                            findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cj3");
//                                    if (zanlBtns.size() != 0) {
//                                        if (!topList.contains(top) && zanlBtns.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
//                                            topList.add(top);
//                                            Log.d("staker","topList:" + topList.toString());
//
//                                            //判断是否需要翻页，如果当前所有页面的父节点都没点过了，就需要翻页
//                                            boolean ifxuyaofanye = false;
//                                            Log.d("staker","Ｏ(≧口≦)Ｏ: i=" + i + "  fuBtns.size():" + fuBtns.size());
//                                            if (i == fuBtns.size() - 1) {
//                                                ifxuyaofanye = true;
//                                            }
//                                            if (ifxuyaofanye) {
//                                                //滑动前检测一下是否还有没有点过的点
//                                                if (jianceIfLou()) {
//                                                    Log.d("staker","还有遗漏的点！！！！再检查一遍!!!!!!!!!!");
//                                                    test3(service.getRootInActiveWindow());
//                                                } else {
//                                                    if (listTimeLine.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
//                                                        test3(service.getRootInActiveWindow());
//                                                        return;
//                                                    }
//                                                }
//
//
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    } else {
//                        Log.d("staker","**************************:点赞区域为空!plBtns.size() :" + plBtns.size());
//
//                        //开始执行点赞流程
//                        if (plBtns.size() != 0) {
//
//                            Rect outBounds = new Rect();
//                            plbtn.getBoundsInScreen(outBounds);
//                            int top = outBounds.top;
//
//                            //根据top判断如果已经点开了就不重复点开了
//                            if (topList.contains(top)) {
//                                return;
//                            }
//                            //com.tencent.mm:id/cj5 赞
//                            if (plbtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
//                                List<AccessibilityNodeInfo> zanlBtns = rootNodeInfo.
//                                        findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cj3");
//                                if (zanlBtns.size() != 0) {
//                                    if (!topList.contains(top) && zanlBtns.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
//                                        topList.add(top);
//                                        Log.d("staker","topList:" + topList.toString());
//
//                                        //判断是否需要翻页，如果当前所有页面的父节点都没点过了，就需要翻页
//                                        boolean ifxuyaofanye = false;
//                                        Log.d("staker","Ｏ(≧口≦)Ｏ: i=" + i + "  fuBtns.size():" + fuBtns.size());
//                                        if (i == fuBtns.size() - 1) {
//                                            ifxuyaofanye = true;
//                                        }
//                                        if (ifxuyaofanye) {
//                                            //滑动前检测一下是否还有没有点过的点
//                                            if (jianceIfLou()) {
//                                                Log.d("staker","还有遗漏的点！！！！再检查一遍!!!!!!!!!!");
//                                                test3(service.getRootInActiveWindow());
//                                            } else {
//                                                if (listTimeLine.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
//                                                    test3(service.getRootInActiveWindow());
//                                                    return;
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }
