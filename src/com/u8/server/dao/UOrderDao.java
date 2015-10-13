package com.u8.server.dao;

import com.u8.server.common.UHibernateTemplate;
import com.u8.server.data.UOrder;
import org.springframework.stereotype.Repository;

/**
 * 订单数据访问类
 */
@Repository("orderDao")
public class UOrderDao extends UHibernateTemplate<UOrder, Long>{


}
