package com.example.passage.utils;

import java.io.File;

public class FileExist {
    public static boolean ifFileExist(String path)
    {
        try {
            File file=new File(path);
            if (!file.exists())
            {
                return false;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
