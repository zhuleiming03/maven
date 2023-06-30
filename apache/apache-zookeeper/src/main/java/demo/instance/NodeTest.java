package demo.instance;

import demo.callback.PrintInfo;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;


import java.util.List;
import java.util.concurrent.CountDownLatch;

public class NodeTest implements Watcher {

    public static void main(String[] args) throws Exception {

        // 连接zookeeper并且注册一个默认的监听器
        zooKeeper = new ZooKeeper("localhost:2181", 5000,
                new NodeTest());
        connectedSemaphore.await();

        // 节点创建和删除
        createAndDeleteNodeTest();

        // 节点内容修改
        alterNodeTest();

        Thread.sleep(5_000);
    }

    @Override
    public void process(WatchedEvent event) {

        System.out.println(">> receive watched event: " + event);

        if (Event.KeeperState.SyncConnected == event.getState()) {

            // 节点列表变更事件
            if (Event.EventType.NodeChildrenChanged == event.getType()) {
                try {
                    // 重新注册节点列表变更监听
                    zooKeeper.getChildren(event.getPath(), true);
                } catch (InterruptedException | KeeperException e) {
                    e.printStackTrace();
                }
            }

            // 节点内容变更事件
            if (Event.EventType.NodeDataChanged == event.getType()) {
                try {
                    // 获取节点最新状态并添加监听
                    Stat stat = new Stat();
                    String nodeData = new String(zooKeeper.getData(TEST_NODE, true, stat));
                    System.out.println(String.format(">> get node data:【%s】 version:【%s】,add listener again",
                            nodeData, stat.getVersion()));
                } catch (InterruptedException | KeeperException e) {
                    e.printStackTrace();
                }
            }

            // 解除等待阻塞
            connectedSemaphore.countDown();
        }
    }

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static ZooKeeper zooKeeper = null;

    private static final String TEST_NODE = "/zookeeper/test";

    private static void createAndDeleteNodeTest() throws InterruptedException, KeeperException {

        // 同步创建一个临时结点
        System.out.println(">> create node result: " + zooKeeper.create("/zookeeper/tmp_one",
                "ephemeral".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL));

        // 同步创建一个临时顺序结点
        System.out.println(">> create node result: " + zooKeeper.create("/zookeeper/tmp_two_",
                "ephemeral sequential".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL));

        // 读取节点列表并添加监听
        List<String> rootItems = zooKeeper.getChildren("/zookeeper", true);
        System.out.println(">> node count:" + rootItems.size());
        for (String item : rootItems) {
            System.out.println(item);
        }

        // 检查节点是否存在
        Stat exists = zooKeeper.exists(TEST_NODE, true);

        // 节点存在则删除
        if (exists != null) {
            System.out.println(String.format(">> exist node 【%s】, 创建时事务ID:【%s】 最后一次修改事务ID:【%s】 version:【%s】",
                    TEST_NODE, exists.getCzxid(), exists.getMzxid(), exists.getVersion()));
            // 删除该节点
            zooKeeper.delete(TEST_NODE, exists.getVersion());
        } else {
            System.out.println(">> no exist node: " + TEST_NODE);
        }

        // 异步创建一个持久结点
        zooKeeper.create(TEST_NODE, "persistent sequential".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT, new PrintInfo(), "This is a persistent node");
    }

    private static void alterNodeTest() throws InterruptedException, KeeperException {

        // 获取节点最新状态并添加监听
        Stat stat = new Stat();
        String nodeData = new String(zooKeeper.getData(TEST_NODE, true, stat));
        System.out.println(String.format(">> get node data:【%s】,add listener; 创建时事务ID:【%s】 最后一次修改事务ID:【%s】 version:【%s】",
                nodeData, stat.getCzxid(), stat.getMzxid(), stat.getVersion()));

        // 更新节点的值
        stat = zooKeeper.setData(TEST_NODE, "node change value".getBytes(), stat.getVersion());
        System.out.println(String.format(">> update node 创建时事务ID:【%s】 最后一次修改事务ID:【%s】 version:【%s】",
                stat.getCzxid(), stat.getMzxid(), stat.getVersion()));

        // 取最新的值再次更新
        stat = zooKeeper.setData(TEST_NODE, "finish value".getBytes(), -1);
        System.out.println(String.format(">> update node 创建时事务ID:【%s】 最后一次修改事务ID:【%s】 version:【%s】",
                stat.getCzxid(), stat.getMzxid(), stat.getVersion()));
    }
}
