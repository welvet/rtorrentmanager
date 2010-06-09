package dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 03.06.2010
 * Time: 22:46:32
 */
public class Dialog {
    private String name;
    private String path;
    private List<Input> inputs = new ArrayList<Input>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public void addField(Input input) {
        inputs.add(input);        
    }
}
