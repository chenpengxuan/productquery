package com.ymatou.productquery.domain.model;

import java.util.List;

/**
 * Created by zhangyong on 2017/4/11.
 */
public class CatalogInfo {
    private String CatalogId ;
    private boolean IsPreSale ;
    private List<PriceInfo> PriceList ;
    private List<CatalogPropertyInfo> PropertyList ;
    private String SKU ;
    private List<StockInfo> StockList ;

    public String getCatalogId() {
        return CatalogId;
    }

    public void setCatalogId(String catalogId) {
        CatalogId = catalogId;
    }

    public boolean isPreSale() {
        return IsPreSale;
    }

    public void setPreSale(boolean preSale) {
        IsPreSale = preSale;
    }

    public List<PriceInfo> getPriceList() {
        return PriceList;
    }

    public void setPriceList(List<PriceInfo> priceList) {
        PriceList = priceList;
    }

    public List<CatalogPropertyInfo> getPropertyList() {
        return PropertyList;
    }

    public void setPropertyList(List<CatalogPropertyInfo> propertyList) {
        PropertyList = propertyList;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public List<StockInfo> getStockList() {
        return StockList;
    }

    public void setStockList(List<StockInfo> stockList) {
        StockList = stockList;
    }
}
