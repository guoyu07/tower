<?php
function get_client_ip() {
    $ip = false;
    if(!empty($_SERVER["HTTP_CLIENT_IP"])) {
        $ip = $_SERVER["HTTP_CLIENT_IP"];
    }

    if (!empty($_SERVER['HTTP_X_FORWARDED_FOR'])) {
        $ips = explode(", ", $_SERVER['HTTP_X_FORWARDED_FOR']);
        if ($ip) {
            array_unshift($ips, $ip);
            $ip = false;
        }
        for ($i = 0; $i < count($ips); $i++) {
            if (!eregi("^(10|172\.16|192\.168)\.", $ips[$i])) {
                $ip = $ips[$i];
                break;
            }
        }
    }

    if(!$ip){
        $ip = $_SERVER['REMOTE_ADDR'];
    }

    return $ip;
}

function normpath($path) {
    if (empty($path))
        return '.';

    if (strpos($path, '/') === 0)
        $initial_slashes = true;
    else
        $initial_slashes = false;
    if (
        ($initial_slashes) &&
        (strpos($path, '//') === 0) &&
        (strpos($path, '///') === false)
    )
    $initial_slashes = 2;
    $initial_slashes = (int) $initial_slashes;

    $comps = explode('/', $path);
    $new_comps = array();
    foreach ($comps as $comp)
    {
        if (in_array($comp, array('', '.')))
            continue;
        if (
            ($comp != '..') ||
            (!$initial_slashes && !$new_comps) ||
            ($new_comps && (end($new_comps) == '..'))
        )
        array_push($new_comps, $comp);
        elseif ($new_comps)
            array_pop($new_comps);
    }
    $comps = $new_comps;
    $path = implode('/', $comps);
    if ($initial_slashes)
        $path = str_repeat('/', $initial_slashes) . $path;
    if ($path)
        return $path;
    else
        return '.';
}

function load_config () {
    global $config;
    $file = normpath(dirname(__FILE__) . '/config.php');
    if (file_exists($file)) {
        include_once($file);
    }

    // load outer config file
    $file = normpath(dirname(__FILE__) . '/../../config.php');
    if (file_exists($file)) {
        include_once($file);
    }

    return $config;
}

function is_blocker_enable () {
    $config = load_config();
    if (isset($config['blocker_enable'])) {
        return $config['blocker_enable'];
    }
    return false;
}

function get_invalid_ips () {
    $config = load_config();
    if (isset($config['invalid_ips'])) {
        return $config['invalid_ips'];
    }
    return array();
}

/*
 * get memcached object
 *
 * according to the config file
 *
 */
function get_memcached () {
    $config = load_config();
    $ret = FALSE;
    if (isset($config['memcached_server']) &&
        isset($config['memcached_port'])) {
        $ret = new Memcached();
        $ret->addServer($config['memcached_server'],
            $config['memcached_port']);
    }
    return $ret;
}

function inc_general_seq ($mem) {
    $key = sprintf("soj_general_seq_%s", date('Ymd_H'));
    $ret = inc_mem_value($mem, $key);
    if ($ret !== FALSE) {
        $ret = get_idc() . "." . date("H") . "." . $ret;
    }
    return $ret;
}

function inc_mem_value ($mem, $key) {
    $ret = $mem->increment($key);
    if ($ret === FALSE) {
        if ($mem->getResultCode() === Memcached::RES_NOTFOUND) {
            $mem->set($key, 0, 172800);
            $ret = $mem->increment($key);
        }
        else {
            syslog(LOG_ERR, "increment failded! resultCode: " .
                $mem->getResultCode());
        }
    }
    return $ret;
}

function get_idc () {
    $idc = "IDC10";
    $config = load_config();
    if (isset($config['idc'])) {
        $idc = $config['idc'];
    }
    return $idc;
}
