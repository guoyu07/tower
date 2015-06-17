<?php

$config['blocker_enable'] = false;

$config['memcached_server'] = '127.0.0.1';
$config['memcached_port'] = 11211;
$config['idc'] = 'IDC10';

/**
 * patterns are defined just like unix shell filename matching.
 * more info please reference fnmatch(3) of any linux or unix system.
 */
$config['invalid_ips'] = array(
    '192.168.1.59',
    // '192.168.201.*',
    // '192.168.233.*'
);

