package com.akash.folderstructure.controller;

import com.akash.folderstructure.bean.CommonResponseBean;
import com.akash.folderstructure.bean.FolderRequestBean;
import com.akash.folderstructure.service.FolderService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akash kushwaha
 */
@RestController
@RequestMapping( "/api" )
public class FolderController
{

    @Autowired
    private FolderService folderService;

    @RequestMapping( value = "/folder", method =
    {
        RequestMethod.POST, RequestMethod.DELETE
    } )
    public ResponseEntity<CommonResponseBean> createFolder(@RequestBody FolderRequestBean folderRequest, HttpServletRequest request)
    {
        CommonResponseBean commonResponseBean = new CommonResponseBean();
        ResponseEntity<CommonResponseBean> responseEntity;
        String method = request.getMethod();
        System.out.println("method>>"+method);
        switch ( method )
        {
            case "POST":
                responseEntity = folderService.createFolder(folderRequest);
                break;
            case "DELETE":
                responseEntity = folderService.deleteFolder(folderRequest);
                break;
            default:
                commonResponseBean.setErrorMsg("Invalid HTTP method");
                responseEntity = ResponseEntity.badRequest().body(commonResponseBean);
                break;
        }
        return responseEntity;
    }

    @RequestMapping( value = "/readFolder", method ={RequestMethod.POST})
    public ResponseEntity<CommonResponseBean> getFolderContents(@RequestBody FolderRequestBean folderRequest)
    {
        ResponseEntity<CommonResponseBean> data = folderService.getFolderData(folderRequest.getName());
        return data;
    }

    @RequestMapping( value = "/json/objects", method ={RequestMethod.POST})
    public ResponseEntity<CommonResponseBean> getJsonExplorer(@RequestBody String requestParam)
    {
        ResponseEntity<CommonResponseBean> data = folderService.getJsonExplorer(requestParam);
        return data;
    }
}
