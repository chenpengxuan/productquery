package com.ymatou.productquery.domain.mapper;

import com.ymatou.productquery.domain.model.Catalogs;
import com.ymatou.productquery.domain.model.Products;
import com.ymatou.productquery.model.res.ProductInListDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhujinfeng on 2017/4/28.
 */
public class ProductInListMapper {

    /**
     * 组装商品简化信息列表对象
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

        //productDto.setOwnProduct(product);

        return productDto;
    }

    /**
     * 取商品主图列表中的首图
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
     * @param catalogs
     * @return
     */
    private static boolean getIsAnyPreSale(List<Catalogs> catalogs)
    {
        if(catalogs == null || catalogs.isEmpty())
        {
            //// FIXME: 2017/4/28 log 规格数据不完整
            return false;
        }
        return catalogs.stream().anyMatch(c->c.isPriceSale());
    }

    /**
     * 判断商品中是否全部是预订规格
     * @param catalogs
     * @return
     */
    private static boolean getIsAllPreSale(List<Catalogs> catalogs)
    {
        if(catalogs == null || catalogs.isEmpty())
        {
            //// FIXME: 2017/4/28 log 规格数据不完整
            return false;
        }
        return catalogs.stream().allMatch(c->c.isPriceSale());
    }
}
