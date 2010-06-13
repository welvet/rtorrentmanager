package rtorrent.tracker;

import dialog.*;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.TrackerUtils;

/**
 * Для всех трекеров одинаковые настройки
 * User: welvet
 * Date: 13.06.2010
 * Time: 14:31:39
 */
public class TrackerDialog extends Dialog {
    public TrackerDialog(ActionTorrent torrent) {
        this.setName(torrent.getName());
        this.setPath("torrent");

        SimpleTrackerImpl tracker = (SimpleTrackerImpl) torrent.getTracker();

        //урл
        Input input = new TextField();
        input.setFieldName("requestUrl");
        input.setFieldValue(tracker != null ? tracker.getUrl() : "");
        input.setFieldText("Url");
        addField(input);
        //хеш
        input = new TextField();
        input.setFieldName("hash");
        input.setFieldValue(torrent.getHash());
        input.setFieldText("Hash");
        input.setHidden(true);
        addField(input);
        //watching
        input = new CheckField();
        input.setFieldName("watching");
        input.setFieldValue(torrent.isWatching());
        input.setFieldText("Наблюдаемый");
        addField(input);
        //tracker
        SelectField selectField = new SelectField();
        selectField.setFieldName("tracker");
        selectField.setFieldValues(TrackerUtils.strings());
        selectField.setFieldValue(tracker != null ? TrackerUtils.trackers2string(tracker.getTracker()) : "");
        selectField.setFieldText("Трекер");
        addField(selectField);

    }

    public Boolean isWatching() {
        for (Input input : getInputs()) {
            if (input.getFieldName().equals("watching"))
                return (Boolean) input.getFieldValue();
        }
        return false;
    }

    public String getHash() {
        for (Input input : getInputs()) {
            if (input.getFieldName().equals("hash"))
                return (String) input.getFieldValue();
        }
        return "";
    }

    public Trackers getTracekr() {
        for (Input input : getInputs()) {
            if (input.getFieldName().equals("tracker")) {
                return TrackerUtils.string2trackers((String) input.getFieldValue());
            }
        }
        return null;
    }

    public String getUrl() {
        for (Input input : getInputs()) {
            if (input.getFieldName().equals("requestUrl"))
                return (String) input.getFieldValue();
        }
        return "";
    }
}
