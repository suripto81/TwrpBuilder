package github.grace5921.TwrpBuilder.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by sumit on 5/11/16.
 */

public class ShellExecuter {
    public static String command;
    public static String TAG="ShellExecuter";

    public static final  String runAsRoot()
    {

        try {
            // Executes the command.
            Process process = Runtime.getRuntime().exec(command);
            // Reads stdout.
            // NOTE: You can write to stdin of the command using
            //       process.getOutputStream().
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();

            // Waits for the command to finish.
            process.waitFor();

            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e)

        {
            throw new RuntimeException(e);
        }
    }
    public static void rm(Context context,String name){
        /*
        * Usage
        * rm (getContext,"path to file");
        * */
        File dir = Environment.getExternalStorageDirectory();
        Log.d(TAG,"Request to delete "+ dir+"/"+name + " received .");
        File file = new File(dir, "/"+name);
        if(file.isFile())
        {
            Log.d(TAG,"Found file "+name+" .");
            file.delete();
            Log.i(TAG,"File "+name+" deleted .");

        }else if(file.isDirectory()){
            Log.d(TAG,"Found dir "+name+" .");
            rmDir(name);
        }
    }

    public static void rmDir(String name){
        File dir = new File(Environment.getExternalStorageDirectory()+"/"+name);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
            Log.i(TAG,"Folder "+name+" deleted .");
        }
    }

    public static void mkdir(String name){
        File makedir = new File(Environment.getExternalStorageDirectory() +
                File.separator +"/"+name );
        Log.d(TAG,"Request to make folder "+name+" received .");
        boolean success = true;
        if (!makedir.exists()) {
            success = makedir.mkdirs();
            if (success) {
                Log.i(TAG,"Dir "+name+" made .");
            } else {
                Log.e(TAG,"Failed to make dir "+name);
            }
        }else
        {
            Log.i(TAG,name+" dir alredy exist");
            Log.e(TAG,"Failed to make dir "+name);

        }
       }

    public static void cp(String src, String dst) throws IOException {
        FileInputStream var2 = new FileInputStream(src);
        FileOutputStream var3 = new FileOutputStream(dst);
        byte[] var4 = new byte[1024];

        int var5;
        while((var5 = var2.read(var4)) > 0) {
            var3.write(var4, 0, var5);
        }

        var2.close();
        var3.close();
    }


}
