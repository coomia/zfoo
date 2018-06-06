package com.zfoo.net.dispatcher.manager;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.29 11:01
 */

import com.zfoo.net.NetContext;
import com.zfoo.net.dispatcher.model.anno.PacketReceiver;
import com.zfoo.net.dispatcher.model.vo.IPacketReceiver;
import com.zfoo.net.dispatcher.model.vo.PacketReceiverDefinition;
import com.zfoo.net.dispatcher.util.EnhanceUtils;
import com.zfoo.net.protocol.manager.ProtocolManager;
import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.server.model.Session;
import com.zfoo.net.task.TaskManager;
import com.zfoo.util.ReflectionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 访问者模式，对于不同的信息访问和发送有不同的相应
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.28 14:33
 */
public class PacketDispatcherManager implements IPacketDispatcherManager {

    private ConcurrentHashMap<Integer, Session> sessionMap = new ConcurrentHashMap<>();
    private ArrayList<IPacketReceiver> receiverList = new ArrayList<>();

    public PacketDispatcherManager() {
        for (int i = 0; i < ProtocolManager.MAX_PROTOCOL_NUM; i++) {
            receiverList.add(null);
        }
    }

    @Override
    public void receive(Session session, IPacket packet) {
        TaskManager.getInstance().addTask(session, new ReceiveTask(session, packet));
    }

    @Override
    public void send(Session session, IPacket packet) {
        ByteBuf writeBuff = Unpooled.directBuffer();
        Channel channel = session.getChannel();
        NetContext.getProtocolService().write(writeBuff, packet);
        channel.writeAndFlush(writeBuff);
    }


    @Override
    public void registPacketReceiverDefintion(Object bean) {
        Class<?> clazz = bean.getClass();

        if (!ReflectionUtils.isPOJOClass(clazz)) {
            return;
        }

        Method[] methods = ReflectionUtils.getMethodsByAnnoInPOJOClass(clazz, PacketReceiver.class);
        for (Method method : methods) {
            Class<?>[] clazzs = method.getParameterTypes();
            if (clazzs.length != 2) {
                FormattingTuple message = MessageFormatter.format("[class:{}] [method:{}] must have two parameter!", bean.getClass().getSimpleName(), method.getName());
                throw new IllegalArgumentException(message.getMessage());
            }
            if (!Session.class.isAssignableFrom(clazzs[0])) {
                FormattingTuple message = MessageFormatter.format("[class:{}] [method:{}],the first parameter must be Session type parameter Exception.", bean.getClass().getSimpleName(), method.getName());
                throw new IllegalArgumentException(message.getMessage());
            }
            if (!IPacket.class.isAssignableFrom(clazzs[1])) {
                FormattingTuple message = MessageFormatter.format("[class:{}] [method:{}],the second parameter must be IPacket type parameter Exception.", bean.getClass().getSimpleName(), method.getName());
                throw new IllegalArgumentException(message.getMessage());
            }

            Field protocolIdField;
            short protocolId = -1;
            IPacketReceiver definition = null;
            try {
                protocolIdField = clazzs[1].getDeclaredField(ProtocolManager.PROTOCOL_ID);
                ReflectionUtils.makeAccessible(protocolIdField);
                protocolId = (short) protocolIdField.get(null);
                definition = new PacketReceiverDefinition(bean, method, clazzs[1]);
                definition = EnhanceUtils.createPacketReciver((PacketReceiverDefinition) definition);
            } catch (NoSuchFieldException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | CannotCompileException | NotFoundException e) {
                e.printStackTrace();
            }

            receiverList.set(protocolId, definition);
        }
    }


    public void doWithReceivePacket(Session session, IPacket packet) {
        IPacketReceiver receiver = receiverList.get(packet.protcolId());
        if (receiver == null) {
            FormattingTuple message = MessageFormatter.format("no any receiverDefintion found for this [packet:{}]", packet.getClass().getSimpleName());
            throw new NullPointerException(message.getMessage());
        }
        receiver.invoke(session, packet);
    }

    public final class ReceiveTask implements Runnable {

        private Session session;
        private IPacket packet;


        public ReceiveTask(Session session, IPacket packet) {
            this.session = session;
            this.packet = packet;

        }

        @Override
        public void run() {
            doWithReceivePacket(session, packet);
        }
    }

}
