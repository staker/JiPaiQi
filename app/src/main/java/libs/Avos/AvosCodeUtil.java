package libs.Avos;

import android.util.Log;

import com.staker.main.pojo.TimeCodePojo;
import com.staker.main.util.AsynTaskUtil;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
public class AvosCodeUtil {
    public static void queryCode(final String code, final HttpObjectListener listener){
        if(listener==null){
            return;
        }

        AsynTaskUtil.startTask(new AsynTaskUtil.IAsynTask() {
            @Override
            public Object run() {
                AVQuery<AVObject> avQuery = new AVQuery<>("TimeCode");
                avQuery.whereEqualTo("code",code).limit(10);
                List<AVObject> list=avQuery.find();
                return list;
            }
            @Override
            public void over(Object object) {
                List<AVObject> list=(List<AVObject>) object;
                if(list==null){
                    listener.onDataError("数据获取失败");
                }else{
                    int size=list.size();
                    if(size==0){
                        listener.onDataError("这是一个无效的激活码");
                    }else{
                        for (AVObject av:list ) {
                            TimeCodePojo timeCodePojo=new TimeCodePojo();
                            timeCodePojo.code=av.getString("code");
                            timeCodePojo.canUse=av.getBoolean("can_use");
                            timeCodePojo.days=av.getInt("days");
                            timeCodePojo.codeLevel=av.getInt("code_level");
                            timeCodePojo.objectId=av.getObjectId();
                            timeCodePojo.startTime=av.getString("start_time");
                            timeCodePojo.startTime=av.getString("end_time");
                            listener.onSucess(timeCodePojo);
                            break;
                        }
                    }
                }
            }
        });
    }


    public static void updateCode(TimeCodePojo codePojo, final HttpObjectListener listener){
        if(listener==null){
            return;
        }
        if(codePojo==null){
            listener.onDataError("更新失败");
        }
        String objectId=codePojo.objectId;
        AVObject avObject=AVObject.createWithoutData("TimeCode",objectId);
        avObject.put("can_use", false);
        avObject.saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                Log.e("staker","onSubscribe`1111111111111");
                listener.onDataError("");
            }
            @Override
            public void onNext(AVObject avObject) {
                Log.e("staker","onNext`1111111111111");
                listener.onSucess(null);
            }
            @Override
            public void onError(Throwable throwable) {
                Log.e("staker","onError`1111111111111");
                listener.onDataError("操作失败，请联系管理员或稍后尝试");
            }
            @Override
            public void onComplete() {
                Log.e("staker","onComplete`1111111111111");
            }
        });
    }

}
