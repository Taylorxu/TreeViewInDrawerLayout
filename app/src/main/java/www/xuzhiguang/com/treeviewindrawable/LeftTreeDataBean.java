package www.xuzhiguang.com.treeviewindrawable;

import java.util.List;

public class LeftTreeDataBean {
    public LeftTreeDataBean() {
    }

    public LeftTreeDataBean(int code, String name, List<LeftTreeDataBean> children) {
        this.code = code;
        this.name = name;
        this.children = children;
    }

    private int code;
    private String name;
    private List<LeftTreeDataBean> children;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LeftTreeDataBean> getChildren() {
        return children;
    }

    public void setChildren(List<LeftTreeDataBean> children) {
        this.children = children;
    }
}
