package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.model.BizException;
import com.ymatou.productquery.model.res.*;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.jetbrains.annotations.NotNull;
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
        result.setProperties(getCatalogPropertyList(catalog.getProps()));
        return result;
    }

    /**
     * 历史数据类型转换
     *
     * @param model
     * @return
     */
    public static ProductHistoryDto toProductHistoryDto(ProductDetailModel model) {
        if (model == null) {
            return null;
        }
        ProductHistoryDto productHistoryDto = new ProductHistoryDto();
        try {
            BeanUtils.copyProperties(productHistoryDto, model);
        } catch (Exception ex) {
        }

//        productHistoryDto.setProductId(model.getProductId());
//        productHistoryDto.setTitle(model.getTitle());
//        productHistoryDto.setMainPic(model.getPicList() != null ? model.getPicList().stream().findFirst().orElse("") : "");
//        productHistoryDto.setTariffType(model.getTariffType());
//        productHistoryDto.setFreeShipping(model.getFreight() <= 0);
//        productHistoryDto.setDeliveryMethod(model.getDeliveryMethod());
//        productHistoryDto.setLocalReturn(model.getLocalReturn());
//        productHistoryDto.setValidEnd(model.getValidEnd());
//        productHistoryDto.setValidStart(model.getValidStart());
//        productHistoryDto.setPrice(model.getMinCatalogPrice());
        return productHistoryDto;
    }

    public static ProductHistoryDto toProductHistoryDto(Products model) {
        try {
            ProductHistoryDto productHistoryDto = new ProductHistoryDto();
            BeanUtils.copyProperties(productHistoryDto, model);
            return productHistoryDto;
        } catch (Exception ex) {
            throw new BizException("line 146:BeanUtils.copyProperties fail,liveid" + model.getProductId(), ex);
        }
    }

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
        List<PropertyDto> propertyDtoList = new ArrayList<>();
        if (propertyInfoList == null || propertyInfoList.isEmpty()) {
            propertyDtoList.add(new PropertyDto() {
            });
            return propertyDtoList;
        }

        propertyInfoList.forEach(t -> {
                    PropertyDto prodto = new PropertyDto();
                    prodto.setPropertyValue(t.getValue());
                    prodto.setPropertyName(t.getName());
                    prodto.setPropertyPictureUrl(t.getPic());
                    propertyDtoList.add(prodto);
                }
        );

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


}
