package com.npush.task;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.HashedWheelTimer;

public class NettySharedHolder {

	public static final HashedWheelTimer timer = new HashedWheelTimer();

	public static final ByteBufAllocator byteBufAllocator;

	static {
		byteBufAllocator = UnpooledByteBufAllocator.DEFAULT;
	}

}