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
    private Map<String, Object> fields = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFieldValue(String name) {
        return fields.get(name);
    }

    public void setFieldValue(String name, Object value) {
        fields.put(name, value);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config config = (Config) o;

        return !(name != null ? !name.equals(config.name) : config.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
