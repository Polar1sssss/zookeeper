package com.hujtb.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author hujtb
 * @create on 2019-01-09-15:05
 */
public class TestZk {

    private String connectString = "hadoop1:2181";
    private int sessionTimeOut = 2000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            //在客户端注册监听
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("-------start-------");
                List<String> chilidren = null;
                try {
                    chilidren = zkClient.getChildren("/", true);
                    for(String child : chilidren){
                        System.out.println(child);
                    }
                    System.out.println("-------end---------");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //创建子节点
    @Test
    public void create() throws IOException, KeeperException, InterruptedException {
       String path = zkClient.create("/hujtb", "aaabbbccc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    //获取子节点数据并监控节点变化
    @Test
    public void getDataAndWatch() throws IOException, KeeperException, InterruptedException {
        List<String> chilidren = zkClient.getChildren("/", true);
        for(String child : chilidren){
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    //判断节点是否存在
    @Test
    public void exists() throws IOException, KeeperException, InterruptedException {
        Stat flag = zkClient.exists("/hujtb", true);
        System.out.println(flag == null ? "not exists" : "exists");
    }
}
