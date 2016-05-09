package www.treeview.com.imooctreeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzy on 2015/11/13.
 */
public class Node {

    private int id;
    /**
     * 根节点的pid = 0
     */
    private int pId = 0;
    private String name;

    /**
     * 树的层级
     */
    private int level;

    /**
     * 是否展开
     */
    private boolean isExpend;
    private int icon;

    private Node parent;
    private List<Node> children = new ArrayList<Node>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }


    /**
     * 是否为根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }


    /**
     * 判断当前父节点收缩状态
     *
     * @return
     */
    public boolean isParentExpend() {

        if (parent == null) return false;

        return parent.isExpend();
    }

    /**
     * 是否为叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 得到当前节点的层级
     * @return
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpend() {
        return isExpend;
    }

    /**
     *
      * @param isExpend
     */
    public void setIsExpend(boolean isExpend) {
        this.isExpend = isExpend;

        if (!isExpend){
            for (Node node : children){
                node.setIsExpend(false);
            }
        }
    }
}
