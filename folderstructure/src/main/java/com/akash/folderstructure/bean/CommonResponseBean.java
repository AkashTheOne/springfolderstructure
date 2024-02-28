package com.akash.folderstructure.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author akash kushwaha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseBean {

    private int statusCode;
    private String status;
    private String errorMsg;
    private Object data;
}
