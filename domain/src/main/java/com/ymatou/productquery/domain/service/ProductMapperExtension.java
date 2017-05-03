package com.ymatou.productquery.domain.service;

import com.ymatou.productquery.domain.model.*;
import com.ymatou.productquery.infrastructure.util.Tuple;
import com.ymatou.productquery.model.BizException;
import com.ymatou.productquery.model.res.*;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Created by zhangyong on 2017/4/11.
 */

/**
 * 转换ProductInCartDto 对象
 */
public class ProductMapperExtension {

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
        result.setFreeShipping(product.getIsFreeShipping() <= 0);
        result.setTariffType(product.getTariffType());
        result.setLimitNumber(0);
        result.setLimitStartTime(new Date(1900, 1, 1));
        result.setProductCode(product.getProductCode() == null ? "" : product.getProductCode());
        result.setLocalReturn(product.getLocalReturn());
        result.setCatalogType(product.getCatalogType());
        result.setNoReasonReturn(product.isNoReasonReturn());
        result.setStockNum(getCatalogStock(catalog, activityProduct));
        result.setCatalogCount((int) catalogsList.stream().filter(t -> t.getProductId().equals(product.getProductId())).count());
        result.setPrice(getCatalogPrice(catalog, activityProduct));
        result.setSku(catalog.getSku() == null ? "" : catalog.getSku());
        result.setPreSale(catalog.isPriceSale());
        result.setPspProduct(product.isPspProduct());
        result.setProperties(getCatalogPropertyList(catalog.getProps()));
        return result;
    }

    public static ProductDetailDto toProductDetailDto(Products products, List<Catalogs> catalogsList, ActivityProducts activityProducts) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        try {
            BeanUtils.copyProperties(productDetailDto, products);
        } catch (Exception ex) {
            throw new BizException("line 62:BeanUtils.copyProperties fail,liveid:" + products.getProductId(), ex);
        }
        productDetailDto.setFreeShipping(products.getIsFreeShipping() <= 0);
        productDetailDto.setHasTextDescription(products.isNewDesc());
        productDetailDto.setNewProduct(isNewestProduct(products.getNewStartTime(), products.getNewEndTime()));
        productDetailDto.setHotRecmd(products.isTopProduct());
        productDetailDto.setVersion(getSnapshotVersion(products.getVersion()));
        productDetailDto.setCatalogList(toCatalogDtoList(catalogsList, activityProducts));
        return productDetailDto;
    }

    public static ProductLiveDto toProductLiveDto(LiveProducts liveProduct) {
        if (liveProduct == null) {
            return null;
        }
        ProductLiveDto productLiveDto = new ProductLiveDto();
        try {
            BeanUtils.copyProperties(productLiveDto, liveProduct);
        } catch (Exception ex) {
            throw new BizException("line 62:BeanUtils.copyProperties fail,liveid:" + liveProduct.getLiveId(), ex);
        }
        return productLiveDto;
    }

    /**
     * 返回活动商品的最高价，最低价
     *
     * @param catalogDtoList
     * @param activityProduct
     * @return
     */
    public static Tuple<Double, Double> getMaxMinPrice(List<CatalogDto> catalogDtoList, ActivityProducts activityProduct) {
        List<Double> activityPrices = activityProduct.getCatalogs().stream().map(t -> t.getActivityPrice()).collect(Collectors.toList());
        return new Tuple<>(Collections.max(activityPrices), Collections.min(activityPrices));
    }

    /**
     * @param model
     * @return
     */
    public static ProductActivityDto toProductActivityDto(ActivityProducts model) {
        if (model == null) {
            return null;
        }
        ProductActivityDto productActivityDto = new ProductActivityDto();
        try {
            BeanUtils.copyProperties(productActivityDto, model);
        } catch (Exception ex) {
            throw new BizException("line 85:BeanUtils.copyProperties fail,liveid:" + model.getProductInActivityId(), ex);
        }
        productActivityDto.setPromotionType(3);
        productActivityDto.setBeginTimeOfProductInActivity(model.getStartTime());
        productActivityDto.setEndTimeOfProductInActivity(model.getEndTime());
        productActivityDto.setCatalogList(model.getCatalogs().stream().map(t -> t.getCatalogId()).collect(Collectors.toList()));
        return productActivityDto;
    }

    /**
     * 历史数据类型转换
     *
     * @param model
     * @return
     */
    public static ProductHistoryDto toProductHistoryDto(HistoryProductModel model) {
        if (model == null) {
            return null;
        }
        ProductHistoryDto productHistoryDto = new ProductHistoryDto();
        try {
            BeanUtils.copyProperties(productHistoryDto, model);
            productHistoryDto.setMainPic(model.getPicList() != null ? model.getPicList().stream().findFirst().orElse("") : "");
            productHistoryDto.setFreeShipping(model.getFreight() <= 0);
            productHistoryDto.setPrice(model.getMinCatalogPrice());
        } catch (Exception ex) {
            throw new BizException("line 72:BeanUtils.copyProperties fail,productid:" + model.getProductId(), ex);
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
        if (model == null) {
            return null;
        }
        try {
            ProductHistoryDto productHistoryDto = new ProductHistoryDto();
            BeanUtils.copyProperties(productHistoryDto, model);
            productHistoryDto.setMainPic(model.getPicList() != null ? model.getPicList().stream().findFirst().orElse("") : "");
            productHistoryDto.setFreeShipping(model.getIsFreeShipping() <= 0);
            productHistoryDto.setPrice(Double.parseDouble(model.getMinCatalogPrice().split(",")[0]));
            return productHistoryDto;
        } catch (Exception ex) {
            throw new BizException("line 98:BeanUtils.copyProperties fail,productid:" + model.getProductId(), ex);
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
            throw new BizException("line 111:BeanUtils.copyProperties fail,liveid:" + model.getLiveId(), e);
        }
    }

    public static ProductDetailDto toProductDetailDto(HistoryProductModel historyProductModel) {
        if (historyProductModel == null)
            return null;
        try {
            ProductDetailDto productDetailDto = new ProductDetailDto();
            BeanUtils.copyProperties(historyProductModel, productDetailDto);
            productDetailDto.setStatus(ProductStatusEnum.Disable.getCode());
            return productDetailDto;
        } catch (Exception ex) {
            throw new BizException("line 185:BeanUtils.copyProperties fail,productid:" + historyProductModel.getProductId(), ex);
        }
    }

    public static ProductActivityCartDto toProductActivityCartDto(ActivityProducts model) {
        if (model == null) {
            return null;
        }
        ProductActivityCartDto pa = new ProductActivityCartDto();
        pa.setActivityId(model.getActivityId());
        pa.setActivityName(model.getActivityName());
        pa.setProductActivityStartTime(model.getStartTime());
        pa.setProductActivityEndTime(model.getEndTime());
        pa.setActivityLimitNumber(model.getActivityLimit());
        pa.setPromotionType(3);
        pa.setProductActivityLimitNumber(model.getProductLimit());
        pa.setProductInActivityId(model.getProductInActivityId());
        pa.setActivityCatalogList(model.getCatalogs().stream().map(t -> t.getCatalogId()).collect(Collectors.toList()));
        pa.setNewBuyer(model.isNewBuyer());
        return pa;
    }

    private static List<CatalogDto> toCatalogDtoList(List<Catalogs> catalogsList, ActivityProducts activityProduct) {
        List<CatalogDto> catalogDtoList = new ArrayList<>();
        catalogsList.forEach(t ->
        {
            CatalogDto catalogDto = new CatalogDto();
            try {
                BeanUtils.copyProperties(catalogDto, t);
            } catch (Exception ex) {
                throw new BizException("line 142:BeanUtils.copyProperties fail,liveid:" + t.getCatalogId(), ex);
            }
            if (activityProduct != null) {
                ActivityCatalogInfo activityCatalogInfo = activityProduct.getCatalogs().stream().
                        filter(x -> x.getCatalogId().equals(t.getCatalogId())).findFirst().orElse(null);
                if (activityCatalogInfo != null) {
                    catalogDto.setInActivity(true);
                    catalogDto.setActivityPrice(activityCatalogInfo.getActivityPrice());
                    catalogDto.setActivityStock(activityCatalogInfo.getActivityStock());
                }
            }
            // 规格属性
            List<CatalogPropertyDto> catalogPropertyDtos = new ArrayList<>();
            if (t.getProps() != null && !t.getProps().isEmpty()) {
                t.getProps().forEach(q -> {
                    CatalogPropertyDto catalogPropertyDto = new CatalogPropertyDto();
                    catalogPropertyDto.setName(q.getName());
                    catalogPropertyDto.setPicUrl(q.getPic());
                    catalogPropertyDto.setSort(1);
                    catalogPropertyDto.setValue(q.getValue());
                    catalogPropertyDtos.add(catalogPropertyDto);
                });
            }
            catalogDto.setPropertyList(catalogPropertyDtos);

            catalogDtoList.add(catalogDto);
        });
        return catalogDtoList;
    }

    private static String getSnapshotVersion(String version) {
        return String.valueOf(Double.valueOf(version) / 1000);
    }

    private static int getCatalogStock(Catalogs catalog, ActivityProducts activityProduct) {
        if (catalog == null) {
            return 0;
        }
        if (activityProduct != null) {

            if (activityProduct.getCatalogs().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())) != null) {
                return activityProduct.getCatalogs().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())).findFirst().orElse(new ActivityCatalogInfo()).getActivityStock();
            }
        }
        return catalog.getStock();
    }

    private static Double getCatalogPrice(Catalogs catalog, ActivityProducts activityProduct) {
        if (catalog == null) {
            return 0d;
        }
        if (activityProduct != null) {

            if (activityProduct.getCatalogs().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())) != null) {
                return activityProduct.getCatalogs().stream().filter(t -> t.getCatalogId().equals(catalog.getCatalogId())).findFirst().orElse(new ActivityCatalogInfo()).getActivityPrice();
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

    /**
     * 验证新品商品
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private static boolean isNewestProduct(Date startTime, Date endTime) {
        Date now = new Date();
        return !now.before(startTime) && !now.after(endTime);
    }

    /**
     * 组装商品简化信息列表对象
     *
     * @param product
     * @param catalogs
     * @return
     */
    public static ProductInListDto toProductInListDto(Products product, List<Catalogs> catalogs) {
        ProductInListDto productDto = new ProductInListDto();
        if (product == null || catalogs == null || catalogs.isEmpty()) {
            //// FIXME: 2017/4/28 : 无该商品，
            return null;
        }

        productDto.setProdId(product.getProdId());
        productDto.setProductId(product.getProductId());
        productDto.setVersion(product.getVersion());
        productDto.setTitle(product.getTitle());
        productDto.setMainPic(getProductFirstPicture(product.getPicList()));
        productDto.setValidStart(product.getValidStart());
        productDto.setValidEnd(product.getValidEnd());
        productDto.setMinPrice(getPrice(product.getMinCatalogPrice()));
        productDto.setMaxPrice(getPrice(product.getMaxCatalogPrice()));
        productDto.setSellerId(product.getSellerId());
        productDto.setSellerName(product.getSellerName());
        productDto.setCountryId(product.getCountryId());
        productDto.setStock(getStock(catalogs));
        productDto.isFreeShipping(true);
        productDto.setTariffType(product.getTariffType());
        productDto.setDeliveryMethod(product.getDeliveryMethod());
        productDto.isNewProduct(getNewestProduct(product.isNewProduct(), product.getNewStartTime(), product.getNewEndTime()));
        productDto.setNewStartTime(product.getNewStartTime());
        productDto.setNewEndTime(product.getNewEndTime());
        productDto.setIsHotRecmd(product.isTopProduct());
        productDto.isAnyPreSale(getIsAnyPreSale(catalogs));
        productDto.isAllPreSale(getIsAnyPreSale(catalogs));
        productDto.isPspProduct(product.isPspProduct());
        //// FIXME: 2017/4/28 代做
        //productDto.setOwnProduct(product);

        return productDto;
    }

    /**
     * 取商品主图列表中的首图
     *
     * @param productPictureList
     * @return
     */
    private static String getProductFirstPicture(List<String> productPictureList) {
        if (productPictureList == null || productPictureList.isEmpty()) {
            //Fixme : exception 商品没有首图异常。
        }
        return productPictureList.get(0);
    }

    /**
     * 取商品价格
     *
     * @param catalogPrice
     * @return
     */
    private static double getPrice(String catalogPrice) {
        if (catalogPrice == null || catalogPrice.isEmpty()) {
            //// FIXME: 2017/4/28 log 取不到商品价格异常
            return 0;
        }
        return Double.valueOf(catalogPrice.split(",")[0]);
    }

    /**
     * 取商品的所有规格库存的汇总
     *
     * @param catalogs
     * @return
     */
    private static int getStock(List<Catalogs> catalogs) {
        if (catalogs == null || catalogs.isEmpty()) {
            //// FIXME: 2017/4/28 log 取不到商品的库存
            return 0;
        }
        return catalogs.stream().collect(Collectors.summingInt(Catalogs::getStock));
    }

    /**
     * 判断商品是否新品
     *
     * @param isNewProduct
     * @param startTime
     * @param endTime
     * @return
     */
    private static boolean getNewestProduct(boolean isNewProduct, Date startTime, Date endTime) {
        if (!isNewProduct) return false;

        Date now = new Date();
        return (startTime.getTime() <= now.getTime() && endTime.getTime() >= now.getTime());
    }

    /**
     * 判断商品中是否有预订的规格
     *
     * @param catalogs
     * @return
     */
    private static boolean getIsAnyPreSale(List<Catalogs> catalogs) {
        if (catalogs == null || catalogs.isEmpty()) {
            //// FIXME: 2017/4/28 log 规格数据不完整
            return false;
        }
        return catalogs.stream().anyMatch(c -> c.isPriceSale());
    }

    /**
     * 判断商品中是否全部是预订规格
     *
     * @param catalogs
     * @return
     */
    private static boolean getIsAllPreSale(List<Catalogs> catalogs) {
        if (catalogs == null || catalogs.isEmpty()) {
            //// FIXME: 2017/4/28 log 规格数据不完整
            return false;
        }
        return catalogs.stream().allMatch(c -> c.isPriceSale());
    }
}
