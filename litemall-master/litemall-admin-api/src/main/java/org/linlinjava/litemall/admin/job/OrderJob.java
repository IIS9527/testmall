package org.linlinjava.litemall.admin.job;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.service.LogHelper;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.linlinjava.litemall.util.service.DyAddressService;
import org.linlinjava.litemall.util.service.DyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.ORDER_CONFIRM_NOT_ALLOWED;

/**
 * 检测订单状态
 */
@Component
public class OrderJob {
    private final Log logger = LogFactory.getLog(OrderJob.class);

    @Autowired
    private LitemallOrderGoodsService orderGoodsService;
    @Autowired
    private LitemallOrderService orderService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallGrouponService grouponService;
    @Autowired
    private LitemallGrouponRulesService rulesService;
    @Resource
    private DyAddressService dyAddressService;
    @Autowired
    private LogHelper logHelper;
    @Resource
    private DyTaskService dyTaskService;
    /**
     * 自动确认订单
     * <p>
     * 定时检查订单未确认情况，如果超时 LITEMALL_ORDER_UNCONFIRM 天则自动确认订单
     * 定时时间是每天凌晨3点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致订单真正超时时间是 [LITEMALL_ORDER_UNCONFIRM, 1 + LITEMALL_ORDER_UNCONFIRM]
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void checkOrderUnconfirm() {
        logger.info("系统开启定时任务检查订单是否已经超期自动确认收货");

        List<LitemallOrder> orderList = orderService.queryUnconfirm(SystemConfig.getOrderUnconfirm());
        for (LitemallOrder order : orderList) {

            // 设置订单已取消状态
            order.setOrderStatus(OrderUtil.STATUS_AUTO_CONFIRM);
            order.setConfirmTime(LocalDateTime.now());
            if (orderService.updateWithOptimisticLocker(order) == 0) {
                logger.info("订单 ID=" + order.getId() + " 数据已经更新，放弃自动确认收货");
            } else {
                logger.info("订单 ID=" + order.getId() + " 已经超期自动确认收货");
            }
        }

        logger.info("系统结束定时任务检查订单是否已经超期自动确认收货");
    }

    /**
     * 可评价订单商品超期
     * <p>
     * 定时检查订单商品评价情况，如果确认商品超时 LITEMALL_ORDER_COMMENT 天则取消可评价状态
     * 定时时间是每天凌晨4点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致订单真正超时时间是 [LITEMALL_ORDER_COMMENT, 1 + LITEMALL_ORDER_COMMENT]
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void checkOrderComment() {
        logger.info("系统开启任务检查订单是否已经超期未评价");

        List<LitemallOrder> orderList = orderService.queryComment(SystemConfig.getOrderComment());
        for (LitemallOrder order : orderList) {
            order.setComments((short) 0);
            orderService.updateWithOptimisticLocker(order);

            List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
            for (LitemallOrderGoods orderGoods : orderGoodsList) {
                orderGoods.setComment(-1);
                orderGoodsService.updateById(orderGoods);
            }
        }

        logger.info("系统结束任务检查订单是否已经超期未评价");
    }


    /**
     * 检查时间dy开始时间
     * <p>
     * 定时检查订单情况
     * 每2分钟检查一次
     * <p>
     * TODO
     *
     */
    @Scheduled(fixedDelay =  60 * 1000)
    public void checkDyOrderStart(){
        System.out.println("sss");
        List<LitemallOrder> orderList = orderService.querySelectByStatus(OrderUtil.STATUS_PAY);
        System.out.println(orderList.toString());
        LocalDateTime nowTime= LocalDateTime.now();

        for (LitemallOrder order : orderList) {

            LocalDateTime beginTime = order.getDatetimeBegin();

            if (nowTime.isBefore(beginTime)){ continue; }

            String roomId =  dyAddressService.getRoomIdByPersonAddress(order.getMessage());

            if (roomId == null){continue;}


            order.setVideoName(dyAddressService.getXiGuaName(roomId));

            order.setRoomId(roomId);

//            发送任务给任务后台
            System.out.println("sss");
           if (dyTaskService.taskPush(beginTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00")) ,order.getDuration().toString(),order.getDeviceNumber().toString(),roomId,order.getVideoName(),
                   order.getMessage(),order.getIntegral())){

               //发送成功标记已发货
               String shipSn =  "555";
               String shipChannel = "ZTO";
               order.setOrderStatus(OrderUtil.STATUS_SHIP);
               order.setShipSn(shipSn);
               order.setShipChannel(shipChannel);
               order.setShipTime(nowTime);

               if (orderService.updateWithOptimisticLocker(order) == 0) {
                   logHelper.logOrderFail("发货失败订单更新失败", "订单编号 " + order.getOrderSn());

               }

               //记录
               logHelper.logOrderSucceed("发货", "订单编号 " + order.getOrderSn());
           }
          else {
               //更新失败  提醒
               logHelper.logOrderFail("发货失败 发布任务失败", "订单编号 " + order.getOrderSn());
           }


        }
    }
}
