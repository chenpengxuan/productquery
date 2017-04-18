package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.model.res.LiveProductCartDto;
import com.ymatou.productquery.model.res.ProductActivityCartDto;
import com.ymatou.productquery.model.res.ProductInCartDto;
import com.ymatou.productquery.model.res.PropertyDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangyong on 2017/4/11.
 */

/**
 * 转换ProductInCartDto 对象
 */
public class DtoMapper {
    public static ProductInCartDto toProductInCartDto(Products product, Catalogs catalog, ActivityProducts activityProduct, List<Catalogs> catalogsList) {
        ProductInCartDto result = new ProductInCartDto();
        result.setProductId(product.getSpid());
        result.setCatalogId(catalog.getCid());
        result.setVersion(getSnapshotVersion(product.getVer()));
        result.setProductName(product.getTitle());
        result.setMainPicUrl(product.getPics() != null ? product.getPics().stream().findFirst().orElse("") : "");
        result.setValidStart(product.getStart());
        result.setValidEnd(product.getEnd());
        result.setBrandId(product.getBid());
        result.setMasterCategoryId(product.getMcatid());
        result.setCategoryId(product.getTcatid());
        result.setThirdCategoryId(product.getTcatid());
        result.setSellerId(product.getSid());
        result.setSellerName(product.getSname());
        result.setDeliveryMethod(product.getDeliv());
        result.setBondedArea(product.getBonded());
        result.setWeight(0d);
        result.setFreeShipping(product.getShipping() <=0);
        result.setTariffType(product.getTariffy());
        result.setLimitNumber(0);
        result.setLimitStartTime(new Date(1900, 1, 1));
        result.setProductCode(product.getPcode());
        result.setLocalReturn(product.getLocalr());
        result.setCatalogType(product.getCtype());
        result.setNoReasonReturn(product.isNoreason());
        result.setStockNum(getCatalogStock(catalog, activityProduct));
        result.setCatalogCount((int) catalogsList.stream().filter(t -> t.getSpid().equals(product.getSpid())).count());
        result.setPrice(getCatalogPrice(catalog, activityProduct));
        result.setSku(catalog.getSku());
        result.setPreSale(catalog.isPresale());
        result.setPspProduct(product.ispsp());
        result.setProperties(getCatalogPropertyList(catalog.getProps()));
        return result;
    }

    private static String getSnapshotVersion(String version) {
        return String.valueOf(Double.valueOf(version) / 1000);
    }

    private  static int getCatalogStock(Catalogs catalog, ActivityProducts activityProduct) {
        if (catalog == null) {
            return 0;
        }
        if (activityProduct != null) {

            if (activityProduct.getCatalogs().stream().filter(t -> t.getCid().equals( catalog.getCid())) != null) {
                return activityProduct.getCatalogs().stream().filter(t -> t.getCid().equals(catalog.getCid())).findFirst().orElse(new ActivityCatalogInfo()).getStock();
            }
        }
        return catalog.getStock();
    }

    private static Double getCatalogPrice(Catalogs catalog, ActivityProducts activityProduct) {
        if (catalog == null) {
            return 0d;
        }
        if (activityProduct != null) {

            if (activityProduct.getCatalogs().stream().filter(t -> t.getCid().equals( catalog.getCid())) != null) {
                return activityProduct.getCatalogs().stream().filter(t -> t.getCid().equals( catalog.getCid())).findFirst().orElse(new ActivityCatalogInfo()).getPrice();
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
        pa.setActivityId(model.getAid());
        pa.setActivityName(model.getAname());
        pa.setProductActivityStartTime(model.getStart());
        pa.setProductActivityEndTime(model.getEnd());
        pa.setActivityLimitNumber(model.getAlimit());
        pa.setPromotionType(3);
        pa.setProductActivityLimitNumber(model.getPlimit());
        pa.setProductInActivityId(model.getAid());
        pa.setActivityCatalogList(model.getCatalogs().stream().map(t -> t.getCid()).collect(Collectors.toList()));
        pa.setNewBuyer(model.isNbuyer());
        return pa;
    }

    public static LiveProductCartDto toLiveProductCartDto(LiveProducts model) {
        if (model == null) {
            return null;
        }
        LiveProductCartDto lp = new LiveProductCartDto();
        lp.setLiveId(model.getLid());
        lp.setLiveName("");
        lp.setStartTime(model.getStart());
        lp.setEndTime(model.getEnd());
        lp.setSellStatus(model.getStatus());
        return lp;
    }
}
