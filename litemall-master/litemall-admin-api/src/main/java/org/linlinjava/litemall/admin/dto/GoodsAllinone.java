package org.linlinjava.litemall.admin.dto;

import org.linlinjava.litemall.db.domain.*;

public class GoodsAllinone {

    LitemallGoods goods;

    LitemallGoodsSpecification[] specifications;

    LitemallGoodsAttribute[] attributes;

    LitemallGoodsProduct[] products;

    LitemallGoodsDyPrice[] goodsDyPrices;

    public LitemallGoods getGoods() {
        return goods;
    }

    public  LitemallGoodsDyPrice[]  getGoodsDyPrices() {
        return goodsDyPrices;
    }

    public void   setGoodsDyPrices(LitemallGoodsDyPrice[] goodsDyPrices) {
        this.goodsDyPrices = goodsDyPrices;
    }

    public void setGoods(LitemallGoods goods) {
        this.goods = goods;
    }

    public LitemallGoodsProduct[] getProducts() {
        return products;
    }

    public void setProducts(LitemallGoodsProduct[] products) {
        this.products = products;
    }

    public LitemallGoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(LitemallGoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public LitemallGoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(LitemallGoodsAttribute[] attributes) {
        this.attributes = attributes;
    }



}
