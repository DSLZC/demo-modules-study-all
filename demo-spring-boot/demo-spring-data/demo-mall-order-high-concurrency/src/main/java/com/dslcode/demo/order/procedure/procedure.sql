-- 提交订单存储过程
DELIMITER $$
DROP PROCEDURE IF EXISTS p_insert_submit_order_item;
CREATE PROCEDURE p_insert_submit_order_item(
  IN goods_id BIGINT, IN user_id BIGINT, IN num SMALLINT,IN code VARCHAR(20), INOUT row_num INT) BEGIN

  DECLARE handler_count INT DEFAULT 0;

  START TRANSACTION ;-- 开启事物

  UPDATE goods SET stock = stock - num WHERE id = goods_id AND stock >= num; -- 先修改库存

  SELECT ROW_COUNT() INTO handler_count;-- 操作成功的行数

  IF handler_count = 0 THEN
    INSERT INTO submit_order_item(goods_id, user_id, num, code, success) VALUES (goods_id, user_id, num, code, 0);-- 订单生成失败
  ELSE
    INSERT INTO submit_order_item(goods_id, user_id, num, code, success) VALUES (goods_id, user_id, num, code, 1);-- -- 订单生成成功
  END IF;

  SET row_num = handler_count;
  COMMIT;-- 提交事物，若出错则ROLLBACK ;

END;-- 存储过程结束
$$

CALL p_delete_repeat_goods_common_channel();
DROP TABLE IF EXISTS t_temp_delete_repeat_goods_common_channel;