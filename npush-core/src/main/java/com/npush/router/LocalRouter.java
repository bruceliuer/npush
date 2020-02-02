package com.npush.router;

import com.npush.connection.Connection;

/**
 * Created by ohun on 2015/12/23.
 */
public class LocalRouter implements Router {
    private final Connection connection;

    public LocalRouter(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnect() {
        return connection;
    }

    public RouterInfo getRouterInfo() {
        return null;
    }
}
