package rtorrent.config;

import java.util.HashMap;
import java.util.Map;

/**
 * User: welvet
 * Date: 05.06.2010
 * Time: 10:53:23
 */
public class Config {
    private String name;
    private Map<String, String> fields = new HashMap<String, String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldValue(String name) {
        return fields.get(name);
    }

    public void setFieldValue(String name, String value) {
        fields.put(name, value);
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config config = (Config) o;

        if (name != null ? !name.equals(config.name) : config.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
