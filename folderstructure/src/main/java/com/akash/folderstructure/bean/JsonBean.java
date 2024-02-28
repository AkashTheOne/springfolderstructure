package com.akash.folderstructure.bean;

import java.util.ArrayList;
import java.util.List;
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
public class JsonBean
{

    private String id;
    private String name;
    private String type;
    private List<JsonBean> children = new ArrayList<>();

}
