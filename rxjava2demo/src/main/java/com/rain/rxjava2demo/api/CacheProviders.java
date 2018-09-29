package com.rain.rxjava2demo.api;

import com.rain.rxjava2demo.bean.NewsListBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.Encrypt;
import io.rx_cache2.EncryptKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Expirable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Author:rain
 * Date:2018/9/25 14:51
 * Description:
 * 参见：https://www.jianshu.com/p/b58ef6b0624b
 *
 * 这里边存放一些需要缓存的接口
 *
 * useExpiredDataIfLoaderNotAvailable
 * 设置成true,会在数据为空或者发生错误时,忽视EvictProvider为true或者缓存过期的情况,继续使用缓存(前提是之前请求过有缓存)
 *
 * DynamicKey userName, EvictDynamicKey evictDynamicKey
 *
 * 标识符规则为: 方法名 + "$d$d$d$" + DynamicKey.dynamicKey + "$g$g$g$" + DynamicKeyGroup.group DynamicKey
 * DynamicKeyGroup为空时则返回空字符串,即什么都不传的标识符为: "方法名$d$d$d$$g$g$g$"
 * 内存中的缓存以map集合键值对存储，本地存储以上面方式文件名进行存储
 * DynamicKey 对于请求的接口一样，只是参数不同返回的数据不同的情况，比如分页，可以加上该参数，比如参数为分页，以缓存不同分页的数据
 * DynamicKeyGroup 允许有两个key，比如上一种情形，不仅分页不一样，还带有不同用户标识
 *
 * EvictProvider & EvictDynamicKey & EvictDynamicKeyGroup
 * 继承关系为EvictProvider < EvictDynamicKey < EvictDynamicKeyGroup,这三个对象你只能传其中的一个,多传一个都会报错,
 * 按理说你不管传那个对象都一样,因为里面都保存有一个boolean字段,根据这个字段判断是否使用缓存
 * 三个都不传,RxCache会自己new EvictProvider(false);,这样默认为false就不会删除任何缓存
 * EvictDynamicKeyGroup 只会删除对应分页下,对应用户的缓存
 * EvictDynamicKey 会删除那个分页下的所有缓存,比如你请求的是第一页下user1的数据,它不仅会删除user1的数据还会删除当前分页下其他user2,user3...的数据
 * EvictProvider 会删除当前接口下的所有缓存,比如你请求的是第一页的数据,它不仅会删除第一页的数据,还会把这个接口下其他分页的数据全删除
 *
 * 注解 @Expirable(false) rxcache有默认的缓存大小100M，当缓存达到规定值的95%时，会对一些缓存进行清除，该注解作用域为方法上，指定该方法缓存不清除
 *
 * 注解 @EncryptKey & @Encrypt
 * ..@EncryptKey作用域为接口，指明加密的key ，@Encrypt作用域为方法，指定哪些方法加密
 */
public interface CacheProviders {
    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<NewsListBean>> getNewsList(Observable<NewsListBean> obs);

    @Expirable(false)
    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<NewsListBean>> getNewsList2(Observable<NewsListBean> obs, DynamicKey page, EvictDynamicKey evictDynamicKey);

}
