package com.example.demo.config;

import com.kx.c;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

@Slf4j
public class CPooledObjectFactory extends BasePooledObjectFactory<c> {

    private final String host;
    private final int port;

    public CPooledObjectFactory(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public c create() throws Exception {
        return new c(host, port);
    }

    @Override
    public PooledObject<c> wrap(c c) {
        return new DefaultPooledObject<>(c);
    }

    @Override
    public boolean validateObject(PooledObject<c> p) {
        try {
            c c = p.getObject();
            if (c.s == null || !c.s.isConnected()) {
                return false;
            }
            c.k("1"); // NOTE: Please read Socket.isConnected()
        } catch (c.KException | IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void destroyObject(PooledObject<c> p) throws Exception {
        close(p.getObject());
    }

    private void close(c c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                // ignored
            }
        }
    }
}
