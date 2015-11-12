--20151111
ALTER TABLE `note_content` ADD `size` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '文本大小' AFTER `content`;

--20151112
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