package com.zhy.http.okhttp.callback;

import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhy on 15/12/15.
 * 这个回掉接口主要是把返回的字符串自动解析成 文件，此外创建这个类的时候，需要输入保存文件的地址和文件的名字
 */
public abstract class FileCallBack extends Callback<File>
{
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public abstract void inProgress(float progress);

    public FileCallBack(String destFileDir, String destFileName)
    {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }
    public FileCallBack(String destFileName)
    {
        this.destFileName = destFileName;
    }


    @Override
    public File parseNetworkResponse(Response response) throws IOException
    {
        return saveFile(response);
    }


    public File saveFile(Response response) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            File file;
            if (destFileDir != null) {
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                file = new File(dir, destFileName);
            } else
            {
                file=new File(destFileName);
                File dir=file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                OkHttpUtils.getInstance().getDelivery().post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        inProgress(finalSum * 1.0f / total);
                    }
                });
            }
            fos.flush();

            return file;

        } finally
        {
            try
            {
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }


}
