import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;


public class ConnectTest implements Watcher {

    @Override
    public void process(WatchedEvent event) {

        System.out.println(">> receive watched event: " + event);

        if (Event.KeeperState.SyncConnected == event.getState()) {
            // 解除等待阻塞
            connectedSemaphore.countDown();
        }
    }

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        // 连接zookeeper并且注册一个默认的监听器
        ZooKeeper zooKeeper = new ZooKeeper("aliyun:10011", 5000,
                new ConnectTest());
        System.out.println(">> state: " + zooKeeper.getState() + " ; session ID: " + zooKeeper.getSessionId());

        try {
            // 等待连接成功的通知
            connectedSemaphore.await();

        } catch (InterruptedException e) {
            System.out.println("Zookeeper session established");
        }
    }
}
