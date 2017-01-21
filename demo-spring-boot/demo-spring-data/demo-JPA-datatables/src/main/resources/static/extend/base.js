/**
 * Created by dongsilin on 2017/1/6.
 */
/** ---------------------- 字符串 ---------------------*/
    /**
     * 两端去空格
     * @returns {string}
     */
    String.prototype.trim=function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
    };
    /**
     * 头部去空格
     * @returns {string}
     */
    String.prototype.ltrim=function(){
        return this.replace(/(^\s*)/g,"");
    };
    /**
     * 尾部去空格
     * @returns {string}
     */
    String.prototype.rtrim=function(){
        return this.replace(/(\s*$)/g,"");
    };
/** -------------------------------------------*/


/** ---------------------- 数组 ---------------------*/
    /**
     * 删除数组
     * @param index 起始下标
     * @param count 删除个数
     * @returns {*} 被删除的元素
     */
    Array.prototype.remove = function(index, count){
        var len = this.length;
        if(isNaN(index) || index > len || index < 0) return false;
        if(index == 0 && count == 1) return this.shift();
        if(index+1 == len) return this.pop();
        if(isNaN(count) || count < 1) return false;
        if(index+count > len) return this.splice(index, len-index);
        return this.splice(index, count);
    };
    /**
     * 获取数组下标区间所在的部分
     * @param begin 开始下标
     * @param end 结束下标，结果不包括对应的元素
     * @returns {*} 数组下标区间所在的部分
     */
    Array.prototype.indexBetween = function(begin, end){
        var len = this.length;
        if(isNaN(index) || index > len || index < 0) return false;
        if(isNaN(end) || end >= len-1) return this.slice(start);
        if(begin >= end) return false;
        return this.slice(start,end);
    };
/** -------------------------------------------*/