package com.sakura.maven.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;


/**
 * @Author Sakura
 * @Date 2024/6/6 9:06
 * @Version 1.0
 */
public class TestDepend {

    @Test
    public void testFastJson(){
        //使用b工程的fastjson,体现依赖传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key","value");
        System.out.println(jsonObject.toJSONString());
    }
}
