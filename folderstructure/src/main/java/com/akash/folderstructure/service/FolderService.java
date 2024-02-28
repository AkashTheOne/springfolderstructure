package com.akash.folderstructure.service;

import com.akash.folderstructure.bean.CommonResponseBean;
import com.akash.folderstructure.bean.FolderRequestBean;
import com.akash.folderstructure.bean.JsonBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author akash kushwaha
 */
@Service
public class FolderService
{

    @Value( "${TEMP_FILE_PATH}" )
    public String tempPath;

//    private Map<String, Object> objectMap = new HashMap<>();

    public ResponseEntity<CommonResponseBean> createFolder(FolderRequestBean folderRequest)
    {
        Map<String, Object> objectMap = new HashMap<>();
        CommonResponseBean commonResponseBean = new CommonResponseBean();
        commonResponseBean.setStatusCode(HttpStatus.OK.value());
        commonResponseBean.setStatus("Failed");
        try
        {
        	System.out.println("tempPath>>>"+tempPath);
            if (new File(tempPath).exists())
            {
                new File(tempPath + folderRequest.getName()).mkdir();
                commonResponseBean.setStatus("Success");
                objectMap.put("folder_path", tempPath + folderRequest.getName());
                commonResponseBean.setData(objectMap);
            }
            else
            {
                commonResponseBean.setErrorMsg("Root Folder not Found");
            }
        }
        catch ( Exception e )
        {
            commonResponseBean.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());// also we use log4j
        }
        return new ResponseEntity<>(commonResponseBean, HttpStatus.OK);
    }

    public ResponseEntity<CommonResponseBean> deleteFolder(FolderRequestBean folderRequest)
    {
        CommonResponseBean commonResponseBean = new CommonResponseBean();
        commonResponseBean.setStatusCode(HttpStatus.OK.value());
        commonResponseBean.setStatus("Failed");
        try
        {
            if (new File(tempPath).exists())
            {
                if (new File(tempPath + folderRequest.getName()).exists())
                {
                    new File(tempPath + folderRequest.getName()).delete();
                    commonResponseBean.setStatus("Success");
                }
                else
                {
                    commonResponseBean.setErrorMsg("Folder not Found");
                }
            }
            else
            {
                commonResponseBean.setErrorMsg("Root Folder not Found");
            }
        }
        catch ( Exception e )
        {
            commonResponseBean.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());// also we use log4j
        }
        return new ResponseEntity<>(commonResponseBean, HttpStatus.OK);
    }

    public ResponseEntity<CommonResponseBean> getFolderData(String fileName)
    {
        CommonResponseBean commonResponseBean = new CommonResponseBean();
        commonResponseBean.setStatusCode(HttpStatus.OK.value());
        commonResponseBean.setStatus("Failed");
        try
        {
            List<String> fileList = new ArrayList<>();
            String file_path = tempPath + fileName;
            File folder = new File(file_path);

            if (folder.isDirectory())
            {
                File[] files = folder.listFiles();
                if (files != null)
                {
                    for ( File file : files )
                    {
                        fileList.add(file.getName());
                    }
                }
            }
            else
            {
                commonResponseBean.setErrorMsg("Root Folder not Found");
            }
            commonResponseBean.setData(fileList);
            commonResponseBean.setStatus("Success");
        }
        catch ( Exception e )
        {
            commonResponseBean.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());// also we use log4j
        }
        return new ResponseEntity<>(commonResponseBean, HttpStatus.OK);
    }

    public ResponseEntity<CommonResponseBean> getJsonExplorer(String requestJsonData)
    {
        CommonResponseBean commonResponseBean = new CommonResponseBean();
        commonResponseBean.setStatusCode(HttpStatus.OK.value());
        commonResponseBean.setStatus("Failed");
        try
        {
            List<JsonBean> data = new ArrayList<>();
            JsonElement jsonElement = JsonParser.parseString(requestJsonData);
            if (jsonElement.isJsonArray())
            {
                JsonArray jsonArray = jsonElement.getAsJsonArray(); // Convert the JSON element to a JSON array
                // Iterate each element in the array
                for ( JsonElement element : jsonArray )
                {
                    // Convert the element to a JSON object
                    JsonObject jsonObject = element.getAsJsonObject();
                    JsonBean jsonBean = new Gson().fromJson(jsonObject, JsonBean.class
                    );
                    data.add(jsonBean);
                }
            }
            else
            {
                commonResponseBean.setErrorMsg("Invaid JSON Format Found");
            }

            commonResponseBean.setData(data);
            commonResponseBean.setStatus("Success");
        }
        catch ( Exception e )
        {
            commonResponseBean.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());// also we use log4j
        }
        return new ResponseEntity<>(commonResponseBean, HttpStatus.OK);
    }
}
