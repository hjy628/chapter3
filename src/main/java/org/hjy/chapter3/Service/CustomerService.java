/*
package org.hjy.chapter3.Service;

import org.hjy.chapter2.Model.Customer;
import org.hjy.chapter2.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

*/
/**
 * Created by hjy on 15-12-26.
 * 提供客户数据服务
 *//*

public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    */
/**
     * 获取客户列表
     *//*

    public List<Customer> getCustomerList(String keyWord){
            String sql = "SELECT * FROM customer ";
            return DatabaseHelper.queryEntityList(Customer.class,sql);
    }

    */
/**
     * 获取客户
     *//*

    public Customer getCustomer(Long id){
        //todo
        return null;
    }

    */
/**
     * 创建客户
     *//*


    public boolean createCustomer(Map<String,Object> fieldMap){
        return DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }

    */
/**
     * 更新客户
     *//*

    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    */
/**
     * 删除客户
     *//*


    public boolean deleteCustomer(long id){
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }


}
*/
