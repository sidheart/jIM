package io.milk.jim;

/**
 * A simple class that stores a JimPeer's SHA1 id and host IP address
 */
public class NodeInfo {
    private byte[] id;
    private String host;

    public NodeInfo(byte[] id, String host) {
        this.id = id;
        this.host = host;
    }

    public byte[] getId() {
        return id;
    }

    public String getHost() {
        return host;
    }
}
