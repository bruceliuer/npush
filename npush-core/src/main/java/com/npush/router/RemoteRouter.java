package com.npush.router;

import com.npush.connection.Connection;

/**
 * Created by ohun on 2015/12/23.
 */
public class RemoteRouter implements Router {
    private final RouterInfo routerInfo;

    public RemoteRouter(RouterInfo routerInfo) {
        this.routerInfo = routerInfo;
    }

    public Connection getConnect() {
        return null;
    }

    public RouterInfo getRouterInfo() {
        return null;
    }
}
