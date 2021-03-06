package com.qianzhui.enode.infrastructure.impl;

import com.qianzhui.enode.common.io.AsyncTaskResult;
import com.qianzhui.enode.infrastructure.IMessage;
import com.qianzhui.enode.infrastructure.IMessageHandler;
import com.qianzhui.enode.infrastructure.IMessageHandlerProxy1;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * Created by junbo_xu on 2016/3/31.
 */
public class MessageHandlerProxy1 implements IMessageHandlerProxy1 {
    private IMessageHandler _handler;
    private MethodHandle _methodHandle;
    private Method _method;

    public MessageHandlerProxy1(IMessageHandler handler, MethodHandle methodHandle,Method method) {
        _handler = handler;
        _methodHandle = methodHandle;
        _method = method;
    }

    @Override
    public CompletableFuture<AsyncTaskResult> handleAsync(IMessage message) {
        try {
            return (CompletableFuture<AsyncTaskResult>) _methodHandle.invoke(_handler, message);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public Object getInnerObject() {
        return _handler;
    }
}
