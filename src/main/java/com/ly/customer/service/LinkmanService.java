package  com.ly.customer.service;

import com.ly.customer.vo.Linkman;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class LinkmanService extends IdEntityService<Linkman> {

	public static String CACHE_NAME = "linkman";
    public static String CACHE_COUNT_KEY = "linkman_count";

    public List<Linkman> queryCache(Cnd c,Page p)
    {
        List<Linkman> list_linkman = null;
        String cacheKey = "linkman_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_linkman = this.query(c, p);
            cache.put(new Element(cacheKey, list_linkman));
        }else{
            list_linkman = (List<Linkman>)cache.get(cacheKey).getObjectValue();
        }
        return list_linkman;
    }

    public int listCount(Cnd c)
    {
        Long num = 0L;
        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(CACHE_COUNT_KEY) == null)
        {
            num = Long.valueOf(this.count(c));
            cache.put(new Element(CACHE_COUNT_KEY, num));
        }else{
            num = (Long)cache.get(CACHE_COUNT_KEY).getObjectValue();
        }
        return num.intValue();
    }



}


