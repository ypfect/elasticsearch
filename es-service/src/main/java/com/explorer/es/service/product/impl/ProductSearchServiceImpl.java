package com.explorer.es.service.product.impl;

import com.explorer.es.export.service.IProductSearchService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/5/23 15:34
 */
@Service
public class ProductSearchServiceImpl implements IProductSearchService {
    @Override
    public String getRelationWord(String key) {
        return "you know for search";
    }
}
