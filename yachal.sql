-- phpMyAdmin SQL Dump
-- version 4.0.5
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2015 年 11 月 10 日 13:46
-- 服务器版本: 5.1.73-community
-- PHP 版本: 5.2.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `yachal`
--

-- --------------------------------------------------------

--
-- 表的结构 `forum_node`
--

CREATE TABLE IF NOT EXISTS `forum_node` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `no` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `created` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- 转存表中的数据 `forum_node`
--

INSERT INTO `forum_node` (`id`, `no`, `name`, `created`) VALUES
(4, 'shenzhen', '深圳', 1429367059),
(3, 'yueyang', '岳阳', 1429367030);

-- --------------------------------------------------------

--
-- 表的结构 `forum_topic`
--

CREATE TABLE IF NOT EXISTS `forum_topic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0',
  `node_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `type` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '1主题,2附言,3回帖',
  `title` varchar(1024) NOT NULL,
  `content` text NOT NULL,
  `created` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- 转存表中的数据 `forum_topic`
--

INSERT INTO `forum_topic` (`id`, `parent_id`, `node_id`, `user_id`, `type`, `title`, `content`, `created`) VALUES
(1, 0, 3, 15, 1, '测试标题1', '测试标题内容', 1429370649),
(2, 0, 3, 15, 1, '测试标题2', '', 1429370649),
(3, 0, 3, 15, 1, '测试标题3', '', 1429370649),
(4, 0, 3, 15, 1, '测试标题4', '', 1429370649),
(5, 0, 3, 15, 1, '测试标题5', '', 1429370649),
(6, 0, 3, 15, 1, '测试6', '', 1429370618);

-- --------------------------------------------------------

--
-- 表的结构 `note_content`
--

CREATE TABLE IF NOT EXISTS `note_content` (
  `id` int(10) unsigned NOT NULL,
  `parent_id` int(10) unsigned DEFAULT '0',
  `user_id` int(10) unsigned DEFAULT '0',
  `type` int(1) unsigned DEFAULT '0' COMMENT '0记事;1文件夹',
  `title` varchar(512) DEFAULT '',
  `content` text,
  `status` int(1) unsigned DEFAULT '0' COMMENT '0私密;1公开',
  `syntax` int(1) unsigned DEFAULT '0',
  `modifyed` int(10) unsigned DEFAULT '0' COMMENT '修改次数',
  `updated` int(10) unsigned DEFAULT '0' COMMENT '最后修改时间',
  `created` int(10) unsigned DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `sys_group`
--

CREATE TABLE IF NOT EXISTS `sys_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '组名称',
  `type` enum('admin','user') NOT NULL COMMENT '组类型',
  `manage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '管理组',
  `min_credits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最小积分',
  `max_credits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大积分',
  `read_access` tinyint(3) unsigned NOT NULL DEFAULT '10' COMMENT '阅读权限',
  `allow_message` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '发送短消息',
  `allow_visit` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '访问权限',
  `allow_manage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '登陆后台',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `sys_group`
--

INSERT INTO `sys_group` (`id`, `name`, `type`, `manage`, `min_credits`, `max_credits`, `read_access`, `allow_message`, `allow_visit`, `allow_manage`) VALUES
(1, '管理员', 'admin', 1, 0, 0, 10, 1, 3, 1),
(2, '注册会员', 'user', 0, 0, 0, 10, 0, 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `sys_invite`
--

CREATE TABLE IF NOT EXISTS `sys_invite` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `no` varchar(16) DEFAULT NULL,
  `is_valid` int(1) unsigned DEFAULT '0' COMMENT '0有效;1无效',
  `created_user_id` int(10) unsigned DEFAULT NULL,
  `register_user_id` int(10) unsigned DEFAULT NULL,
  `created` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `sys_invite`
--

INSERT INTO `sys_invite` (`id`, `no`, `is_valid`, `created_user_id`, `register_user_id`, `created`) VALUES
(3, '73204edabdd9b24d', 1, 0, 23, 1413557527),
(2, '07be4bcaa3a73af4', 1, 0, 24, 1413557507),
(4, '181a4a4594bcc119', 0, 0, 15, 1413557527);

-- --------------------------------------------------------

--
-- 表的结构 `sys_kv`
--

CREATE TABLE IF NOT EXISTS `sys_kv` (
  `name` varchar(64) NOT NULL,
  `value` text,
  `type` varchar(32) NOT NULL,
  `user_id` varchar(16) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`,`user_id`,`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `sys_kv`
--

INSERT INTO `sys_kv` (`name`, `value`, `type`, `user_id`) VALUES
('site_title', '冰剑封城', 'option', '0'),
('site_switch', '0', 'option', '0'),
('site_description', '轻击键盘，静候回音。', 'option', '0'),
('site_email', 'yourname@admin.com', 'option', '0'),
('site_url', 'http://127.0.0.1', 'option', '0'),
('site_register', '0', 'option', '0'),
('site_invite', '0', 'option', '0');

-- --------------------------------------------------------

--
-- 表的结构 `sys_user`
--

CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(10) unsigned NOT NULL DEFAULT '0',
  `email` varchar(256) DEFAULT NULL COMMENT '电子邮件',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `password` varchar(32) DEFAULT NULL COMMENT '密码（双重MD5）',
  `credits` int(10) unsigned DEFAULT '0' COMMENT '积分',
  `created` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='用户表' AUTO_INCREMENT=28 ;

--
-- 转存表中的数据 `sys_user`
--

INSERT INTO `sys_user` (`id`, `group_id`, `email`, `nickname`, `password`, `credits`, `created`) VALUES
(15, 1, 'icesword28@qq.com', 'binjoo', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1414247360),
(16, 0, 'asdh9123@asd.com', 'binjood', '113969485f92c6e5c66b9e503222d7fd', 0, 1414247481),
(17, 0, 'icesword28@1qq.com', 'binjoo1', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1414740180),
(18, 0, 'asfh729@q.com', '1231', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1414740879),
(19, 0, 'asd1@q.com', 'asdasd', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1414740936),
(20, 0, 'aasfhdt78239@q.com', '123asda', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1414741460),
(21, 0, 'asd@asd.com', '12313', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1414741546),
(22, 0, 'asf237948h@q.com', 'asf237948h', '309f759e4ea91080ec3189df7e8d198a', 0, 1414741627),
(23, 0, '73204edabdd9b24d@q.com', '73204edabdd9b24d', '429cd376ff452813b0d20b468d198fdd', 0, 1414741684),
(24, 0, 'dshad9h9@asd.cc', '123123', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1416551541),
(25, 0, 'sadfh92@8.com', 'sadfh928', '9e53401549040bcb0e2c8fc3fb13f141', 0, 1429460053),
(26, 0, 'asdfhj98@asc.com', 'asdfhj98', 'efb690b11e55d9e46abc69d25bc3716b', 0, 1429460181),
(27, 0, 'gawygh9@7gr9h.com', 'gawygh97gr9h', '5c8d78d1efb6ca983723d6c74fbb7169', 0, 1429460215);

-- --------------------------------------------------------

--
-- 表的结构 `ya_bookmark`
--

CREATE TABLE IF NOT EXISTS `ya_bookmark` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `title` varchar(512) DEFAULT NULL COMMENT '书签标题',
  `long_url` varchar(1024) DEFAULT NULL COMMENT '原网址',
  `short_url` varchar(16) DEFAULT NULL COMMENT '短址',
  `description` varchar(1024) DEFAULT NULL,
  `full_text` text COMMENT '页面全文',
  `created` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='书签表' AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `ya_bookmark`
--

INSERT INTO `ya_bookmark` (`id`, `user_id`, `title`, `long_url`, `short_url`, `description`, `full_text`, `created`) VALUES
(1, 15, 'dasdasd', 'http://127.0.0.1:8080/bookmark/add', NULL, '', NULL, 1414253778),
(2, 15, '百度', 'http://www.baidu.com', NULL, '', NULL, 1414255186),
(3, 15, 'asdasdasd', 'http://ipjmc.iteye.com/blog/1169491', NULL, '', NULL, 1414255226),
(4, 15, 'asdasd', 'http://shuqian.youdao.com/manage', NULL, '', NULL, 1414255257),
(5, 15, '12', 'http://shuqian.youdao.com/manage', NULL, '', NULL, 1414663696);

-- --------------------------------------------------------

--
-- 表的结构 `ya_bookmark_meta_rel`
--

CREATE TABLE IF NOT EXISTS `ya_bookmark_meta_rel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bookmark_id` int(10) unsigned NOT NULL,
  `meta_id` int(10) unsigned NOT NULL,
  `created` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- 转存表中的数据 `ya_bookmark_meta_rel`
--

INSERT INTO `ya_bookmark_meta_rel` (`id`, `bookmark_id`, `meta_id`, `created`) VALUES
(1, 2, 1, 1414255186),
(2, 2, 2, 1414255186),
(3, 2, 3, 1414255186),
(4, 2, 4, 1414255186),
(5, 2, 5, 1414255186),
(6, 2, 6, 1414255186),
(7, 3, 7, 1414255226),
(8, 3, 8, 1414255226),
(9, 4, 7, 1414255257),
(10, 4, 8, 1414255257),
(11, 4, 9, 1414255257);

-- --------------------------------------------------------

--
-- 表的结构 `ya_message`
--

CREATE TABLE IF NOT EXISTS `ya_message` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 表的结构 `ya_meta`
--

CREATE TABLE IF NOT EXISTS `ya_meta` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '名称',
  `type` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- 转存表中的数据 `ya_meta`
--

INSERT INTO `ya_meta` (`id`, `name`, `type`) VALUES
(1, 'baidu', 'tag'),
(2, '百度', 'tag'),
(3, '度娘', 'tag'),
(4, '李彦宏', 'tag'),
(5, '周杰伦', 'tag'),
(6, 'meta', 'tag'),
(7, '百度', 'tag'),
(8, '周杰伦', 'tag'),
(9, '吴彦祖', 'tag');

-- --------------------------------------------------------

--
-- 表的结构 `ya_reset_password`
--

CREATE TABLE IF NOT EXISTS `ya_reset_password` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL,
  `token` varchar(32) DEFAULT NULL COMMENT '重置令牌',
  `valid_time` int(10) unsigned DEFAULT NULL COMMENT '有效期限',
  `create_ip` varchar(32) DEFAULT NULL COMMENT '创建者IP',
  `reset_ip` varchar(32) DEFAULT NULL COMMENT '使用者IP',
  `status` int(1) DEFAULT '0' COMMENT '0未使用;1已使用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- 转存表中的数据 `ya_reset_password`
--

INSERT INTO `ya_reset_password` (`id`, `user_id`, `token`, `valid_time`, `create_ip`, `reset_ip`, `status`) VALUES
(1, 15, '417eb869deb94ab695c413df273987e2', 1414746537, '127.0.0.1', '127.0.0.1', 1),
(2, 15, 'f5a204d3ed1c4a35beab0d7bb2affa52', 1414747687, '127.0.0.1', NULL, 0),
(3, 15, 'd2d6f696e17045bdb2c07bcd08ab993c', 1414747702, '127.0.0.1', NULL, 0),
(4, 15, 'ff7f454851184b09a0ab2965b787459d', 1414747761, '127.0.0.1', NULL, 0),
(5, 15, '94444fa8666f4309bd18e7882e4ccc7e', 1414747786, '127.0.0.1', NULL, 0),
(6, 15, 'c1bf1f0c64e74e809632cdc6d9e9c0da', 1414747805, '127.0.0.1', NULL, 0),
(7, 15, '0d36c23b408e4a68b99a2366230dacc3', 1414747822, '127.0.0.1', NULL, 0),
(8, 15, '0c10299d39f44ca296ad10f870e04ed1', 1414747837, '127.0.0.1', NULL, 0),
(9, 15, '4fbde274b7774cf194b7969c40ab44f6', 1414747882, '127.0.0.1', NULL, 0),
(10, 15, 'a333f30964c1490d8676f3bfeccec976', 1414747922, '127.0.0.1', NULL, 0),
(11, 15, '654e74256a0d4b0a860b5b30faf8032b', 1414748135, '127.0.0.1', NULL, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
