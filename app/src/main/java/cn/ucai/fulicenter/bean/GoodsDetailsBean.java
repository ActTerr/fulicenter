package cn.ucai.fulicenter.bean;

import java.util.List;

/**
 * Created by mac-yk on 2016/10/13.
 */

public class GoodsDetailsBean {
    public class Albums {
        private int pid;

        private int imgId;

        private String imgUrl;

        private String thumbUrl;

        public void setPid(int pid){
            this.pid = pid;
        }
        public int getPid(){
            return this.pid;
        }
        public void setImgId(int imgId){
            this.imgId = imgId;
        }
        public int getImgId(){
            return this.imgId;
        }
        public void setImgUrl(String imgUrl){
            this.imgUrl = imgUrl;
        }
        public String getImgUrl(){
            return this.imgUrl;
        }
        public void setThumbUrl(String thumbUrl){
            this.thumbUrl = thumbUrl;
        }
        public String getThumbUrl(){
            return this.thumbUrl;
        }

    }




    public class Properties {
        private int id;

        private int goodsId;

        private int colorId;

        private String colorName;

        private String colorCode;

        private String colorImg;

        private String colorUrl;

        private List<Albums> albums ;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setGoodsId(int goodsId){
            this.goodsId = goodsId;
        }
        public int getGoodsId(){
            return this.goodsId;
        }
        public void setColorId(int colorId){
            this.colorId = colorId;
        }
        public int getColorId(){
            return this.colorId;
        }
        public void setColorName(String colorName){
            this.colorName = colorName;
        }
        public String getColorName(){
            return this.colorName;
        }
        public void setColorCode(String colorCode){
            this.colorCode = colorCode;
        }
        public String getColorCode(){
            return this.colorCode;
        }
        public void setColorImg(String colorImg){
            this.colorImg = colorImg;
        }
        public String getColorImg(){
            return this.colorImg;
        }
        public void setColorUrl(String colorUrl){
            this.colorUrl = colorUrl;
        }
        public String getColorUrl(){
            return this.colorUrl;
        }
        public void setAlbums(List<Albums> albums){
            this.albums = albums;
        }
        public List<Albums> getAlbums(){
            return this.albums;
        }

    }



    public class Root {
        private int id;

        private int goodsId;

        private int catId;

        private String goodsName;

        private String goodsEnglishName;

        private String goodsBrief;

        private String shopPrice;

        private String currencyPrice;

        private String promotePrice;

        private String rankPrice;

        private boolean isPromote;

        private String goodsThumb;

        private String goodsImg;

        private int addTime;

        private String shareUrl;

        private List<Properties> properties ;

        private boolean promote;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setGoodsId(int goodsId){
            this.goodsId = goodsId;
        }
        public int getGoodsId(){
            return this.goodsId;
        }
        public void setCatId(int catId){
            this.catId = catId;
        }
        public int getCatId(){
            return this.catId;
        }
        public void setGoodsName(String goodsName){
            this.goodsName = goodsName;
        }
        public String getGoodsName(){
            return this.goodsName;
        }
        public void setGoodsEnglishName(String goodsEnglishName){
            this.goodsEnglishName = goodsEnglishName;
        }
        public String getGoodsEnglishName(){
            return this.goodsEnglishName;
        }
        public void setGoodsBrief(String goodsBrief){
            this.goodsBrief = goodsBrief;
        }
        public String getGoodsBrief(){
            return this.goodsBrief;
        }
        public void setShopPrice(String shopPrice){
            this.shopPrice = shopPrice;
        }
        public String getShopPrice(){
            return this.shopPrice;
        }
        public void setCurrencyPrice(String currencyPrice){
            this.currencyPrice = currencyPrice;
        }
        public String getCurrencyPrice(){
            return this.currencyPrice;
        }
        public void setPromotePrice(String promotePrice){
            this.promotePrice = promotePrice;
        }
        public String getPromotePrice(){
            return this.promotePrice;
        }
        public void setRankPrice(String rankPrice){
            this.rankPrice = rankPrice;
        }
        public String getRankPrice(){
            return this.rankPrice;
        }
        public void setIsPromote(boolean isPromote){
            this.isPromote = isPromote;
        }
        public boolean getIsPromote(){
            return this.isPromote;
        }
        public void setGoodsThumb(String goodsThumb){
            this.goodsThumb = goodsThumb;
        }
        public String getGoodsThumb(){
            return this.goodsThumb;
        }
        public void setGoodsImg(String goodsImg){
            this.goodsImg = goodsImg;
        }
        public String getGoodsImg(){
            return this.goodsImg;
        }
        public void setAddTime(int addTime){
            this.addTime = addTime;
        }
        public int getAddTime(){
            return this.addTime;
        }
        public void setShareUrl(String shareUrl){
            this.shareUrl = shareUrl;
        }
        public String getShareUrl(){
            return this.shareUrl;
        }
        public void setProperties(List<Properties> properties){
            this.properties = properties;
        }
        public List<Properties> getProperties(){
            return this.properties;
        }
        public void setPromote(boolean promote){
            this.promote = promote;
        }
        public boolean getPromote(){
            return this.promote;
        }

    }
}
