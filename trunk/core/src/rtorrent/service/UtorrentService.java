package rtorrent.service;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.service.gson.Build;
import rtorrent.service.gson.Props;
import rtorrent.service.gson.Torrents;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.Torrent;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: welvet
 * Date: 03.09.2010
 * Time: 22:58:17
 */
public class UtorrentService implements RtorrentService
{
    private static Logger logger;
    private UtorrentServiceConnector connector;

    public UtorrentService()
    {
        ConfigManager configManager = (ConfigManager) ContextUtils.lookup("rconfig");
        Config config = configManager.getConfig("rtorrent");
        String host = (String) config.getFieldValue("host");
        String port = (String) config.getFieldValue("port");
        String login = (String) config.getFieldValue("login");
        String pass = (String) config.getFieldValue("pass");
        String gui = (String) config.getFieldValue("gui");
        connector = new UtorrentServiceConnector(host, port, login, pass, gui);
        UtorrentService.logger = LoggerSingleton.getLogger();
        logger.info("Utorrent service загружен");
    }

    @Deprecated
    public UtorrentService(String host, String port, String login, String pass, String gui)
    {
        connector = new UtorrentServiceConnector(host, port, login, pass, gui);
        UtorrentService.logger = LoggerSingleton.getLogger();
    }

    public synchronized void add(ActionTorrent torrent) throws RtorrentServiceException
    {
        try
        {
            connector.uploadFile(torrent.getFile().getFile());
            logger.debug(torrent + " успешно добавлен");
        }
        catch (Exception e)
        {
            throw new RtorrentServiceException(e);
        }
    }

    public synchronized void start(Torrent torrent) throws RtorrentServiceException
    {
        InputStreamReader streamReader = null;
        try
        {
            Gson gson = new Gson();
            streamReader = connector.execute("", "&action=start&hash=" + torrent.getHash());
            Build build = gson.fromJson(streamReader, Build.class);
            streamReader.close();
            logger.debug("Торрент " + torrent + " успешно запущен");
        }
        catch (Exception e)
        {
            String s = torrent.getHash();
            logger.debug("Ошибка при запуске торрента: " + s, e);
            throw new RtorrentServiceException(e);
        }
        finally
        {
            if (streamReader != null)
                try
                {
                    streamReader.close();
                }
                catch (IOException e)
                {
                    throw new RtorrentServiceException(e);
                }
        }
    }

    public synchronized void stop(Torrent torrent) throws RtorrentServiceException
    {
        InputStreamReader streamReader = null;
        try
        {
            Gson gson = new Gson();
            streamReader = connector.execute("", "&action=stop&hash=" + torrent.getHash());
            Build build = gson.fromJson(streamReader, Build.class);
            streamReader.close();
            logger.debug("Торрент " + torrent + " успешно остановлен");
        }
        catch (Exception e)
        {
            String s = torrent.getHash();
            logger.debug("Ошибка при остановке торрента: " + s, e);
            throw new RtorrentServiceException(e);
        }
        finally
        {
            if (streamReader != null)
                try
                {
                    streamReader.close();
                }
                catch (IOException e)
                {
                    throw new RtorrentServiceException(e);
                }
        }
    }

    public synchronized void remove(Torrent torrent) throws RtorrentServiceException
    {
        InputStreamReader streamReader = null;
        try
        {
            Gson gson = new Gson();
            streamReader = connector.execute("", "&action=removetorrent&hash=" + torrent.getHash());
            Build build = gson.fromJson(streamReader, Build.class);
            streamReader.close();
            logger.debug("Торрент " + torrent + " успешно удален");
        }
        catch (Exception e)
        {
            String s = torrent.getHash();
            logger.debug("Ошибка при удалении торрента: " + s, e);
            throw new RtorrentServiceException(e);
        }
        finally
        {
            if (streamReader != null)
                try
                {
                    streamReader.close();
                }
                catch (IOException e)
                {
                    throw new RtorrentServiceException(e);
                }
        }
    }

    public synchronized Boolean verify(String hash) throws RtorrentServiceException
    {
        InputStreamReader streamReader = null;
        try
        {
            Gson gson = new Gson();
            streamReader = connector.execute("", "&action=getprops&hash=" + hash);
            Props props = gson.fromJson(streamReader, Props.class);
            streamReader.close();
            try
            {
                return props.getProps()[0].getHash().equals(hash);
            }
            catch (Exception e)
            {
                return false;
            }
        }
        catch (Exception e)
        {
            logger.debug("Ошибка при обновлении статуса торрента: " + hash, e);
            throw new RtorrentServiceException(e);
        }
        finally
        {
            if (streamReader != null)
                try
                {
                    streamReader.close();
                }
                catch (IOException e)
                {
                    throw new RtorrentServiceException(e);
                }
        }
    }

    public synchronized Set<ActionTorrent> getSet() throws RtorrentServiceException
    {
        InputStreamReader streamReader = null;
        try
        {
            Gson gson = new Gson();
            streamReader = connector.execute("", "&list=1");
            Torrents array = gson.fromJson(streamReader, Torrents.class);
            Set<ActionTorrent> torrentSet = new HashSet<ActionTorrent>();

            for (String[] strings : array.getTorrents())
            {
                Torrent torrent = new ActionTorrent();
                //0 HASH (string),
                torrent.setHash(strings[0]);
                //1 STATUS* (integer),
                int status = Integer.parseInt(strings[1]);
                //2 NAME (string),
                torrent.setName(strings[2]);
                //3 SIZE (integer in bytes),
                torrent.setSizeBytes(Long.parseLong(strings[3]));
                //5 DOWNLOADED (integer in bytes),
                torrent.setBytesDone(Long.parseLong(strings[5]));
                //6 UPLOADED (integer in bytes),
                torrent.setUpTotal(Long.parseLong(strings[6]));
                //7 RATIO (integer in per mils),
                torrent.setRatio(Long.parseLong(strings[7]));
                //11 LABEL (string),
                torrent.setLabel(strings[11]);

//                TODO: not implemented in utorrent
                torrent.setDir("");

                //бред же
                torrent.setHashChecking((2 | status) == status ? 1L : 0);
                torrent.setHashChecked((8 | status) == status ? 1L : 0);
                torrent.setStart((1 | status) == status ? 1L : 0);
                //calc
                torrent.setLeftBytes(torrent.getSizeBytes() - torrent.getBytesDone());

                //12 PEERS CONNECTED (integer),
                torrent.setPeersConnected(Long.parseLong(strings[12]));
                //13 PEERS IN SWARM (integer),
                torrent.setPeersComplite(Long.parseLong(strings[13]));


                torrentSet.add((ActionTorrent) torrent);
            }
            streamReader.close();
            logger.debug("Список торрентов получен");
            return torrentSet;
        }
        catch (Exception e)
        {
            throw new RtorrentServiceException(e);
        }
        finally
        {
            if (streamReader != null)
                try
                {
                    streamReader.close();
                } catch (IOException e)
                {
                    throw new RtorrentServiceException(e);
                }
        }
    }

    public synchronized Boolean isAlive()
    {
        InputStreamReader streamReader = null;
        try
        {
            Gson gson = new Gson();
            streamReader = connector.execute("", "");
            Build build = gson.fromJson(streamReader, Build.class);
            streamReader.close();
            return build.getBuild() != null && !build.getBuild().isEmpty();
        }
        catch (Exception e)
        {
            logger.debug("Ошибка при обновлении статуса utorrent", e);
        }
        finally
        {
            if (streamReader != null)
                try
                {
                    streamReader.close();
                }
                catch (IOException e)
                {
                    //ignore
                }
        }
        return false;
    }

    public synchronized void launch(List<Torrent> list) throws RtorrentServiceException
    {
        for (Torrent torrent : list)
        {
            try
            {
                start(torrent);
            } catch (Exception e)
            {
                logger.debug("Во время запуска " + torrent + " произошла ошибка: " + e.getMessage());
            }
        }
    }

    public synchronized void shutdown(List<Torrent> list) throws RtorrentServiceException
    {
        for (Torrent torrent : list)
        {
            try
            {
                stop(torrent);
            } catch (Exception e)
            {
                logger.debug("Во время остановки " + torrent + " произошла ошибка: " + e.getMessage());
            }
        }
    }
}
