package com.leo.leomasapp.Data;

public class ProductClass {
    private String nameProduct;
    private Long priceProduct;
    private String detailProduct;
    private String jenisProduct;
    private int imageProduct1;
    private int imageProduct2;
    private int imageProduct3;

    public ProductClass(){

    }
    public ProductClass(String nameProduct, Long priceProduct, String detailProduct, String jenisProduct, int imageProduct1, int imageProduct2, int imageProduct3) {
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.detailProduct = detailProduct;
        this.imageProduct1 = imageProduct1;
        this.imageProduct2 = imageProduct2;
        this.imageProduct3 = imageProduct3;
        this.jenisProduct = jenisProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public Long getPriceProduct() {
        return priceProduct;
    }
    public String getDetailProduct() {
        return detailProduct;
    }
    public String getJenisProduct() {
        return jenisProduct;
    }

   public int getImageProduct1() {
        return imageProduct1;

   }
   public int getImageProduct2() {
       return imageProduct2;
   }
   public int getImageProduct3() {
       return imageProduct3;
   }

}
