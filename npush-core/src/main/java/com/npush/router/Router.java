package com.npush.router;

import com.npush.connection.Connection;

/**
 * Created by ohun on 2015/12/23.
 */
public interface Router {

    Connection getConnect();

    RouterInfo getRouterInfo();
}
