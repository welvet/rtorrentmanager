package rtorrent.service;

import java.util.HashMap;
import java.util.Map;

/**
 * User: welvet
 * Date: 12.09.2010
 * Time: 14:56:47
 */
public class ServiceReslover
{
    private static Map<String, Class<? extends RtorrentService>> classMap = new HashMap<String, Class<? extends RtorrentService>>();
    static
    {
        classMap.put("rtorrent", RtorrentServiceImpl.class);
        classMap.put("utorrent", UtorrentService.class);
    }

    public static RtorrentService resolve(String name) throws IllegalAccessException, InstantiationException
    {
        Class aClass = classMap.get(name);
        return (RtorrentService) aClass.newInstance();
    }
}
