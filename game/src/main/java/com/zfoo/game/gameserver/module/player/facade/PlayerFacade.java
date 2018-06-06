package com.zfoo.game.gameserver.module.player.facade;

import com.zfoo.game.gameserver.module.GameContext;
import com.zfoo.game.gameserver.module.player.entity.PlayerEntity;
import com.zfoo.game.gameserver.module.player.manager.IPlayerManager;
import com.zfoo.game.gameserver.module.player.packet.CM_Login;
import com.zfoo.game.gameserver.module.system.util.SessionPlayerUtils;
import com.zfoo.net.NetContext;
import com.zfoo.net.dispatcher.model.anno.PacketReceiver;
import com.zfoo.net.server.manager.ISessionManager;
import com.zfoo.net.server.model.Session;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-24 12:27
 */
@Component
public class PlayerFacade {

    @PacketReceiver
    public void login(Session session, CM_Login cm) {
        long id = cm.getId();

        // 检查数据库中是否存在这个id
        IPlayerManager playerManager = GameContext.getPlayerManager();
        PlayerEntity player = playerManager.getPlayerEntityById(id);

        // 如果数据库中没有这个id，则创建这个id，并且保存在数据库中；有则登录成功，次数加一
        if (player == null) {
            player = playerManager.createPlayerEntity(id);
            playerManager.insertPlayerEntity(player);
        }

        short loginNum = player.getLoginNum();
        player.setLoginNum(++loginNum);

        // 将SessionManager中的旧Session移除，并且添加新的Session
        ISessionManager sessionManager = NetContext.getSessionManager();
        sessionManager.removeSession(session);
        session.setId(player.getId());
        sessionManager.addSession(session);

        SessionPlayerUtils.addPlayerToSession(player, session);

    }

}
