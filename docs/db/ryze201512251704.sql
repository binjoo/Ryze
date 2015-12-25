-- phpMyAdmin SQL Dump
-- version 4.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-12-25 17:04:01
-- 服务器版本： 10.0.16-MariaDB
-- PHP Version: 5.4.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ryze`
--

DELIMITER $$
--
-- 函数
--
CREATE DEFINER=`root`@`localhost` FUNCTION `get_sequence`(`in_name` VARCHAR(64)) RETURNS varchar(16) CHARSET utf8
begin
    declare p_last_id int;
    declare p_no      varchar(4);
    declare p_result  varchar(16);
    declare p_max_no  int;

    insert into sys_sequence () values ();
    select last_insert_id() into p_last_id;
    delete from sys_sequence where id = p_last_id;

    if(in_name is not null and in_name <> '') then
        select no into p_no from sys_sequence where name = in_name;
        if(p_no is null) then
            select max(cast(no as unsigned)) into p_max_no from sys_sequence;
            set p_no = lpad(p_max_no + 1, 4, 0);
            insert into sys_sequence (no, name) values (p_no, in_name);
        end if;
    else
        set p_no = '0000';
    end if;

    set p_result = concat(p_no, lpad(p_last_id, 12, 0));

    return p_result;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- 表的结构 `forum_node`
--

CREATE TABLE IF NOT EXISTS `forum_node` (
  `id` varchar(16) NOT NULL,
  `no` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `created` int(10) unsigned NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `forum_node`
--

INSERT INTO `forum_node` (`id`, `no`, `name`, `created`) VALUES
('4', 'shenzhen', '深圳', 1429367059),
('3', 'yueyang', '岳阳', 1429367030);

-- --------------------------------------------------------

--
-- 表的结构 `forum_topic`
--

CREATE TABLE IF NOT EXISTS `forum_topic` (
  `id` varchar(16) NOT NULL,
  `parent_id` varchar(16) DEFAULT '0',
  `node_id` varchar(16) DEFAULT '',
  `user_id` varchar(16) DEFAULT '',
  `type` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '1主题,2附言,3回帖',
  `title` varchar(1024) NOT NULL,
  `content` text NOT NULL,
  `created` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `note_content`
--

CREATE TABLE IF NOT EXISTS `note_content` (
  `id` varchar(16) NOT NULL DEFAULT '',
  `parent_id` varchar(16) DEFAULT '',
  `user_id` int(10) unsigned DEFAULT '0',
  `type` int(1) unsigned DEFAULT '0' COMMENT '0记事;1文件夹',
  `title` varchar(512) DEFAULT '',
  `content` text,
  `size` int(10) unsigned DEFAULT '0' COMMENT '内容大小',
  `status` int(1) unsigned DEFAULT '0' COMMENT '0私密;1公开',
  `syntax` int(1) unsigned DEFAULT '0',
  `modifyed` int(10) unsigned DEFAULT '0' COMMENT '修改次数',
  `updated` int(10) unsigned DEFAULT '0' COMMENT '最后修改时间',
  `created` int(10) unsigned DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `note_content`
--

INSERT INTO `note_content` (`id`, `parent_id`, `user_id`, `type`, `title`, `content`, `size`, `status`, `syntax`, `modifyed`, `updated`, `created`) VALUES
('0003000000000035', '0', 15, 1, 'a', 'zxc', 0, 0, 0, 0, 0, 1451032450),
('0003000000000037', '0003000000000035', 15, 0, '20151225164345', 'asd', 3, 0, 0, 0, 1451033025, 1451033025);

-- --------------------------------------------------------

--
-- 表的结构 `sys_group`
--

CREATE TABLE IF NOT EXISTS `sys_group` (
  `id` varchar(16) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT '组名称',
  `type` enum('admin','user') NOT NULL COMMENT '组类型',
  `manage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '管理组',
  `min_credits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最小积分',
  `max_credits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大积分',
  `read_access` tinyint(3) unsigned NOT NULL DEFAULT '10' COMMENT '阅读权限',
  `allow_message` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '发送短消息',
  `allow_visit` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '访问权限',
  `allow_manage` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '登陆后台'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `sys_group`
--

INSERT INTO `sys_group` (`id`, `name`, `type`, `manage`, `min_credits`, `max_credits`, `read_access`, `allow_message`, `allow_visit`, `allow_manage`) VALUES
('1', '管理员', 'admin', 1, 0, 0, 10, 1, 3, 1),
('2', '注册会员', 'user', 0, 0, 0, 10, 0, 1, 0);

-- --------------------------------------------------------

--
-- 表的结构 `sys_invite`
--

CREATE TABLE IF NOT EXISTS `sys_invite` (
  `id` int(10) unsigned NOT NULL,
  `no` varchar(16) DEFAULT NULL,
  `is_valid` int(1) unsigned DEFAULT '0' COMMENT '0有效;1无效',
  `created_user_id` int(10) unsigned DEFAULT NULL,
  `register_user_id` int(10) unsigned DEFAULT NULL,
  `created` int(10) unsigned DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

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
  `user_id` varchar(16) NOT NULL DEFAULT '0'
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
('site_invite', '0', 'option', '0'),
('site_reg_captcha', '0', 'option', '0'),
('site_login_captcha', '0', 'option', '0');

-- --------------------------------------------------------

--
-- 表的结构 `sys_sequence`
--

CREATE TABLE IF NOT EXISTS `sys_sequence` (
  `id` int(11) unsigned NOT NULL,
  `no` varchar(4) NOT NULL DEFAULT '',
  `name` varchar(64) NOT NULL DEFAULT ''
) ENGINE=MyISAM AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `sys_sequence`
--

INSERT INTO `sys_sequence` (`id`, `no`, `name`) VALUES
(22, '0003', 'note_content'),
(20, '0002', 'sys_invite'),
(16, '0001', 'sys_user');

-- --------------------------------------------------------

--
-- 表的结构 `sys_user`
--

CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` varchar(16) NOT NULL,
  `group_id` varchar(16) DEFAULT '',
  `email` varchar(256) DEFAULT '' COMMENT '电子邮件',
  `nickname` varchar(64) DEFAULT '' COMMENT '昵称',
  `password` varchar(32) DEFAULT '' COMMENT '密码（双重MD5）',
  `credits` int(10) unsigned DEFAULT '0' COMMENT '积分',
  `created` int(10) unsigned DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户表';

--
-- 转存表中的数据 `sys_user`
--

INSERT INTO `sys_user` (`id`, `group_id`, `email`, `nickname`, `password`, `credits`, `created`) VALUES
('0001000000000040', '0', '123123@asdas.com', '123', '63ee451939ed580ef3c4b6f0109d1fd0', 0, 1451033348),
('0001000000000041', '1', 'icesword28@qq.com', 'binjoo', '14e1b600b1fd579f47433b88e8d85291', 0, 1451033840);

-- --------------------------------------------------------

--
-- 表的结构 `sys_user_reset`
--

CREATE TABLE IF NOT EXISTS `sys_user_reset` (
  `id` varchar(16) NOT NULL,
  `user_id` varchar(16) DEFAULT '',
  `token` varchar(32) DEFAULT NULL COMMENT '重置令牌',
  `valid_time` int(10) unsigned DEFAULT NULL COMMENT '有效期限',
  `create_ip` varchar(32) DEFAULT NULL COMMENT '创建者IP',
  `reset_ip` varchar(32) DEFAULT NULL COMMENT '使用者IP',
  `status` int(1) DEFAULT '0' COMMENT '0未使用;1已使用'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ya_bookmark`
--

CREATE TABLE IF NOT EXISTS `ya_bookmark` (
  `id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `title` varchar(512) DEFAULT NULL COMMENT '书签标题',
  `long_url` varchar(1024) DEFAULT NULL COMMENT '原网址',
  `short_url` varchar(16) DEFAULT NULL COMMENT '短址',
  `description` varchar(1024) DEFAULT NULL,
  `full_text` text COMMENT '页面全文',
  `created` int(10) unsigned DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='书签表';

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
  `id` int(10) unsigned NOT NULL,
  `bookmark_id` int(10) unsigned NOT NULL,
  `meta_id` int(10) unsigned NOT NULL,
  `created` int(10) unsigned NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

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
  `id` int(10) NOT NULL,
  `title` varchar(128) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ya_meta`
--

CREATE TABLE IF NOT EXISTS `ya_meta` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(64) NOT NULL COMMENT '名称',
  `type` varchar(16) NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

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

--
-- Indexes for dumped tables
--

--
-- Indexes for table `forum_node`
--
ALTER TABLE `forum_node`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `forum_topic`
--
ALTER TABLE `forum_topic`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `note_content`
--
ALTER TABLE `note_content`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `sys_group`
--
ALTER TABLE `sys_group`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sys_invite`
--
ALTER TABLE `sys_invite`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sys_kv`
--
ALTER TABLE `sys_kv`
  ADD PRIMARY KEY (`name`,`user_id`,`type`);

--
-- Indexes for table `sys_sequence`
--
ALTER TABLE `sys_sequence`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `sys_user`
--
ALTER TABLE `sys_user`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `sys_user_reset`
--
ALTER TABLE `sys_user_reset`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ya_bookmark`
--
ALTER TABLE `ya_bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ya_bookmark_meta_rel`
--
ALTER TABLE `ya_bookmark_meta_rel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ya_message`
--
ALTER TABLE `ya_message`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ya_meta`
--
ALTER TABLE `ya_meta`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `forum_node`
--
ALTER TABLE `forum_node`
AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `forum_topic`
--
ALTER TABLE `forum_topic`
AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `note_content`
--
ALTER TABLE `note_content`
AUTO_INCREMENT=4294967296;
--
-- AUTO_INCREMENT for table `sys_group`
--
ALTER TABLE `sys_group`
AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `sys_invite`
--
ALTER TABLE `sys_invite`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `sys_sequence`
--
ALTER TABLE `sys_sequence`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=42;
--
-- AUTO_INCREMENT for table `sys_user`
--
ALTER TABLE `sys_user`
AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT for table `sys_user_reset`
--
ALTER TABLE `sys_user_reset`
AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `ya_bookmark`
--
ALTER TABLE `ya_bookmark`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `ya_bookmark_meta_rel`
--
ALTER TABLE `ya_bookmark_meta_rel`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `ya_message`
--
ALTER TABLE `ya_message`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ya_meta`
--
ALTER TABLE `ya_meta`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
