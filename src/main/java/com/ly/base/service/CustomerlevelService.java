package  com.ly.base.service;

import com.ly.base.vo.Customerlevel;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class CustomerlevelService extends IdEntityService<Customerlevel> {

	public static String CACHE_NAME = "customerlevel";
    public static String CACHE_COUNT_KEY = "customerlevel_count";

    public List<Customerlevel> queryCache(Cnd c,Page p)
    {
        List<Customerlevel> list_customerlevel = null;
        String cacheKey = "customerlevel_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_customerlevel = this.query(c, p);
            cache.put(new Element(cacheKey, list_customerlevel));
        }else{
            list_customerlevel = (List<Customerlevel>)cache.get(cacheKey).getObjectValue();
        }
        return list_customerlevel;
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


