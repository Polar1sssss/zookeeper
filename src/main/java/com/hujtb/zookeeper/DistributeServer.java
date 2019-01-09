package com.hujtb.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author hujtb
 * @create on 2019-01-09-15:55
 */
public class DistributeServer {

    private ZooKeeper zkClient = null;
    private String connectString = "hadoop1:2181";
    private int sessionTimeOut = 2000;

    public static void main(String[] args) throws Exception {

        args = new String[]{"hadoop1"};
        DistributeServer server = new DistributeServer();

        //连接zk集群
        server.getConnect();

        //注册节点
        server.regist(args[0]);

        //业务逻辑处理
        server.business();

    }

    private void getConnect() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    private void regist(String hostname) throws KeeperException, InterruptedException {
        // 节点创建要重复执行，服务器关机节点下线，所以创建的应该是短暂带序号的节点
        String path = zkClient.create("/servers/server", hostname.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online ");
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
