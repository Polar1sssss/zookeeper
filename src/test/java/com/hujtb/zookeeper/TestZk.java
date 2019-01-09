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
            //�ڿͻ���ע�����
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

    //�����ӽڵ�
    @Test
    public void create() throws IOException, KeeperException, InterruptedException {
       String path = zkClient.create("/hujtb", "aaabbbccc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    //��ȡ�ӽڵ����ݲ���ؽڵ�仯
    @Test
    public void getDataAndWatch() throws IOException, KeeperException, InterruptedException {
        List<String> chilidren = zkClient.getChildren("/", true);
        for(String child : chilidren){
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    //�жϽڵ��Ƿ����
    @Test
    public void exists() throws IOException, KeeperException, InterruptedException {
        Stat flag = zkClient.exists("/hujtb", true);
        System.out.println(flag == null ? "not exists" : "exists");
    }
}
