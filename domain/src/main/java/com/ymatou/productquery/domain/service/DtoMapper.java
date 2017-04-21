package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.model.BizException;
import com.ymatou.productquery.model.res.LiveProductCartDto;
import com.ymatou.productquery.model.res.ProductActivityCartDto;
import com.ymatou.productquery.model.res.ProductInCartDto;
import com.ymatou.productquery.model.res.PropertyDto;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
/**
 * Created by zhangyong on 2017/4/11.
 */

/**
 * 转换ProductInCartDto 对象
 */
public class DtoMapper {
    public static ProductInCartDto toProductInCartDto(Products product, Catalogs catalog, ActivityProducts activityProduct, List<Catalogs> catalogsList) {
        ProductInCartDto result = new ProductInCartDto();
        result.setProductId(product.getProductId());
        result.setCatalogId(catalog.getCatalogId());
        result.setVersion(getSnapshotVersion(product.getVersion()));
        result.setProductName(product.getTitle());
        result.setMainPicUrl(product.getPicList() != null ? product.getPicList().stream().findFirst().orElse("") : "");
        result.setValidStart(product.getValidStart());
        result.setValidEnd(product.getValidEnd());
        result.setBrandId(product.getBrandId());
        result.setMasterCategoryId(product.getMasterCategoryId());
        result.setCategoryId(product.getSecondCategoryId());
        result.setThirdCategoryId(product.getThirdCategoryId());
        result.setSellerId(product.getSellerId());
        result.setSellerName(product.getSellerName());
        result.setDeliveryMethod(product.getDeliveryMethod());
        result.setBondedArea(product.getBondedArea());
        result.setWeight(0d);
        result.setFreeShipping(product.getIsShipping() <= 0);
        result.setTariffType(product.getIsTariffy());
        result.setLimitNumber(0);
        result.setLimitStartTime(new Date(1900, 1, 1));
        result.setProductCode(product.getProductCode());
        result.setLocalReturn(product.getLocalReturn());
        result.setCatalogType(product.getCatalogType());
        result.setNoReasonReturn(product.isNoReasonReturn());
        result.setStockNum(getCatalogStock(catalog, activityProduct));
        result.setCatalogCount((int) catalogsList.stream().filter(t -> t.getProductId().equals(product.getProductId())).count());
        result.setPrice(getCatalogPrice(catalog, activityProduct));
        result.setSku(catalog.getSku());
        result.setPreSale(catalog.isPriceSale());
        result.setPspProduct(product.isPspProduct());
        result.setProperties(getCatalogPropertyList(catalog.getPropertyList()));
        return result;
    }

    private static String getSnapshotVersion(String version) {
        return String.valueOf(Double.valueOf(version) / 1000);
    }

    private static int getCatalogStock(Catalogs catalog, ActivityProducts activityProduct) {
        if (catalog == null) {
            return 0;
        }
        if (activityProduct != null) {

            if (activityProduct.getActivityCatalogList().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())) != null) {
                return activityProduct.getActivityCatalogList().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())).findFirst().orElse(new ActivityCatalogInfo()).getActivityStock();
            }
        }
        return catalog.getStock();
    }

    private static Double getCatalogPrice(Catalogs catalog, ActivityProducts activityProduct) {
        if (catalog == null) {
            return 0d;
        }
        if (activityProduct != null) {

            if (activityProduct.getActivityCatalogList().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())) != null) {
                return activityProduct.getActivityCatalogList().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())).findFirst().orElse(new ActivityCatalogInfo()).getActivityPrice();
            }
        }
        return catalog.getPrice();
    }

    private static List<PropertyDto> getCatalogPropertyList(List<PropertyInfo> propertyInfoList) {
        if (propertyInfoList == null || propertyInfoList.isEmpty()) {
            return null;
        }
        List<PropertyDto> propertyDtoList = new ArrayList<>();
        for (PropertyInfo pro : propertyInfoList) {
            PropertyDto prodto = new PropertyDto();
            prodto.setPropertyValue(pro.getValue());
            prodto.setPropertyName(pro.getName());
            prodto.setPropertyPictureUrl(pro.getPic());
            propertyDtoList.add(prodto);
        }
        return propertyDtoList;
    }

    public static ProductActivityCartDto toProductActivityCartDto(ActivityProducts model) {
        ProductActivityCartDto pa = new ProductActivityCartDto();
        if (model == null) {
            return pa;
        }
        pa.setActivityId(model.getActivityId());
        pa.setActivityName(model.getActivityName());
        pa.setProductActivityStartTime(model.getStartTime());
        pa.setProductActivityEndTime(model.getEndTime());
        pa.setActivityLimitNumber(model.getActivityLimit());
        pa.setPromotionType(3);
        pa.setProductActivityLimitNumber(model.getProductLimit());
        pa.setProductInActivityId(model.getProductInActivityId());
        pa.setActivityCatalogList(model.getActivityCatalogList().stream().map(t -> t.getCatalogId()).collect(Collectors.toList()));
        pa.setNewBuyer(model.isNewBuyer());
        return pa;
    }


//    public static LiveProductCartDto toLiveProductCartDto(LiveProducts model) {
//        if (model == null) {
//            return null;
//        }
//        LiveProductCartDto lp = new LiveProductCartDto();
//        lp.setLiveId(model.getLiveId());
//        lp.setLiveName("");
//        lp.setStartTime(model.getStartTime());
//        lp.setEndTime(model.getEndTime());
//        lp.setSellStatus(model.getSellStatus());
//        return lp;
//    }

    public static LiveProductCartDto toLiveProductCartDto(LiveProducts model) {
        if (model == null) {
            return null;
        }
        LiveProductCartDto lp = new LiveProductCartDto();
        try {
            BeanUtils.copyProperties(lp, model);
            return lp;
        } catch (Exception e) {
            throw new BizException("line 146:BeanUtils.copyProperties fail,liveid" + model.getLiveId(), e);
        }
    }

}