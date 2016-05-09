package www.treeview.com.imooctreeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzy on 2015/11/13.
 */
public class TreeHelper {

    /**
     * 将用户的数据转化为树形数据
     * @param datas
     * @param <T>
     * @return
     */
    public static <T>List<Node> convertDatas2Nodes(List<T> datas){

        List<Node> nodes = new ArrayList<Node>();
        Node node = null;

        for (T t : datas){
            node = new Node();
        }

        return null;
    }
}
