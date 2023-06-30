package demo.callback;

import org.apache.zookeeper.AsyncCallback;

public class PrintInfo implements AsyncCallback.StringCallback {

    @Override
    public void processResult(int i, String s, Object o, String s1) {
        System.out.println(String.format(">> create node result: result code:【%s】 path:【%s】 context:【%s】 name:【%s】",
                i, s, o, s1));
    }
}
