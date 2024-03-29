package com.dodou.scaffold.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dodou.scaffold.dao.mapper.AccountInfoMapper;
import com.dodou.scaffold.dao.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Finlay
 * @ClassName: AccountInfoServiceImpl
 * @Description:
 * @date: 2019-11-11 10:40 AM
 */
@Service
@Slf4j
public class AccountInfoServiceImpl extends ServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoMapper accountInfoMapper;

    @Override
    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public void updateAccountBalance(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 try begin 开始执行------------------------xid:{}", transId);
    }

    /**
     * confirm方法
     * confirm幂等校验
     * 正式增加金额
     *
     * @param accountNo
     * @param amount
     */
    @Transactional
    public void confirmMethod(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 confirm begin 开始执行------------------------xid:{}", transId);
        if (accountInfoMapper.isExistConfirm(transId) > 0) {
            log.info("bank2 confirm 已经执行，不可重复执行------------------------xid:{}", transId);
            return;
        }
        //增加金额
        accountInfoMapper.addAccountBalance(accountNo, amount);
        //增加一条confirm日志，用于幂等
        accountInfoMapper.addConfirm(transId);
        log.info("bank2 confirm end 结束执行------------------------xid:{}", transId);
    }


    /**
     * @param accountNo
     * @param amount
     */
    public void cancelMethod(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 cancel begin 开始执行------------------------xid:{}", transId);

    }
}
