CREATE TABLE `xtl_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(45) DEFAULT NULL COMMENT '手机号码',
  `name` varchar(45) DEFAULT NULL COMMENT '用户姓名',
  `status` tinyint(4) DEFAULT NULL COMMENT '用户状态：0-删除 1-正常 2-冻结',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `wechat_account` varchar(45) DEFAULT NULL COMMENT '微信账号',
  `mail` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';