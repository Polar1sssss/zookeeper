package com.hujtb.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hujtb
 * @create on 2019-01-09-16:46
 */
public class DistributeClient {
    private ZooKeeper zkClient = null;
    private String connectString = "hadoop1:2181";
    private int sessionTimeOut = 2000;

    public static void main(String[] args) throws Exception {

        DistributeClient client = new DistributeClient();

        //连接zk集群
        client.getConnection();

        //注册监听
        client.getChildren();
        
        //业务逻辑处理
        client.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void getChildren() throws KeeperException, InterruptedException {

        List<String> children =  zkClient.getChildren("/servers", true);
        //存储服务器主机节点名称集合
        ArrayList<String> hosts = new ArrayList<>();

        for(String child : children){
            //获取节点数据
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            hosts.add(new String(data));
        }
        //将所有在线主机节点打印
        System.out.println(hosts);
        
    }

    private void getConnection() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    //！！！必须在这里再写一遍这个方法，否则监听一次后就失效
                    getChildren();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
