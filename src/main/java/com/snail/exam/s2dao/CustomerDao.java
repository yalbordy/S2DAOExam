package com.snail.exam.s2dao;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;

@S2Dao(bean = CustomerBean.class)
public interface CustomerDao {

    /**
     * select処理. 返り値が、Entity でないとき、 SELECT COUNT(*) FROM CUSTOMER のように、
     * 値がひとつだけ返ってくる検索処理とみなされる。
     * 
     * @return 格納されている CUSTOMER の数
     */
    @Sql(value = "SELECT COUNT(ID) FROM CUSTOMER")
    int countAll();

    /**
     * delete処理. delete処理は、delete,remove で始まるメソッド名にする。 返り値は、int か void。
     * 
     * @param customer Customer
     * @return 更新された行数
     */
    int delete(CustomerBean customer);

    /**
     * select処理. 引数を指定しないと全件検索
     */
    List<CustomerBean> findAll();

    /**
     * select処理. &#x40;Query アノテーション で、複雑なWHERER句 ORDER BY句を記述することができます。
     * 
     * @param id ID
     * @return Customer
     */
    @Query("ADDRESS LIKE ?%")
    List<CustomerBean> findByAddress(final String address);

    /**
     * select処理. 返り値が、Entity , List(List<Entity>) , Entity[] のとき Entity を返す検索処理
     * とみなされる。 &#x40;Arguments
     * アノテーションで、引数に対応する"列名"を 指定すると、単純な WHERE 句が生成される。
     * 
     * (引数の値がnullのときに検索条件に含めないような処理が 行われる)
     * 
     * @param id ID
     * @return Customer
     */
    @Arguments( { "ID" })
    CustomerBean findCustomer(final int id);

    /**
     * insert処理. insert処理は、insert,add,create で始まるメソッド名にする。 返り値は、int か void。
     * 
     * @param customer Customer
     * @return 更新された行数
     */
    int insert(CustomerBean customer);

    /**
     * update処理. update処理は、update,modify,store で始まるメソッド名にする。 返り値は、int か void。
     * 
     * @param customer Customer
     * @return 更新された行数
     */
    int update(CustomerBean customer);

    /**
     * update処理. メソッド名の末尾に UnlessNull がついている update 処理は null でないフィールドのみ更新。 返り値は、int か void。
     * 
     * @param customer Customer
     * @return 更新された行数
     */
    int updateUnlessNull(CustomerBean customer);
}
