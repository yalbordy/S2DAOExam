package com.snail.exam.s2dao;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

public class S2DAOExam {

    private static Log logger = LogFactory.getLog(S2DAOExam.class);

    public static void main(String[] args) {
        S2Container container = null;
        try {
            container = S2ContainerFactory.create("app.dicon");

            CustomerDao dao = (CustomerDao) container.getComponent(CustomerDao.class);

            List<CustomerBean> customerList = dao.findAll();
            
            for( CustomerBean bean : customerList ){
            	logger.debug(BeanUtils.describe(bean));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (container != null) {
                container.destroy();
            }
        }

    }
}
