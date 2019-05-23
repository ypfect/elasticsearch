package com.explorer.es.service.controller;

import com.overstar.product_export.service.IProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/5/23 16:29
 */
@RestController
public class RpcController {

    @Autowired
    private IProductPriceService productPriceService;

    @GetMapping("hello")
    public String firstDubboReq(){
        return productPriceService.firstRpc();    }
}
