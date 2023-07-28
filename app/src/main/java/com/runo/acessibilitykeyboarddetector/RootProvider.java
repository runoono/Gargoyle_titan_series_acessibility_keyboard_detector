package com.runo.acessibilitykeyboarddetector;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class RootProvider {
    public static void EnableRoot()
    {
        RunAsRoot("exit");
    }

    public static void RunAsRoot(String command)
    {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());

            os.writeBytes(command + "\n");

            os.writeBytes("exit\n");

            os.flush();
            os.close();

            try { p.waitFor(); } catch (InterruptedException e) { Log.d("EXCEPTION", e.toString());}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Remount() {
        RunAsRoot("remount");
    }
}
