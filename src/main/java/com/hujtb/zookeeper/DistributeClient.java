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

        //����zk��Ⱥ
        client.getConnection();

        //ע�����
        client.getChildren();
        
        //ҵ���߼�����
        client.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void getChildren() throws KeeperException, InterruptedException {

        List<String> children =  zkClient.getChildren("/servers", true);
        //�洢�����������ڵ����Ƽ���
        ArrayList<String> hosts = new ArrayList<>();

        for(String child : children){
            //��ȡ�ڵ�����
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            hosts.add(new String(data));
        }
        //���������������ڵ��ӡ
        System.out.println(hosts);
        
    }

    private void getConnection() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    //������������������дһ������������������һ�κ��ʧЧ
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
